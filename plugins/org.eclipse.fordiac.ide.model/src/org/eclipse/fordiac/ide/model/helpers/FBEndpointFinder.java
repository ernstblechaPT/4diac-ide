/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Philipp Bauer - initial implementation, including developing the algorithm; and initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class FBEndpointFinder {

	protected FBEndpointFinder() {
	}
	
	/**
	 * data class for recursively called methods
	 */
	private static class RecursionState {
		public final Deque<String> plexStack;
		public final boolean inputSide;
		public final IInterfaceElement ifElem;
		public final Set<IInterfaceElement> connections;

		/**
		 * @param plexStack a stack of visited (De)Multiplexer-Interfaces (muxers interfaces get pushed onto the stack demuxers pop that interface from the stack)
		 * @param ifElem the {@link IInterfaceElement} to traverse to the next destination (until the destination is a "regular" FB)
		 * @param inputSide true if skipping should be done through inputs (find left side FBs of the supplied connection), false otherwise
		 * @param connections a set of destination interfaces from the initial starting point to that destinations
		 */
		public RecursionState(final Deque<String> plexStack, final boolean inputSide, final IInterfaceElement ifElem, final Set<IInterfaceElement> connections) {
			this.plexStack = plexStack;
			this.inputSide = inputSide;
			this.ifElem = ifElem;
			this.connections = connections;
		}
	}

	/**
	 * find end-points (FBs on the output-side of the supplied FBs connections)
	 *
	 * @param from the FB to get the destinations from
	 * @return a Set of end-point FBs
	 */
	public static Set<FBNetworkElement> findDestinations(final FBNetworkElement from) {
		return findConnectedElements(from, false, true, true).keySet();
	}

	/**
	 * find source points (FBs on the input-side of the supplied FBs connections)
	 *
	 * @param from the FB to get the sources from
	 * @return a Set of end-point FBs
	 */
	public static Set<FBNetworkElement> findSources(final FBNetworkElement from) {
		return findConnectedElements(from, true, false, true).keySet();
	}

	/**
	 * find both source and destination end-points
	 *
	 * @param from the FB to get the sources from
	 * @return a Set of end-point FBs
	 */
	public static Set<FBNetworkElement> findAllConnectedElements(final FBNetworkElement from) {
		return findConnectedElements(from, true, true, true).keySet();
	}

	/**
	 * find the interfaces of the connected atomic FBs which are the destinations for the supplied interface
	 * (De)Multiplexer and SubApps between atomic FBs are ignored
	 * if the supplied interface is an input the sources are traced
	 * else the destinations are traced
	 *
	 * @param ifElem the interface to find the destinations/sources from
	 * @return a Set of end-point interfaces/pins
	 */
	public static Set<IInterfaceElement> findConnectedInterfaceElements(final IInterfaceElement ifElem){
		final Set<IInterfaceElement> connectedInt = new HashSet<>();
		for (final Connection con: (ifElem.isIsInput()) ? ifElem.getInputConnections() : ifElem.getOutputConnections()) {
			trace(new RecursionState(new ArrayDeque<>(), ifElem.isIsInput(), ifElem.isIsInput() ? con.getSource() : con.getDestination(), connectedInt));
		}
		return connectedInt;
	}

	/**
	 * find all connected interfaces for the supplied FB
	 * (only checks the output side so far, input side for this function was not needed)
	 *
	 * @param fb the FB to find other interfaces to this FBs interfaces
	 * @return a Map of the source interfaces and a set of their destination interfaces
	 */
	public static Map<IInterfaceElement, Set<IInterfaceElement>> findConnectedInterfaces(final FBNetworkElement fb) {
		final Map<IInterfaceElement, Set<IInterfaceElement>> results = new HashMap<>();

		final List<IInterfaceElement> outInt = new ArrayList<>();
		outInt.addAll(fb.getInterface().getEventOutputs());
		outInt.addAll(fb.getInterface().getOutputVars());
		outInt.addAll(fb.getInterface().getPlugs());
		flattenConnections(outInt, false)
		.forEach(con -> results.put(con.getSource(), findConnectedInterfaceElements(con.getSource())));

		return results;
	}

	/**
	 * find all possible connected FBs, from one given FB. SubApps, Multiplexer and Demultiplexer between 2 FBs are ignored.
	 *
	 * @param fb the FB to search for connected FBs
	 * @param includeInputs true if destinations to the input side of the FB should be included
	 * @param includeOutputs true if destinations to the output side of the FB should be included
	 * @param traverseParent if true the search will extend to higher levels of the hierarchy,
	 * otherwise the parent of the supplied FB will be the top limit, it will still search lower parts of the hierarchy
	 * @return a Set of unique FB as destinations including the number of connections to that FB from the given start point
	 */
	public static Map<FBNetworkElement, Integer> findConnectedElements(
			final FBNetworkElement fb,
			final boolean includeInputs,
			final boolean includeOutputs,
			final boolean traverseParent) {

		final Map<FBNetworkElement, Integer> result = new HashMap<>();
		final boolean tp = traverseParent || fb.getOuterFBNetworkElement() == null;

		final List<IInterfaceElement> ifs = new ArrayList<>();
		if (includeInputs) {
			ifs.addAll(fb.getInterface().getEventInputs());
			ifs.addAll(fb.getInterface().getInputVars());
			ifs.addAll(fb.getInterface().getSockets());
		}

		//include destinations to the output side of the given FB
		if (includeOutputs) {
			ifs.addAll(fb.getInterface().getEventOutputs());
			ifs.addAll(fb.getInterface().getOutputVars());
			ifs.addAll(fb.getInterface().getPlugs());
		}

		final Set<IInterfaceElement> connectedIfs = new HashSet<>();
		ifs.stream().forEach(ifElem -> (ifElem.isIsInput() ? ifElem.getInputConnections() : ifElem.getOutputConnections())
				.forEach(con -> trace(
						new RecursionState(new ArrayDeque<>(), ifElem.isIsInput(), ifElem.isIsInput() ? con.getSource() : con.getDestination(), connectedIfs))));
		connectedIfs.stream().map(IInterfaceElement::getFBNetworkElement)
		.forEach(destFB -> result.put(destFB, result.containsKey(destFB) ? result.get(destFB)+1 : 1));

		if (!tp) {
			final List<FBNetworkElement> parents = new ArrayList<>();
			FBNetworkElement parent = fb.getOuterFBNetworkElement();
			while (parent != null) {
				parents.add(parent);
				parent = parent.getOuterFBNetworkElement();
			}
			result.keySet().removeIf(k -> parents.contains(k.getOuterFBNetworkElement()));
		}

		return result;
	}

	/**
	 * helper method to avoid duplicated code in {@link #findConnectedElements(FBNetworkElement, boolean, boolean)}
	 *
	 * @param fbInt Interface list
	 * @param inputs true if interface is a list of inputs
	 * @return Stream of {@link Connection}
	 */
	private static Stream<Connection> flattenConnections(final List<? extends IInterfaceElement> fbInt, final boolean inputs) {
		return fbInt.stream().flatMap(e -> inputs ? e.getInputConnections().stream() : e.getOutputConnections().stream());
	}

	/**
	 * skips all (De)Multiplexers (and SubApps) between FBs
	 *
	 * @param state the current state of the recursion
	 */
	private static void trace(final RecursionState state) {
		if (state.ifElem == null) {
			return;
		}
		final FBNetworkElement fb = state.ifElem.getFBNetworkElement();
		if (fb == null) {
			return;
		}

		//fb is a SubApp traverse down over the interface connections of the subapp
		if (fb instanceof SubApp) {
			traceSubApp(state);
		}
		//fb is mux/demux
		else if(fb instanceof Multiplexer || fb instanceof Demultiplexer) {
			//from left into mux or from right into demux
			if ((state.inputSide && fb instanceof Demultiplexer) || (!state.inputSide && fb instanceof Multiplexer)) {
				traceTrunk(state);
			}
			//from right into mux or from left into demux
			else {
				traceFan(state);
			}
		}
		//no SubApp or plexer -> add to found FBs
		else {
			state.connections.add(state.ifElem);
		}
	}

	/**
	 * trace connections through subapp
	 *
	 * @param state the current state of the recursion
	 */
	private static void traceSubApp(final RecursionState state) {
		final EList<Connection> subCons = state.inputSide ? state.ifElem.getInputConnections() : state.ifElem.getOutputConnections();
		for (final Connection subInt: subCons) {
			trace(new RecursionState(state.plexStack, state.inputSide, state.inputSide ? subInt.getSource() : subInt.getDestination(), state.connections));
		}
	}

	/**
	 * trace connections of the given interface from left into mux or from right into demux
	 * in other words trace the trunk connection (the side with only one connections) of either a mux or demux
	 *
	 * @param state the current state of the recursion
	 */
	private static void traceTrunk(final RecursionState state) {
		final FBNetworkElement fb = state.ifElem.getFBNetworkElement();
		//get single in/output
		final EList<VarDeclaration> vars = (state.inputSide) ? fb.getInterface().getInputVars() : fb.getInterface().getOutputVars();
		if (vars.isEmpty()) {
			return;
		}
		final EList<Connection> varCons = (state.inputSide) ? vars.get(0).getInputConnections() : vars.get(0).getOutputConnections();
		if (varCons.isEmpty()) {
			return;
		}

		//push interface onto the stack
		state.plexStack.push(state.ifElem.getName());
		//next item to skip through the only in/output of the plexer
		trace(new RecursionState(state.plexStack, state.inputSide, state.inputSide ? varCons.get(0).getSource() : varCons.get(0).getDestination(), state.connections));
	}

	/**
	 * trace connection of the given interface from right into mux or from left into demux
	 * in other words trace all the connections of the fan side (side where more the multiple connections are) of either the mux or demux
	 *
	 * @param state the current state of the recursion
	 */
	private static void traceFan(final RecursionState state) {
		final FBNetworkElement fb = state.ifElem.getFBNetworkElement();
		
		//trace find right interface from the interface stack
		for (final IInterfaceElement structMem:
			((StructManipulator)fb).getStructType().getMemberVariables().stream()
			.filter(mem -> fb.getInterfaceElement(mem.getName()) != null)
			.collect(Collectors.toList()))
		{
			final IInterfaceElement realInt = fb.getInterfaceElement(structMem.getName());
			
			Deque<String> subStack;
			/* case stack empty: occurs when a FB has a struct as output data type which is connected to a demux first
			 * all of the inputs therefore have to be traversed */
			if (state.plexStack.isEmpty()) {
				subStack = new ArrayDeque<>();
			}
			/* case stack is not empty: first destination plexer was a mux
			 * -> go to last added interface from the stack */
			else if (structMem.getName().equals(state.plexStack.peek())) {
				subStack = ((ArrayDeque<String>) state.plexStack).clone();
				subStack.pop();
			}
			else {
				continue;
			}

			//find destinations (skip plexers between) from the interface of the plexer according to the previously updated interface-stack
			for (final Connection next: (state.inputSide) ? realInt.getInputConnections() : realInt.getOutputConnections()) {
				trace(new RecursionState(subStack, state.inputSide, state.inputSide ? next.getSource() : next.getDestination(), state.connections));
			}
		}
	}
}
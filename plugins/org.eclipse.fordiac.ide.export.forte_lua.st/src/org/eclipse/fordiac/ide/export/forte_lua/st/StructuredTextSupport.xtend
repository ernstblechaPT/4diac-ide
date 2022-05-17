/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst, Patrick Aigner
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Patrick Aigner - adapted for Lua Code generation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua.st

import java.math.BigDecimal
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.List
import java.util.Set
import java.util.Stack
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.model.data.AnyDurationType
import org.eclipse.fordiac.ide.model.data.AnyElementaryType
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.AnyRealType
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DateAndTimeType
import org.eclipse.fordiac.ide.model.data.DateType
import org.eclipse.fordiac.ide.model.data.LdateType
import org.eclipse.fordiac.ide.model.data.LdtType
import org.eclipse.fordiac.ide.model.data.LtimeType
import org.eclipse.fordiac.ide.model.data.LtodType
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.data.TimeOfDayType
import org.eclipse.fordiac.ide.model.data.TimeType
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.value.ValueConverterFactory
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNop
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.util.STFunctionUtil.*
import static extension org.eclipse.xtext.util.Strings.convertToJavaString
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction

abstract class StructuredTextSupport implements ILanguageSupport {
	@Accessors final List<String> errors = newArrayList
	int uniqueVariableIndex = 0;
	int loopIndex = 0;
	Stack<String> loopStack = new Stack<String>();

	override getInfos() { emptyList }

	override getWarnings() { emptyList }

	def protected CharSequence generateLocalVariables(Iterable<? extends STVarDeclarationBlock> blocks,
		boolean temp) '''
		«FOR block : blocks»
			«block.generateLocalVariableBlock(temp)»
		«ENDFOR»
	'''

	def protected CharSequence generateLocalVariableBlock(STVarDeclarationBlock block, boolean temp) '''
		«FOR variable : block.varDeclarations.filter(STVarDeclaration)»
			«variable.generateLocalVariable(temp, block.constant)»
		«ENDFOR»
	'''

	def protected CharSequence generateLocalVariable(STVarDeclaration variable, boolean temp, boolean const) {
		'''«IF temp»local «ENDIF»«variable.generateFeatureName»«IF variable.defaultValue !== null» = «variable.defaultValue.generateInitializerExpression»«ENDIF»'''
	}

	def protected dispatch CharSequence generateInitializerExpression(STElementaryInitializerExpression expr) {
		expr.value.generateExpression
	}

	def protected dispatch CharSequence generateInitializerExpression(STArrayInitializerExpression expr) //
	'''{«FOR elem : expr.values SEPARATOR ", "»«elem.generateArrayInitElement»«ENDFOR»}'''

	def protected CharSequence generateArrayInitElement(STArrayInitElement elem) //
	'''«IF elem.initExpressions.empty»«elem.indexOrInitExpression.generateInitializerExpression»«ELSE»«elem.generateMultiArrayInitElement»«ENDIF»'''

	def protected CharSequence generateMultiArrayInitElement(STArrayInitElement elem) //
	'''«FOR i : 0..<(elem.indexOrInitExpression as STElementaryInitializerExpression).value.integerFromConstantExpression SEPARATOR ", "»«FOR initExpression : elem.initExpressions SEPARATOR ", "»«initExpression.generateInitializerExpression»«ENDFOR»«ENDFOR»'''

	def protected CharSequence generateStatementList(List<STStatement> statements) '''
		«FOR statement : statements»
			«statement.generateStatement»
		«ENDFOR»
	'''

	def protected dispatch CharSequence generateStatement(STStatement stmt) {
		errors.add('''The statement «stmt.eClass.name» is not supported''')
		'''#error "The statement «stmt.eClass.name» is not supported"'''
	}

	def protected dispatch CharSequence generateStatement(STNop stmt) {
		"" // nop
	}

	def protected dispatch CharSequence generateStatement(STAssignmentStatement stmt) //
	'''«stmt.left.generateExpression» = «stmt.right.generateExpression»'''

	def protected dispatch CharSequence generateStatement(STIfStatement stmt) '''
		if «stmt.condition.generateExpression» then
		  «stmt.statements.generateStatementList»
		«FOR elseif : stmt.elseifs»
			elsif «elseif.condition.generateExpression» then
			  «elseif.statements.generateStatementList»
		«ENDFOR»
		«IF stmt.^else !== null»
			else
			  «stmt.^else.statements.generateStatementList»
		«ENDIF»
		end
	'''

	def protected dispatch generateStatement(STCaseStatement stmt) '''
		«var selector = stmt.selector.generateExpression»
		if «FOR clause : stmt.cases SEPARATOR ' elsif '»«clause.generateCaseClause(selector)»«ENDFOR»
		«IF stmt.^else !== null»
			else
			  «stmt.^else.statements.generateStatementList»
		«ENDIF»
	'''

	def protected generateCaseClause(STCaseCases clause, CharSequence selector) '''
		«FOR value : clause.conditions SEPARATOR ' or '»«selector» == «value.generateExpression»«ENDFOR» then
		  «clause.statements.generateStatementList»
	'''

	def protected dispatch generateStatement(STForStatement stmt) {
		loopStack.push("loop_" + loopIndex);
		loopIndex++;
		'''
			for «stmt.variable.generateFeatureName» = «stmt.from.generateExpression», «stmt.to.generateExpression», «IF stmt.by !== null»«stmt.by.generateExpression»«ELSE»1«ENDIF» do
			  «stmt.statements.generateStatementList»
			  ::«loopStack.pop()»::
			end
		'''
	}

	def protected dispatch generateStatement(STWhileStatement stmt) {
		loopStack.push("loop_" + loopIndex);
		loopIndex++;
		'''
			while «stmt.condition.generateExpression» do
			  «stmt.statements.generateStatementList»
			  ::«loopStack.pop()»::
			end
		'''
	}

	def protected dispatch generateStatement(STRepeatStatement stmt) {
		loopStack.push("loop_" + loopIndex);
		loopIndex++;
		'''
			repeat
			  «stmt.statements.generateStatementList»
			  ::«loopStack.pop()»::
			until «stmt.condition.generateExpression»
		'''
	}

	def protected dispatch CharSequence generateStatement(STContinue stmt) '''goto «loopStack.peek»''' // Lua 5.2 or LuaJit 2.0.1

	def protected dispatch CharSequence generateStatement(STReturn stmt) '''return'''

	def protected dispatch CharSequence generateStatement(STExit stmt) '''break'''

	def protected dispatch CharSequence generateStatement(STCallStatement stmt) '''«stmt.call.generateExpression»'''

	def protected dispatch CharSequence generateExpression(STExpression expr) {
		errors.add('''The expression «expr.eClass.name» is not supported''')
		""
	}

	def protected dispatch CharSequence generateExpression(STBinaryExpression expr) {
		switch (expr.op) {
			// case RANGE: '''«expr.left.generateExpression», «expr.right.generateExpression»'''
			case OR: '''(«expr.left.generateExpression» | «expr.right.generateExpression»)'''
			case XOR: '''(«expr.left.generateExpression» ~ «expr.right.generateExpression»)'''
			case AND: '''(«expr.left.generateExpression» & «expr.right.generateExpression»)'''
			case AMPERSAND: '''(«expr.left.generateExpression» & «expr.right.generateExpression»)'''
			case EQ: '''(«expr.left.generateExpression» == «expr.right.generateExpression»)'''
			case NE: '''(«expr.left.generateExpression» ~= «expr.right.generateExpression»)'''
			case LT: '''(«expr.left.generateExpression» < «expr.right.generateExpression»)'''
			case LE: '''(«expr.left.generateExpression» <= «expr.right.generateExpression»)'''
			case GT: '''(«expr.left.generateExpression» > «expr.right.generateExpression»)'''
			case GE: '''(«expr.left.generateExpression» >= «expr.right.generateExpression»)'''
			case ADD:
				if (expr.left.resultType instanceof AnyNumType) {
					'''(«expr.left.generateExpression» + «expr.right.generateExpression»)'''
				} else if (expr.left.resultType instanceof AnyDurationType) {
					'''''' // TODO: Time type handling
				} else {
					errors.add('''The ADD operation for «expr.left.resultType.name» is not yet supported''')
					''''''
				}
			case SUB:
				if (expr.left.resultType instanceof AnyNumType) {
					'''(«expr.left.generateExpression» - «expr.right.generateExpression»)'''
				} else if (expr.left.resultType instanceof AnyDurationType) {
					'''''' // TODO: Time type handling
				} else {
					errors.add('''The SUB operation for «expr.left.resultType.name» is not yet supported''')
					''''''
				}
			case MUL: '''(«expr.left.generateExpression» * «expr.right.generateExpression»)'''
			case DIV: '''(«expr.left.generateExpression» «IF expr.left.resultType instanceof AnyRealType || expr.right.resultType instanceof AnyRealType»/«ELSE»//«ENDIF» «expr.right.generateExpression»)'''
			case MOD: '''(«expr.left.generateExpression» % «expr.right.generateExpression»)'''
			case POWER: '''(«expr.left.generateExpression»^«expr.right.generateExpression»)'''
			default: {
				errors.add('''The operation «expr.op.getName» is not supported''')
				''''''
			}
		}
	}

	def protected dispatch CharSequence generateExpression(STUnaryExpression expr) {
		switch (expr.op) {
			case MINUS: '''-«expr.expression.generateExpression»'''
			case PLUS: '''«expr.expression.generateExpression»'''
			case NOT: '''~«expr.expression.generateExpression»'''
			default: {
				errors.add('''The operation «expr.op.getName» is not supported''')
				''''''
			}
		}
	}

	def protected dispatch CharSequence generateExpression(STMemberAccessExpression expr) //
	'''«expr.receiver.generateExpression».«expr.member.generateExpression»'''

	def protected dispatch CharSequence generateExpression(STArrayAccessExpression expr) //
	'''«expr.receiver.generateExpression»«FOR index : expr.index»[«index.generateExpression»]«ENDFOR»'''

	def protected dispatch CharSequence generateExpression(STFeatureExpression expr) // TODO: function call
	'''«expr.feature.generateFeatureName»«IF expr.call»(«FOR arg : expr.generateCallArguments SEPARATOR ", "»«arg»«ENDFOR»)«ENDIF»'''

	def protected Iterable<CharSequence> generateCallArguments(STFeatureExpression expr) {
		try {
			expr.mappedInputArguments.entrySet.map[key.generateInputCallArgument(value)] +
				expr.mappedInOutArguments.entrySet.map[key.generateInOutCallArgument(value)] +
				expr.mappedOutputArguments.entrySet.map[key.generateOutputCallArgument(value)]
		} catch (IndexOutOfBoundsException e) {
			errors.add('''Not enough arguments for «expr.feature.name»''')
			emptyList
		} catch (ClassCastException e) {
			errors.add('''Mixing named and unnamed arguments is not allowed''')
			emptyList
		}
	}

	def protected CharSequence generateInputCallArgument(INamedElement parameter, STExpression argument) {
		if(argument === null) parameter.generateVariableDefaultValue else argument.generateExpression
	}

	def protected CharSequence generateInOutCallArgument(INamedElement parameter, STExpression argument) {
		if (argument === null)
			'''ST_IGNORE_OUT_PARAM(«parameter.generateVariableDefaultValue»)'''
		else
			argument.generateExpression
	}

	def protected CharSequence generateOutputCallArgument(INamedElement parameter, STExpression argument) {
		if (argument === null)
			'''ST_IGNORE_OUT_PARAM(«parameter.generateVariableDefaultValue»)'''
		else
			argument.generateExpression
	}

	def protected dispatch CharSequence generateExpression(STMultibitPartialExpression expr) //
	'''partial<«expr.specifier.generateMultiBitAccessSpecifier»>(«IF expr.expression !== null»«expr.expression.generateExpression»«ELSE»«expr.index»«ENDIF»)'''

	def protected CharSequence generateMultiBitAccessSpecifier(STMultiBitAccessSpecifier spec) {
		switch (spec) {
			case null,
			case X: "CIEC_BOOL"
			case B: "CIEC_BYTE"
			case W: "CIEC_WORD"
			case D: "CIEC_DWORD"
			case L: "CIEC_LWORD"
		}
	}

	def protected dispatch CharSequence generateExpression(STNumericLiteral expr) //
	'''«expr.value»'''

	def protected dispatch CharSequence generateExpression(STStringLiteral expr) //
	'''"«expr.value.toString.convertToJavaString»"'''

	def protected dispatch CharSequence generateExpression(STDateLiteral expr) //
	'''CIEC_DATE(«expr.value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000000000L»)'''

	def protected dispatch CharSequence generateExpression(STTimeLiteral expr) //
	'''CIEC_TIME(«expr.value.toNanos»)'''

	def protected dispatch CharSequence generateExpression(STTimeOfDayLiteral expr) //
	'''CIEC_TIME_OF_DAY(«expr.value.toNanoOfDay»)'''

	def protected dispatch CharSequence generateExpression(STDateAndTimeLiteral expr) //
	'''CIEC_DATE_AND_TIME(«LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).until(expr.value, ChronoUnit.NANOS)»)'''

	def protected dispatch CharSequence generateTemplateExpression(STBinaryExpression expr) {
		switch (expr.op) {
			case RANGE: '''«expr.left.generateTemplateExpression», «expr.right.generateTemplateExpression»'''
			case AMPERSAND: '''AND(«expr.left.generateTemplateExpression», «expr.right.generateTemplateExpression»)'''
			default: '''«expr.op.getName»(«expr.left.generateTemplateExpression», «expr.right.generateTemplateExpression»)'''
		}
	}

	def protected dispatch CharSequence generateTemplateExpression(STUnaryExpression expr) //
	'''«expr.op.getName»(«expr.expression.generateTemplateExpression»)'''

	def protected dispatch CharSequence generateTemplateExpression(STNumericLiteral expr) { expr.value.toString }

	def protected dispatch CharSequence generateVariableDefaultValue(INamedElement feature) {
		errors.add('''The variable «feature.eClass.name» is not supported''')
		"0"
	}

	def protected dispatch CharSequence generateVariableDefaultValue(VarDeclaration variable) {
		generateVariableDefaultValue(variable)
	}

	def protected dispatch CharSequence generateVariableDefaultValue(STVarDeclaration variable) {
		if (variable.defaultValue !== null)
			variable.defaultValue.generateInitializerExpression
		else if (variable.array)
			"{}"
		else
			(variable.type as DataType).generateTypeDefaultValue
	}

	def protected dispatch CharSequence generateFeatureName(INamedElement feature) {
		errors.add('''The feature «feature.eClass.name» is not supported''')
		""
	}

	def protected dispatch CharSequence generateFeatureName(VarDeclaration feature) //
	'''«IF feature.rootContainer instanceof BaseFBType»st_«ENDIF»«feature.name»()'''

	def protected dispatch CharSequence generateFeatureName(STVarDeclaration feature) '''st_lv_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(STFunction feature) '''func_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(STStandardFunction feature) '''func_«feature.name»''' // TODO: map standard functions

	def protected dispatch CharSequence generateFeatureName(STMethod feature) '''method_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(FB feature) '''fb_«feature.name»()'''

	def protected dispatch CharSequence generateFeatureName(Event feature) '''evt_«feature.name»'''

	def protected dispatch INamedElement getType(INamedElement feature) {
		errors.add('''The feature «feature.eClass.name» is not supported''')
		null
	}

	def protected dispatch INamedElement getType(VarDeclaration feature) { feature.type }

	def protected dispatch INamedElement getType(STVarDeclaration feature) { feature.type }

	def protected int getIntegerFromConstantExpression(STExpression expr) {
		try {
			((expr as STNumericLiteral).value as BigDecimal).intValueExact
		} catch (Exception e) {
			errors.add("Not a constant integer expression")
			1
		}
	}

	def protected Set<INamedElement> getContainedDependencies(EObject object) {
		object.<EObject>getAllProperContents(true).toIterable.flatMap[dependencies].toSet
	}

	def protected Iterable<INamedElement> getDependencies(EObject object) {
		switch (object) {
			STVarDeclaration:
				#[object.type]
			STNumericLiteral:
				#[object.resultType]
			STStringLiteral:
				#[object.resultType]
			STDateLiteral:
				#[object.type]
			STTimeLiteral:
				#[object.type]
			STTimeOfDayLiteral:
				#[object.type]
			STDateAndTimeLiteral:
				#[object.type]
			STFeatureExpression:
				object.feature.dependencies
			STFunction:
				#[LibraryElementFactory.eINSTANCE.createLibraryElement => [
					name = object.sourceName
				]]
			default:
				emptySet
		}
	}

	def protected generateUniqueVariableName() '''st_lv_synthetic_«uniqueVariableIndex++»'''

	def static CharSequence generateVarDefaultValue(VarDeclaration decl) {
		if (decl.value?.value.nullOrEmpty) {
			decl.type.generateTypeDefaultValue
		} else {
			val converter = ValueConverterFactory.createValueConverter(decl.type)
			if (converter !== null) {
				val value = converter.toValue(decl.value.value)
				'''«decl.type.generateTypeName»(«switch (value) {
				String: '''"«value.convertToJavaString»"'''
				Duration: Long.toString(value.toNanos)
				LocalTime: Long.toString(value.toNanoOfDay)
				LocalDate: Long.toString(value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000000000L)
				LocalDateTime: Long.toString(LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).until(value, ChronoUnit.NANOS))
				default: value
			}»)'''
			} else
				throw new UnsupportedOperationException("No value converter for type " + decl.type?.name)
		}
	}

	def static CharSequence generateTypeDefaultValue(DataType type) {
		switch (type) {
			AnyStringType: '''""'''
			AnyElementaryType: '''0'''
			ArrayType: '''()'''
			StructuredType: '''{}'''
			default: '''0'''
		}
	}

	def static CharSequence generateTypeName(DataType type) {
		switch (type) {
			TimeType,
			LtimeType: "CIEC_TIME"
			DateType,
			LdateType: "CIEC_DATE"
			TimeOfDayType,
			LtodType: "CIEC_TIME_OF_DAY"
			DateAndTimeType,
			LdtType: "CIEC_DATE_AND_TIME"
			ArrayType: "CIEC_ARRAY"
			default: '''CIEC_«type.name»'''
		}
	}
}
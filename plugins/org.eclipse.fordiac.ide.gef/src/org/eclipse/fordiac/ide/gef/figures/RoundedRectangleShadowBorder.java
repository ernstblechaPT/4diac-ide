/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

public class RoundedRectangleShadowBorder extends AbstractShadowBorder {

	@Override
	public void paintBackground(final IFigure figure, final Graphics graphics, final Insets insets) {
		graphics.pushState();
		graphics.setBackgroundColor(ColorConstants.black);

		final Rectangle shadowRect = figure.getBounds().getExpanded(2, 2);
		final Rectangle clipRect = shadowRect.getCopy();
		clipRect.width += SHADOW_SIZE;
		clipRect.height += SHADOW_SIZE;
		graphics.setClip(clipRect);

		drawShadowHalo(graphics, shadowRect);

		drawDropShadow(graphics, shadowRect);

		graphics.popState();
	}

	private static void drawShadowHalo(final Graphics graphics, final Rectangle shadowRect) {
		graphics.setAlpha(SHADOW_ALPHA);
		drawShadowFigure(graphics, shadowRect);
		shadowRect.shrink(1, 1);
		graphics.setAlpha(2 * SHADOW_ALPHA);
		drawShadowFigure(graphics, shadowRect);
	}

	private static void drawDropShadow(final Graphics graphics, final Rectangle shadowRect) {
		graphics.setAlpha(SHADOW_ALPHA);
		final double horInc = 0.7;  // emulate a roughly 30° shadow angle
		double horI = 0;
		for (int i = 0; i < SHADOW_SIZE; i++) {
			horI += horInc;
			shadowRect.translate((int) horI, 1);
			drawShadowFigure(graphics, shadowRect);
			if (horI >= 1.0) {
				horI -= 1.0;
			}
		}
	}

	private static void drawShadowFigure(final Graphics graphics, final Rectangle shadowRect) {
		graphics.fillRoundRectangle(shadowRect, SHADOW_CORNER_RADIUS, SHADOW_CORNER_RADIUS);
	}


}

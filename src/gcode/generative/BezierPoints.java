package gcode.generative;

import java.util.ArrayList;
import java.lang.Math;

import processing.core.PVector;

public class BezierPoints {
	public ArrayList<PVector> points;
	Gcoder gcoder;

	/**
	 * 
	 * @param pt1
	 * @param pt2
	 * @param pt3
	 * @param pt4
	 * @param _gcoder
	 */
	public BezierPoints(PVector pt1, PVector pt2, PVector pt3, PVector pt4, Gcoder _gcoder) {
		points = new ArrayList<PVector>();
		points.add(pt1);
		points.add(pt2);
		points.add(pt3);
		points.add(pt4);
		gcoder = _gcoder;
	}

	/**
	 * 
	 * @param P0
	 * @param P1
	 * @param P2
	 * @param P3
	 * @param t
	 * @return
	 */
	public PVector interpolateBezierPoint(PVector P0, PVector P1, PVector P2, PVector P3, double t) {
		double Px = (Math.pow(1 - t, 3) * P0.x + 3 * Math.pow(1 - t, 2) * t * P1.x
				+ 3 * (1 - t) * Math.pow(t, 2) * P2.x) + Math.pow(t, 3) * P3.x;
		double Py = (Math.pow(1 - t, 3) * P0.y + 3 * Math.pow(1 - t, 2) * t * P1.y
				+ 3 * (1 - t) * Math.pow(t, 2) * P2.y + Math.pow(t, 3) * P3.y);

		PVector result = new PVector((float) Px, (float) Py);
		return result;

	}

	/**
	 * 
	 * @param showControlPoints
	 */
	public void processingDrawBezier(boolean showControlPoints) {
		gcoder.myParent.noFill();
		gcoder.myParent.stroke(0, 255, 0);
		gcoder.myParent.pushMatrix();
		gcoder.myParent.translate(gcoder.offsetProcessingDrawingX + gcoder.canvasOriginX,
				gcoder.offsetProcessingDrawingY + gcoder.canvasOriginY);
		gcoder.myParent.beginShape();
		gcoder.myParent.vertex(points.get(0).x, points.get(0).y);
		gcoder.myParent.bezierVertex(points.get(1).x, points.get(1).y, points.get(2).x, points.get(2).y,
				points.get(3).x, points.get(3).y);
		gcoder.myParent.endShape();
		gcoder.myParent.stroke(255, 0, 0);
		gcoder.myParent.strokeWeight(5);
		gcoder.myParent.point(points.get(0).x, points.get(0).y);
		gcoder.myParent.point(points.get(3).x, points.get(3).y);

		if(showControlPoints) { // show the control points if the variable is set to true
		gcoder.myParent.strokeWeight(2);
		gcoder.myParent.stroke(0,0,255);
		gcoder.myParent.point(points.get(1).x, points.get(1).y);
		gcoder.myParent.point(points.get(2).x, points.get(2).y);
		gcoder.myParent.strokeWeight(1);
		gcoder.myParent.line(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
		gcoder.myParent.line(points.get(2).x, points.get(2).y, points.get(3).x, points.get(3).y);
		}

		gcoder.myParent.stroke(0,0,0);
		gcoder.myParent.popMatrix();

	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @param isFirstInstruction
	 */
	public void drawArcFrom3Points(PVector a, PVector b, PVector c, boolean isFirstInstruction) {
		drawArcFrom3Points(a, b, c, isFirstInstruction, false);
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @param isFirstInstruction
	 * @param isLastInstruction
	 */
	public void drawArcFrom3Points(PVector a, PVector b, PVector c, boolean isFirstInstruction,
			boolean isLastInstruction) {

		// The formulas are here to find the center of 3 points
		PVector mpAB = new PVector((float) 0.5 * (b.x + a.x), (float) 0.5 * (b.y + a.y));
		PVector mpBC = new PVector((float) 0.5 * (c.x + b.x), (float) 0.5 * (c.y + b.y));

		float mAB = (b.y - a.y) / (b.x - a.x);
		float mBC = (c.y - b.y) / (c.x - b.x);

		float centerX;
		float centerY;
		float mPerpAB = -1 / mAB;
		float mPerpBC = -1 / mBC;

		if (mAB == 0 && mBC == 0) {
			gcoder.drawLine(a.x, a.y, b.x, b.y);
			if (isLastInstruction) {
				gcoder.drawLine(b.x, b.y, c.x, c.y);
			}
			return;
		}

		if (mAB == 0) { // case if AB is an horizontal line
			centerX = mpAB.x;
			centerY = -mPerpBC * (centerX - mpBC.x) + mpBC.y;
		} else if (mBC == 0) { // case if BC is an horizontal line
			centerX = mpBC.x;
			centerY = mPerpAB * (centerX - mpAB.x) + mpAB.y;
		} else {
			if (mPerpAB - mPerpBC == 0) {
				// if this is 0 it the points are on the same line
				gcoder.drawLine(a.x, a.y, b.x, b.y);
				if (isLastInstruction) {
					gcoder.drawLine(b.x, b.y, c.x, c.y);
				}
				return;
			}

			centerX = (mpBC.y - mpAB.y + mPerpAB * mpAB.x - mPerpBC * mpBC.x) / (mPerpAB - mPerpBC);
			centerY = mPerpAB * (centerX - mpAB.x) + mpAB.y;
		}

		PVector centerPoint = new PVector(centerX, centerY);

		float radius = (float) Math.pow(Math.pow(a.x - centerX, 2) + Math.pow(a.y - centerY, 2), 0.5);

		PVector aa = new PVector(c.x - a.x, c.y - a.y);
		PVector bb = new PVector(b.x - a.x, b.y - a.y);

		float sensRotation = (c.x - a.x) * (b.y - a.y) - (c.y - a.y) * (b.x - a.x);

		gcoder.drawArc(centerPoint, a,b, sensRotation, isFirstInstruction);
		if(isLastInstruction) {
		gcoder.drawArc(centerPoint, b,c, sensRotation, isFirstInstruction);
			
		}

	}

}

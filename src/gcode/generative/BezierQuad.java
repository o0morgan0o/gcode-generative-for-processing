package gcode.generative;

import java.util.ArrayList;

import processing.core.PVector;

public class BezierQuad {
	Gcoder gcoder;
	ArrayList<BezierPoints> points;
	double resolution; // quality of interpolation of the bezier curve. must be between 0 and 1. 0.1 or
						// 0.01 are OK

	public BezierQuad(Gcoder _gcoder, PVector pt1, PVector pt2, PVector pt3, PVector pt4, double _resolution) {
		gcoder = _gcoder;
		points = new ArrayList<BezierPoints>();
		BezierPoints origin = new BezierPoints(pt1, pt2, pt3, pt4, gcoder);
		points.add(origin);
		resolution = _resolution;

	}
//	public BezierQuad(Gcoder _gcoder, PVector pt1, PVector pt2, PVector pt3, PVector pt4) {
//		this(_gcoder,pt1,pt2,pt3,pt4,.1);
//	}

	public void add(PVector pt1, PVector pt2, PVector pt3, PVector pt4) {
		BezierPoints newPoint = new BezierPoints(pt1, pt2, pt3, pt4, gcoder);
		points.add(newPoint);

	}

	public void draw() {
		BezierPoints bezierPoint;
		System.out.println("drawBezier");

//		gcoder.elevatePen();
//		gcoder.movePen
		boolean isFirstInstruction = true;
		for (int i = 0; i < points.size(); i++) {
			boolean isLastInstruction = false; // necessaire pour finir l'arc lorsqu'on fait le dernier point
			bezierPoint = points.get(i);
			bezierPoint.processingDrawBezier();
			ArrayList<PVector> currentControlPoints = bezierPoint.points;

//				//PVector currentPoint = bezierPoint.interpolateBezierPoint(currentControlPoints.get(0), currentControlPoints.get(1), currentControlPoints.get(2), currentControlPoints.get(3), (double) 0 + 2 * resolution);
			PVector currentPrevPoint = bezierPoint.interpolateBezierPoint(currentControlPoints.get(0),
					currentControlPoints.get(1), currentControlPoints.get(2), currentControlPoints.get(3),
					(double) 0 + resolution);
			PVector currentPrevPrevPoint = bezierPoint.interpolateBezierPoint(currentControlPoints.get(0),
					currentControlPoints.get(1), currentControlPoints.get(2), currentControlPoints.get(3), (double) 0);
			int count = 0;
			for (double j = 2.0 * resolution; j < 1.0; j += resolution) {
				gcoder.myParent.println(j);
				// here we must get 3 interpolated points in order to draw arc
				PVector currentPoint = bezierPoint.interpolateBezierPoint(currentControlPoints.get(0),
						currentControlPoints.get(1), currentControlPoints.get(2), currentControlPoints.get(3),
						(double) j);
				bezierPoint.drawArcFrom3Points(currentPrevPrevPoint, currentPrevPoint, currentPoint,
						isFirstInstruction);
				currentPrevPrevPoint = currentPrevPoint;
				currentPrevPoint = currentPoint;
				isFirstInstruction = false;

				if (count == 5) {
//					break;
				}
				count++;

			}
			isLastInstruction = true;
//			 for approximation raisons we make sure we draw the last point
//				gcoder.myParent.println("trigger last point");
//				gcoder.myParent.println(currentPoint.x, currentPoint.y);
			PVector currentPoint = bezierPoint.interpolateBezierPoint(currentControlPoints.get(0),
					currentControlPoints.get(1), currentControlPoints.get(2), currentControlPoints.get(3), 1);
			bezierPoint.drawArcFrom3Points(currentPrevPrevPoint, currentPrevPoint, currentPoint, isFirstInstruction,
					isLastInstruction);
			currentPrevPrevPoint = currentPrevPoint;
			currentPrevPoint = currentPoint;
		}
		gcoder.elevatePen();
	}

}

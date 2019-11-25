package gcode.generative;

import java.util.ArrayList;

import processing.core.PVector;

/**
 * The BezierQuad class contains all the BezierPoints of the bezier curve.
 * 
 * @author Morgan Thibert
 *
 */
public class BezierQuad {
	Gcoder gcoder;
	ArrayList<BezierPoints> points;
	double resolution; // quality of interpolation of the bezier curve. must be between 0 and 1. 0.1 or
	public BezierPoints lastBezierPoint;
	public PVector lastPoint;
	public boolean showControlPoints = false;

	/**
	 * 
	 * @param _gcoder
	 * @param pt1 first Point
	 * @param pt2 second Point
	 * @param pt3 third Point
	 * @param pt4 fourth Point
	 * @param _resolution must be between 0 and 1. 0.1 is fast but not very beautiful, 0.01 is better but slower.
	 */
	public BezierQuad(Gcoder _gcoder, PVector pt1, PVector pt2, PVector pt3, PVector pt4, double _resolution) {
		gcoder = _gcoder;
		points = new ArrayList<BezierPoints>();
		BezierPoints origin = new BezierPoints(pt1, pt2, pt3, pt4, gcoder);
		points.add(origin);
		if(gcoder.guiEnabled == true) {
			resolution = gcoder.cp5.getController("ccResolution").getValue();
		}else {
			resolution = _resolution;
		}

		lastBezierPoint = points.get(points.size() - 1);
		lastPoint = lastBezierPoint.points.get(3);
	}

	/**
	 * 
	 * @param _gcoder
	 * @param pt1
	 * @param ctrlPt1X
	 * @param ctrlPt1Y
	 * @param pt4
	 * @param ctrlPt4X
	 * @param ctrlPt4Y
	 * @param _resolution
	 */
	public BezierQuad(Gcoder _gcoder, float[] pt1, float ctrlPt1X, float ctrlPt1Y, float[] pt4, float ctrlPt4X,
			float ctrlPt4Y, double _resolution) {
		this(_gcoder, new PVector(pt1[0], pt1[1]), new PVector(pt1[0] + ctrlPt1X, pt1[1] + ctrlPt1Y),
				new PVector(pt4[0] + ctrlPt4X, pt4[1] + ctrlPt4Y), new PVector(pt4[0], pt4[1]), _resolution);
	}


	/**
	 * 
	 * @param endPoint
	 * @param ctrlPtX
	 * @param ctrlPtY
	 */
	public void addPoint(float[] endPoint, float ctrlPtX, float ctrlPtY) {
		addPoint(new PVector(endPoint[0] + ctrlPtX, endPoint[1] + ctrlPtY), new PVector(endPoint[0], endPoint[1]));
	}

	
	/**
	 * 
	 * @param pt1
	 * @param pt2
	 * @param pt3
	 * @param pt4
	 */
	public void addPoint(PVector pt1, PVector pt2, PVector pt3, PVector pt4) {
		BezierPoints newPoint = new BezierPoints(pt1, pt2, pt3, pt4, gcoder);
		points.add(newPoint);
		lastBezierPoint = points.get(points.size() - 1);
		lastPoint = lastBezierPoint.points.get(3);

	}

	/**
	 * 
	 * @param pt3
	 * @param pt4
	 */
	public void addPoint(PVector pt3, PVector pt4) {
		// method to add point to the bezier curve. We need only 2 control points
		// because the first and second are the points of the precedent curve

		// get the points of the previous segment quad
		BezierPoints prevSegment = points.get(points.size() - 1);
		PVector prevP4 = prevSegment.points.get(3);
		PVector prevP3 = prevSegment.points.get(2);

		float distX = prevP4.x - prevP3.x;
		float distY = prevP4.y - prevP3.y;

		PVector tmpP2 = new PVector(prevP4.x + distX, prevP4.y + distY);
		addPoint(prevP4, tmpP2, pt3, pt4);

	}

	
	/**
	 * 
	 */
	public void show() {
		
		BezierPoints bezierPoint;
		boolean isFirstInstruction = true;
		for (int i = 0; i < points.size(); i++) {
			boolean isLastInstruction = false; // necessaire pour finir l'arc lorsqu'on fait le dernier point
			bezierPoint = points.get(i);
			bezierPoint.applyPushMatrix();

			ArrayList<PVector> currentControlPoints = bezierPoint.points;

			PVector currentPrevPoint = bezierPoint.interpolateBezierPoint(currentControlPoints.get(0),
					currentControlPoints.get(1), currentControlPoints.get(2), currentControlPoints.get(3),
					(double) 0 + resolution);
			PVector currentPrevPrevPoint = bezierPoint.interpolateBezierPoint(currentControlPoints.get(0),
					currentControlPoints.get(1), currentControlPoints.get(2), currentControlPoints.get(3), (double) 0);
			for (double j = 2.0 * resolution; j < 1.0 - resolution; j += resolution) {
				// here we must get 3 interpolated points in order to draw arc
				PVector currentPoint = bezierPoint.interpolateBezierPoint(currentControlPoints.get(0),
						currentControlPoints.get(1), currentControlPoints.get(2), currentControlPoints.get(3),
						(double) j);
				bezierPoint.drawArcFrom3Points(currentPrevPrevPoint, currentPrevPoint, currentPoint,
						isFirstInstruction);
				currentPrevPrevPoint = currentPrevPoint;
				currentPrevPoint = currentPoint;
				isFirstInstruction = false;

			}
			isLastInstruction = true;
//			 for approximation raisons we make sure we draw the last point
			PVector currentPoint = bezierPoint.interpolateBezierPoint(currentControlPoints.get(0),
					currentControlPoints.get(1), currentControlPoints.get(2), currentControlPoints.get(3), 1);
			bezierPoint.drawArcFrom3Points(currentPrevPrevPoint, currentPrevPoint, currentPoint, isFirstInstruction,
					isLastInstruction);
			currentPrevPrevPoint = currentPrevPoint;
			currentPrevPoint = currentPoint;
			bezierPoint.processingDrawBezier(showControlPoints);
		}
		gcoder.elevatePen();
	}

}

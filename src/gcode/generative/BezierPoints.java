package gcode.generative;
import java.util.ArrayList;
import java.lang.Math;

import processing.core.PVector;

public class BezierPoints {
	ArrayList<PVector> points;
	Gcoder gcoder;

	public BezierPoints(PVector pt1, PVector pt2, PVector pt3, PVector pt4, Gcoder _gcoder) {
		points.add(pt1);
		points.add(pt2);
		points.add(pt3);
		points.add(pt4);
		gcoder= _gcoder;
	}
	
	public PVector calculatePolynom(PVector P0, PVector P1, PVector P2 ,PVector P3, double t) {
		double Px = (float) (Math.pow(1 - t, 3) * P0.x + 3 * Math.pow(1 - t, 2) * t * P1.x + 3 * (1 - t) * Math.pow(t, 2) * P2.x )+ Math.pow(t, 3) * P3.x;
		double Py = (float) (Math.pow(1 - t, 3) * P0.y + 3 * Math.pow(1 - t, 2) * t * P1.y + 3 * (1 - t) * Math.pow(t, 2) * P2.y + Math.pow(t, 3) * P3.y);

		//strokeWeight(6);
		//stroke(255);
		PVector result = new PVector((float)Px,(float) Py);
		return result;
		
	}
	
	public void drawArcFrom3Points(PVector a, PVector b, PVector c) {
		PVector mpAB = new PVector((float) 0.5 * (b.x + a.x), (float)0.5 * (b.y + a.y));
		PVector mpBC = new PVector((float) 0.5 * (c.x + b.x), (float)0.5 * (c.y + b.y));

		float mAB = (b.y - a.y) / (b.x - a.x);
		float mBC = (c.y - b.y) / (c.x - b.x);

		float mPerpAB = -1 / mAB;
		float mPerpBC = -1 / mBC;
		if (mPerpAB - mPerpBC == 0) {
			System.out.println("must draw line");
		}

//		strokeWeight(1);
		// point(mpAB.x, mpAB.y)
		// point(mpBC.x, mpBC.y)

		float centerX = (mpBC.y - mpAB.y + mPerpAB * mpAB.x - mPerpBC * mpBC.x) / (mPerpAB - mPerpBC);
		float centerY = mPerpAB * (centerX - mpAB.x) + mpAB.y;
		PVector centerPoint = new PVector(centerX, centerY);

//		strokeWeight(1);
		// stroke(255, 0, 0);
		// point(centerX, centerY)

//		noFill();

		float radius = (float) Math.pow(Math.pow(a.x - centerX, 2) + Math.pow(a.y - centerY, 2), 0.5);
		// console.log(radius)
		// radius *= 2

//		ellipse(centerX, centerY, 2 * radius, 2 * radius);

		PVector aa = new PVector(c.x - a.x, c.y - a.y);
		PVector bb = new PVector(b.x - a.x, b.y - a.y);
//		line(0, 0, aa.x, aa.y);
//		line(0, 0, bb.x, bb.y);
		// let sensRotation = aa.angleBetween(bb)
		float sensRotation = (c.x - a.x) * (b.y - a.y) - (c.y - a.y) * (b.x - a.x);
//		console.log('ROTATION: ', sensRotation);

		// il faut verifier le sens de rotation

		// str += `G1 X${a.x} Y${a.y} <br>`
//		gcoder.draw
//		str += `G${sensRotation >= 0 ? '2' : '3'} X${b.x} Y${b.y} I${centerX - a.x} J${centerY - a.y} <br>`;
		// console.log(a, b, c);
		// console.log((c.x - centerX))

		float angleC = (float) Math.acos((c.x - centerX) / radius);
		if (c.y > centerY) {
			angleC *= -1;
		}
		float angleA = (float) Math.acos((a.x - centerX) / radius);
		if (a.y > centerY) {
			angleA *= -1;
		}
//		console.log(centerX, centerY, angleA, angleC);
		// line(centerX, centerY, c.x, c.y)

//		stroke(0, 255, 0);
//		strokeWeight(1);

		gcoder.drawArc(centerPoint, radius, angleA, angleC);
//		arc(centerX, centerY, 2 * radius, 2 * radius, min(-angleA, -angleC), max(-angleA, -angleC));
		
		
	}


}

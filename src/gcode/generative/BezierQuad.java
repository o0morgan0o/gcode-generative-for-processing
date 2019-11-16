package gcode.generative;
import java.util.ArrayList;

import processing.core.PVector;

public class BezierQuad {
	Gcoder gcoder;
	ArrayList<BezierPoints> points;
	
	public BezierQuad(Gcoder _gcoder, PVector pt1, PVector pt2, PVector pt3, PVector pt4) {
		gcoder = _gcoder;
		BezierPoints origin = new BezierPoints(pt1, pt2, pt3, pt4, gcoder);
		points.add(origin);
		
	}
	
	
	public void add(PVector pt1, PVector pt2, PVector pt3, PVector pt4) {
		BezierPoints newPoint = new BezierPoints(pt1,pt2,pt3,pt4, gcoder);
		points.add(newPoint);
		
	}
	
	

}


package gcode.generative;

import processing.core.PVector;
import static processing.core.PApplet.min;
import static processing.core.PApplet.max;

public class Line {
	Gcoder gcoder;
	PVector origin;
	PVector destination;
	float minX, minY, maxX, maxY;
	boolean optimize;


	/**
	 * 
	 * @param _gcoder
	 * @param _originX
	 * @param _originY
	 * @param _destinationX
	 * @param _destinationY
	 * @param _optimize
	 */
	public Line(Gcoder _gcoder, float _originX, float _originY, float _destinationX, float _destinationY, boolean _optimize) {
		gcoder = _gcoder;
		optimize = _optimize;
		origin = new PVector(_originX, _originY);
		destination = new PVector(_destinationX, _destinationY);
		minX = min(_originX, _destinationX);
		maxX = max(_originX, _destinationX);
		minY = min(_originY, _destinationY);
		maxY = max(_originY, _destinationY);
	}
	
/**
 * 	
 * @param _gcoder
 * @param _originX
 * @param _originY
 * @param _destinationX
 * @param _destinationY
 */
	public Line(Gcoder _gcoder, float _originX, float _originY, float _destinationX, float _destinationY) {
		this(_gcoder, _originX, _originY, _destinationX, _destinationY, true); // optimization turned on by default
	}
	PVector lowestPoint() {
		if (origin.y < destination.y) {
			return origin;
		} else {
			return destination;
		}
	}

	/**
	 * 
	 * @return
	 */
	PVector uppestPoint() {
		if (origin.y < destination.y) {
			return destination;
		} else {
			return origin;
		}
	}

	/**
	 * 
	 * @return
	 */
	PVector leftestPoint() {
		if (origin.x < destination.x) {
			return origin;
		} else {
			return destination;
		}
	}

	/**
	 * 
	 * @return
	 */
	PVector rightestPoint() {
		if (origin.x < destination.x) {
			return destination;
		} else {
			return origin;
		}
	}

	/**
	 * 
	 */
	public void draw() {
		gcoder.drawLine(origin.x, origin.y, destination.x, destination.y, optimize);
	}

}

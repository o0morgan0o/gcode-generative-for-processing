package gcode.generative;

import processing.core.*;
import static processing.core.PApplet.*;
import static processing.core.PApplet.min;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static processing.core.PApplet.max;

/**
 * This is a template class and can be used to start a new processing Library.
 * Make sure you rename this class as well as the name of the example package
 * 'template' to your own Library naming convention.
 * 
 * (the tag example followed by the name of an example included in folder
 * 'examples' will automatically include the example in the javadoc.)
 *
 * @example Hello
 */

public class Gcoder {

	// myParent is a reference to the parent sketch
	PApplet myParent;

	public final static String VERSION = "##library.prettyVersion##";
	public String outputFile;
	public String currentInstructions = "";

	// Hardware limits of the printer (in mm)
	public float PHYSICALLIMITX;
	public float PHYSICALLIMITY;

	//
	public float amplitudeOnZ;

	//
	public float canvasWidth;
	public float canvasHeight;

	//
	public float canvasOriginX;
	public float canvasOriginY;

	public float previousX;
	public float previousY;

	public float offsetProcessingDrawingX = 30;
	public float offsetProcessingDrawingY = 80;
	public float speed;
	
	public float translateVarX = 0;
	public float translateVarY = 0;
	public float rotateVar = 0;

	private PrintWriter output;
	File file;

	/**
	 * a Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the Library.
	 * 
	 * @example Hello
	 * @param theParent the parent PApplet
	 */
	public Gcoder(PApplet theParent, String _outputFile, float _PHYSICALLIMITX, float _PHYSICALLIMITY,
			float _amplitudeOnZ, float _canvasOriginX, float _canvasOriginY, float _canvasWidth, float _canvasHeight) {

		myParent = theParent;

		// initialization of the parameters -----------
		outputFile = _outputFile;
		PHYSICALLIMITX = _PHYSICALLIMITX;
		PHYSICALLIMITY = _PHYSICALLIMITY;
		amplitudeOnZ = _amplitudeOnZ;
		canvasOriginX = _canvasOriginX;
		canvasOriginY = _canvasOriginY;
		canvasWidth = _canvasWidth;
		canvasHeight = _canvasHeight;
		speed= 2400;

		welcome();
		drawLimitsOnSketch();
	}

	private void drawLimitsOnSketch() {
		PFont f = myParent.createFont("Arial", 16, true);
		myParent.textFont(f);
		myParent.text("(0,0)", offsetProcessingDrawingX, offsetProcessingDrawingY - 5);
		myParent.text("(" + Float.toString(PHYSICALLIMITX) + ",0)", offsetProcessingDrawingX + PHYSICALLIMITX,
				offsetProcessingDrawingY - 5);
		myParent.text("(0, " + Float.toString(PHYSICALLIMITY) + ")", offsetProcessingDrawingX - 5,
				offsetProcessingDrawingY + PHYSICALLIMITY + 20);
		myParent.text("(" + Float.toString(PHYSICALLIMITX) + "," + Float.toString(PHYSICALLIMITY) + "0)",
				offsetProcessingDrawingX + PHYSICALLIMITX, offsetProcessingDrawingY + PHYSICALLIMITY + 20);
		myParent.pushMatrix();
		myParent.translate(offsetProcessingDrawingX, offsetProcessingDrawingY);
		myParent.stroke(255, 0, 0);
		myParent.rect(0, 0, PHYSICALLIMITX, PHYSICALLIMITY);
		myParent.strokeWeight(5);
		myParent.stroke(0, 255, 0);
		myParent.rect(canvasOriginX, canvasOriginY, canvasWidth, canvasHeight);
		myParent.strokeWeight(1);
		myParent.popMatrix();
	}

	private void welcome() {
		System.out.println("##library.name## ##library.prettyVersion## by ##author##");
	}

	/**
	 * return the version of the Library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}

	/**
	 * 
	 * @param theA the width of test
	 * @param theB the height of test
	 */
//	public void setVariable(int theA, int theB) {
//		return null;
//	}

	/**
	 * 
	 * @return int
	 */
//	public int getVariable() {
//		return null;
//	}
	
	public void setSpeed(float _speed) {
		speed= _speed;
	}
	
	public void setAmplitudeOnZ(float _amplitudeOnZ) {
		amplitudeOnZ = _amplitudeOnZ;
	}

	public void resetInstructions() {
		currentInstructions = "";
	}

	public void drawRect(float x, float y, float w, float h) {
		drawLine(x, y, x + w, y);
		drawLine(x + w, y, x + w, y + h);
		drawLine(x + w, y + h, x, y + h);
		drawLine(x, y + h, x, y);

//	    if(fillRectangles){
//	        // for (int i = 0 ; i < max(w + tan(PI/4) * h, h + tan(PI/4) * w) ; i++ ){
//	        for (int i = 0 ; i < ( w) ; i++ ){
//	            //il faut calculer l'e
//	            // drawLine(min(x+i, x+w), y , x ,min(y+i, y+h));
//	            // float newX = crop()
//	            // drawLine(x+i, y , x ,min(y+i, y+h));
//	        }
//
//	    }
	}
	
	public void drawLine(float x1, float y1, float x2, float y2) {
		drawLine(x1, y1, x2, y2, true);
	}

	public void drawLine(float x1, float y1, float x2, float y2, boolean applyTransformations) {
		if(applyTransformations) {
		// we calculate if we have a rotation in push Matrix
		float tempX = x1* cos(rotateVar) + y1 * sin(rotateVar);
		float tempY = -x1 * sin(rotateVar) + y1 * cos(rotateVar);
		x1 = tempX;
		y1 = tempY;
		
		tempX = x2 * cos(rotateVar) + y2 * sin(rotateVar);
		tempY = -x2 * sin(rotateVar) + y2 * cos(rotateVar);
		x2 = tempX;
		y2 = tempY;
		//first we adapt if we made a pushMatrix =>
		x1 += translateVarX;
		x2 += translateVarX;
		y1 += translateVarY;
		y2 += translateVarY;
		}
		
		
		//DEBUG ////////////////////////
//		System.out.println("New point //");
//		System.out.println(x1);
//		System.out.println(x2);
//		System.out.println(y1);
//		System.out.println(y2);
		// ////////////////////////////
		
		
		
		
		// check if the 2 points are included in the canvas. if not, we return nothing
		if (max(x1, x2) < 0 || min(x1, x2) > canvasWidth || max(y1, y2) < 0 || min(y1, y2) > canvasHeight) {
			return;
		}

		// calculate the distance previousPoint => x1,y1 and previousPoint => x2,y2 to
		// choose quickest draw
		float mag1 = (new PVector(x1 - previousX, y1 - previousY)).mag();
		float mag2 = (new PVector(x2 - previousX, y2 - previousY)).mag();
		if (mag1 > mag2) { // we swap the 2 points because it will be quicker to draw
			float tmpX = x1;
			float tmpY = y1;
			x1 = x2;
			y1 = y2;
			x2 = tmpX;
			y2 = tmpY;
		}

		if (x1 <= canvasWidth && y1 <= canvasHeight && x2 <= canvasWidth && y2 <= canvasHeight && x1 >= 0 && y1 >= 0
				&& x2 >= 0 && y2 >= 0) { // Check if the points are inside the canvas
			myParent.pushMatrix();
			myParent.translate(offsetProcessingDrawingX + canvasOriginX, offsetProcessingDrawingY + canvasOriginY);
			myParent.stroke(0);
			myParent.line(x1, y1, x2, y2);
			myParent.popMatrix();
			boolean isContinousDraw = false;
			if (previousX == x1 && previousY == y1) {
				isContinousDraw = true;
			}
			if (!isContinousDraw) {
				elevatePen();
				movePenTo(x1, y1, previousX, previousY);
				lowerPen();
			}
			movePenTo(x2, y2, previousX, previousY);

		} else if (x1 == x2) {
			// If the line is a vertical line, we must treat it apart because of the
			// equation of the line => pente is + infinity
			float newY1 = myParent.max(myParent.min(y1, y2), 0);
			float newY2 = myParent.min(myParent.max(y1, y2), canvasHeight);
			drawLine(x1, newY1, x2, newY2, false);

		} else {
//			System.out.println("GO interpolation");
			// interpolation if a point is outside the canvas => we calcul the equation of
			// the line and get the intersection of the line with the limits of the canvas
			float pente = (y2 - y1) / (x2 - x1);
			float reste = y2 - pente * x2;
			if (x1 > canvasWidth) {
				float newX1 = canvasWidth;
				float newY1 = pente * newX1 + reste;
				drawLine(newX1, newY1, x2, y2, false);
			} else if (x2 > canvasWidth) {
				float newX2 = canvasWidth;
				float newY2 = pente * newX2 + reste;
				drawLine(x1, y1, newX2, newY2, false);
			} else if (y1 > canvasHeight) {
				float newY1 = canvasHeight;
				float newX1 = (newY1 - reste) / pente;
				drawLine(newX1, newY1, x2, y2, false);
			} else if (y2 > canvasHeight) {
				float newY2 = canvasHeight;
				float newX2 = (newY2 - reste) / pente;
				drawLine(x1, y1, newX2, newY2, false);
			} else if (x1 < 0) {
				float newX1 = 0;
				float newY1 = pente * newX1 + reste;
				drawLine(newX1, newY1, x2, y2, false);
			} else if (x2 < 0) {
				float newX2 = 0;
				float newY2 = pente * newX2 + reste;
				drawLine(x1, y1, newX2, newY2, false);
			} else if (y1 < 0) {
				float newY1 = 0;
				float newX1 = (newY1 - reste) / pente;
				drawLine(newX1, newY1, x2, y2, false);
			} else if (y2 < 0) {
				float newY2 = 0;
				float newX2 = (newY2 - reste) / pente;
				drawLine(x1, y1, newX2, newY2, false);
			}
		}
	}

	public void elevatePen() {
		currentInstructions += "G0 Z" + Float.toString(amplitudeOnZ) + "\n";
	}

	public void lowerPen() {
		currentInstructions += "G0 Z" + Float.toString(-amplitudeOnZ) + "\n";
	}
	
	public void pushMatrix() {
		translateVarX = 0;
		translateVarY = 0;
		rotateVar = 0;
	}
	
	public void popMatrix() {
		translateVarX = 0;
		translateVarY = 0;
		rotateVar = 0;
	}
	
	public void translate(float _x, float _y) {
		translateVarX += _x;
		translateVarY += _y;
	}
	
	public void rotate(float _angle) {
		rotateVar +=  _angle;
	}
	
	

	public void movePenTo(float x, float y, float _originX, float _originY) {
		if (x == _originX && y == _originY) {
			return;
		}
		// Check if we don't draw outside limits
		if (canvasOriginX + x > canvasOriginX + canvasWidth) {
			// println("ERROR : value X is outside limit");
			// formule a generaliser
			float pente = y / x;
			float reste = ((y + _originY) - pente * (x + _originX)) / 2;
			x = canvasWidth - canvasOriginX;
			y = pente * x + reste;
			// return;
		}
		if (x < 0) {
			// println("ERROR : value X is below origin point");
			float pente = y / x;
			float reste = ((y + _originY) - pente * (x + _originX)) / 2;
			x = canvasOriginX;
			y = pente * x + reste;
			// return;
		}
		if (y > canvasHeight) {
			// println("ERROR : value X is outside limit");
			float pente = y / x;
			float reste = (y + _originY) - pente * (x + _originX);
			y = canvasHeight - canvasOriginX;
			x = y / pente - reste;
			// return;
		}
		if (y < 0) {
			// println("ERROR : value Y is below origin point");
			float pente = y / x;
			float reste = (y + _originY) - pente * (x + _originX);
			y = canvasOriginY;
			x = y / pente - reste;
			// return;
		}

		currentInstructions += "G1 X" + Float.toString(canvasOriginX + x) + " Y" + Float.toString(canvasOriginY + y)
				+ "\n";

		// store previous position so that we don't need to elevate the pen if it is not
		// needed
		previousX = x;
		previousY = y;
	}

	public void writeToFile() {
		File file = new File("D:\\code\\plotterCR10\\" + outputFile + ".gcode");
		try {
			output = new PrintWriter(file);
			String initCommands = "G28\n"; // Auto Home
			initCommands += "G90\n";
			initCommands += "G0 Z4\n"; // move pen up
			initCommands += "G1 X" + Float.toString(canvasOriginX) + " Y" + Float.toString(canvasOriginY) + " F" + Float.toString(speed) + "\n"; // F2400 // is // the // speed // which // will // be // used // by // default
			initCommands += "G0 Z-4\n";
			output.print(initCommands);
			output.print("; end of initialization\n");
			output.print(currentInstructions);

			output.print("; ending commands\n");
			String endCommands = "G0 Z4\nG28\n";
			output.print(endCommands);

			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

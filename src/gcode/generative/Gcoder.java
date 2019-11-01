package gcode.generative;

import processing.core.*;
import geomerative.*;
import static processing.core.PApplet.*;
import static processing.core.PApplet.min;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

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
	
	public float minX;
	public float maxX;
	public float minY;
	public float maxY;

	public float offsetProcessingDrawingX = 30;
	public float offsetProcessingDrawingY = 80;
	public float speed;
	
	public float translateVarX = 0;
	public float translateVarY = 0;
	public float rotateVar = 0;

	private PrintWriter output;
	File file;

	/**
	 * The constructor for initialization of the library.
	 * 
	 * @param theParent the parent PApplet
	 * @param _outputFile the output file of the generated gcode
	 * @param _PHYSICALLIMITX the physical maximum x of the printer ( = 300 mm for the CR10)
	 * @param _PHYSICALLIMITY the physical maximum y of the printer ( = 300 mm for the CR10)
	 * @param _amplitudeOnZ the z amplitude during the elevation or the descent of the pen
	 * @param _canvasOriginX the origin X of the sketch on the printer 
	 * @param _canvasOriginY the origin Y of the sketch on the printer 
	 * @param _canvasWidth the width of the printing sketch
	 * @param _canvasHeight the height of the printing sketch
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
		
		minX = 10000;
		minY = 10000;
		maxX = -10000;
		maxY = -10000;

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
	 * reset the current the gcode so that all the precedent instructions will be ignored
	 *
	 */
	public void reset() {
		drawLimitsOnSketch();
		currentInstructions = "";
	}

	/**
	 * return the version of the Library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}

//	public void setVariable(int theA, int theB) {
//		return null;
//	}

	/**
	 * change the moving speed for the next instructions 
	 * 
	 * @param _speed the new speed in mm/min
	 */
	public void setSpeed(float _speed) {
		speed= _speed;
	}
	
	/**
	 * change the amplitude of displacement on z axis for the elevation or descent of the pen
	 * 
	 * @param _amplitudeOnZ the new amplitude in mm
	 */
	public void setAmplitudeOnZ(float _amplitudeOnZ) {
		amplitudeOnZ = _amplitudeOnZ;
	}

	/**
	 * Draw a rectangle and create the corresponding gcode
	 * 
	 * @param x position origin of the rect (top left)
	 * @param y position origin of the rect (top left)
	 * @param w width of the rectangle
	 * @param h height of the rectangle
	 */
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
	
	/**
	 * Draw a line and create the corresponding gcode
	 * 
	 * @param x1 origin x
	 * @param y1 origin y
	 * @param x2 destination x
	 * @param y2 destination y
	 */
	public void drawLine(float x1, float y1, float x2, float y2) {
		drawLine(x1, y1, x2, y2, true);
	}

	/**
	 * Same as draw line but including the rotation or translation passed before the function is called 
	 * 
	 * @param x1 origin x
	 * @param y1 origin y
	 * @param x2 destination x
	 * @param y2 destination y
	 * @param applyTransformations if true the rotation and translation will be applied. If false rotation and translation will be ignored.
	 */
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
			// equation of the line => slope is +infinity
			float newY1 = myParent.max(myParent.min(y1, y2), 0);
			float newY2 = myParent.min(myParent.max(y1, y2), canvasHeight);
			drawLine(x1, newY1, x2, newY2, false);

		} else {
			// interpolation if a point is outside the canvas => we calc the equation of
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

	/**
	 * Elevate the pen of amplitudeOnZ
	 * 
	 */
	public void elevatePen() {
		currentInstructions += "G0 Z" + Float.toString(amplitudeOnZ) + "\n";
	}

	/**
	 * Lower the pen
	 * 
	 */
	public void lowerPen() {
		currentInstructions += "G0 Z" + Float.toString(-amplitudeOnZ) + "\n";
	}
	
	/**
	 * pushMatrix()
	 * 
	 */
	public void pushMatrix() {
		translateVarX = 0;
		translateVarY = 0;
		rotateVar = 0;
	}
	
	/**
	 * popMatrix()
	 * 
	 */
	public void popMatrix() {
		translateVarX = 0;
		translateVarY = 0;
		rotateVar = 0;
	}
	
	/**
	 * translate
	 * 
	 * @param _x translation factor on x
	 * @param _y translation factor on y
	 */
	public void translate(float _x, float _y) {
		translateVarX += _x;
		translateVarY += _y;
	}
	
	/**
	 * rotate
	 * 
	 * @param _angle rotation angle in radians
	 */
	public void rotate(float _angle) {
		rotateVar +=  _angle;
	}
	

	/**
	 * Write gcode instruction for a movement from origin to destination
	 * 
	 * @param x new PositionX
	 * @param y new PositionY
	 * @param _originX originX
	 * @param _originY originY
	 */
	public void movePenTo(float x, float y, float _originX, float _originY) {
		if (x == _originX && y == _originY) {
			return;
		}
		// Check if we don't draw outside limits
		if (canvasOriginX + x > canvasOriginX + canvasWidth) {
			float pente = y / x;
			float reste = ((y + _originY) - pente * (x + _originX)) / 2;
			x = canvasWidth - canvasOriginX;
			y = pente * x + reste;
		}
		if (x < 0) {
			float pente = y / x;
			float reste = ((y + _originY) - pente * (x + _originX)) / 2;
			x = canvasOriginX;
			y = pente * x + reste;
		}
		if (y > canvasHeight) {
			float pente = y / x;
			float reste = (y + _originY) - pente * (x + _originX);
			y = canvasHeight - canvasOriginX;
			x = y / pente - reste;
		}
		if (y < 0) {
			float pente = y / x;
			float reste = (y + _originY) - pente * (x + _originX);
			y = canvasOriginY;
			x = y / pente - reste;
		}

		if(canvasOriginX + x < minX) { minX = canvasOriginX + x; }
		if(canvasOriginY + y < minY) { minY = canvasOriginY + y; }
		if(canvasOriginX + x > maxX) { maxX = canvasOriginX + x; }
		if(canvasOriginY + y > maxY) { maxY = canvasOriginY + y; }

		currentInstructions += "G1 X" + Float.toString(canvasOriginX + x) + " Y" + Float.toString(canvasOriginY + y)
				+ "\n";

		// store previous position so that we don't need to elevate the pen if it is not needed
		previousX = x;
		previousY = y;
	}
	
	
	/**
	 * This method is for simulating the drawing of a filled rectangle in white. It uses the geomerative library for calculating the intersect polygon of 2 shapes.
	 * 
	 * @param down the shape on which we want to draw (shape to cut)
	 * @param up the shape which should appear on front
	 * @return RShape
	 */
	public RShape addFilledShape(RShape down , RShape up) {
	    try {
	        int ii = down.getPointsInPaths().length;
	    } catch (Exception e) {
	        System.out.println("Probably empty start RShape. Returning upfront RShape");
	        return up;
	    }

	    if (down.getPointsInPaths().length == 1) {
	        RShape tmp = new RShape();

	        tmp = down.diff(up);
	        if (tmp.countPaths() == 0) {
	        	// if tmp.countPaths is 0 , so it means that the upfront shape cover completely the shape. So we return only the upfront shape
	            return up;
	        }
	        up.addChild(tmp);
	        RShape res = new RShape();
	        res.addChild(up);
	        res.addChild(tmp);
	        RShape res2 = new RShape();
	        for (int i = 0; i < res.getPointsInPaths().length - 1; i++) {
	            RPath p = new RPath(res.getPointsInPaths()[i]);
	            res2.addPath(p);
	        }
	        return res2;

	    } else {
	        RShape global = new RShape();
	        for (int i = 0; i < down.getPointsInPaths().length; i++) {
	            RShape rtemp = new RShape();
	            RPath ptemp = new RPath(down.getPointsInPaths()[i]);
	            rtemp.addPath(ptemp);
	            global = addFilledShape(rtemp, up);
	        }
	        return global;
	    }
	}
	
	
	public void drawRShape(RShape r) {
	    for (int j = 0; j < r.getPointsInPaths().length; j++) {
	        for (int i = 1; i < r.getPointsInPaths()[j].length - 1; i++) {
	            RPoint rpointPrev = r.getPointsInPaths()[j][i - 1];
	            RPoint rpoint = r.getPointsInPaths()[j][i];
	            float prevX = rpointPrev.x;
	            float prevY = rpointPrev.y;
	            float x = rpoint.x;
	            float y = rpoint.y;
	            drawLine(prevX, prevY, x, y);

	        }
	    }
	}
	


	/**
	 * Actual writing of all the buffered instructions to the output gcode file
	 * 
	 */
	public void writeToFile() {
		DecimalFormat decimalFormat = new DecimalFormat("#0");
		myParent.fill(255,0,0);
		myParent.text("MinX = " + decimalFormat.format(minX) + " // MaxX = " + decimalFormat.format(maxX) ,
				offsetProcessingDrawingX + PHYSICALLIMITX + 100, 40);
		myParent.text("MinY = " + decimalFormat.format(minY) + " // MaxY = " + decimalFormat.format(maxY) ,
				offsetProcessingDrawingX + PHYSICALLIMITX + 100, 60);
		File file = new File(myParent.sketchPath()+ "\\" + outputFile + ".gcode");
		try {
			output = new PrintWriter(file);
			String initCommands = "G28\n"; // Auto Home
			initCommands += "G90\n";
			initCommands += "G0 Z4\n"; // move pen up
			initCommands += "G1 X" + Float.toString(canvasOriginX) + " Y" + Float.toString(canvasOriginY) + " F" + Float.toString(speed) + "\n";
			initCommands += "G0 Z-4\n";
			output.print(initCommands);
			output.print("; end of initialization\n");
			output.print(currentInstructions);
			output.print("; ending commands\n");
			String endCommands = "G0 Z4\nG28\n";
			output.print(endCommands);

			output.flush();
			output.close();
			System.out.println("Generation of GCODE terminated !\n");
			System.out.println("MinX = " + Float.toString(minX));
			System.out.println("MaxX = " + Float.toString(maxX));
			System.out.println("MinY = " + Float.toString(minY));
			System.out.println("MaxY = " + Float.toString(maxY));
			if(minX <= 10 || maxX >= 300 || minY <= 10 || maxY >= 300 ) {
				System.out.println("///////////////////////////////////////");
				System.out.println("ATTENTION : risk to draw outside limits");
				System.out.println("///////////////////////////////////////");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

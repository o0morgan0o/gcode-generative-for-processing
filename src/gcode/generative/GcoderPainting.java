package gcode.generative;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import processing.core.PApplet;
import processing.core.PVector;

public class GcoderPainting extends Gcoder{
	
	public float distanceMade=0;
	public float highZ;
	public float reloadZ;
	public float paintingZ;
	boolean comingFromReload = false;
	
	
	public PVector color1 = new PVector(70,265);
	public PVector color2 = new PVector(70,227);
	public PVector color3 = new PVector(70,189);
	public PVector color4 = new PVector(70,151);
	public PVector water;
	float speed = 8000;
	
	

	public GcoderPainting(PApplet theParent, String _outputFile, float _amplitudeOnZ, float _canvasWidth, float _canvasHeight) {
		super(theParent, _outputFile, 300, 300, _amplitudeOnZ, 150, 35,
				_canvasWidth, _canvasHeight);
		// TODO Auto-generated constructor stub
	}
	
	
	public void defineZOffsets(float _paintingZ, float _reloadZ, float _highZ) {
		paintingZ = _paintingZ;
		reloadZ = _reloadZ;
		highZ = _highZ;
	}
	
	public void defineColorPosition(PVector _col1Pos) {
//	public void defineColorPosition(PVector _col1Pos, PVector _col2Pos, PVector _col3Pos, PVector _col4Pos, PVector _waterPos) {
//		col1Pos = _col1Pos;
//		col2Pos = _col2Pos;
//		col3Pos = _col3Pos;
//		col4Pos = _col4Pos;
//		waterPos = _waterPos;
		
	}
	
	public void reloadColor(PVector colPos) {
		float returnX = previousX;
		float returnY = previousY;
		previousX = -1;
		previousY = -1;
		elevatePenCustom(highZ);
		movePenDangerously(colPos.x, colPos.y);
		elevatePenCustom(reloadZ);
		
		// add water
		
		mixingPen(colPos);
		cleanUpPen();
		elevatePenCustom(highZ);
		comingFromReload = true;
//		movePenDangerously(super.canvasOriginX, super.canvasOriginY);
//		movePenTo(returnX, returnY,0,0);


		
	}
	
	
	public void movePenDangerously(float X, float Y) {
		currentInstructions += "G1 X" + Float.toString(X) + " Y" + Float.toString(Y) + " \n";
	}

	public void elevatePenCustom(float Z) {
		currentInstructions += "G0 Z" + Float.toString(Z + additionalLiftOnZ) + "\n";
		previousZ = Z;
	}
	
	public void mixingPen(PVector colPos) {

		movePenDangerously(colPos.x+5, colPos.y +0 );
		movePenDangerously(colPos.x+0, colPos.y +5 );
		movePenDangerously(colPos.x-5, colPos.y +0 );
		movePenDangerously(colPos.x+0, colPos.y -5 );
		movePenDangerously(colPos.x+0, colPos.y +0 );
		
	}
	public void cleanUpPen() {
		
	}
	
//	public void drawLine(float originX, float originY, float destX, float destY, PVector _color, String noReload) {
//		if (noReload != "NO_RELOAD") {
//			reloadColor(_color);
//		}
//		elevatePenCustom(paintingZ);
//		super.drawLine(originX, originY, destX, destY, false); //no optimization
//	}

	public void paintLine(float originX, float originY, float destX, float destY) {
		if(comingFromReload == true) {
			// if we come from reload we make sure that the first move is inside the canvas, else we return to the canvas origin point
			if(canvasOriginX + originX >= 0 && canvasOriginX + originX <= PHYSICALLIMITX && canvasOriginY + originY >=0 && canvasOriginY + originY <=PHYSICALLIMITY) {
				movePenDangerously(canvasOriginX + originX, canvasOriginY + originY);
			}else {
				movePenDangerously(canvasOriginX, canvasOriginY);
			}
			comingFromReload = false;
		}
		drawLine(originX, originY, destX, destY, false);
//		drawLine(originX, originY, destX, destY, _color, false);
			
	}

	public void drawRect(float x, float y, float w, float h, PVector _color, String noReload) {
//		drawLine(x, y, x + w, y, _color, noReload);
//		drawLine(x + w, y, x + w, y + h, _color, noReload);
//		drawLine(x + w, y + h, x, y + h, _color, noReload);
//		drawLine(x, y + h, x, y, _color, noReload);
		paintLine(x, y, x + w, y);
		paintLine(x + w, y, x + w, y + h);
		paintLine(x + w, y + h, x, y + h);
		paintLine(x, y + h, x, y);
	}
	public void drawRect(float x, float y, float w, float h, PVector _color) {
		drawRect(x,y,w,h,_color, "RELOAD");
	}
	
	public void basicMoveTo(float X, float Y) {
		currentInstructions += "G1 X" + Float.toString(X) + " Y" + Float.toString(Y) + " \n";
	}

	public void writeToFile() {
		DecimalFormat decimalFormat = new DecimalFormat("#0");
		myParent.fill(255, 0, 0);
		myParent.text("MinX = " + decimalFormat.format(minX) + " // MaxX = " + decimalFormat.format(maxX),
				offsetProcessingDrawingX + PHYSICALLIMITX + 100, 40);
		myParent.text("MinY = " + decimalFormat.format(minY) + " // MaxY = " + decimalFormat.format(maxY),
				offsetProcessingDrawingX + PHYSICALLIMITX + 100, 60);
		File file = new File(myParent.sketchPath() + "\\" + outputFile + ".gcode");
		try {
			output = new PrintWriter(file);
			String initCommands = "";
			initCommands += "G0 F" + Float.toString(speed) + " \n";
			initCommands += "G0 Z" + Float.toString(additionalLiftOnZ + highZ) + "\n"; // additional lift on Z
																								// axis
			initCommands += "G28\n"; // Auto Home
			initCommands += "G90\n"; // Set absolute positionning
			initCommands += "G0 Z" + Float.toString(additionalLiftOnZ + highZ) + "\n"; // additional lift on Z
																								// axis
			initCommands += "G1 X" + Float.toString(canvasOriginX) + " Y" + Float.toString(canvasOriginY) + " F"
					+ Float.toString(speed) + "\n";
			output.print(initCommands);
			output.print("; end of initialization\n");
			output.print(currentInstructions);
			output.print("; ending commands\n");
			String endCommands = "";
			endCommands += "G0 Z" + Float.toString(additionalLiftOnZ + highZ + 20) + "\n"; // elevatepen last time
			endCommands += "G1 X" + Float.toString(canvasOriginX) + " Y" + Float.toString(canvasOriginY) + " \n"; 
			endCommands += "G28\n";
			endCommands += "M84\n";
			output.print(endCommands);

			output.flush();
			output.close();

			System.out.println("-------------------------------\n");
			System.out.println("Generation of GCODE terminated !\n");
			System.out.println("Use the -calibration.gcode file to set your pen ! \n");
			System.out.println("-------------------------------\n");
			System.out.println("MinX = " + Float.toString(minX));
			System.out.println("MaxX = " + Float.toString(maxX));
			System.out.println("MinY = " + Float.toString(minY));
			System.out.println("MaxY = " + Float.toString(maxY));
			System.out.println("-------------------------------\n");
			if (minX <= 10 || maxX >= 300 || minY <= 10 || maxY >= 300) {
				System.out.println("///////////////////////////////////////");
				System.out.println("ATTENTION : risk to draw outside limits");
				System.out.println("///////////////////////////////////////");
			}
			writeCalibrationGcode();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	

}

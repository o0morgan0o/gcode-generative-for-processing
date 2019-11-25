/**
* part of the example files of the gcode-generative-for-processing library
* show the basics of painting Lines and Points.
* IMPORTANT : Painting is actual experimental and work in progress.
* Please be careful during the actual print and be ready to shutdown the printer if something goes wrong.
* Morgan Thibert - Novembre 2019
* morgan.thibert@protonmail.com
*/

import gcode.generative.*;

GcoderPainting g;


void setup() {
    size(1200,1200);


    g=new GcoderPainting(this,  // necessary
    "output",  	// name of the output file
    300,				// Physical Limit of the printer on X (300mm for cr10)
    300,				// Physical Limit of the printer on Y (300mm for cr10)
    2.5,				// Z offset between drawing position and moving position
    150,				// X position of the sheet (beginning of the drawing)
    25,					// Y position of the sheet (beginning of the drawing)
    150,				// width of the sheet
    250);				// height of the sheet

    g.defineZOffsets(0, 3, 15); // use to define, the paintingZ = 0, the reloadZ = 3, the movingZ during reload = 15
    
    g.reloadColor(g.color1, true); //the boolean defines if you want to "shake" the paint during reloading

	// You can use setDrawingStyle to adjust the pression on the pencil
	g.setDrawingStyle("NORMAL", 0.0); // will draw at normal positionZ
	g.paintPoint(50,50);
	
	
	g.setDrawingStyle("NORMAL", 2.0); // will draw at normal positionZ + 2mm
	g.paintPoint(60,50);
	
	g.setDrawingStyle("NORMAL", 0.0);
	g.paintLine(50, 80, 80, 80);

	g.paintRectangle(50, 80, 80, 80, g.color1); //draw a rectangle with reloading painting at each line
	
    g.show();
    g.writeToFile();



}
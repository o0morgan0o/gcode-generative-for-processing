/**
* part of the example files of the gcode-generative-for-processing library
* show the basics of drawing RShape. This is used to draw complex shapes or make boolean operations on the shapes so that we can simulate a fill effect on the shapes.
* It requires the geomerative library
* Morgan Thibert - Novembre 2019
* morgan.thibert@protonmail.com
*/
import geomerative.*;
import gcode.generative.*;

Gcoder g;
RShape rfinal;

// This sketch use the geomerative library in order to have a kind of filled shape.

void setup() {
    size(1200,1200);
    
    
    g=new Gcoder(this,  // necessary
    "output.gcode",  	// name of the output file
    300,				// Physical Limit of the printer on X (300mm for cr10)
    300,				// Physical Limit of the printer on Y (300mm for cr10)
    2.5,				// Z offset between drawing position and moving position
    100,				// X position of the sheet (beginning of the drawing)
    25,					// Y position of the sheet (beginning of the drawing)
    180,				// width of the sheet
    250);				// height of the shee

    RG.init(this); // needed from geomerative
    RG.setPolygonizer(RG.ADAPTATIVE);  // setting for the rendre for geomerative. See the geomerative documentation for the setting of the Polygonizer.

    rfinal = new RShape();

    rfinal = g.addFilledShape(rfinal, RShape.createEllipse(120,120,100,100)); // add a filled ellipse
    rfinal = g.addFilledShape(rfinal, RShape.createRectangle(50,50 , 80,80)); // add a filled rectangle


    g.drawRShape(rfinal); // Convert and draw lines to gcode instructions
    g.show();
    g.writeToFile(); // write GCode


}

/**
* part of the example files of the gcode-generative-for-processing library
* show the basics of drawing RShape. This is used to draw complex shapes.
* It requires the geomerative library
* Morgan Thibert - Novembre 2019
* morgan.thibert@protonmail.com
*/

import geomerative.*;
import gcode.generative.*;

Gcoder g;
RShape rfinal;

void setup() {
    size(1200, 1200, P2D);

	g=new Gcoder(this,  // necessary
    "output.gcode",  	// name of the output file
    300,				// Physical Limit of the printer on X (300mm for cr10)
    300,				// Physical Limit of the printer on Y (300mm for cr10)
    2.5,				// Z offset between drawing position and moving position
    100,				// X position of the sheet (beginning of the drawing)
    25,					// Y position of the sheet (beginning of the drawing)
    180,				// width of the sheet
    250);				// height of the sheet

    RG.init(this);  // needed from geomerative library
    RG.setPolygonizer(RG.ADAPTATIVE);  // see the geomerative documentation for the different renders

    rfinal = new RShape();

    // creation of a custom RShape from 4 points.
    RPoint[] points;
    points = new RPoint[4];
    points[0] = new RPoint(50,50);
    points[1] = new RPoint(120,50);
    points[2] = new RPoint(150,180);
    points[3] = new RPoint(50,150);
    RPath p = new RPath(points);
    RShape s = new RShape(p);
    s.addClose();


    rfinal = g.addFilledShape(rfinal, s); // make the shape a filled Shape

    g.drawRShape(rfinal);
    g.show();

    // Convert and draw lines to gcode instructions
    g.writeToFile(); // write GCode


}

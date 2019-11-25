/**
* part of the example files of the gcode-generative-for-processing library
* show the basics of drawing bezier Curves.
* Morgan Thibert - Novembre 2019
* morgan.thibert@protonmail.com
*/
import gcode.generative.*;

Gcoder g;
BezierQuad be;
BezierQuad be2;

void setup() {
    size(800, 800);
    g=new Gcoder(this,  // necessary
    "output.gcode",  	// name of the output file
    300,				// Physical Limit of the printer on X (300mm for cr10)
    300,				// Physical Limit of the printer on Y (300mm for cr10)
    2.5,				// Z offset between drawing position and moving position
    100,				// X position of the sheet (beginning of the drawing)
    25,					// Y position of the sheet (beginning of the drawing)
    180,				// width of the sheet
    250);				// height of the sheet


    
    // define beginning and ending Points.
    float[] pA = {20,20};
    float[] pB = {100,100};
    
    // creation of the bezier Quad by specifing offsets of control points
    be2 = new BezierQuad(g, pA, 20,20, pB , 30,-30, .01);

	// only to see better the control points.
    be2.showControlPoints= true;

	// adding a point to the existing BezierQuad
    float[] pC = {40,120};
    be2.addPoint(pC, 10,10);
    
    
    // show the bezier
    be2.show();
    

    g.show();
    g.writeToFile();
}

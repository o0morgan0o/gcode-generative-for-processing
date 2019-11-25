/**
* part of the example files of the gcode-generative-for-processing library
* show the basics of drawing shapes in 3D space. The coordinates of the shapes are interpolated to 2D space.
* Morgan Thibert - Novembre 2019
* morgan.thibert@protonmail.com
*/

import gcode.generative.*;

Gcoder g;
PGraphics canvas;
GcodeRectMesh m;

void setup() {

    size(1200,1200, P3D);
    
    g=new Gcoder(this,  // necessary
    "output.gcode",  	// name of the output file
    300,				// Physical Limit of the printer on X (300mm for cr10)
    300,				// Physical Limit of the printer on Y (300mm for cr10)
    2.5,				// Z offset between drawing position and moving position
    100,				// X position of the sheet (beginning of the drawing)
    25,					// Y position of the sheet (beginning of the drawing)
    180,				// width of the sheet
    250);				// height of the shee

    canvas = createGraphics((int)g.canvasWidth,(int)g.canvasHeight, P3D); // we will first draw the mesh in a separate PGraphics

    g.addMorePush(.2); // used in order to add some pression to the pen during the drawing. (value in mm)

    m = new GcodeRectMesh(g, this, canvas, new PVector(0, 0, 0), 200,200, 20,20); 
    // pvector(0,0,0) is the position of the pshape
    // 200 is meshwidth
    // 200 is meshheight
    // 20 is number of vertexes per meshwidth
    // 20 is number of vertexes per meshheight

    canvas.beginDraw();
    canvas.pushMatrix();
    canvas.translate(g.canvasWidth/2,g.canvasHeight/2,626);
    canvas.rotateX(0.78147);
    canvas.shape(m.s);
    m.calculate2DProjection(); // it calculate the 2D projection of the 3D space for the drawing.
    m.drawQuads(); // draw the quads and prepare the gcode instructions.
    canvas.popMatrix();
    canvas.endDraw();

 
    g.drawRect(0, 0, g.canvasWidth, g.canvasHeight);
    g.show();
    g.writeToFile(); // write gcode instructions into file
}



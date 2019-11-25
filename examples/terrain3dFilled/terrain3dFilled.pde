/**
* part of the example files of the gcode-generative-for-processing library
* show the basics of drawing filled shapes in 3D space. The coordinates of the shapes are interpolated to 2D space.
* this process is very slow... needs improvements, still Work In Progress 
* Morgan Thibert - Novembre 2019
* morgan.thibert@protonmail.com
*/

import geomerative.*;
import gcode.generative.*;

Gcoder g;
RShape rfinal;
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
    250);				// height of the sheet
	

    RG.init(this);
    RG.setPolygonizer(RG.ADAPTATIVE);

    rfinal = new RShape();

    // creation of a custom RShape from 4 points.
    RPoint[] points;
    points = new RPoint[4];
    points[0] = new RPoint(50, 50);
    points[1] = new RPoint(120, 50);
    points[2] = new RPoint(150, 180);
    points[3] = new RPoint(50, 150);
    RPath p = new RPath(points);
    RShape s = new RShape(p);
    s.addClose();
}


void draw() {
    canvas = createGraphics((int) g.canvasWidth, (int) g.canvasHeight, P3D);
    g.reset();
    

    m = new GcodeRectMesh(g, this, canvas, new PVector(0,500,1000), 600, 800, 5, 5);

    float[][] arr = new float[m.meshsPerHeight][m.meshsPerWidth];
    for (int i =0 ; i < m.meshsPerHeight; i++){
        for(int j=0; j < m.meshsPerWidth; j++){
            arr[i][j]=1500* noise((float)i/8, (float)j/8);
          }
    }
    m.updateVertexesModifierZ(arr);
    rfinal = new RShape();

    canvas.beginDraw();
    canvas.pushMatrix();
    canvas.translate(g.canvasWidth / 2, g.canvasHeight / 2, 626);
    canvas.rotateX(0.78147);
    canvas.shape(m.s);
    float aa = map(mouseX, 0, width, 100, 3000);


    m.calculate2DProjection();
  


    for (int i = m.meshsPerHeight-2; i >=0; i--) {
        println(i);
        for (int j = 0; j < m.meshsPerWidth-1 ; j++) {
            int index = j + i * m.meshsPerWidth;


            PVector v0 = m.screenVertexes[index];
            PVector v1 = m.screenVertexes[index + 1];
            PVector v2 = m.screenVertexes[index + m.meshsPerWidth];
            PVector v3 = m.screenVertexes[index + 1 + m.meshsPerWidth];
            RPoint[] points;

            points = new RPoint[4];
            points[0] = new RPoint(v0.x, v0.y);
            points[1] = new RPoint(v1.x, v1.y);
            points[3] = new RPoint(v2.x, v2.y);
            points[2] = new RPoint(v3.x, v3.y);
            RPath p = new RPath(points);
            RShape s = new RShape(p);
            s.addClose();


            rfinal = g.addFilledShape(rfinal, s);
        }
    }
    println("FINISHED");


    canvas.popMatrix();
    canvas.endDraw();

    g.drawRShape(rfinal);

    g.drawRect(0, 0, g.canvasWidth, g.canvasHeight);
	   g.show();
    g.writeToFile();
    noLoop();
}

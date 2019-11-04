import gcode.generative.*;

Gcoder g;
PGraphics canvas;
GcodeRectMesh m;

void setup() {

    size(800, 800, P2D);
    g = new Gcoder(this, "output", 300, 300, 2.5, 110, 35, 180, 255);

    canvas = createGraphics((int)g.canvasWidth,(int)g.canvasHeight, P3D); // we will first draw the mesh in a separate PGraphics
}


void draw() {
    g.resetAndRedraw();
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
    g.writeToFile(); // write gcode instructions into file
    noLoop();
}

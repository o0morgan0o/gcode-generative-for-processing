import gcode.generative.*;

Gcoder g;
PGraphics canvas;
GcodeRectMesh m;

void setup() {

    size(800, 800, P2D);
    g = new Gcoder(this, "output", 300, 300, 2.5, 110, 35, 180, 255);

    canvas = createGraphics((int)g.canvasWidth,(int)g.canvasHeight, P3D);
}


void draw() {
    g.resetAndRedraw();
    g.addMorePush(.2);

    m = new GcodeRectMesh(g, this, canvas, new PVector(0, millis()/100, 0), 200,200, 20,20); 
    // pvector(0,0,0) is the position of the pshape
    // 800 is meshwidth
    // 1150 is meshheight
    // 200 is number of vertexes per meshwidth
    // 100 is number of vertexes per meshheight

    canvas.beginDraw();
    canvas.pushMatrix();
    canvas.translate(g.canvasWidth/2,g.canvasHeight/2,626);
    canvas.rotateX(0.78147);
    canvas.shape(m.s);
    m.calculate2DProjection();
    m.drawQuads();
    canvas.popMatrix();
    canvas.endDraw();

    g.drawRect(0, 0, g.canvasWidth, g.canvasHeight);
    g.writeToFile();
    noLoop();
}

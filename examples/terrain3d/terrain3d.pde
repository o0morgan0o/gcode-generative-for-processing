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

    m = new GcodeRectMesh(g, this, canvas, new PVector(0, 0, 0), 800, 1150, 200, 100); 
    // PVector(0,0,0) is the position of the PShape
    // 800 is MeshWidth
    // 1150 is MeshHeight
    // 200 is number of vertexes per MeshWidth
    // 100 is number of vertexes per MeshHeight

    canvas.beginDraw();
    canvas.pushMatrix();
    canvas.translate(g.canvasWidth/2,g.canvasHeight/2,626);
    canvas.rotateX(0.78147);
    canvas.shape(m.s);
    m.updateScreenVertexes();
    canvas.popMatrix();
    canvas.endDraw();

    m.drawQuads();
    // or m.drawHorizontalTiles();
    // or m.drawVerticalTiles();

    g.drawRect(0, 0, g.canvasWidth, g.canvasHeight);
    g.writeToFile();
    noLoop();
}

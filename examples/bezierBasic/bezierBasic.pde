import gcode.generative.*;
Gcoder g;
BezierQuad be;

void setup() {
    println("test");
    size(800, 800);
    g = new Gcoder(this, "gg", 300, 300, 2.5, 110, 35, 180, 255);


    be = new BezierQuad(g,new PVector(20,20), new PVector(80, 30), new PVector(120, 80), new PVector(150,120), .1);
    be.addPoint(new PVector(be.lastPoint.x - 40, be.lastPoint.y + 30), new PVector(0,200));

    be.draw();
        // break;
        // be.draw();

    // g.drawRect(0, 0, g.canvasWidth, g.canvasHeight);
    g.writeToFile();
}

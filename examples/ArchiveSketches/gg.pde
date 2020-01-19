import gcode.generative.*;
import controlP5.*;
// GcoderPainting g;
Gcoder g;

BezierQuad be;

void setup() {
    size(1200, 1200);
    // background(0,255,0);
    // g = new GcoderPainting(this, "gg", 4, 150, 255);
    g = new Gcoder(this, "gg", 300, 300, 2.5, 15,120, 40, 150, 250);

// g.setSpeed(11000);
    // g.defineColorPosition(new PVector(200,200));
    // g.defineZOffsets(0, 3.5, 15);
    // g.enableGUI();
    // g.setDrawingStyle("NORMAL", -10);

    // g.setAmplitudeOnZ(1.2);

    // g.drawLine(20,20, 80,40);


    

    // g.writeToFile();
    // g.writeCalibrationGcode();
    // noLoop();
    // exit();



}

void draw() {

    g.reset();
    g.adjustPaintingZ(-.3);

for(int j =0 ; j < 20; j++){
    float xx = map(j,0,60,0,65);

    float[] a = new float[] {30 , 30};
    float[] b = new float[] { g.canvasWidth -30, g.canvasHeight -30};
    float offX = 1 * (300+xx );
    float offY = -80 + 10* xx;
    // offY *= offY;
    BezierQuad be = new BezierQuad(g,a, offX, offY, b, -offX, -offY, .01);
    be.show();

}
    g.show();
    g.writeToFile();
    g.writeCalibrationGcode();
    noLoop();
}

void keyPressed() {}
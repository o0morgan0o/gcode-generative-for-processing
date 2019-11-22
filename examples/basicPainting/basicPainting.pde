import geomerative.*;
import gcode.generative.*;

GcoderPainting g;

RShape rfinal;


float len = 2;

void setup() {
    size(800, 800, P3D);
    g = new GcoderPainting(this, "output", 4, 150, 255); // valeur ok pour pas taper les pinces
    g.defineZOffsets(0, 3, 15);
    g.writeToFile(); // write GCode



}
void draw() {

    g.reset();
    int maxRad = 50;


    // for (int i = 0; i < 20; i++) {
    //     g.pushMatrix();
    //     g.rotate(-.4);
    //     g.translate(20, 40);
    //     g.drawLine(0, 0, 100, 0);
    //     g.popMatrix();
    // }

g.paintPoint(50,50);

    g.show();


}
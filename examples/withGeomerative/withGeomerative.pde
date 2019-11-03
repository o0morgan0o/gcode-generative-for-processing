import geomerative.*;
import gcode.generative.*;

Gcoder g;
RShape rfinal;


void setup() {
    size(800, 800);
	g = new Gcoder(this, "output", 300, 300, 2.5, 110, 35, 180, 255); // valeur ok pour pas taper les pince

    RG.init(this);
    RG.setPolygonizer(RG.ADAPTATIVE);

    rfinal = new RShape();
    RShape s = new RShape();

    rfinal = g.addFilledShape(rfinal, RShape.createEllipse(120,120,100,100));
    rfinal = g.addFilledShape(rfinal, RShape.createRectangle(50,50 , 80,80));


    g.drawRShape(rfinal); // Convert and draw lines to gcode instructions
    g.writeToFile(); // write GCode


}

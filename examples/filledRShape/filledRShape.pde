import geomerative.*;
import gcode.generative.*;

Gcoder g;
RShape rfinal;

// This sketch use the geomerative library in order to have a kind of filled shape.

void setup() {
    size(800, 800);
  	g = new Gcoder(this, "output", 300, 300, 2.5, 110, 35, 180, 255); // 

    RG.init(this); // needed from geomerative
    RG.setPolygonizer(RG.ADAPTATIVE);  // setting for the rendre for geomerative. See the geomerative documentation for the setting of the Polygonizer.

    rfinal = new RShape();

    rfinal = g.addFilledShape(rfinal, RShape.createEllipse(120,120,100,100)); // add a filled ellipse
    rfinal = g.addFilledShape(rfinal, RShape.createRectangle(50,50 , 80,80)); // add a filled rectangle


    g.drawRShape(rfinal); // Convert and draw lines to gcode instructions
    g.writeToFile(); // write GCode


}

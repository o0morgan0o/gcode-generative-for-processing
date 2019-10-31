import geomerative.*;
import gcode.generative.*;

Gcoder g;

RShape rfinal;


float len = 10;

void setup() {
    size(800, 800);
	g = new Gcoder(this, "output", 300, 300, 2.5, 110, 35, 180, 255); // valeur ok pour pas taper les pince
    smooth();
    stroke(0);
    strokeWeight(2);
    // noFill();

    RG.init(this);
    RG.ignoreStyles(true);
    // RG.setPolygonizer(RG.UNIFORMLENGTH);
    RG.setPolygonizerLength(len);
    RG.setPolygonizer(RG.ADAPTATIVE);

    rfinal = new RShape();
    RShape s = new RShape();
    // RPoint r1 = new RPoint(random(100), random(100));
    // RPoint r2 = new RPoint(random(100), random(100));
    // RPoint r3 = new RPoint(random(100), random(100));
    // s.addBezierTo(r1,r2,r3);
    // s.addClose();


    rfinal = addFilledShape(rfinal, RShape.createEllipse(120,120,100,100));
    rfinal = addFilledShape(rfinal, RShape.createRectangle(50,50 , 80,80));


    // println(rfinal.countChildren(), " childrens");
    // println(rfinal.countPaths(), " paths");

    
    drawLines(rfinal); // Convert and draw lines to gcode instructions
    g.writeToFile(); // write GCode


}

RShape addFilledShape(RShape down, RShape up) {
    try {
        if (down.getPointsInPaths().length == 1) {
            RShape tmp = new RShape();
            tmp = down.diff(up);
            up.addChild(tmp);
            RShape res = new RShape();
            res.addChild(up);
            res.addChild(tmp);
            RShape res2 = new RShape();
            for (int i = 0; i < res.getPointsInPaths().length - 1; i++) {
                RPath p = new RPath(res.getPointsInPaths()[i]);
                res2.addPath(p);
            }
            return res2;

        } else {
            RShape global = new RShape();
            for (int i = 0; i < down.getPointsInPaths().length; i++) {
                RShape rtemp = new RShape();
                RPath ptemp = new RPath(down.getPointsInPaths()[i]);
                rtemp.addPath(ptemp);
                global = addFilledShape(rtemp, up);
            }
            return global;
        }
    } catch (Exception e) {
        println("error Trying to return, may be the result in incorrect");
        return up;
    }
}



void drawLines(RShape r) {
    for (int j = 0; j < r.getPointsInPaths().length; j++) {
        for (int i = 1; i < r.getPointsInPaths()[j].length - 1; i++) {
            RPoint rpointPrev = r.getPointsInPaths()[j][i - 1];
            RPoint rpoint = r.getPointsInPaths()[j][i];
            float prevX = rpointPrev.x;
            float prevY = rpointPrev.y;
            float x = rpoint.x;
            float y = rpoint.y;
            g.drawLine(prevX, prevY, x, y);

        }
    }
}
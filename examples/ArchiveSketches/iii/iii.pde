import geomerative.*;
import gcode.generative.*;

Gcoder g;
RShape rfinal;


void setup() {
    size(800, 800);
    g = new Gcoder(this, "output", 300, 300, 2.5, 110, 35, 180, 255); // valeur ok pour pas taper les pince

    RG.init(this);
    RG.setPolygonizer(RG.ADAPTATIVE);

    // rfinal = new RShape();
    RShape s = new RShape();
    RShape s1 = new RShape();
    s1 = RShape.createRectangle(20,20,50,50);


       // creation of a custom RShape from 4 points.
        RPoint[] points;
        points = new RPoint[4];
        points[0] = new RPoint(50,50);
        points[1] = new RPoint(120,50);
        points[2] = new RPoint(150,180);
        points[3] = new RPoint(50,150);
        // RPath p = new RPath(points);

    // println(s1.getPointsInPaths().length);
    // for(int i = 0 ; i < s1.getPointsInPaths()[0].length; i++){
    //     println(s1.getPointsInPaths()[0][i].x, s1.getPointsInPaths()[0][i].y);
    // }
    // s1.print();

//     float a = millis();
//     for (int i = 0; i < 100; i++) {

//         rfinal = g.addFilledShape(rfinal, RShape.createRectangle(i, i, 20,20));
//         // rfinal =  g.addFilledShape(rfinal, RShape.createRectangle(random(120), random(200), 20,20));
//         println(i);
// // 

//     }
//     float b = millis();
    rfinal = RShape.createEllipse(150, 50, 200,200);
    println(rfinal.getTangentsInPaths().length, rfinal.getPointsInPaths().length);
    // println(rfinal.getTangents().length, rfinal.getPointsInPaths()[0].length);
    // for(int i = 0 ; i< rfinal.getTangentsInPaths()[0].length; i++){
    for(int i = 0 ; i< rfinal.getPointsInPaths()[0].length; i++){
        RPoint p = rfinal.getTangentsInPaths()[0][i];
        float fact = (float)i / rfinal.getPointsInPaths()[0].length; 
        // println(fact);
        RPath pp = new RPath(rfinal.getPointsInPaths()[0]); 
        RPoint t = pp.getTangent(fact);
        println(t.x, t.y);

        
        RPoint org = pp.getPoint(fact);
        // println(p.x, p.y);
        // RPoint org = rfinal.getPointsInPaths()[0][i];
        g.drawLine(org.x, org.y, org.x + 50*t.x  , org.y + 50*t.y);
        // g.drw

    }

    // rfinal.print();

        // println( " --- children ", rfinal.countChildren(), " ---- paths  ", rfinal.countPaths());
        // for (int j= 0 ; j < rfinal.countPaths(); j++){
        //     println("     Path ",j, "  ==>> ", rfinal.getPointsInPaths()[j].length);
        // }

    // println("////////////////////////");
    // println(b-a);
    // println("////////////////////////");
   
     g.drawRShape(rfinal); // Convert and draw lines to gcode instructions
   g.show();
     g.writeToFile(); // write GCode


}

import gcode.generative.*;

Gcoder g;

void setup(){
    size(800,800);

    g=new Gcoder(this, "output.gcode", 300,300, 2.5,100,25, 150,200);


    // Line l = new Line(g, 0,0, 50,50);
    // l.draw();
    g.drawLine(0,0,50,50);
    g.drawRect(30,30, 80,100);
    g.drawRect(80,80, 200,200);

    g.writeToFile();
    println(g.canvasWidth);
    println(g.canvasHeight);
    g.pushMatrix();
    g.translate(20,20);
    g.popMatrix();
}




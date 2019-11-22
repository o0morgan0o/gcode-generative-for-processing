import gcode.generative.*;

Gcoder g;

void setup(){
    size(800,800);

    g=new Gcoder(this, "output.gcode", 300,300, 2.5,100,25, 180,250);


	// draw with a class Line
     Line l = new Line(g, 50,50, 100,50);
     l.draw();
     
    // or draw directly 
    g.drawLine(50,50, 100, 70);

    g.drawRect(50,100, 60,60);
    g.drawRect(70,120, 100, 60);

    g.show();

    g.writeToFile();
}




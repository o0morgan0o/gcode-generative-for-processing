import peasy.*;
import gcode.generative.*;

PGraphics canvas;
PImage img;
Gcoder g;

void setup(){

size(800,800,P2D);

g= new Gcoder(this, "output", 300,300,2.5, 100,10, 180, 270);


img = loadImage("mar.jpg");
img.loadPixels();
// cam = new PeasyCam(this,0,0,0,100);
int counterx =0;
int countery = 0;
for(int x = 0 ; x < 600 ; x += 20 ){
    
    for(int y = 0 ; y < 600; y += 20){
        

        int index = 600 * y + x;
        color c =img.get(x,y);
        float b = brightness(c);
        //go from 0 to 180
    //    fill(c); 
    //    noStroke();
    //    rect(x,y, 20,20);
    // int nbArrows =floor(map(b,0,180, 3,0));
    // int nbArrows = 1;
    
    // for (int arrows = 0 ; arrows < nbArrows ; arrows++){
    //     //pick random
    //     PVector a = new PVector(random(10), random(10));
    //     PVector aa = new PVector(random(10), random(10));
    //     int xx = floor(map(x,0, 600, 0, g.canvasWidth -10));
    //     int yy = floor(map(y,0,600,0, g.canvasHeight -10));
    //     // g.drawLine(x+a.x, y+a.y, x+aa.x, y+aa.y);
    //     // g.drawLine(xx + a.x, yy + a.y, xx + aa.x, yy + a.y);
    //     rect(x +5*arrows, y, 5, 5);
    // }

    

    countery++;
    }
    counterx++;
}

g.show();
g.writeToFile();
g.writeCalibrationGcode();

}

void draw(){
    
    // image(img, 0, 0);
}
import peasy.*;
import gcode.generative.*;

PGraphics canvas;
PImage img;
Gcoder g;

void setup(){

size(800,800,P2D);

g= new Gcoder(this, "output", 300,300,2.5, 100,25, 180, 270);


img = loadImage("will.jpg");
img.loadPixels();
// cam = new PeasyCam(this,0,0,0,100);
int counterx =0;
int countery = 0;

final int lenPix = 5;

for(int x = 0 ; x < 180 ; x += lenPix ){
        println(x);
    for(int y = 0 ; y < 270; y += lenPix){

        
        int index = 180 * y + x;


        color c =img.get(x,y);
        float b = brightness(c);
        //go from 0 to 180
       fill(c); 
       noStroke();

   if( b < 130) {
       for(int j = 0 ; j< 3; j++){
        float a = getRandomInSquare(x, lenPix);
        float bb = getRandomInSquare(y, lenPix);
        float cc = getRandomInSquare(x, lenPix);
        float d = getRandomInSquare(y, lenPix);
        g.drawLine(a,bb,cc,d);
        line(a,bb,cc,d);
        }
   }

   if( b < 70) {
       for(int j = 0 ; j< 5; j++){
        float a = getRandomInSquare(x, lenPix);
        float bb = getRandomInSquare(y, lenPix);
        float cc = getRandomInSquare(x, lenPix);
        float d = getRandomInSquare(y, lenPix);
        g.drawLine(a,bb,cc,d);
        line(a,bb,cc,d);
        }

   }
   if( b < 10) {
       for(int i = 0 ; i< 10; i++){

        float a = getRandomInSquare(x, lenPix);
        float bb = getRandomInSquare(y, lenPix);
        float cc = getRandomInSquare(x, lenPix);
        float d = getRandomInSquare(y, lenPix);
        g.drawLine(a,bb,cc,d);
        line(a,bb,cc,d);
        // }
       }

   }
    // int nbArrows =floor(map(b,0,180, 30,0));
    
    // for (int arrows = 0 ; arrows < nbArrows ; arrows++){
        //pick random
        // PVector a = new PVector(random(10), random(10));
        // PVector aa = new PVector(random(10), random(10));
        // int xx = floor(map(x,0, 600, 0, g.canvasWidth -10));
        // int yy = floor(map(y,0,600,0, g.canvasHeight -10));
        // // g.drawLine(x+a.x, y+a.y, x+aa.x, y+aa.y);
        // g.drawLine(x,y, x+1,y+1);
        // // rect(x +5*arrows, y, 5, 5);
    // }

    

    countery++;
    }
    counterx++;
}
g.show();
g.writeToFile();
g.writeCalibrationGcode();

}
float getRandomInSquare(int x ,  int lenPix){
    return random(x, x + lenPix);
}

void draw(){
    
    // image(img, 0, 0);
}
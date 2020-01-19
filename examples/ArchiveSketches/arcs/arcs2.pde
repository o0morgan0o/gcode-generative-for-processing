
/**
* part of the example files of the gcode-generative-for-processing library
* show the basics of drawing lines or rectangles.
* Morgan Thibert - Novembre 2019
* morgan.thibert@protonmail.com
*/
import gcode.generative.*;

Gcoder g;

void setup(){
    size(800,800);
    noiseSeed(30);

    g=new Gcoder(this,  // necessary
    "output.gcode",  	// name of the output file
    300,				// Physical Limit of the printer on X (300mm for cr10)
    300,				// Physical Limit of the printer on Y (300mm for cr10)
    2.5,				// Z offset between drawing position and moving position
    100,				// X position of the sheet (beginning of the drawing)
    25,					// Y position of the sheet (beginning of the drawing)
    180,				// width of the sheet
    250);				// height of the sheet
	

     
    // or draw directly 
    // g.drawLine(50,50, 100, 70);

    // g.drawRect(50,100, 60,60);
    // g.drawRect(70,120, 100, 60);


     for(float a = 0 ; a < g.canvasWidth; a +=10){
         for(float aa = 0 ; aa < g.canvasHeight; aa+=10){
             float w= noise(a/100,aa/100);
            //  g.drawRect(a, aa, w *14,w *14);
            PVector centerP = new PVector(100,100);
             g.drawArc(centerP, new PVector(mouseX, mouseY), new PVector(a-10, aa+10),1,true);

         }
     }
    g.show();
    // g.writeToFile();

    g.writeCalibrationGcode(); }


void draw(){
}
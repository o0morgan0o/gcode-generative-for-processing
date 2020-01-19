import peasy.*;

PGraphics canvas;
void setup(){

size(800,800,P2D);

// cam = new PeasyCam(this,0,0,0,100);
canvas = createGraphics(width, height, P3D);
}
// PeasyCam cam;
float counter = 0;

void draw(){
    background(255);
    counter+= .01;
    canvas.beginDraw();
    canvas.background(255,255,255);
    canvas.pushMatrix();
    canvas.translate(width/2,height/2,0); 
    canvas.rotateX(-PI/6);
    canvas.rotateY(counter);
    canvas.rotateZ(counter/1.5);
    PShape s;
    s=createShape();
    s.setStrokeWeight(10);
    s.beginShape();
    s.vertex(-100,0,-100);
    s.vertex(-100,0,100);
    s.vertex(100,0,100);
    s.vertex(100,0,-100);

    s.endShape(CLOSE);
    s.setFill(color(0,0,255));
    canvas.shape(s);

    float mx = canvas.screenX(s.getVertex(3).x, s.getVertex(3).y, s.getVertex(3).z);
    float my = canvas.screenY(s.getVertex(3).x, s.getVertex(3).y, s.getVertex(3).z);
    // float mz = canvas.modelZ(s.getVertex(3).x, s.getVertex(3).y, s.getVertex(3).z);
    canvas.popMatrix();
    canvas.endDraw();
    image(canvas,0,0,width,height);

    stroke(255,0,0);
    strokeWeight(4);

    line(0,0,mx,my);
}
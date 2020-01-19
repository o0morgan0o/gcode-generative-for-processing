import peasy.*;
PeasyCam cam;
PGraphics canvas;


void setup(){
    size(800,800,P2D);

}


void draw(){

    
    canvas = createGraphics(400,400, P3D);
    // cam = new PeasyCam(this.canvas, 0,0,0,50);




    // canvas.ortho();
    PVector eye = new PVector(map(mouseX, 0,width, -400,400),200,200);
    PVector center = new PVector(0,0,0);
    PVector upFace = new PVector(0,-1,0);
    
    canvas.beginDraw();
    canvas.background(255,0,0);
    canvas.stroke(0);

    //ortho camera ---------------------
    canvas.ortho(-200,200,-200,200);
    canvas.translate(200,200,0);
    canvas.rotateX(-PI/6);
    canvas.rotateY(PI/3);

    // standard camera -------------------
    // canvas.camera(eye.x, eye.y, eye.z, center.x , center.y, center.z, upFace.x, upFace.y,upFace.z );
    // float fov = map(mouseY, 0,height, 0, PI);
    // // float cameraZ = (height/2.0)/ tan(fov/2.0);
    // float cameraZ = (height/2.0)/ tan((PI/3)/2.0);
    // float depth = 100;
    // canvas.perspective(fov, width/height ,cameraZ/depth, cameraZ * depth);


    for(int i = 0 ; i < 5; i++){
        canvas.translate(20*i,0,0);
        canvas.box(20);
        canvas.translate(-40*i,0,0);
        canvas.box(20);
        canvas.translate(20*i,0,0);
    }
    canvas.endDraw();


    image(canvas,0,0);
    // canvas.printCamera();
    // println(canvas.camera());
    // canvas.camera().position();

}
package gcode.generative;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;
import processing.core.PVector;

public class GcodeRectMesh {

    public PShape s;
    float meshWidth;
    float meshHeight;
    int meshsPerWidth;
    int meshsPerHeight;
    PVector[] vertexes;
    PVector[] screenVertexes;
    PVector center;
    PVector position;
    Line[] lines;
    PApplet parent;
    PGraphics canvas;
    Gcoder g;
 
    public GcodeRectMesh(Gcoder _g, PApplet _myParent,PGraphics _canvas, PVector _pos, float _meshWidth, float _meshHeight, int _meshsPerWidth, int _meshsPerHeight) {
        meshWidth = _meshWidth;
        meshHeight = _meshHeight;
        meshsPerWidth = _meshsPerWidth;
        meshsPerHeight = _meshsPerHeight;
        position = new PVector(_pos.x, _pos.y, _pos.y);
        parent = _myParent;
        canvas = _canvas;
        g = _g;
        


        s = parent.createShape();
        s.beginShape();
        for (float ii = (float) (- meshHeight / 2.0); ii < meshHeight / 2.0; ii += meshHeight / (float) meshsPerHeight) {
            for (float i = (float) (- meshWidth / 2.0); i < meshWidth / 2.0; i += meshWidth / (float) meshsPerWidth) {
                s.vertex(i + position.x, ii + position.y, 40 * parent.noise(i / 40, ii / 80) + position.z);
            }
        }
        s.endShape();
        screenVertexes = new PVector[s.getVertexCount()];
    }
    
    

    public void updateScreenVertexes() {
        for (int i = 0; i < s.getVertexCount(); i++) {
            PVector v = s.getVertex(i);
            float mx = canvas.screenX(v.x, v.y, v.z);
            float my = canvas.screenY(v.x, v.y, v.z);
            screenVertexes[i] = new PVector(mx, my);
        }
    }


    public void drawQuads() {
        drawHorizontalTiles();
        drawVerticalTiles();
    }
    public void drawHorizontalTiles(){
        for (int i = 1; i < s.getVertexCount(); i++) {
            if (i % meshsPerWidth != 0) {
                PVector v = screenVertexes[i];
                PVector prevV = screenVertexes[i - 1];
                Line l = new Line(g, v.x, v.y, prevV.x, prevV.y);
                l.draw();
            }
        }

    }

    public void drawVerticalTiles(){
        for (int ii = 0; ii < meshsPerWidth; ii++) {
            for (int i = meshsPerWidth; i < s.getVertexCount(); i += meshsPerWidth) {
                int index = i + ii;
                PVector v = screenVertexes[index];
                PVector prevV = screenVertexes[index - meshsPerWidth];
                Line l = new Line(g, v.x, v.y, prevV.x, prevV.y);
                l.draw();
            }
        }

    }

}

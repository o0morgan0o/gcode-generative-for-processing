package gcode.generative;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;
import processing.core.PVector;

public class GcodeRectMesh {

    public PShape s;
    public float meshWidth;
    public float meshHeight;
    public int meshsPerWidth;
    public int meshsPerHeight;
    public float[][] vertexesModifierZ;
    public PVector[] screenVertexes;
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
        
        //initialisation parametres vertexesModifierZ
        vertexesModifierZ = new float[meshsPerHeight][meshsPerWidth];
        for (int i = 0 ; i < meshsPerHeight; i++) {
        	for(int j = 0 ; j < meshsPerWidth; j++) {
        	vertexesModifierZ[i][j] = 0;
        	}
        }
        


        s = parent.createShape();
        s.beginShape();
        int counteri = 0;
        int counterj = 0;
        for (float i = (float) (- meshHeight / 2.0); i < meshHeight / 2.0; i += meshHeight / (float) meshsPerHeight) {
            for (float j = (float) (- meshWidth / 2.0); j < meshWidth / 2.0; j += meshWidth / (float) meshsPerWidth) {
                s.vertex(j + position.x, i + position.y, vertexesModifierZ[counteri][counterj]+ position.z);
                counteri++;
            }
            counteri=0;
            counterj++;

        }
        s.endShape();
        screenVertexes = new PVector[s.getVertexCount()];
    }

    public void updateVertexesModifierZ(float[][] newArr) {
    	vertexesModifierZ = newArr;
    	updateVertexes();
    }
    
    private void updateVertexes() {
        int counteri = 0;
        int counterj = 0;
        for (float i = (float) (- meshHeight / 2.0); i < meshHeight / 2.0; i += meshHeight / (float) meshsPerHeight) {
            for (float j = (float) (- meshWidth / 2.0); j < meshWidth / 2.0; j += meshWidth / (float) meshsPerWidth) {
            	int index = counterj * meshsPerWidth + counteri;
            	PVector t = s.getVertex(index);
            	t.z = vertexesModifierZ[counteri][counterj] + position.z;
                s.setVertex(index, t);
                counteri++;
            }
            counteri=0;
            counterj++;

        }
    	
    }
    
    

    public void calculate2DProjection() {
        for (int i = 0; i < s.getVertexCount(); i++) {
            PVector v = s.getVertex(i);
            float mx = canvas.screenX(v.x, v.y, v.z);
            float my = canvas.screenY(v.x, v.y, v.z);
            screenVertexes[i] = new PVector(mx, my);
        }
    }


    public void drawQuads() {
        drawHorizontalTiles(false);
        drawVerticalTiles(false);
    }
    
    
    public void drawHorizontalTiles(boolean try_optimize){
        for (int i = 1; i < s.getVertexCount(); i++) {
            if (i % meshsPerWidth != 0) {
                PVector v = screenVertexes[i];
                PVector prevV = screenVertexes[i - 1];
                Line l = new Line(g, prevV.x, prevV.y, v.x, v.y, try_optimize);
                l.draw();
            }
        }

    }

    public void drawVerticalTiles(boolean try_optimize){
        for (int ii = 0; ii < meshsPerWidth; ii++) {
            for (int i = meshsPerWidth; i < s.getVertexCount(); i += meshsPerWidth) {
                int index = i + ii;
                PVector v = screenVertexes[index];
                PVector prevV = screenVertexes[index - meshsPerWidth];
                Line l = new Line(g, prevV.x, prevV.y, v.x, v.y, try_optimize);
                l.draw();
            }
        }

    }

}

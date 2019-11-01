package gcode.generative;

import processing.core.PVector;
import gcode.generative.Line;

public class Rectangle {
	  Line[] lines;
	    float minX , maxX, minY, maxY;
	    Gcoder gcoder;

	    public Rectangle(Gcoder _gcoder, float _originX, float _originY, float _rectWidth, float _rectHeight){
	    	gcoder = _gcoder;
	        lines = new Line[4];
	        lines[0] = new Line(gcoder ,_originX, _originY,                                 _originX + _rectWidth, _originY);
	        lines[1] = new Line(gcoder, _originX + _rectWidth, _originY,                    _originX + _rectWidth, _originY + _rectHeight );
	        lines[2] = new Line(gcoder, _originX + _rectWidth, _originY + _rectHeight,      _originX, _originY + _rectHeight);
	        lines[3] = new Line(gcoder, _originX, _originY + _rectHeight,                   _originX, _originY);
	        minX = _originX;
	        maxX = minX + _rectWidth;
	        minY = _originY;
	        maxY = minY + _rectHeight;
	    } 

	    public void draw(){
	        for (Line l : lines){
	            l.draw();
	        }
	    }

	    PVector getMiddlePoint(){
	       // float middleX = (myParent.lines[0].origin.x + lines[1].origin.x + lines[2].origin.x + lines[3].origin.x) / 4.;
	        //float middleY = (lines[0].origin.y + lines[1].origin.y + lines[2].origin.y + lines[3].origin.y) / 4.;
	        //return ( new PVector(middleX, middleY));
	    	return null;
	    }

}

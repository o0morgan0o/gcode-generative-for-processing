# Processing GCODE for CR10 pen Plotter



## Main

This library aims at easily create gcode file from simple shapes creates within Processing code.


## Usage


```
g = new Gcoder(this, "outputFile", 300,300, 2.5, 100, 25, 193, 278);
// Parameters are PHYSICALLIMITX, PHYSICALLIMITY, amplitudeOnZ, canvasOriginX, canvasOriginY, canvasWidth, canvasHeight
```

Drawing functions:

```
Line l = new Line(0,0, 50,50) // Draw a line from points (0,0) and (50,50)
// or
g.drawLine(0,0,50,50);
```

```
Rectangle r = new Rectangle(20,20, 50, 40) // Draw a rectangle at (20,20) and of dimensions 50x40mm
// or
g.drawRectangle(20,20, 50,40);
```

```
// Write to the file
g.writeToFile(currentInstruction);
```


You can translate or rotate in a "push Matrix" style:

```
for(int i = 0 ; i < 20 ; i+= 4)
g.pushMatrix();
    g.translate(i,30);
    g.rotate(PI /3);
    Rectangle r1 = new Rectangle(0,0, 20,55);
g.popMatrix();
```

To save the gcode:
```
g.writeToFile();
```



<!-- 
### Circles and arcs

```
// Add the instruction do draw a circle at point (centerX, centerY) of radius r:
DrawCircle(centerX, centerY, r);
``` -->

<!-- 
### Other functions

```
fillCircles();
noFillCircles();
setAmplitudeZ(z);
setFillDensity(d);
``` -->
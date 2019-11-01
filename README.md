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


## Usage with Geomerative

In order to have the possibility to draw with a virtual "fill", I made this library to use with Geomerative Library for processing. Operations are made on the RShapes in order to know which contour should be drawn or not.

### Basic Usage

```
// Initialization
RG.init(this);
RG.ignoreStyles(true);
RG.setPolygonizerLength(10);
RG.setPolygonizer(RG.ADAPTATIVE);
```

The filled shapes must all be drawn in a RShape:

```
RShape rfinal;
rfinal = new RShape();
```

#### Add Filled Rectangle:
`rfinal = addFilledShape(rfinal, RShape.createRectangle(20,20,100,100));`

#### Add Filled Ellipse:
`rfinal = addFilledShape(rfinal, RShape.createEllipse(20,20,100,100));`


#### Add Bezier Curve
a faire

### Usage en 3D
a faire

To draw the ensemble, and add it to printable GCODE, just do:
==TODO: changer de nom et integrer a la librairie.
`drawLines(rfinal);` 

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
# Processing GCODE for CR10 pen Plotter



## Main

This library aims at easily create gcode file from simple shapes creates within Processing code.

Please see the examples in the examples folder. It is the best way to understand the different functions.

This library was designed for usage with the Creality CR10. I haven't yet tested other printers so if you need to adjust the gcode, you will be have to change it in the source code of this library or make a python script to correct the instructions according to your needs.

***This library is still WorkInProgress. The functions and usage will probably be updated and adjusted in the future.***

## Usage


```java
g = new Gcoder(this, "outputFile", 300,300, 2.5, 100, 25, 193, 278);
// Parameters are PHYSICALLIMITX, PHYSICALLIMITY, amplitudeOnZ, canvasOriginX, canvasOriginY, canvasWidth, canvasHeight
```

![Gcoder Init](./img/gcoder.jpg)

> All dimensions are in mm.

Drawing functions:

```java
Line l = new Line(g,0,0, 50,50) // Draw a line from points (0,0) and (50,50)
// or
g.drawLine(0,0,50,50);
```

```java
Rectangle r = new Rectangle(g, 20,20, 50, 40) // Draw a rectangle at (20,20) and of dimensions 50x40mm
// or
g.drawRectangle(20,20, 50,40);
```

___


You can translate or rotate in a "push Matrix" style:

```java
for(int i = 0 ; i < 20 ; i+= 4)
g.pushMatrix();
    g.translate(i,30);
    g.rotate(PI /3);
    Rectangle r1 = new Rectangle(0,0, 20,55);
g.popMatrix();
```

At the end of the file you will want to save all the instructions in the gcode file:
```java
// Write to the file
g.writeToFile();
```


## Usage with Geomerative

In order to have the possibility to draw with a virtual "fill", I made this library to use with Geomerative Library for processing. Operations are made on the RShapes in order to know which contour should be drawn or not.

![filled Shapes](./img/filledShapes.jpg)


### Basic Usage

Please see the geomerative library documentation for more information on the different setPolygonizer options. It impacts the way the shapes are interpolated.
For the best quality according to my tries, choose the following :

```java
// Initialization
RG.init(this);
RG.setPolygonizer(RG.UNIFORMLENGTH);
RG.setPolygonizerLength(1);
```

The filled shapes must all be drawn in a RShape:

```java
RShape rfinal;
rfinal = new RShape();
```

When you want to draw the RShape you can call the function:
```java
g.drawRShape(rfinal);
// or you can specify the resolution as a parameter
g.drawRShape(rfinal, .05);
// the resolution must be between 0 and 1. (best is near 0).
// By default, the resolution passed is 0.01
```

#### Add Filled Rectangle or Ellipse:

```java
rfinal = addFilledShape(rfinal, RShape.createRectangle(20,20,100,100));
rfinal = addFilledShape(rfinal, RShape.createEllipse(20,20,100,100));
```

#### Add Bezier Curve
<!-- a faire -->
TODO

### 3D Sketch
TODO = add documentation


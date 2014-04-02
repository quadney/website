class GeneratedArt {
  int xBound = 85;
  int yBound = 85;
  int x; int y;
  int draw;
  Line line;
  Circle circle;
  Rectangle rectangle;
  int constraintX, constraintY, constraintX2, constraintY2;
  PGraphics pGraphics;
  color c;
  
  GeneratedArt(PGraphics pg){
    x = 0;
    y = 0;
    this.pGraphics = pg;
  }
  
  void newDrawing(){
    //figure out place to start drawing, based on probability
    chooseNewDrawingLocation();
    chooseWhichThingToDraw();
    c = randomColor();
    //figure out which type of drawing to make (lines, arcs, circles, etc.)
  }
  
  void chooseNewDrawingLocation(){
    int rand = (int)random(101);
     if(rand <= 1){
       //top panel, not including sides
       setConstraint(0, width, 0, yBound);
       y = (int)random(yBound);
     }
     else if(rand <= 5){
       //left top square panel
       setConstraint(0, xBound, 0, yBound);
       y = (int)random(yBound);
     }
     else if(rand <= 10){
       //right top spuare panel
       setConstraint(width-xBound, width, 0, yBound);
       y = (int)random(yBound);
     }
     else if(rand <= 35){
       //left sidebar
       setConstraint(0, xBound, yBound, height);
       y = (int)Math.floor(random(30))* 23 + yBound;
     }
     else{
       //right sidebar
       setConstraint(width-xBound, width, yBound, height);
       y = (int)Math.floor(random(30))* 23 + yBound;
     }
  }
  void setConstraint(int x1, int x2, int y1, int y2){
    x = (int)random(x1, x2);
    constraintX = x1;
    constraintX2 = x2;
    constraintY = y1;
    constraintY2 = y2;
  }
  
  void chooseWhichThingToDraw(){
    float rand = random(1.0);
    if(rand >= .80){
      draw = 0;
      x+= 10;
      y+=10;
      rectangle = new Rectangle(x, y, constraintX, constraintY, constraintX2, constraintY2, pGraphics); 
    }
    else if(rand >= .50){
      draw = 1;
      x+= 10;
      y+=10;
      circle = new Circle(x, y, constraintX, constraintY, constraintX2, constraintY2, pGraphics);
    }
    else{
      draw = 2;
      line = new Line(x, y, constraintX, constraintY, constraintX2, constraintY2, pGraphics);
    }
  }
  
  void generate(){
    switch(draw){
      case 0:
        drawRectangles();
        break;
      case 1:
        drawCircles();
        break;
      case 2:
        drawLines();
        break;   
    }
  }
  
  void drawLines(){
    line.drawLine(c);
  }
  
  void drawRectangles(){
    rectangle.drawRectangle(c);
  }
  
  void drawCircles(){
    circle.drawCircle(c);
  }
  
  color randomColor(){
     return color((float)Math.floor(random(0, 128)), (float)Math.floor(random(0, 128)), (float)Math.floor(random(0, 128)), 100.0);
  }
  
}

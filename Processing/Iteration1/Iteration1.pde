class Circle{
  int x, y, h;
  int constraintX, constraintY, constraintX2, constraintY2;
  PGraphics pg;
 
 Circle(int x, int y, int constraintX, int constraintY, int constraintX2, int constraintY2, PGraphics graphics){
   this.x = x;
   this.y = y; 
   h = 20;
   this.constraintX = constraintX;
   this.constraintY = constraintY;
   this.constraintX2 = constraintX2;
   this.constraintY2 = constraintY2;
   this.pg = graphics;
 }
 
 void drawCircle(color c){
   pg.beginDraw();
   pg.stroke(c);
   pg.noFill();
   pg.ellipse(x, y, h, h);
   if(random(1.0) > .5){
      //fill in with lines
      pg.fill(c);
      pg.noStroke();
      pg.ellipse(x, y, h, h);
   }
   if(random(1.0) > .5){
     //then recalculate the x and y's
     
     //increment or decrement x, but only increment y if it reaches the end of the constraint
       if(random(1.0) > .5){
          //add to x 
          x += h + random(3)-1;
       }
       else{
          //subtract from x 
          x -= h + random(3)-1;
       }
       
       if(x < constraintX + h/2){
         //also go to the next line
         x = constraintX + (int)random(3) + h/2;
         y += Math.floor(random(3)-1)* h;
       }
       else if(x > constraintX2 - h/2){
         x = constraintX2 - (int)random(3) -h/2;
         y += Math.floor(random(3)-1)* h;
       }
       
       if(y < constraintY +h/2)
         y = constraintY + (int)random(3)+h/2;
       else if(y > constraintY2 - h/2)
         y = constraintY2 - (int)random(3)-h/2;
   }
   pg.endDraw();
   image(pg, 0, 0);
   
   
 }
  
}
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
class Line{
  int x, y, h, constrainYstart, endY, endX, constrainYend;
  int constraintX, constraintY, constraintX2, constraintY2;
  PGraphics pg;
 
 Line(int x, int y, int constraintX, int constraintY, int constraintX2, int constraintY2, PGraphics graphics){
   this.x = x;
   this.y = y; 
   h = 23;
   this.constrainYstart = y;
   this.endY = y + h;
   this.endX = x;
   this.constrainYend = endY;
   
   this.constraintX = constraintX;
   this.constraintY = constraintY;
   this.constraintX2 = constraintX2;
   this.constraintY2 = constraintY2;
   this.pg = graphics;
 }
 
 void drawLine(color c){
   pg.beginDraw();
   pg.stroke(c);
   endX = x + (int)random(3)-1;
   endY = y + h + (int)random(3)-1;
   pg.line(x, y, endX, endY);
   pg.endDraw();
   image(pg, 0, 0);
   
  //increment or decrement x, but only increment y if it reaches the end of the constraint
   x += random(5)-2;
   //maybe increment/decrement y
   float test = random(1.0);
   if(test >= .90){
     y += 1;
   }
   else if (test >= .80){
     y -= 1;
   }
   
   if(x < constraintX ){
     //also go to the next line
     x = constraintX + (int)random(3) + h/2;
     y += Math.floor(random(3)-1)*h;
     constrainYstart = y;
     endY = y+h;
     constrainYend = endY;
   }
   else if(x > constraintX2 ){
     x = constraintX2 - (int)random(3) -h/2;
     y += Math.floor(random(3)-1)* h;
     constrainYstart = y;
     endY = y+h;
     constrainYend = endY;
   }
   
   if(y < constraintY +h/2)
     y = constraintY + (int)random(3)+h/2;
   else if(y > constraintY2 - h/2)
     y = constraintY2 - (int)random(3)-h/2;
     
     
     
     constrain(y, constrainYstart, constrainYstart+5);
     constrain(endY, constrainYend-5, constrainYend);
     constrain(endX, x-2, x+2);
 }
  
}
class LinePaper {
  int xBound = 85;
  int yBound = 85;
  int innerWidth;
  int innerHeight;
  String title = "Note Art";
  PFont font24, font16;
  String typeStuff;
  PGraphics pg;
  
  LinePaper(int width, int height, PGraphics graphics){
    this.pg = graphics;
    font24 = loadFont("Avenir-Book-24.vlw");
    font16 = loadFont("Avenir-Book-16.vlw");
    innerWidth = width - xBound*2;
    innerHeight = height - 119;
    typeStuff = "Start Typing...";
    drawNotebookStuff(typeStuff);
    
    drawGeneratedArts();
  }
  
  void drawGeneratedArts(){
  }
  
  void drawNotebookStuff(String strings) {
    this.setTypeStuff(strings);
    pg.beginDraw();
    pg.background(255);
    
    //draw blue lines
    pg.stroke(0, 255, 255);
    for (int i = yBound; i < height; i += 23) {
      pg.line(0, i, width, i);
    }
    
    //draw red lines
    pg.stroke(255, 0, 0);
    pg.line(xBound, 0, xBound, height);
    pg.stroke(255, 0, 0, 128);
    pg.line(width-xBound, 0, width-xBound, height);
  
    //draw holes
    pg.stroke(0);
    pg.fill(255);
    for (int i = 75; i < height; i+= 325) {
      pg.ellipse(40, i, 20, 20);
    }
  
    //draw title 
    pg.fill(0);
    pg.textFont(font24, 24);
    pg.textAlign(CENTER, BOTTOM);
    pg.text(title, xBound, 62, innerWidth, 30);
  
    //draw written words
    pg.textFont(font16, 16);
    pg.textAlign(LEFT, TOP);
    pg.text(typeStuff, xBound+1, 119, innerWidth, innerHeight);
    
    pg.endDraw();
    
    msOfLastKeyRelease = millis();
  }

  void setTypeStuff(String words){
    typeStuff = words;
  }
}
class Rectangle{
  int x, y, h;
  int constraintX, constraintY, constraintX2, constraintY2;
  PGraphics pg;
 
 Rectangle(int x, int y, int constraintX, int constraintY, int constraintX2, int constraintY2, PGraphics graphics){
   this.x = x;
   this.y = y; 
   h = 20;
   this.constraintX = constraintX;
   this.constraintY = constraintY;
   this.constraintX2 = constraintX2;
   this.constraintY2 = constraintY2;
   this.pg = graphics;
 }
 
 void drawRectangle(color c){
   pg.beginDraw();
   pg.stroke(c);
   pg.noFill();
   pg.rectMode(CENTER);
   pg.rect(x, y, h, h);
   
   if(random(1.0) > .5){
      //fill in with lines
      pg.fill(c);
      pg.noStroke();
      pg.rect(x, y, h, h);
   }
   if(random(1.0) > .5){
         //increment or decrement x, but only increment y if it reaches the end of the constraint
         if(random(1.0) > .5){
            //add to x 
            x += h + random(3)-1;
         }
         else{
            //subtract from x 
            x -= h + random(3)-1;
         }
         
         if(x < constraintX + h/2){
           //also go to the next line
           x = constraintX + (int)random(3) + h/2;
           y += Math.floor(random(3)-1)* h;
         }
         else if(x > constraintX2 - h/2){
           x = constraintX2 - (int)random(3) -h/2;
           y += Math.floor(random(3)-1)* h;
         }
         
         if(y < constraintY +h/2)
           y = constraintY + (int)random(3)+h/2;
         else if(y > constraintY2 - h/2)
           y = constraintY2 - (int)random(3)-h/2;
   }
   pg.endDraw();
   image(pg, 0, 0);
   
   
 }
  
}
boolean startedTyping = false;
int msOfLastKeyRelease = 0;
LinePaper linePaper;
GeneratedArt gen;
String typeStuff = "";
PGraphics generatedImage;
PGraphics linePaperImage;
int tooBored = 0;
//MenuBar menuBar;
//Menu fileMenu;
//MenuItem save;

void setup() {
  //draw notebook paper
  size(609, 800);
  
//  menuBar = new MenuBar();
//  fileMenu = new Menu("File");
//  save = new MenuItem("Save");
//  fileMenu.add(save);
//  menuBar.add(fileMenu);
//  frame.setMenuBar(menuBar);
  
  linePaperImage = createGraphics(609, 800);
  linePaper = new LinePaper(609, 800, linePaperImage);

  generatedImage = createGraphics(609, 800);
  gen = new GeneratedArt(generatedImage);
  generatedImage.beginDraw();
  generatedImage.endDraw();
  
  image(linePaperImage,0,0);
}
void draw() {
  if(startedTyping){
    int currentMilli = millis() - msOfLastKeyRelease;
    if(currentMilli > 7000){
      //if it has been at least 10 seconds (10000 ms) since the last keyrelease,
      //then start drawing
       gen.generate();
       refreshDrawing();
       tooBored++;
     }
    if(tooBored >=700){
     //do it again!
      tooBored = 0;
      gen.newDrawing();
    }
    refreshDrawing();
  }
}
void keyReleased() {
  if (!startedTyping) {
    startedTyping = true;
    typeStuff = ""+key;
    gen.newDrawing();
  }
  else if (keyCode == 8) {
    //delete the last character
    typeStuff.deleteCharAt(typeStuff.length-1);
  }
  else if(keyCode == 16){
  }
  else{
    typeStuff = typeStuff+""+key;
    gen.newDrawing();
  }
  
  if(typeStuff.length == 0){
    typeStuff = "Start Typing...";
    startedTyping = false;
  }
  linePaper.drawNotebookStuff(typeStuff);
  refreshDrawing();
}
void refreshDrawing(){
  image(linePaperImage,0,0);
  image(generatedImage,0,0);
}

void saveImage(){
  save("Note_Art.pdf");
}


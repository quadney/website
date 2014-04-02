class LinePaper {
  int xBound = 85;
  int yBound = 85;
  int innerWidth;
  int innerHeight;
  String title = "Note Art";
  PFont font24, font16;
  StringBuffer typeStuff;
  PGraphics pg;
  
  LinePaper(int width, int height, PGraphics graphics){
    this.pg = graphics;
    font24 = loadFont("Avenir-Book-24.vlw");
    font16 = loadFont("Avenir-Book-16.vlw");
    innerWidth = width - xBound*2;
    innerHeight = height - 119;
    typeStuff = new StringBuffer("Start Typing...");
    drawNotebookStuff(typeStuff);
    
    drawGeneratedArts();
  }
  
  void drawGeneratedArts(){
  }
  
  void drawNotebookStuff(StringBuffer strings) {
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
    pg.text(typeStuff.toString(), xBound+1, 119, innerWidth, innerHeight);
    
    pg.endDraw();
    
    msOfLastKeyRelease = millis();
  }

  void setTypeStuff(StringBuffer words){
    typeStuff = words;
  }
}

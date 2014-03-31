boolean startedTyping = false;
int msOfLastKeyRelease = 0;
LinePaper linePaper;
GeneratedArt gen;
StringBuffer typeStuff = new StringBuffer();
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
    typeStuff = new StringBuffer(""+key);
    gen.newDrawing();
  }
  else if (keyCode == 8) {
    //delete the last character
    typeStuff.deleteCharAt(typeStuff.length()-1);
  }
  else if(keyCode == 16){
  }
  else{
    typeStuff.append(""+key);
    gen.newDrawing();
  }
  
  if(typeStuff.length() == 0){
    typeStuff.append("Start Typing...");
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

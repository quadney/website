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

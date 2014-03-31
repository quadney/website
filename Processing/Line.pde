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

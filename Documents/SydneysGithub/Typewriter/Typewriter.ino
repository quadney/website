int selectedPin = 7;

int value2;
int valueA4;



// the setup function runs once when you press reset or power the board
void setup() {
  Serial.begin(9600);
  // initialize digital pin 13 as an input.
  for(int i = 2; i < 13; i++){
    pinMode(i, INPUT);
  }
  pinMode(A0, INPUT); 
  pinMode(A1, INPUT);
  pinMode(A2, INPUT);
  pinMode(A3, INPUT);
  pinMode(A4, INPUT);
  pinMode(A5, INPUT);
  
  pinMode(selectedPin, OUTPUT);
  digitalWrite(selectedPin, HIGH);
}

// the loop function runs over and over again forever
void loop() {
    
  //print all the pins, whether they are HIGH or LOW, somehow get their address? is that a thing?
  for(int i = 2; i < 13; i++){
    printOutput(i);
  }
  printOutput(A0);
  printOutput(A1);
  printOutput(A2);
  printOutput(A3);
  printOutput(A4);
  printOutput(A5);
  
  // now for the analog, do later
  Serial.println("");
  //delay(1000);              // wait for a second
}

void printOutput(int pin){
  Serial.print(digitalRead(pin));
  Serial.print(" ");
}

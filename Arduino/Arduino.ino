#include <SoftwareSerial.h>  

int bluetoothTx = 2;  // TX-O pin of bluetooth mate, Arduino D2
int bluetoothRx = 3;  // RX-I pin of bluetooth mate, Arduino D3
int led = 5; // for testing purposes; this is a PWM pin, uses analogWrite



SoftwareSerial bluetooth(bluetoothTx, bluetoothRx);

void setup()
{
  Serial.begin(9600);  // Begin the serial monitor at 9600bps
  pinMode(led, OUTPUT);
  bluetooth.begin(115200);  // The Bluetooth Mate defaults to 115200bps
 
}

void loop()
{
  if(bluetooth.available())  // If the bluetooth sent any characters
  {
   
    // Send any characters the bluetooth prints to the serial monitor
    char incoming = (char)bluetooth.read();
    
    // convert char to integer
    int volume = incoming - '0';
    
    if( volume < 0 || volume > 9 ){
      // messed up values
      volume = 0;
    }
    
    
    if ( volume == 0 ){
    
      // keep minimum brightness
      volume = 15;
    }
    else{
      // search for peak values
      volume = volume*28; // convert to 0-255
      
      
    }
   
    
    
    
    Serial.println(volume); 
    analogWrite(led, volume); // volume determines brightness of LED, from 0-9
    }
      
      
}
 
 



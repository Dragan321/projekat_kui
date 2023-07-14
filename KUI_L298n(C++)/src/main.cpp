#include <Arduino.h>
#define IN1 7
#define IN2 6
#define IN3 5
#define IN4 4
void ugasi()
{
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, LOW);

  digitalWrite(IN3, LOW);
  digitalWrite(IN4, LOW);

}
void pokreni()
{
  ugasi();
  digitalWrite(IN1, HIGH);
}
void pokreniSuprotanSmjer()
{
  ugasi();
  digitalWrite(IN2, HIGH);
}
void ocitaj(char pom)
{
  if(pom=='1')
  {
    pokreni();
  }
  else if(pom=='2')
  {
    pokreniSuprotanSmjer();
  }
  else if(pom=='3')
  {
    ugasi();
  }
  
  

}


void setup() {

  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);

  
  Serial.begin(9600);
  
}

void loop() {
  
 
  if(Serial.available()>0)
    { 
      char c=(char)Serial.read();
      ocitaj(c);
    }
   
   
}
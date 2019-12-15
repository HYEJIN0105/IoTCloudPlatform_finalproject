#include "Buzzer.h"

Buzzer::Buzzer(int pin) {
  // Use 'this->' to make the difference between the
  // 'pin' attribute of the class and the
  // local variable 'pin' created from the parameter.
  this->pin = pin;
  init();
}
void Buzzer::init() {
  pinMode(pin, OUTPUT);
  // Always try to avoid duplicate code.
  // Instead of writing digitalWrite(pin, LOW) here,
  // call the function off() which already does that
  off();
  state = Buzzer_OFF;
}
void Buzzer::on() {
  digitalWrite(pin, HIGH);
  state = Buzzer_ON;
}
void Buzzer::off() {
  digitalWrite(pin, LOW);
  state = Buzzer_OFF;
}

byte Buzzer::getState() {
  return state;
}

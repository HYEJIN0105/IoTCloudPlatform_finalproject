#include <Arduino.h>

#define Buzzer_OFF 0
#define Buzzer_ON 1

class Buzzer {
  private:
    int pin;
    byte state;

  public:
    Buzzer(int pin);
    void init();
    void on();
    void off();
    byte getState();
};

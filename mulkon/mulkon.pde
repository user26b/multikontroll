/** 
 * LED Matrix Simulation (v2.04)
 * by Federico Bond (2013/Aug)
 * mod GoToLoop
 * 
 * http://forum.processing.org/topic/led-matrix-simulation
 */
import oscP5.*;
import netP5.*;

final static color[] pallete = {
  #E65350, 
  #FAE400, 
  #A6E800, 
  #7C9CFF,
};

LEDPanel panel;

MKFixture myfix;

void setup() {
  size(500, 500);
  stroke(0);
  strokeWeight(2);
  background(0);

  panel = new LEDPanel();

  String newname = "lampe1";
  String[] newchannels = {"1"};
  myfix = new MKFixture(newname, newchannels, new MKOSCVerbindung("192.168.0.16", "12000"));
}

void draw() {
  if (frameCount % 5 == 0)  panel.randomLED().pulse();
  panel.go();
}

void mouseMoved() {
  panel.within(mouseX, mouseY).pulse();
}

class LED {
  final PVector pos;
  final int w, h;

  final static color STANDARD = #010101;
  final color chosen = pallete[(int) random(pallete.length)];

  color c;
  float amt;

  LED(PVector position, int ww, int hh) {
    pos = position;
    w = ww;
    h = hh;
  }

  void go() {
    update();
    display();
  }

  void pulse() {
    amt = 1.01;
  }

  void update() {
    if ((amt -= .01) > 0)  c = lerpColor(STANDARD, chosen, amt);
  }

  void display() {
    fill(c);
    rect(pos.x, pos.y, w, h);
  }

  boolean within(int x, int y) {
    return x >= pos.x & y >= pos.y & x < pos.x + w & y < pos.y + h;
  }
}

class LEDPanel {
  final static int ROWS = 10, COLS = 10;
  final LED[] leds = new LED[ROWS*COLS];

  LEDPanel() {
    final int w = width/ROWS;
    final int h = height/COLS;

    for (int i=0; i!=ROWS; i++)  for (int j=0; j!=COLS; j++)
      leds[i*COLS + j] = new LED(new PVector(i*w, j*h), w, h);
  }

  void go() {
    for (LED led: leds)  led.go();
  }

  LED randomLED() {
    return leds[(int) random(ROWS*COLS)];
  }

  LED within(int x, int y) {
    for (LED led: leds)  if (led.within(x, y))  return led;
    return null;
  }
}
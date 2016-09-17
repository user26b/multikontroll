/** 
 * LED Matrix Simulation (v2.04)
 * by Federico Bond (2013/Aug)
 * mod GoToLoop
 * 
 * http://forum.processing.org/topic/led-matrix-simulation
 */

import java.util.HashMap;
import java.util.Map;
import oscP5.*;
import netP5.*;

final static int[] pallete = {
  #E65350, 
  #FAE400, 
  #A6E800, 
  #7C9CFF,
};

final static int[] monochrome = {
  #000000, 
  #FFFFFF
};


final static int ncols = 6;
final static int nrows = 8;

LEDPanel panel;
boolean ledmonochrome = true;

MKVerbindung oscverbindung;
MKFixture myfix;
MKKontroller myosc1;

void setup() {
  size(500, 500);
  stroke(0);
  strokeWeight(2);
  background(0);

  panel = new LEDPanel();
  oscverbindung = new MKOSCVerbindung("192.168.0.28", "12000", this);

  String newname = "lampe1";
  String[] newchannels = {"/1/fader2", "/1/fader1"};
  myfix = new MKFixture(newname, newchannels, oscverbindung);

  // int[] newvalues = {11,99};
  // myfix.set_values(newchannels, newvalues);
  // myfix.send_values();
  // myfix.setSend("/1/fader1", 60);
  String stamm = "/1/toggle";
  String[] newcontrlchannels = new String[nrows * ncols];
  for(int i=0; i < nrows * ncols; i++) {
    newcontrlchannels[i] = stamm + i;
  }
  // String[] newcontrlchannels = {"/1/toggle1", "/1/toggle2"};
  // int[] newcontrlvalues = {1,1};
  myosc1 = new MKKontroller("osc1",newcontrlchannels, oscverbindung);

  // myosc1.set_values(newcontrlchannels, newcontrlvalues);
  // myosc1.send_values();
}

void draw() {
  if (frameCount % 5 == 0)  panel.randomLED().pulse();
  panel.go();
}

void mouseMoved() {
  panel.within(mouseX, mouseY).pulse();
}

class LED extends MKPixel{
  final static int ausgangsfarbe = #000000;
  int zielfarbe = pallete[(int) random(pallete.length)];
  boolean mono = ledmonochrome;

  LED(MKVektor position, int ww, int hh) {
    super(position, ww, hh);
    c = #FFFFFF;
    if (mono) {
      zielfarbe = monochrome[1];
    }
  }

  void go() {
    update();
    display();
  }

  void pulse() {
    amt = 1.01;
  }

  void update() {
    int newcol;
    newcol = lerpColor(ausgangsfarbe, zielfarbe, amt);
    if ((amt -= .01) > 0)  c = newcol;
  }

  void display() {
    fill(c);
    rect(pos.x, pos.y, w, h);
  }

}

public class LEDPanel {
  final static int ROWS = 10, COLS = 10;
  final LED[] leds = new LED[ROWS*COLS];

  public LEDPanel() {
    final int w = width/ROWS;
    final int h = height/COLS;

    for (int i=0; i!=ROWS; i++)  for (int j=0; j!=COLS; j++)
      leds[i*COLS + j] = new LED(new MKVektor(i*w, j*h), w, h);
  }

  public void go() {
    for (LED led: leds)  led.go();
  }

  public LED randomLED() {
    return leds[(int) random(ROWS*COLS)];
  }

  public LED within(int x, int y) {
    for (LED led: leds)  if (led.within(x, y))  return led;
    return null;
  }
}




void oscEvent(OscMessage theOscMessage) {
  String[] newvlcstatus;
  // print("### received an osc message.");
  // print(" addrpattern: "+theOscMessage.addrPattern());
  // print(" firstval: "+theOscMessage.get(0).floatValue());
  // println(" typetag: "+theOscMessage.typetag());

  // match statt checkAddrPattern 
  if(match(theOscMessage.addrPattern(), "/1/toggle") != null) {
    int addrNr = int(split(theOscMessage.addrPattern(), "/1/toggle")[1]);
    if(theOscMessage.checkTypetag("f")) {
      float firstValue = theOscMessage.get(0).floatValue(); // get the second osc argument
      panel.leds[int(addrNr) - 1].pulse();
      return;
    }
  }
}

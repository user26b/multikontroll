/** 
 * LED Matrix Simulation (v2.04)
 * by Federico Bond (2013/Aug)
 * mod GoToLoop
 * 
 * http://forum.processing.org/topic/led-matrix-simulation
 */

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
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

final static int dispsizex = ncols * 100;
final static int dispsizey = nrows * 100;

LEDPanel panel;
boolean ledmonochrome = true;

MKVerbindung oscverbindung;
MKFixture myfix;
MKStage mystage;
MKKontroller myosc1;

void setup() {
  frameRate(25);
  size(600, 800);
  stroke(0);
  strokeWeight(2);
  background(0);

  panel = new LEDPanel();
  oscverbindung = new MKOSCVerbindung("192.168.0.28", "12000", this);

  mystage = new MKStage();

  for(int i=0; i < nrows*ncols; i++) {
      String newname = "pixel" + i;
      String[] newchannels = {"/1/toggle" + i};
      mystage.add_fixture(new MKFixture(newname, newchannels, oscverbindung));    
  }

  String newname = "strahler1";
  String[] newchannels = {"/1/fader2", "/1/fader1"};
  mystage.add_fixture(new MKFixture(newname, newchannels, oscverbindung));

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
  // if (frameCount % 5 == 0)  panel.randomLED().pulse();
  panel.go();
  if (frameCount % 5 == 0) mystage.update_all();
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
  final LED[] leds = new LED[nrows*ncols];

  public LEDPanel() {
    final int w = width/ncols;
    final int h = height/nrows;

    for (int i=0; i!=nrows; i++)  for (int j=0; j!=ncols; j++)
      leds[i*ncols + j] = new LED(new MKVektor(j*w, i*h), w, h);
  }

  public void go() {
    for (int i=0; i < leds.length; i++) {
      leds[i].go();
      mystage.get_fixture("pixel" + (i + 1)).set_value("/1/toggle" + (i + 1), (int) (leds[i].amt+1));
    }
  }

  public LED randomLED() {
    return leds[(int) random(nrows*ncols)];
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

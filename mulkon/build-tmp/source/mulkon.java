import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.Map; 
import java.util.Iterator; 
import oscP5.*; 
import netP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class mulkon extends PApplet {

/** 
 * LED Matrix Simulation (v2.04)
 * by Federico Bond (2013/Aug)
 * mod GoToLoop
 * 
 * http://forum.processing.org/topic/led-matrix-simulation
 */







final static int[] pallete = {
  0xffE65350, 
  0xffFAE400, 
  0xffA6E800, 
  0xff7C9CFF,
};

LEDPanel panel;

MKFixture myfix;

public void setup() {
  
  stroke(0);
  strokeWeight(2);
  background(0);

  panel = new LEDPanel();

  MKVerbindung oscverbindung = new MKOSCVerbindung("192.168.0.28", "12000", this);

  String newname = "/1/rotary2";
  String[] newchannels = {"/1/fader2", "/1/fader1"};
  myfix = new MKFixture(newname, newchannels, oscverbindung);

  int[] newvalues = {11,23};
  myfix.set_values(newchannels, newvalues);
  myfix.send_values();

  // println(myfix.get_value_int("1"));
}

public void draw() {
  if (frameCount % 5 == 0)  panel.randomLED().pulse();
  panel.go();
}

public void mouseMoved() {
  panel.within(mouseX, mouseY).pulse();
}

class LED {
  final PVector pos;
  final int w, h;

  final static int STANDARD = 0xff010101;
  final int chosen = pallete[(int) random(pallete.length)];

  int c;
  float amt;

  LED(PVector position, int ww, int hh) {
    pos = position;
    w = ww;
    h = hh;
  }

  public void go() {
    update();
    display();
  }

  public void pulse() {
    amt = 1.01f;
  }

  public void update() {
    if ((amt -= .01f) > 0)  c = lerpColor(STANDARD, chosen, amt);
  }

  public void display() {
    fill(c);
    rect(pos.x, pos.y, w, h);
  }

  public boolean within(int x, int y) {
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

class MKOSCVerbindung extends MKVerbindung{
  public String name = "mkoscverbindung";
  private String ip;
  private String port;
  private OscP5 oscP5;
  private NetAddress theNetAddress;

  public MKOSCVerbindung(String ip, String port, Object parent) {
    this.ip = ip;
    this.port = port;
    int port_as_int = Integer.parseInt(this.port);
    this.theNetAddress = new NetAddress(this.ip, port_as_int);
    oscP5 = new OscP5(parent,12000);
  }

  public NetAddress get_remote_adress() {
    return this.theNetAddress;
  }

  public void send_values(Map<String, Integer> channelValues) {
    for (Map.Entry<String, Integer> entry : channelValues.entrySet()) {
      String newAddress = entry.getKey();
      System.out.println(newAddress);
      OscMessage myMessage = new OscMessage(newAddress);
      // System.out.println("mapsze: " + channelValues.size());
      float newvalasfloat = (float) entry.getValue();
      newvalasfloat = newvalasfloat /100;
      myMessage.add(0.33f);
      System.out.println(myMessage);
      oscP5.send(myMessage, this.theNetAddress);
    }
  }

  public int get_values_int() {
    return 0;

  };



  
}
  public void settings() {  size(500, 500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "mulkon" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

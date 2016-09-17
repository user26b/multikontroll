/**
 * MKFixture.java.
 *
 * Licensed under the LGPL License - http://www.gnu.org/licenses/lgpl.txt
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA  
 */

public class MKPixel {
  MKVektor pos;
  int w, h;
  int c;
  float amt;

  public MKPixel(MKVektor position, int ww, int hh) {
    this.pos = position;
    this.w = ww;
    this.h = hh;
    this.amt = 1;
  }

  public MKPixel(MKVektor position, int ww, int hh, float amt) {
    this.pos = position;
    this.w = ww;
    this.h = hh;
    this.amt = amt;
  }

  public MKPixel(MKVektor position, int ww, int hh, int col) {
    this.pos = position;
    this.w = ww;
    this.h = hh;
    this.c = col;
  }

  void pulse() {
    this.amt = 1;
  }

  boolean within(int x, int y) {
    return x >= this.pos.x & y >= this.pos.y &
     x < this.pos.x + w & y < this.pos.y + h;
  }
}
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

public class MKFixture {
	public String name;
	public MKVerbindung verbindung;
	private String channel;
	private int value;

	public MKFixture (String name, String channel, MKVerbindung verbindung) {
		this.name = name;
		this.verbindung = verbindung;
		this.channel = channel;
		this.value = 0;

	}

	// public String[] get_values_string() {
	// 	return this.verbindung.recieve_this.values;
	// }

	public int get_value_int() {
		return this.verbindung.get_value_int();
	}

	public void set_values(int newval) {
		this.verbindung.send_value_int(this.value, this.channel);
	}


  
}

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

import java.util.HashMap;
import java.util.Map;

public class MKFixture {
	public String name;
	public MKVerbindung verbindung;
	private Map<String, Integer> channelValues = new HashMap<String, Integer>();

	public MKFixture (String name, String[] channels, MKVerbindung verbindung) {
		int initValue = 0;
		this.name = name;
		this.verbindung = verbindung;
		for(int i=0; i < channels.length; i++) {
			this.channelValues.put(channels[i], initValue);
		}

	}

	// public String[] get_values_string() {
	// 	return this.verbindung.recieve_this.values;
	// }

	public int get_values_int() {
		return this.verbindung.get_values_int();
	}

	public void set_values(String[] channels, int[] newvals) {
		for(int i=0; i < channels.length; i++) {
			this.channelValues.put(channels[i], newvals[i]);
		}
	}

	public void set_value(String channel, int newval) {
		this.channelValues.put(channel, newval);
	}

	public void send_values() {
		this.verbindung.send_values(this.channelValues);
	}

	public void setSend(String channel, int newval) {
		this.channelValues.put(channel, newval);
		this.send_values();
	}

	public void setSend_values(String[] channels, int[] newvals) {
		this.set_values(channels, newvals);
		this.send_values();
	}

	public int get_value_int(String channel) {
		return channelValues.get(channel);
	}

	public String[] get_channelNames() {
		return channelValues.keySet().toArray(new String[0]);
	}


  
}
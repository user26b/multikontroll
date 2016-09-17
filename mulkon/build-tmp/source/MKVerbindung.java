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

public abstract class MKVerbindung {
	public String name;
	public String ip;
	private String port;

	void MKVerbindung(String name, String ip, String port) {
		this.name = name;
		this.ip = ip;
		this.port = port;
	}

	abstract void send_value_int(int newval, String channel);

	abstract int get_value_int();



  
}
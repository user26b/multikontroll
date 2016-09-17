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

import oscP5.*;
import netP5.*;

public class MKOSCVerbindung extends MKVerbindung{
	public String name = "mkoscverbindung";
	private String ip;
	private String port;
	private OscP5 oscP5;

	public MKOSCVerbindung(String ip, String port) {
		this.ip = ip;
		this.port = port;
	}

	public NetAddress get_remote_adress() {
		int port_as_int = Integer.parseInt(this.port);
		return new NetAddress(this.ip, port_as_int);
	}

	public void send_value_int(int newval, String channel) {
		OscMessage myMessage = new OscMessage(channel);
	    myMessage.add(newval);
	    oscP5.send(myMessage, this.get_remote_adress());
	}

	public int get_value_int() {
		return 0;

	};



  
}
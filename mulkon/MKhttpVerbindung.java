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
import java.util.Iterator;
import oscP5.*;
import netP5.*;

public class MKhttpVerbindung extends MKVerbindung{
	public String name = "mkhttpverbindung";
	private String ip;
	private String port;

	public MKhttpVerbindung(String ip, String port) {
		this.ip = ip;
		this.port = port;
	}

	public String get_remote_adress() {
		return this.ip + port;
	}

	public void send_values(Map<String, Integer> channelValues) {
	    Iterator it = channelValues.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());

			String newurl = this.get_remote_adress() + pair.getKey() + pair.getValue();
			XMLReader response = new XMLReader();
			try{
	        	response.processDocument(newurl);
	        } catch(Exception e) {
	        	
	        }
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}

	public int get_values_int() {
		return 0;

	};



  
}

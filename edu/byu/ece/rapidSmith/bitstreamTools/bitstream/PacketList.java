/*
 * Copyright (c) 2010-2011 Brigham Young University
 * 
 * This file is part of the BYU RapidSmith Tools.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 * 
 */
package edu.byu.ece.rapidSmith.bitstreamTools.bitstream;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A list of Packet objects. A bitstream is composed of a set of Packet objects and
 * this object is a container class for such packets. 
 * 
 * Other important methods available in this object include:
 * - Special toString for printing packets
 * - Return data as a byte array
 * 
 */
public class PacketList extends ConfigurationData implements Iterable<Packet> {
	
    public PacketList() {
        _packets = new ArrayList<Packet>();
    }
    
    public boolean add(Packet packet) {
        return _packets.add(packet);
    }
    
    public boolean addAll(Collection<Packet> packets) {
        return _packets.addAll(packets);
    }
    
    public boolean addAll(PacketList packetList) {
        boolean result = false;
        for (Packet packet : packetList) {
            boolean addedThisOne = _packets.add(packet);
            result = result || addedThisOne;
        }
        return result;
    }
    
    public Iterator<Packet> iterator() {
        return _packets.iterator();
    }
    
	/**
	 * This method returns a list of bytes that coincide with the packets in the PacketList.
	 * @return An ArrayList of Bytes representing all the packets in the PacketList.
	 */
    @Override
	public List<Byte> toByteArray(){
		List<Byte> bytes = new ArrayList<Byte>();
		
		int size = _packets.size();
		for(int i = 0; i < size; i++){
			bytes.addAll(_packets.get(i).toByteArray());
		}
		
		return bytes;
	}
	
	@Override
	public String toString() {
		return toXML();
	}

	/**
	 * Print information about all packets in this list. This method
	 * will combine adjacent NOPs if requested.
	 * 
	 * @param groupNOPs If true, this method will collapse all of the adjacent NOPs
	 * and indicate how many there are rather than listing them individually.
	 */
	public String toString(boolean groupNOPs) {
		String string = new String();
		int size = _packets.size();
		int nop_count = 0;
		
		for (int i = 0; i < size; i++) {
			Packet p = _packets.get(i);
			if (groupNOPs) {
				if (p.getOpcode() == PacketOpcode.NOP) {
					// count NOP but don't print it
					nop_count++;
				} else {
					// non NOP packet. Check to see if previous packets were NOPs.
					if (nop_count != 0) {
						string += "NOP";
						if (nop_count > 1) 
							string += " ("+nop_count+")";
						nop_count = 0;
						string += "\n";
					}
					string += p.toString(true) + "\n";
				}
			} else {
				// don't group NOPs
				string += p.toString(true) + "\n";				
			}
		}

		return string;
	}
	

	public void toStream(boolean groupNOPs, PrintWriter pw) {
	    int size = _packets.size();
	    int nop_count = 0;

	    for (int i = 0; i < size; i++) {
	        Packet p = _packets.get(i);
	        if (groupNOPs) {
	            if (p.getOpcode() == PacketOpcode.NOP) {
	                // count NOP but don't print it
	                nop_count++;
	            } else {
	                // non NOP packet. Check to see if previous packets were NOPs.
	                if (nop_count != 0) {
	                    pw.print("NOP");
	                    if (nop_count > 1) 
	                        pw.print(" ("+nop_count+")");
	                    nop_count = 0;
	                    pw.print("\n");
	                }
	                pw.print(p.toString(true) + "\n");
	            }
	        } else {
	            // don't group NOPs
	            pw.print(p.toString(true) + "\n");              
	        }
	    }
	}


	public String toXML() {
		String string = new String();
		int size = _packets.size();
		int i = 0;
		int numNOPs = 0;
		
		while(i < size){
			//Since NOPs usually come in large quantities, this function was made to group
			//them together.
			if(_packets.get(i).getOpcode() == PacketOpcode.NOP){
				while( (i < size) && (_packets.get(i).getOpcode() == PacketOpcode.NOP) ){
					numNOPs++;
					i++;	
				}
				string += "<packet nops=\"true\">NOP " + numNOPs + "</packet>\n\n";
				numNOPs = 0;
			}
			//All other packets are written out as normal
			else{
				string += _packets.get(i).toString() + "\n\n";
				i++;
			}
		}
		return string;
	}

	/**
	 * The list of packet objects that makeup a bitstream.
	 */
	protected List<Packet> _packets;

}

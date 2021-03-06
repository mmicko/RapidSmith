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

/**
 * There are two packets types: One and Two.  One is the normal type of packet that is normally
 * used.  A type Two packet follows directly after a type One packet.  A type two packet is used
 * when there is more data to be read/written than can be represented by the type One (Type One
 * can represent a word count with 11 bits whereas type Two can represent the word count with 27
 * bits). 
 * 
 * Bits 31:29 represent the header type.
 * 
 * @author Benjamin Sellers
 * Brigham Young University
 * Created: May 2008
 * Last Modified: 5/2/08
 *
 */
public enum PacketType {
	
	ONE(0x00000001, 0x20000000), 
	TWO(0x000000002, 0x40000000),
	LEGACY(0x00000000, 0x00000000),
	NONE(-1, -1);

	/**
	 * Packet/Header type with bits at 2:0, shifted all the way to the right
	 */
	private final int value;		
	
	/**
	 * Packet/Header type with bits at 31:29, their location in the packet header
	 */
	private final int packetValue;	
	
	/**
	 * Constructor creating a PacketType
	 * @param value This is the value of the header type
	 * @param packetValue This is the header masked with 0xe0000000
	 */
	PacketType(int value, int packetValue) {
		this.value = value;
		this.packetValue = packetValue;
	}
	
	/**
	 * Gets the current value of the header/packet type and returns it
	 * @return Current header type with value in bits 2:0
	 */
	public int Value() {
	    return value;
	}
	
	/**
	 * Gets the current value of the header/packet type and returns it
	 * @return Current header type with value in bits 2:0
	 */
	public int PacketValue() {
	    return packetValue;
	}

	public static int PacketTypeMask = 0xE0000000;
	
	public static PacketType getPacketType(int header) {
	    header = PacketTypeMask & header;
		if(header == 0x20000000)
			return PacketType.ONE;
		else if (header == 0x40000000)
			return PacketType.TWO;
		else if (header == 0x00000000)
		    return PacketType.LEGACY;
		else {
			return PacketType.NONE;
		}
	}
	
	/**
	 * Gets the number of words base on bits 10:0 for a type 1 packet or bits 26:0 for a type 2 packet.
	 * @param header The integer version of the packet.
	 * @return The number of words in the packet.
	 */
	public int getNumWords(int header) {
		int mask = 0;
		if(this == PacketType.ONE) {
			mask = 0x7FF;
		}
		else if(this == PacketType.TWO) {
			mask = 0x7FFFFFF;
		}
		else if (this == PacketType.LEGACY) {
		    mask = 0x0;
		}
		return header & mask;
	}
}

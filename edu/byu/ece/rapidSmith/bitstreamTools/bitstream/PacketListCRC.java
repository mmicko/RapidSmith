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

import java.util.List;

/**
 * Extends the PacketList object by computing the CRC of packets
 * that are created. This simplifies the process of creating
 * new packets and computing the resulting CRC.
 *
 */
public class PacketListCRC extends PacketList {

	public PacketListCRC() {
		super();
		crc = new CRC();
	}
	
	public boolean add(Packet p) {
		crc.updateCRC(p);
		return super.add(p);
	}
	
	public boolean addAll(PacketList packets) {
	    boolean result = false;
        for (Packet packet : packets) {
            boolean addedThisOne = add(packet);
            result = result || addedThisOne;
        }
        return result;
	}

	public boolean addAll(List<Packet> packets) {
	    boolean result = false;
	    for (Packet packet : packets) {
	        boolean addedThisOne = add(packet);
	        result = result || addedThisOne;
	    }
	    return result;
	}

	public Packet addCRCWritePacket() {
		Packet p = PacketUtils.CRC_WRITE_PACKET(crc.computeCRCRegValue());
		add(p);
		return p;
	}

	protected CRC crc;
}

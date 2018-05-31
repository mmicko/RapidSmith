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
package edu.byu.ece.rapidSmith.bitstreamTools.configuration;

import java.util.ArrayList;

import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.BitstreamException;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Packet;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.PacketListCRC;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.PacketOpcode;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.PacketUtils;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.RegisterType;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.XilinxConfigurationSpecification;

/**
 * 
 * Question for Jon: How different are the initial and ending bitstreams
 * for V5 and V6? Is it necessary to have separate classes?
 * 
 */
public class V5BitstreamGenerator extends BitstreamGenerator {

	public static int V5_ENDING_FAR = 0x00EF8000;
    
    private static V5BitstreamGenerator _singleton = null;
    
    private V5BitstreamGenerator() {
        
    }
    
    public static V5BitstreamGenerator getSharedInstance() {
        if (_singleton == null) {
            _singleton = new V5BitstreamGenerator();
        }
        return _singleton;
    }
    
    /**
     * Initial packets of a V5 bitstream.
     */
    public PacketListCRC createInitialFullBitstream(int idcode) {
    
        PacketListCRC packets = new PacketListCRC();
    
        packets.add(PacketUtils.NOP_PACKET);
        packets.add(Packet.buildOneWordPacket(PacketOpcode.WRITE, RegisterType.WBSTAR, 0));
        packets.add(PacketUtils.NULL_CMD_PACKET);
        packets.add(PacketUtils.NOP_PACKET);
        packets.add(PacketUtils.RCRC_CMD_PACKET);
        packets.addAll(PacketUtils.NOP_PACKETS(2));
        packets.add(Packet.buildOneWordPacket(PacketOpcode.WRITE, RegisterType.TIMER, 0));
        packets.add(Packet.buildOneWordPacket(PacketOpcode.WRITE, RegisterType.UNKNOWN0, 0));
        packets.add(PacketUtils.COR_PACKET(0x00003FE5));
        packets.add(Packet.buildOneWordPacket(PacketOpcode.WRITE, RegisterType.COR1, 0));
        packets.add(PacketUtils.IDCODE_PACKET(idcode));
        packets.add(PacketUtils.SWITCH_CMD_PACKET);
        packets.add(PacketUtils.NOP_PACKET);
        packets.add(PacketUtils.MASK_PACKET(0x00400000));
        packets.add(PacketUtils.CTL_PACKET(0x00400000));
        packets.add(PacketUtils.MASK_PACKET(0));       
        //packets.add(Packet.buildOneWordPacket(PacketOpcode.WRITE, RegisterType.CTL1, 0));
        packets.add(PacketUtils.DEFAULT_CTL1_PACKET);
        packets.addAll(PacketUtils.NOP_PACKETS(8));
        
        return packets;
    }

    /**
     * Creates the ending packets of a bitstream
     */
    public PacketListCRC createEndingFullBitstream(PacketListCRC packets, XilinxConfigurationSpecification spec) {

        packets.addCRCWritePacket();
        packets.add(PacketUtils.GRESTORE_CMD_PACKET);       
        packets.add(PacketUtils.NOP_PACKET);
        packets.add(PacketUtils.LFRM_CMD_PACKET);
        packets.addAll(PacketUtils.NOP_PACKETS(100));
        packets.add(PacketUtils.GRESTORE_CMD_PACKET);
        packets.addAll(PacketUtils.NOP_PACKETS(30));
        packets.add(PacketUtils.START_CMD_PACKET);
        packets.add(PacketUtils.NOP_PACKET);
        packets.add(PacketUtils.FAR_WRITE_PACKET(V5_ENDING_FAR));
        packets.add(PacketUtils.MASK_PACKET(0x00400000));
        packets.add(PacketUtils.CTL_PACKET(0x00400000));
        packets.addCRCWritePacket();
        packets.add(PacketUtils.DESYNC_CMD_PACKET);
        packets.addAll(PacketUtils.NOP_PACKETS(61));
        
        return packets;

    }

    /**
     * Creates the initial part of a packet for partial configurations. This
     * list of packets was created based on a bitstream given to us from
     * Sandia.
     */
    public PacketListCRC createInitialPartialBitstream(int idcode) {
    
        PacketListCRC packets = new PacketListCRC();

        // This turns out to be the same as V4
        packets.add(PacketUtils.NOP_PACKET);
        packets.add(PacketUtils.RCRC_CMD_PACKET);       
        packets.addAll(PacketUtils.NOP_PACKETS(2));
        packets.add(PacketUtils.IDCODE_PACKET(idcode));
        packets.add(PacketUtils.WCFG_CMD_PACKET);       
        packets.add(PacketUtils.NOP_PACKET);
        return packets;
    }

    
    /**
     * Creates the initial part of a packet for partial configurations. This
     * list of packets was created based on a bitstream given to us from
     * Sandia.
     */
    public PacketListCRC createEndingPartialBitstream(PacketListCRC packets) {
        		
        packets.add(PacketUtils.MASK_PACKET(0x00001000));
    	packets.add(PacketUtils.DEFAULT_CTL1_PACKET);
        packets.add(PacketUtils.LFRM_CMD_PACKET);
        packets.addAll(PacketUtils.NOP_PACKETS(101));
        packets.add(PacketUtils.FAR_WRITE_PACKET(V5_ENDING_FAR));
        packets.addCRCWritePacket();
        packets.add(PacketUtils.DESYNC_CMD_PACKET);
        // Jonathan Donaldson requested this final NOP. Is it necessary? Should we make
        // this an option?
        packets.add(PacketUtils.NOP_PACKET);
        return packets;
    }
    
    public PacketListCRC createPartialFDRIPackets(PacketListCRC packetList, ArrayList<Integer> data) throws BitstreamException {
    	// The example PR bitfiles from Xilinx only use type two writes. We are not sure why but
    	// will do the same.
    	packetList.add(PacketUtils.ZERO_WORD_WRITE_PACKET(RegisterType.FDRI));
        packetList.add(PacketUtils.TYPE_TWO_WRITE_PACKET(data));
    	return packetList;
    }

}

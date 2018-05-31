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
package edu.byu.ece.rapidSmith.bitstreamTools.examples;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Bitstream;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.BitstreamParseException;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.BitstreamParser;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Packet;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.PacketList;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.RegisterType;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.FrameAddressRegister;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.DeviceLookup;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.XilinxConfigurationSpecification;

public class RowCounter {

public static void main(String[] args) {
        
        if (args.length != 1) {
            System.err.println("Usage: java edu.byu.ece.bitstreamTools.examples.RowCounter <input.bit>");
        }
        
        Bitstream bitstream = null;
        try {
            bitstream = BitstreamParser.parseBitstream(args[0]);
        } catch (BitstreamParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        XilinxConfigurationSpecification spec = DeviceLookup.lookupPartV4V5V6withPackageName(bitstream.getHeader().getPartName());
        
        FrameAddressRegister far = new FrameAddressRegister(spec);
        
        Set<Integer> topRows = new HashSet<Integer>();
        Set<Integer> bottomRows = new HashSet<Integer>();
        
        PacketList packets = bitstream.getPackets();
        for (Packet p : packets) {
            if (p.getRegType() == RegisterType.LOUT) {
                int farAddress = p.getData().get(0);
                far.setFAR(farAddress);
                int currentRow = far.getRow();
                int currentTopBottom = far.getTopBottom();
                Set<Integer> rowSet = (currentTopBottom == 0) ? topRows : bottomRows;
                rowSet.add(currentRow);
            }
        }

        System.out.println("Top rows: " + topRows.size());
        System.out.println("Bottom rows: " + bottomRows.size());       
    }
}

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Bitstream;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.BitstreamParseException;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.BitstreamParser;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Packet;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.PacketList;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.RegisterType;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.FrameAddressRegister;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.BlockSubType;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.BlockType;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.DeviceLookup;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.XilinxConfigurationSpecification;

/**
 * This example parses a debug bitstream and determines the size of each column. It knows
 * the size of each block sub type.
 */
public class DebugBitstreamDecoder {

    public static void main(String[] args) {
        
        if (args.length != 1) {
            System.err.println("Usage: java edu.byu.ece.bitstreamTools.examples.DebugBitstreamDecoder <input.bit>");
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
        
        Map<Integer, Integer> columnMap = new LinkedHashMap<Integer, Integer>();
        
        PacketList packets = bitstream.getPackets();
        for (Packet p : packets) {
            if (p.getRegType() == RegisterType.LOUT) {
                int farAddress = p.getData().get(0);
                far.setFAR(farAddress);
                if (far.getRow() != 0 || far.getTopBottom() != 0) {
                    break;
                }
                int column = far.getColumn();
                int frame = far.getMinor();
                columnMap.put(column, frame + 1);
                //System.out.println("TB: " + far.getTopBottom() + ", Block: " + far.getBlockType() + ", Row: " + far.getRow() + ", Column: " + far.getColumn() + ", Frame: " + far.getMinor());
            }
        }
        
        
        
        Map<Integer, List<BlockSubType>> frameCountMap = new LinkedHashMap<Integer, List<BlockSubType>>();

        for (BlockType bt : spec.getBlockTypes()) {
            for (BlockSubType bst : bt.getValidBlockSubTypes()) {
                int frameCount = bst.getFramesPerConfigurationBlock();
                List<BlockSubType> stList = frameCountMap.get(frameCount);
                if (stList == null) {
                    stList = new ArrayList<BlockSubType>();
                    frameCountMap.put(frameCount, stList);
                }
                stList.add(bst);
            }
        }
        
        for (int column : columnMap.keySet()) {
            int numFrames = columnMap.get(column);
            String possible = "possible block sub types: [";
            List<BlockSubType> possibleSubTypes = frameCountMap.get(numFrames);
            if (possibleSubTypes != null) {
                Iterator<BlockSubType> it = possibleSubTypes.iterator();
                while (it.hasNext()) {
                    BlockSubType bst = it.next();
                    possible += bst.getName();
                    if (it.hasNext()) {
                        possible += ", ";
                    }
                }
                possible += "]";
            }
            System.out.println("Column: " + column + ",\t# frames: " + numFrames + "\t, " + possible);
        }
        
    }
}

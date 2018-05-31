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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Bitstream;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.BitstreamException;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.BitstreamHeader;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.PacketListCRC;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.BitstreamGenerator;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.FrameAddressRegister;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.V4BitstreamGenerator;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.BlockType;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.DeviceLookup;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.XilinxConfigurationSpecification;

/**
 * This example demonstrates how to create partial bitstreams for configuring the BRAMs
 * on V4 devices. 
 * 
 *  
 */
public class BRAMPartialBitstreams {

	
	public static void main(String args[]) {

		
		if (args.length < 1) {
			System.err.println("usage: <part name>\n");
			System.exit(1);
		}
		String partname = args[0];
			
		XilinxConfigurationSpecification spec = DeviceLookup.lookupPartV4V5V6(partname);
		
		int idCode = spec.getIntDeviceIDCode();

		// Creates the initial packets
		BitstreamGenerator v4gen = V4BitstreamGenerator.getSharedInstance();
		PacketListCRC packets = v4gen.createInitialPartialBitstream(idCode);

		// TODO: Create data packets here
		final int size = 1312;
		List<Integer> data = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++ )
			data.add(0);

		// TODO: figure out the far address
		int topBottom = 0;
		int row = 0;
		int column = 0;
		int minor = 0;

		// Set block type in FAR
		// TODO: need to make it easier to figure out the block number
		int blockType = 0;
		List<BlockType> blockTypes = spec.getBlockTypes();
		
		int i = 0;
		for (BlockType blockTypeI : blockTypes) {
		    if (blockTypeI == spec.getBRAMContentBlockType()) {
		        blockType = i;
		    }
		    i++;
		}
			
		int farAddress = FrameAddressRegister.createFAR(spec, topBottom, blockType,
				row, column, minor);

		try {
			BitstreamGenerator.createPartialWritePackets(packets, spec, farAddress, data);
		} catch (BitstreamException e) {
			System.exit(1);
		}
		
		// Creates the ending packets
		v4gen.createEndingPartialBitstream(packets);

		// 4. Create bitstream from the packets
		// TODO: create a non header bitstream and see if it can be parsed
		BitstreamHeader header = new BitstreamHeader("temp.ncd","4VSX550-pg125");		
		Bitstream bitstream = new Bitstream(header,spec.getSyncData(), packets);

		// 5. Write the bitstream to a file
		// TODO: create an Ostream
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("test.dat");
			//bitstream.outputRawBitstream(fos);
			bitstream.outputHeaderBitstream(fos);
		} catch (IOException e) {
			System.exit(1);
		}
		
	}

}

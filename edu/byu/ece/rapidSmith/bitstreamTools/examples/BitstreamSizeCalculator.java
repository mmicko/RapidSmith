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

import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.BlockSubType;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.BlockType;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.DeviceLookup;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.XilinxConfigurationSpecification;

public class BitstreamSizeCalculator {

	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.err.println("usage: <executable> <part name> [-noBRAMContent]\n");
			DeviceLookup.printAvailableParts(System.err);
			System.exit(1);
		}

		String partname = args[0];
		boolean ignoreBRAMContent = false;
		if (args.length > 1 && args[1].equalsIgnoreCase("-noBRAMContent")) {
			ignoreBRAMContent = true;
		}

		XilinxConfigurationSpecification spec = DeviceLookup.lookupPartV4V5V6(partname);
		if (spec == null) {
			DeviceLookup.printAvailableParts(System.err);
			System.exit(1);
		}

		int frameSize = spec.getFrameSize();
		int numTopRows = spec.getTopNumberOfRows();
		int numBottomRows = spec.getBottomNumberOfRows();
		
		BlockType bramContentType = spec.getBRAMContentBlockType();
		
		
		int frameCount = 0;
		for (BlockType blockType : spec.getBlockTypes()) {
			if (ignoreBRAMContent && blockType == bramContentType) {
				continue;
			}
			for (BlockSubType blockSubType : spec.getBlockSubTypeLayout(blockType)) {
				//System.out.println(blockSubType + " " + blockSubType.getFramesPerConfigurationBlock());
				frameCount += blockSubType.getFramesPerConfigurationBlock();
			}
		}
		
		int result = 32 * /* 32 bits per word */
					 frameSize * /* Number of words in a frame */ 		
					 (numTopRows + numBottomRows) * /* Number of rows */
					 frameCount; /* Number of frames in the columns of one row for all block types */
		
		System.out.println(result + " bits required to configure " + spec.getDeviceName());
		if (ignoreBRAMContent) {
			System.out.println("(without BRAM content bits)");
		}
	}
}

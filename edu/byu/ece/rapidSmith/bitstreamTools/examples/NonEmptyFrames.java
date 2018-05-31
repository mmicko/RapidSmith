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

import joptsimple.OptionSet;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Bitstream;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.FPGA;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.Frame;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.FrameAddressRegister;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.XilinxConfigurationSpecification;
import edu.byu.ece.rapidSmith.bitstreamTools.examples.support.BitstreamOptionParser;

/**
 * This method is used to print the contents of non empty configured frames within the bitstream.
 */
public class NonEmptyFrames {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		/////////////////////////////////////////////////////////////////////
		// Setup class and Options
		/////////////////////////////////////////////////////////////////////
		String PRINT_DETAIL = "d";
		
		/** Setup parser **/
		BitstreamOptionParser cmdLineParser = new BitstreamOptionParser();
		cmdLineParser.addInputBitstreamOption();
		cmdLineParser.addPartNameOption();
		cmdLineParser.addHelpOption();
		cmdLineParser.accepts(PRINT_DETAIL, "Prints all details");

		OptionSet options = cmdLineParser.parseArgumentsExitOnError(args);

		BitstreamOptionParser.printExecutableHeaderMessage(NonEmptyFrames.class);
		
		/////////////////////////////////////////////////////////////////////
		// Begin basic command line parsing
		/////////////////////////////////////////////////////////////////////		
		cmdLineParser.checkHelpOptionExitOnHelpMessage(options);
		
		/////////////////////////////////////////////////////////////////////
		// 1. Parse bitstream
		/////////////////////////////////////////////////////////////////////
		Bitstream bitstream = cmdLineParser.parseRequiredBitstreamFromOptionsExitOnError(options, true);
		
		/////////////////////////////////////////////////////////////////////
		// 2. Obtain part information
		/////////////////////////////////////////////////////////////////////
		XilinxConfigurationSpecification partInfo = cmdLineParser.getPartInfoExitOnError(options, bitstream, true);
		
		boolean printDetail =(options.has(PRINT_DETAIL));

		
		// Create FPGA object
		FPGA fpga = new FPGA(partInfo);		
		// Configure FPGA
		fpga.configureBitstream(bitstream);

		FrameAddressRegister far = new FrameAddressRegister(partInfo, 0);
		
		boolean empty = false;
		boolean nonempty = false;
		int startFar = 0;
		int count = 0;

		while (far.validFARAddress()) {
			Frame f = fpga.getFrame(far);			
			if (!f.isConfigured() || f.getData().isEmpty()) {
				if (printDetail) {
					if (f.isConfigured())
						System.out.println(far + " = EMPTY");
				}
				if (empty) {
					// Another empty: add count
					count++;
				} else {
					if (!nonempty) {
						// In no state. Initialize
						startFar = far.getAddress();
						count = 1;
						empty = true;
					} else {
						// End of non empty state
						System.out.println("0x"+Integer.toHexString(startFar)+" ("+count+" frames) "+far);
						empty = true; nonempty = false;
						count = 1;
						startFar = far.getAddress();
					}
				}
			} else {
				if (nonempty) {
					// another non-empty
					count++;
				} else {
					if (!empty) {
						// In no state. Initialize
						startFar = far.getAddress();
						count = 1;
						nonempty = true;
					} else {						
						// End of empty state
						if (false)
							System.out.println("FAR=0x"+Integer.toHexString(startFar)+" count="+count+" empty "+far);
						empty = false; nonempty = true;
						count = 1;
						startFar = far.getAddress();
					}
				}
				if (printDetail) System.out.println(far);
			}
			far.incrementFAR();
		}
	}


	
}


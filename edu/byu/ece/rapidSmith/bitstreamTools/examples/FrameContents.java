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
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.FPGA;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.FrameAddressRegister;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.XilinxConfigurationSpecification;
import edu.byu.ece.rapidSmith.bitstreamTools.examples.support.BitstreamOptionParser;

/**
 * This method is used to print the contents of frames within the bitstream.
 *
 */
public class FrameContents {

	public static final String FAR_START_ADDRESS_OPTION = "s";
	public static final String FAR_START_ADDRESS_OPTION_HELP = 
		"The starting frame address to investigate (in hex)." +
		"If this option is not given, the first frame in the bitstream is used.";
	public static final String NUMBER_OF_FRAMES_OPTION = "n";
	public static final String NUMBER_OF_FRAMES_OPTION_HELP = "The number of frames to investigate (default is all frames)";


	/**
	 * Prints information about each FDRI write command (i.e., for each FDRI command,
	 * it prints a text location of the FAR address and the # of frames).
	 */
	public static void main(String[] args) {

		/////////////////////////////////////////////////////////////////////
		// Setup class and Options
		/////////////////////////////////////////////////////////////////////
				
		/** Setup parser **/
		BitstreamOptionParser cmdLineParser = new BitstreamOptionParser();
		cmdLineParser.addInputBitstreamOption();
		cmdLineParser.addPartNameOption();
		cmdLineParser.addHelpOption();
		cmdLineParser.addRawReadbackInputOption();
		
		cmdLineParser.accepts(FAR_START_ADDRESS_OPTION, FAR_START_ADDRESS_OPTION_HELP).
				withRequiredArg().ofType(String.class);		
		cmdLineParser.accepts(NUMBER_OF_FRAMES_OPTION, NUMBER_OF_FRAMES_OPTION_HELP).
			withRequiredArg().ofType(String.class);

		OptionSet options = null;
		try {
			options = cmdLineParser.parse(args);
		}
		catch(Exception e){
			System.err.println(e.getMessage());
			System.exit(1);			
		}		

		BitstreamOptionParser.printExecutableHeaderMessage(FrameContents.class);
				
		/////////////////////////////////////////////////////////////////////
		// Begin basic command line parsing
		/////////////////////////////////////////////////////////////////////
		cmdLineParser.checkHelpOptionExitOnHelpMessage(options);

		
		/////////////////////////////////////////////////////////////////////
		// 1. Parse bitstream
		/////////////////////////////////////////////////////////////////////
		FPGA fpga = null;
		
		fpga = cmdLineParser.createFPGAFromBitstreamOrReadbackFileExitOnError(options);

		/*
		if(options.has(READBACK_RAW_FILE_OPTION)){
			// get data from a readback file
			XilinxConfigurationSpecification part = cmdLineParser.getRequiredPartInfoExitOnError(options);
			String readbackfilename = (String) options.valueOf(READBACK_RAW_FILE_OPTION);		

			fpga = ReadbackFPGA.parseRawReadbackDataFromOptionsExitOnError(readbackfilename, 
					part);
			
		} else {
			Bitstream bitstream = cmdLineParser.parseBitstreamFromOptionsExitOnError(options, true);
			// get data from a bitstream
			/////////////////////////////////////////////////////////////////////
			// 2. Obtain part information
			/////////////////////////////////////////////////////////////////////
			XilinxConfigurationSpecification partInfo = cmdLineParser.getPartInfoExitOnError(options, bitstream, true);
			fpga = new FPGA(partInfo);		
			// Configure FPGA
			fpga.configureBitstream(bitstream);

		}
		*/
		
		/////////////////////////////////////////////////////////////////////
		// 3. Parameters
		/////////////////////////////////////////////////////////////////////
		
		// Start frame
		int startFrame = cmdLineParser.getIntegerStringExitOnError(options, FAR_START_ADDRESS_OPTION, 16, 0);

		// Number of frames
		XilinxConfigurationSpecification partInfo = fpga.getDeviceSpecification();
		int defaultNumberOfFrames = FrameAddressRegister.getNumberOfFrames(partInfo);
		int numberOfFrames = cmdLineParser.getIntegerStringExitOnError(options, NUMBER_OF_FRAMES_OPTION, 10, defaultNumberOfFrames);			

		System.out.println(fpga.getFrameContents(startFrame, numberOfFrames));
	}	
	
}


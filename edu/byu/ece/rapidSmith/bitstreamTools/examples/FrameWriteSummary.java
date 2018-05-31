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

import java.util.Iterator;

import joptsimple.OptionSet;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Bitstream;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Packet;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.PacketList;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.PacketOpcode;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.PacketType;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.RegisterType;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.FrameAddressRegister;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.XilinxConfigurationSpecification;
import edu.byu.ece.rapidSmith.bitstreamTools.examples.support.BitstreamOptionParser;

/**
 * This example will search the bitstream for FDRI write commands and will
 * summarize the FAR locations for each FDRI command. It supports bitstreams
 * with multiple FDRI commands.
 *
 */
public class FrameWriteSummary {

	/**
	 * Prints information about each FDRI write command (i.e., for each FDRI command,
	 * it prints a text location of the FAR address and the # of frames).
	 */
	public static void main(String[] args) {
	
		String PRINT_ALL_FRAMES = "a";
		
		/** Setup parser **/
		BitstreamOptionParser cmdLineParser = new BitstreamOptionParser();
		cmdLineParser.addInputBitstreamOption();
		cmdLineParser.addPartNameOption();
		cmdLineParser.addHelpOption();
		cmdLineParser.accepts(PRINT_ALL_FRAMES, "Prints all frames");
		
		OptionSet options = cmdLineParser.parseArgumentsExitOnError(args);

		cmdLineParser.checkHelpOptionExitOnHelpMessage(options);

		BitstreamOptionParser.printExecutableHeaderMessage(FrameWriteSummary.class);

		Bitstream bitstream = cmdLineParser.parseRequiredBitstreamFromOptionsExitOnError(options, true);

		XilinxConfigurationSpecification partInfo = cmdLineParser.getPartInfoExitOnError(options, bitstream, true);	

		boolean printAllFrames =(options.has(PRINT_ALL_FRAMES));

		// Get part packets
		PacketList packets = bitstream.getPackets();
		Iterator<Packet> p = packets.iterator();

		while (p.hasNext()) {
			printFDRIWrite(partInfo, p, printAllFrames);
		}
						
	}

	public static void printFDRIWrite(XilinxConfigurationSpecification spec, Iterator<Packet> pi,
			boolean printAllFrames) {
		boolean debug = false;
		while (pi.hasNext()) {
			Packet p = pi.next();
			if (debug) System.out.println(p.toString(false));
			
			if (p.getPacketType() == PacketType.ONE &&
				p.getOpcode() == PacketOpcode.WRITE &&
				p.getRegType() == RegisterType.FAR) {
				
				// Get FAR address
				int farAddress = p.getData().get(0);

				// Skip all following commands until the FDRI command arrives
				while (pi.hasNext() && !(p.getOpcode() == PacketOpcode.WRITE && p.getRegType() == RegisterType.FDRI)) {
					p = pi.next();
					if (debug) System.out.println("skip:"+p.toString(false));
				}
				// get the write command after the FDRI
				if (pi.hasNext()) {
					p = pi.next();
					if (debug) System.out.println("final write:"+p.toString(false));
					if (p.getOpcode() == PacketOpcode.WRITE) {
						// Print out command
						int words = p.getData().size();
						int frames = (words/spec.getFrameSize());
						if (!printAllFrames) {
							System.out.println("Initial FAR:"+ FrameAddressRegister.toString(spec,farAddress));
							System.out.println("FDRI words="+words + " (" + frames + " frames)");
							//System.out.println("Ending FAR:"+ FrameAddressRegister.toString(spec,farAddress+frames-1));
						} else 
							printIntermediateFAR(spec, farAddress,frames);
					}
				}

			}
		}
	}

	public static void printIntermediateFAR(XilinxConfigurationSpecification spec,
			int farAddress, int frames) {
		FrameAddressRegister far = new FrameAddressRegister(spec,farAddress);
		for (int i = 0; i < frames; i++) {
			System.out.println("Frame #"+i+":"+far);
			far.incrementFAR();
		}
	}
}


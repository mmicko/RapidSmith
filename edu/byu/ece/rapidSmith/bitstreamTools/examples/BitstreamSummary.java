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

import java.io.PrintWriter;

import joptsimple.OptionSet;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Bitstream;
import edu.byu.ece.rapidSmith.bitstreamTools.examples.support.BitstreamOptionParser;

/**
 * This executable prints the contents of a bitstream as an XML file.
 *
 */
public class BitstreamSummary {

		
	//public static final String PRINT_LEVEL_OPTION = "p";
	//public static final String FAR_START_ADDRESS_OPTION_HELP = 
	//	"Determines the print level option";
	public static final String PRINT_HEADER_OPTION = "h";
	public static final String PRINT_HEADER_OPTION_HELP = "Print only the bitstream header.";
	public static final String PRINT_XML_OPTION = "xml";
	public static final String PRINT_XML_OPTION_HELP = "Print bitstream in older XML format.";
	
	/**
	 * Prints the contents of a bitstream as an XML file.
	 * 
	 * @param args bitstream name
	 */
	public static void main(String[] args) {

		/** Setup parser **/
		BitstreamOptionParser cmdLineParser = new BitstreamOptionParser();
		cmdLineParser.addInputBitstreamOption();
		//cmdLineParser.addPartNameOption();
		cmdLineParser.addHelpOption();
		cmdLineParser.accepts(PRINT_HEADER_OPTION, PRINT_HEADER_OPTION_HELP);
		cmdLineParser.accepts(PRINT_XML_OPTION, PRINT_XML_OPTION_HELP);

		OptionSet options = cmdLineParser.parseArgumentsExitOnError(args);

		// 
		BitstreamOptionParser.printExecutableHeaderMessage(NonEmptyFrames.class);
		
		/////////////////////////////////////////////////////////////////////
		// Begin basic command line parsing
		/////////////////////////////////////////////////////////////////////		
		cmdLineParser.checkHelpOptionExitOnHelpMessage(options);
		
		/////////////////////////////////////////////////////////////////////
		// 1. Parse bitstream
		/////////////////////////////////////////////////////////////////////
		Bitstream bitstream = cmdLineParser.parseRequiredBitstreamFromOptionsExitOnError(options, true);
		
		//int printLevel = 1;
		
		// header only
		if (options.has(PRINT_HEADER_OPTION)) {
			System.out.println(bitstream.getHeader().toString());
			System.exit(1);
		}
		// print XML
		if (options.has(PRINT_XML_OPTION)) {
			System.out.println(bitstream.toXMLString());
			System.exit(1);			
		}
		// Print full bitstream
		PrintWriter pw = new PrintWriter(System.out);
		bitstream.toStream(true, true, pw);
		pw.flush();
		pw.close();        
    }

	
}

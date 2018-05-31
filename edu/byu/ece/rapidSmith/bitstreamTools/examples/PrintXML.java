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

import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Bitstream;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.BitstreamParser;

/**
 * This executable prints the contents of a bitstream as an XML file.
 *
 * @deprecated This class is kept for compatibility purposes. This class has been replaced
 * by the BitstreamSummary class with more flexible bitstream reporting methods.
 */
public class PrintXML {

	/**
	 * Prints the contents of a bitstream as an XML file.
	 * 
	 * @param args bitstream name
	 */
	public static void main(String[] args) {

		int printLevel = 0;
		
		if (args.length < 1) {
            System.out.println("Usage: java edu.byu.ece.bitstreamTools.bitstream.test.PrintXML <bitstream.bit> [print level]");
            System.exit(1);
        }
        if (args.length > 1) {
        	try {
        		printLevel = Integer.parseInt(args[1]);
        	} catch (NumberFormatException e) {
        		System.err.println("Not a valid number:"+args[1]);
        		System.exit(1);
        	}
        }
        
        Bitstream bitStream = BitstreamParser.parseBitstreamExitOnError(args[0]);
        System.out.println(bitStream.toString(printLevel));
        
    }

	
}

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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.BitstreamParser;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.XilinxConfigurationSpecification;

/**
 * Generates a configured FPGA object from a readback file. Since a readback bitstream
 * does not have any of the packet information, we cannot use the bitstream parser
 * to parser the bitstream into a data structure.
 * 
 */
public class ReadbackFPGA extends FPGA {

	public ReadbackFPGA(File file, XilinxConfigurationSpecification part) throws IOException { 
		super(part);
		FileInputStream input = new FileInputStream(file);
	    BufferedInputStream buffer = new BufferedInputStream(input);   
		loadFPGAFromReadbackData(buffer, part);
	}

	public ReadbackFPGA(BufferedInputStream istream, XilinxConfigurationSpecification part) throws IOException { 
		super(part);
		loadFPGAFromReadbackData(istream, part);
	}
	
	/** Generate an FPGA object from a readback file. **/
	public static FPGA parseRawReadbackDataFromOptionsExitOnError(String filename, 
			XilinxConfigurationSpecification part) {
		FPGA fpga = null;
		try {
			File file = new File(filename);
			fpga = new ReadbackFPGA(file,part);
		} catch (IOException e) {
			System.err.println(e);
		}
		return fpga;
	}
	
	
	protected void loadFPGAFromReadbackData(BufferedInputStream istream, 
			XilinxConfigurationSpecification part) 
		throws IOException {
		loadFPGAFromReadbackData(istream, part, 0);
	}
	
	protected void loadFPGAFromReadbackData(BufferedInputStream istream, 
			XilinxConfigurationSpecification part, int far) 
		throws IOException {
		
		// Read the bytes into a list
		int numBytes = istream.available();
		ArrayList<Byte> bytes = new ArrayList<Byte> (numBytes);
	    for(int i = 0; i < numBytes; i++) {
	    		bytes.add((byte)istream.read());
	    }
	    istream.close();
	    
	    
	    // Create a list 
	    int numWords = bytes.size() / 4;
	    ArrayList<Integer> data = new ArrayList<Integer>(numWords);
		for (int j = 0; j < numWords; j++) {
		    data.add(BitstreamParser.getWordAsInt(bytes, (j*4)));
		}
		
	    // Remove the first frame of data: this appears to be extra (based on visual
	    // comparisions)
	    for (int i = 0; i < part.getFrameSize(); i++)
	    	data.remove(i);

	    // Configure bitstream
	    setFAR(far);
	    configureWithData(data);
	    
	}

	
}

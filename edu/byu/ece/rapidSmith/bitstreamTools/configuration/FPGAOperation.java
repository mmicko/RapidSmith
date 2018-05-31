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


/**
 * Class for performing logical masking operations on FPGA objects.
 * 
 * TODO: move to a different package. This doesn't belong in the base configuration package.
 * 
 */
public class FPGAOperation {

	public enum OPERATORS { XOR, AND, OR, NOT, MASK };

	public static void ANDoperation(FPGA fpga1, FPGA fpga2) {
		operation(fpga1,fpga2, OPERATORS.AND);
	}
	
	public static void XORoperation(FPGA fpga1, FPGA fpga2) {
		operation(fpga1,fpga2, OPERATORS.XOR);
	}
	
	public static void ORoperation(FPGA fpga1, FPGA fpga2) {
		operation(fpga1,fpga2, OPERATORS.OR);
	}

	public static void MASKoperation(FPGA fpga1, FPGA fpga2) {
		operation(fpga1,fpga2, OPERATORS.MASK);
	}

	public static void NOToperation(FPGA fpga1) {
		operation(fpga1, null, OPERATORS.NOT);
	}
	
	public static void operation(FPGA fpga1, FPGA fpga2, OPERATORS op) {
		
		// Set FAR address of both FPGAs
		fpga1.setFAR(0);
		if (fpga2 != null)
			fpga2.setFAR(0);

		Frame f = null;
		do {
			f = fpga1.getCurrentFrame();
			FrameData fd = f.getData();

			Frame g = null;
			FrameData gd = null;
			
			if (fpga2 != null) {
				g = fpga2.getCurrentFrame();
				gd = g.getData();
			}
			
			// TODO: look at this: we may be making bitstreams that are
			// unnecessarily too large
			//if (g.isConfigured()) {
				
				
				switch(op) {
				case XOR:
					fd.XORDATA(gd);
					break;
				case AND:
					fd.ANDData(gd);
					break;
				case OR:
					fd.ORData(gd);
					break;
				case MASK:
					fd.MASKData(gd);
					break;
				case NOT:
					fd.NOTData();
					break;
				}
				
				// Configure the frame if it was already configured
				if (f.isConfigured())
					f.configure(fd);

				if (DEBUG) {
					// If the frames are the same, then the resulting frame should be empty. Print non-empty
					if (!fd.isEmpty())
						System.out.println("FAR update:"+fpga1.getFAR().getHexAddress());
				}
			//}

			fpga1.incrementFAR();
			if (fpga2 != null)
				fpga2.incrementFAR();
			f = fpga1.getCurrentFrame();
		} while (f != null);
	}
	
	public static boolean DEBUG = false;

}

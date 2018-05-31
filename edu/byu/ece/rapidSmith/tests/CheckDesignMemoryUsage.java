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
package edu.byu.ece.rapidSmith.tests;

import edu.byu.ece.rapidSmith.design.Design;
import edu.byu.ece.rapidSmith.util.MessageGenerator;

public class CheckDesignMemoryUsage {
	public static void main(String[] args) {
		if(args.length != 1){
			MessageGenerator.briefMessageAndExit("USAGE: <designFile.xdl>");
		}
		// Measure Initial Heap Size
		Runtime rt = Runtime.getRuntime();
		System.gc();
		long initial_usage = rt.totalMemory() - rt.freeMemory();

		// Start Timer
		long start = System.nanoTime();
		
		Design design = new Design(args[0]);
		
		// Stop Timer
		long stop = System.nanoTime();
		
		// Measure Final Heap Size
		System.gc();
		long total_usage = rt.totalMemory() - rt.freeMemory() - initial_usage;
		
		System.out.printf("Loaded %s design in %5.3f seconds using %d MBs of heap space.%s", 
			design.getPartName(), (stop-start)/1000000000.0, total_usage/(1024*1024), System.getProperty("line.separator"));
		
		design.saveXDLFile(args[0].replace(".xdl", "_saved.xdl"), true);
	}
}

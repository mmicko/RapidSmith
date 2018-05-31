/*
 * Copyright (c) 2010 Brigham Young University
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
package edu.byu.ece.rapidSmith.placer;

import java.util.Random;
import edu.byu.ece.rapidSmith.design.*;
import edu.byu.ece.rapidSmith.device.*;
import edu.byu.ece.rapidSmith.util.MessageGenerator;

public class RandomPlacer{
  public static void main(String[] args){
    // Create and load a design
    Design design = new Design(args[0]);

    // Create a random number generator
    Random rng = new Random(0);

    // Place all unplaced instances
    for(Instance i : design.getInstances()){
	    if(i.isPlaced()) continue;
	    PrimitiveSite[] sites = design.getDevice().getAllCompatibleSites(i.getType());
	    int idx = rng.nextInt(sites.length);
	    int watchDog = 0;
	    // Find a free primitive site
	    while(design.isPrimitiveSiteUsed(sites[idx])){
	    	if(++idx > sites.length) idx = 0;
	    	if(++watchDog > sites.length) MessageGenerator.briefErrorAndExit("Placement failed.");
	    }
	    i.place(sites[idx]);
    }
    
    // Save the placed design
    design.saveXDLFile(args[1]);
  }
}


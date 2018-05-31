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
package edu.byu.ece.rapidSmith.util;

import java.util.ArrayList;

import edu.byu.ece.rapidSmith.design.Design;
import edu.byu.ece.rapidSmith.design.Net;
import edu.byu.ece.rapidSmith.design.NetType;
import edu.byu.ece.rapidSmith.design.Pin;

/**
 * This class will read in an XDL design and unroute and merge all 
 * static source (GND/VCC) nets into GLOBAL_LOGIC0 and GLOBAL_LOGIC1
 * and also remove all sources (TIEOFFs/SLICEs). This makes the design
 * easier to be routed by the RapidSmith router.
 * @author Chris Lavin
 * Created on: Nov 15, 2010
 */
public class MergeStaticNets {

	public static void main(String[] args){
		if(args.length != 2){
			MessageGenerator.briefMessageAndExit("USAGE: <input.xdl> <output.xdl>");
		}
		
		Design design = new Design();
		
		design.loadXDLFile(args[0]);
				
		Net gnd = new Net("GLOBAL_LOGIC0",NetType.GND);
		Net vcc = new Net("GLOBAL_LOGIC1",NetType.VCC);
		
		ArrayList<Net> netsToRemove = new ArrayList<Net>();
		
		for(Net net : design.getNets()){
			if(net.isStaticNet()){
				netsToRemove.add(net);
				if(net.getSource() != null && net.getSource().getInstance() != null){
					design.getInstanceMap().remove(net.getSource().getInstance().getName());
				}
				if(net.getType().equals(NetType.GND)){
					for(Pin pin : net.getPins()){
						if(pin.isOutPin()) continue;
						gnd.addPin(pin);
					}
				}
				else if(net.getType().equals(NetType.VCC)){
					for(Pin pin : net.getPins()){
						if(pin.isOutPin()) continue;
						vcc.addPin(pin);
					}
				}
			}
		}
		
		for(Net net : netsToRemove){
			design.removeNet(net.getName());
		}
		
		design.addNet(gnd);
		design.addNet(vcc);
		
		design.saveXDLFile(args[1], true);
	}
}

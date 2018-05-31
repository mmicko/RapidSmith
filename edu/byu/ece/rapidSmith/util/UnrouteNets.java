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
import java.util.Collection;

import edu.byu.ece.rapidSmith.design.Design;
import edu.byu.ece.rapidSmith.design.Net;
import edu.byu.ece.rapidSmith.design.NetType;
import edu.byu.ece.rapidSmith.design.Pin;


public class UnrouteNets {
	
	public static ArrayList<Net> combineStaticNets(Collection<Net> nets){
		ArrayList<Net> gndNets = new ArrayList<Net>();
		ArrayList<Net> vccNets = new ArrayList<Net>();
		ArrayList<Net> newNets = new ArrayList<Net>();
		for(Net net : nets){
			if(net.getType().equals(NetType.GND)) {
				gndNets.add(net);
			}
			else if(net.getType().equals(NetType.VCC)) {
				vccNets.add(net);
			}
		}
		
		if(!nets.removeAll(gndNets)) {
			System.out.println("Problem separating GND nets");
		}
		
		if (!nets.removeAll(vccNets)) {
			System.out.println("Problem separating VCC nets");
		}
		
		
		Net gndNet = new Net();
		gndNet.setName("GLOBAL_LOGIC0");
		gndNet.setType(NetType.GND);
		for(Net net : gndNets){
			for(Pin pin : net.getPins()) {
				if(!pin.isOutPin()) {
					gndNet.addPin(pin);
				}
			}
		}
		
		Net vccNet = new Net();
		vccNet.setName("GLOBAL_LOGIC1");
		vccNet.setType(NetType.VCC);
		for(Net net : vccNets){
			for(Pin pin : net.getPins()) {
				if(!pin.isOutPin()) {
					vccNet.addPin(pin);
				}
			}
		}
		
		newNets.addAll(nets);
		newNets.add(gndNet);
		newNets.add(vccNet);
		return newNets;
	}
	
	
	public static void main(String[] args){
		if(args.length != 2){
			System.out.println("USAGE: <input.xdl> <output.xdl>");
			System.exit(0);
		}
		Design design = new Design();
		
		design.loadXDLFile(args[0]);
		
		design.unrouteDesign();
		
		design.setNets(combineStaticNets(design.getNets()));
		
		design.saveXDLFile(args[1]);
	}
	
}

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

import java.util.HashMap;

import edu.byu.ece.rapidSmith.design.Attribute;
import edu.byu.ece.rapidSmith.design.Design;
import edu.byu.ece.rapidSmith.design.Instance;
import edu.byu.ece.rapidSmith.design.Module;
import edu.byu.ece.rapidSmith.design.Net;
import edu.byu.ece.rapidSmith.design.Port;


/**
 * This class compares two designs to check if they are identical.
 * Its depth is only down to the toString functions for now, which seems
 * to be sufficient.  Implement further if needed.
 * 
 * For now, its functionality is restricted to hard macro xdl designs
 * 
 * @author Jaren Lamprecht
 * Created on: July 8, 2010
 */
public class DesignCompare{
	
	private static void status(String s){
		System.out.println(s);
	}
	
	/**
	 * This class compares xdl designs xdl1 and xdl2 and returns true if they
	 * are identical, false otherwise.
	 * 
	 * @param xdl1 The first design.
	 * @param xdl2 The design to compare to the first design.
	 * @return True if the designs match, false otherwise.
	 */
	public static boolean compare(Design xdl1, Design xdl2){
		boolean identical = false;
		for(Module module1:xdl1.getModules()){
			//check name
			identical = false;
			for(Module module2:xdl2.getModules()){
				if(module1.getName().compareTo(module2.getName()) == 0){
					identical = true;
					//check anchorName
					if(module1.getAnchor().getName().compareTo(module2.getAnchor().getName()) != 0){
						status("Anchor name "+module1.getAnchor()+" from design " + xdl1.getName() + " was not found in design " + xdl2.getName() + ".");
						return false;
					}
					//check cfgList
					for(Attribute cfg1:module1.getAttributes()){
						identical = false;
						for(Attribute cfg2:module2.getAttributes()){
							if(cfg1.toString().compareTo(cfg2.toString())==0){
								identical = true;
								break;
							}
						}
						if(!identical){
							status("Module config attribute "+cfg1.toString()+" from design " + xdl1.getName() + " was not found in design " + xdl2.getName() + ".");
							return false;
						}
					}
					//check portList
					for(Port port1:module1.getPorts()){
						identical = false;
						for(Port port2:module2.getPorts()){
							if(port1.toString().compareTo(port2.toString()) == 0){
								identical = true;
								break;
							}
						}
						if(!identical){
							status("Port "+port1.getName()+" from design " + xdl1.getName() + " was not found or was configured differently in design " + xdl2.getName() + ".");
							return false;
						}
					}
					//check instList
					HashMap<String,Instance> instanceMap = xdl2.getInstanceMap();
					for(Instance inst1:xdl1.getInstances()){
						if(instanceMap.containsKey(inst1.getName())){
							Instance inst2 = instanceMap.remove(inst1.getName());
							if(inst1.toString().compareTo(inst2.toString()) != 0){
								status("Instance "+inst1.getName()+" from design " + xdl1.getName() + " was configured differently in design " + xdl2.getName() + ".");
								status(inst1.toString());
								status(inst2.toString());
								return false;
							}
						}else{
							status("Instance "+inst1.getName()+" from design " + xdl1.getName() + " was not found in design " + xdl2.getName() + ".");
							return false;
						}
					}
					
					//check netList
					HashMap<String,Net> netMap = xdl2.getNetMap();
					for(Net net1:xdl2.getNets()){
						if(netMap.containsKey(net1.getName())){
							Net net2 = netMap.remove(net1.getName());
							if(net1.toString().compareTo(net2.toString()) != 0){
								status("Net "+net1.getName()+" from design " + xdl1.getName() + " was configured differently in design " + xdl2.getName() + ".");
								return false;
							}
						}else{
							status("Net "+net1.getName()+" from design " + xdl1.getName() + " was not found in design " + xdl2.getName() + ".");
							return false;
						}
					}					
				}
			}
			if(!identical){
				status("Module name "+module1.getName()+" from design " + xdl1.getName() + " was not found in design " + xdl2.getName() + ".");
				return false;
			}
		}
		return true;	
	}
	
}
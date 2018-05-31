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

import java.io.BufferedReader;
import java.io.InputStreamReader;

import edu.byu.ece.rapidSmith.device.Device;
import edu.byu.ece.rapidSmith.device.PrimitiveSite;
import edu.byu.ece.rapidSmith.device.Tile;
import edu.byu.ece.rapidSmith.device.WireConnection;
import edu.byu.ece.rapidSmith.device.WireEnumerator;

/**
 * This class is a simple method to browse device information by tile.
 * @author Chris Lavin
 * Created on: Jul 12, 2010
 */
public class BrowseDevice{

	public static void run(Device dev, WireEnumerator we){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Tile t = null;
		while(true){
			System.out.println("Commands: ");
			System.out.println(" 1: Get wire connections in tile");
			System.out.println(" 2: Check if wire is a PIP wire");
			System.out.println(" 3: List RouteThrough wires");
			System.out.println(" 4: Follow wire connections");
			System.out.println(" 5: List primitives of a tile");
			System.out.println(" 6: Get tile of a primitive site");
			System.out.println(" 7: Exit");
			try {
				Integer cmd = Integer.parseInt(br.readLine().trim());
				switch(cmd){
					case 1:
						System.out.println("Enter tile name: ");
						t = dev.getTile(br.readLine().trim());
						System.out.println("Choosen Tile: " + t.getName());

						System.out.println("Enter wire name: ");
						String wire = br.readLine().trim();
						WireConnection[] wires = t.getWireConnections(we.getWireEnum(wire));
						if(wires != null){
							for(WireConnection w : wires){
								System.out.println("  " + w.toString(we));
							}
						}
						else{
							System.out.println(" No Connections");
						}
						break;
					case 2:
						System.out.println("Enter wire name:");
						String wire1 = br.readLine().trim();
						System.out.println("isPIP? " + we.isPIPWire(wire1));
						break;
					case 3:
						System.out.println("PIPRouteThroughs");
						for(WireConnection w : dev.getRouteThroughMap().keySet()){
							System.out.println("  " + w.toString(we) + " " + dev.getRouteThroughMap().get(w).toString(we));
						}
						break;
					case 4:
						System.out.println("Enter start tile name: ");
						t = dev.getTile(br.readLine().trim());
						System.out.println("Choosen start tile: " + t.getName());

						System.out.println("Enter start wire name: ");
						String startWire = br.readLine().trim();
						
						while(true){
							if(t.getWireHashMap() == null){
								System.out.println("This tile has no wires.");
								break;
							}
							if(t.getWireConnections(we.getWireEnum(startWire)) == null){
								System.out.println("This wire has no connections, it may be a sink");
								break;
							}
							WireConnection[] wireConnections = t.getWireConnections(we.getWireEnum(startWire));
							System.out.println(t.getName() + " " + startWire + ":");
							for (int i = 0; i < wireConnections.length; i++) {
								System.out.println("  " + i + ". " + wireConnections[i].getTile(t) +" " + we.getWireName(wireConnections[i].getWire()));
							}
							System.out.print("Choose a wire: ");
							int ndx;
							try{
								ndx = Integer.parseInt(br.readLine().trim());
								t = wireConnections[ndx].getTile(t);
								startWire = we.getWireName(wireConnections[ndx].getWire());
							}
							catch(Exception e){
								System.out.println("Did not understand, try again.");
								continue;
							}
							
						}
						break;
					case 5:
						System.out.println("Enter tile name: ");
						t = dev.getTile(br.readLine().trim());
						System.out.println("Choosen Tile: " + t.getName());

						if(t.getPrimitiveSites() == null){
							System.out.println(t.getName() + " has no primitive sites.");
						}
						else{
							for(PrimitiveSite p : t.getPrimitiveSites()){
								System.out.println("  " + p.getName());
							}
						}
					
						break;
					case 6:
						System.out.println("Enter tile name: ");
						String siteName = br.readLine().trim();
						PrimitiveSite site = dev.getPrimitiveSite(siteName);
						if(site == null){
							System.out.println("No primitive site called \"" + siteName +  "\" exists.");
						}
						else {
							System.out.println(site.getTile());
						}
						break;
					case 7:
						System.exit(0);
						
				}
			} catch (Exception e) {
				System.out.println("Bad input, try again.");
			}
		}
	}
	public static void main(String[] args){
		MessageGenerator.printHeader(" RapidSmith Device Browser");		
		if(args.length != 1){
			MessageGenerator.briefMessageAndExit("USAGE: <device part name, ex: xc4vfx12ff668 >");
		}
		Device dev = (Device) FileTools.loadDevice(args[0]);
		WireEnumerator we = FileTools.loadWireEnumerator(args[0]);		
		
		run(dev, we);
	}
}

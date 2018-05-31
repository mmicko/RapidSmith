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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import edu.byu.ece.rapidSmith.design.Design;
import edu.byu.ece.rapidSmith.design.Instance;
import edu.byu.ece.rapidSmith.design.Net;
import edu.byu.ece.rapidSmith.design.PIP;
import edu.byu.ece.rapidSmith.device.PrimitiveSite;
import edu.byu.ece.rapidSmith.router.Node;

/**
 * This class is meant to debug problem with XDL files.  It
 * is not quite complete, but will check for a few kinds of errors.
 * @author Chris Lavin
 */
public class XDLDesignChecker{

	
	public static ArrayList<Net> setNets(ArrayList<Net> nets, int size){
		ArrayList<Net> removed = new ArrayList<Net>();
		while(size > 0){
			removed.add(nets.remove(0));
			size--;
		}
		return removed;
	}
	
	public static ArrayList<PIP> removeFirstHalf(ArrayList<PIP> pips){
		ArrayList<PIP> removed = new ArrayList<PIP>();
		int size = pips.size();
		int halfSize = size/2;
		while(halfSize > 0) {
			removed.add(pips.remove(0));
			halfSize--;
		}
		return removed;
	}
	
	public static ArrayList<PIP> removeLastHalf(ArrayList<PIP> pips){
		ArrayList<PIP> removed = new ArrayList<PIP>();
		int size = pips.size();
		int halfSize = size/2;
		while(halfSize > 0) {
			removed.add(pips.remove(pips.size()-1));
			halfSize--;
		}
		return removed;
	}
	
	public static String readLineFromStdIn(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			return br.readLine();
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void runCommandWithoutOutput(String command){
		
		// Generate NCD
		try {
			Process p = Runtime.getRuntime().exec(command);
			StreamGobbler err = new StreamGobbler(p.getErrorStream(), false);
			StreamGobbler input = new StreamGobbler(p.getInputStream(), false);
			input.start();
			err.start();
			try {
				if(p.waitFor() != 0){
					throw new IOException();
				}
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Unknown Error While converting XDL to NCD/NMC.");
				System.exit(1);
			}
			p.destroy();
		} catch (IOException e){
			System.out.println("COMMAND FAILED:");
			System.out.println("\""+ command +"\"");
		}		
	}	
	
	public static void runCommandAndPrintOutput(String command){
				
		// Generate NCD
		try {
			Process p = Runtime.getRuntime().exec(command);
			StreamGobbler err = new StreamGobbler(p.getErrorStream(), true);
			StreamGobbler input = new StreamGobbler(p.getInputStream(), true);
			input.start();
			err.start();
			try {
				if(p.waitFor() != 0){
					throw new IOException();
				}
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Unknown Error While converting XDL to NCD/NMC.");
				System.exit(1);
			}
			p.destroy();
		} catch (IOException e){
			System.out.println("COMMAND FAILED:");
			System.out.println("\""+ command +"\"");
		}		
	}
		
	public static void main(String[] args){
		if(args.length != 1){
			MessageGenerator.briefMessageAndExit("USAGE: <input.xdl>");
		}

		Design design = new Design();
		design.loadXDLFile(args[0]);
		
		// Check for unique placement of primitives
		MessageGenerator.printHeader("CHECKING FOR UNIQUE PRIMITIVE PLACEMENTS ... ");
		HashMap<PrimitiveSite, Instance> usedSites = new HashMap<PrimitiveSite, Instance>();
		for(Instance inst : design.getInstances()){
			if(inst.getPrimitiveSite() == null){
				System.out.println("Warning: " + inst.getName() +" is unplaced.");
			}
			else if(usedSites.containsKey(inst.getPrimitiveSite())){
				System.out.println("ERROR: Placement conflict at site: " + inst.getPrimitiveSiteName() +" (tile: "+inst.getTile()+")");
				System.out.println("  Involving at least these two instances:");
				System.out.println("    " + inst.getName());
				System.out.println("    " + usedSites.get(inst.getPrimitiveSite()).getName());
			}
			else{
				usedSites.put(inst.getPrimitiveSite(), inst);
			}
		}
		
		
		
		// Check for duplicate PIPs
		HashMap<PIP,Net> pipMap = new HashMap<PIP, Net>();
		MessageGenerator.printHeader("CHECKING FOR DUPLICATE PIPS ... ");
		for(Net net : design.getNets()){
			for(PIP pip : net.getPIPs()){
				Net tmp = pipMap.get(pip);
				if(tmp == null){
					pipMap.put(pip, net);
				}
				else{
					System.out.print("  Duplicate PIP: " + pip.toString(design.getWireEnumerator()));
					System.out.println("  in nets: " + net.getName());
					System.out.println("           " + tmp.getName());
				}
			}
		}
		
		// Checking for duplicate PIP sinks
		HashMap<Node,Net> pipSinks = new HashMap<Node, Net>();
		MessageGenerator.printHeader("CHECKING FOR DUPLICATE PIP SINKS ... ");
		for(Net net : design.getNets()){
			for(PIP pip : net.getPIPs()){
				Node n = new Node(pip.getTile(), pip.getEndWire(), null, 0);
				Net tmp = pipSinks.get(n);
				if(tmp == null){
					pipSinks.put(n, net);
				}
				else{
					System.out.print("  Duplicate PIP Sink: " + n.toString(design.getWireEnumerator()));
					System.out.println("  in nets: ");
					System.out.println("           " + net.getName());
					System.out.println("           " + tmp.getName());
				}
			}
		}
	}
}

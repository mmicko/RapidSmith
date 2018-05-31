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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.byu.ece.rapidSmith.device.Device;
import edu.byu.ece.rapidSmith.device.DeviceFilesCreator;

/**
 * This class will create the device and wire enumerator files 
 * necessary for RapidSmith to operate.   
 * @author Chris Lavin
 */
public class Installer{
	public static String nl = System.getProperty("line.separator");
	public static String disclaimer = 
		"This material is based upon work supported by the National" + nl + 
		"Science Foundation under Grant No. 0801876. Any opinions," + nl + 
		"findings, and conclusions or recommendations expressed in this" + nl + 
		"material are those of the author(s) and do not necessarily" + nl + 
		"reflect the views of the National Science Foundation.";
	
	/**
	 * Parses the text file passed in through main() containing the
	 * desired parts and or families to generate device files for. The file should have 
	 * each part/family on a separate line.
	 * @param fileName Name of the part name file.
	 * @return A list of all the part/family names in the file.
	 */
	public static String[] parsePartNameFile(String fileName){
		ArrayList<String> partAndFamilyNames = new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = null;
			int lineNumber = 0;
			while((line = br.readLine()) != null){
				lineNumber++;
				String partName = line.trim();
				partAndFamilyNames.add(partName);
			}
		}
		catch(FileNotFoundException e){
			MessageGenerator.briefErrorAndExit("Error, part name file not found: " + fileName);
		}
		catch(IOException e){
			MessageGenerator.briefErrorAndExit("Error while reading part name file: " + fileName);
		}
		
		// Convert to an array
		String[] names = new String[partAndFamilyNames.size()];
		names = partAndFamilyNames.toArray(names);
		return names;
	}
	
	public static void main(String[] args){
		MessageGenerator.printHeader("RapidSmith Release " + Device.rapidSmithVersion +" - Installer");
		String[] names = null;
		long timeStart = System.currentTimeMillis();
		if(args.length == 0){
			String nl = System.getProperty("line.separator");
			
			MessageGenerator.briefMessageAndExit("  USAGE: <Xilinx Family Name(s) | Part Name(s) " +
					"| parameterFileName.txt>" + nl +
					"    EXAMPLES:" + nl +
					"      \"virtex4 virtex5\"" + nl +
					"      \"virtex4 xc5vlx20tff323\"" + nl +
					"      \"listOfpartsAndFamiliesFile.txt\" (each parameter on a separate line)" + nl);
		}
		
		System.out.println("DISCLAIMER:");
		System.out.println(disclaimer + nl + nl);
		
		System.out.println("Have you read the above disclaimer and agree to the GPLv2 license" + nl +
				"agreement accompanying this software (/docs/gpl2.txt)");
		MessageGenerator.agreeToContinue();
		
		System.out.println("START: " + FileTools.getTimeString());
		// Check if user supplied file with parameters
		File tmp = new File(args[0]);
		if(tmp.exists() && tmp.isFile()){
			names = parsePartNameFile(args[0]);
		}
		else{
			names = args;
		}
		ArrayList<String> partNames = new ArrayList<String>();
		for(String name : names){
			name = name.toLowerCase();
			if(name.startsWith("x")){
				partNames.add(name);
			}
			else{
				name = name.toUpperCase();
				partNames.addAll(RunXilinxTools.getPartNames(name, false));
			}
			
			for(String partName : partNames){
				System.out.println("Creating/Verifying files for " + partName);
				DeviceFilesCreator.createPartFiles(partName);				
			}
		}
		System.out.println("END: " + FileTools.getTimeString());
		System.out.println("Time Elapsed: " + (System.currentTimeMillis() - timeStart)/60000.0 + " minutes");
		MessageGenerator.printHeader("Installer Completed Successfully!");
	}
}


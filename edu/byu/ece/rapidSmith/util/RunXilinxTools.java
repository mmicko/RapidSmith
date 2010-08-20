/*
 * Copyright (c) 2010 Brigham Young University
 * 
 * This file is part of the BYU RapidSmith Tools.
 * 
 * BYU RapidSmith Tools is free software: you may redistribute it 
 * and/or modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, either version 2 of 
 * the License, or (at your option) any later version.
 * 
 * BYU RapidSmith Tools is distributed in the hope that it will be 
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 * 
 * A copy of the GNU General Public License is included with the BYU 
 * RapidSmith Tools. It can be found at doc/gpl2.txt. You may also 
 * get a copy of the license at <http://www.gnu.org/licenses/>.
 * 
 */
package edu.byu.ece.rapidSmith.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This is a generic class which has several static methods to call Xilinx tools from within the XDL TOOLS
 * framework.  
 * @author Chris Lavin
 */
public class RunXilinxTools {

	/**
	 * Generates the brief version of the XDLRC file specified by partName.
	 * @param partName The part name of the Xilinx device for which to generate the XDLRC file.
	 * @param optionalOutputFileName Provides a way to generated a custom output file name.  If parameter is null, the 
	 * output file name is [part name + _brief.xdlrc]
	 * @return True if completed successfully, false otherwise.
	 */
	public static boolean generateBriefXDLRCFile(String partName, String optionalOutputFileName){
		return generateXDLRCFile(partName, optionalOutputFileName, true);
	}

	/**
	 * Generates the full version of the XDLRC file specified by partName.
	 * @param partName The part name of the Xilinx device for which to generate the XDLRC file.
	 * @param optionalOutputFileName Provides a way to generated a custom output file name.  If parameter is null, the 
	 * output file name is [part name + _full.xdlrc]
	 * @return True if completed successfully, false otherwise.
	 */
	public static boolean generateFullXDLRCFile(String partName, String optionalOutputFileName){
		return generateXDLRCFile(partName, optionalOutputFileName, false);
	}
	
	/**
	 * Generates the XDLRC file specified by partName.
	 * @param partName The part name of the Xilinx device for which to generate the XDLRC file.
	 * @param optionalOutputFileName Provides a way to generated a custom output file name.  If parameter is null, the 
	 * output file name is [part name + _brief/_full.xdlrc]
	 * @param briefFile Determines if it should generate a brief or full XDLRC file.
	 * @return True if completed successfully, false otherwise.
	 */
	public static boolean generateXDLRCFile(String partName, String optionalOutputFileName, boolean briefFile){
		String fileNameSuffix = briefFile ? "_brief.xdlrc" : "_full.xdlrc";
		String defaultFileName = partName + fileNameSuffix;
		String xdlrcFileName = optionalOutputFileName==null ? defaultFileName : optionalOutputFileName;
		String commandParameters = briefFile ? "" : "-pips -all_conns "; 
		String command = "xdl -report " + commandParameters + FileTools.removeSpeedGrade(partName) + " " + xdlrcFileName;
		
		// Check to see if the file already exists
		if(new File(xdlrcFileName).exists()){
			// It already exists
			return true;
		}
		
		try{
			// Run the XDL command
			Process p = Runtime.getRuntime().exec(command);
			if(p.waitFor() != 0){
				MessageGenerator.briefError("XDLRC Generation failed, 'xdl' execution failed." +
						" COMMAND: " + command);
				return false;				
			}
			if(p.exitValue() != 0){
				MessageGenerator.briefError("XDLRC Generation failed, is the part \"" + 
						FileTools.removeSpeedGrade(partName) + "\" name valid?" + " COMMAND: " + command);
				return false;	
			}
		} 
		catch (IOException e){
			MessageGenerator.briefError("XDLRC generation failed: error during 'xdl' execution.");
			return false;
		}
		catch (InterruptedException e){
			MessageGenerator.briefError("XDLRC generation failed: process interrupted.");
			return false;
		}
		return true;
	}

	/**
	 * An easy way to just get one family of part names at a time.
	 * See getPartNames(String[] familyNames, boolean includeSpeedGrades)
	 * @param familyName The single family name to get part names for.
	 * @param includeSpeedGrades A flag indicating to include all parts by various speed grades.
	 * @return All installed part names for the specified family, or null if none were specified.
	 */
	public static ArrayList<String> getPartNames(String familyName, boolean includeSpeedGrades){
		if(familyName == null) return null;
		String[] family = {familyName};
		return getPartNames(family, includeSpeedGrades);
	}
	
	/**
	 * Runs Xilinx PartGen to obtain all installed Xilinx FPGA part names for a given set of families.
	 * @param familyNames Names of the Xilinx FPGA families (virtex4, virtex5, ...) to generate names for.
	 * @param includeSpeedGrades A flag indicating to include all parts by various speed grades.
	 * @return All installed part names for the specified families, or null if none were specified.
	 */
	public static ArrayList<String> getPartNames(String[] familyNames, boolean includeSpeedGrades){
		if(familyNames == null) return null;
		String line;
		String lastPartName = null;
		String[] tokens;
		Process p;
		BufferedReader input;
		ArrayList<String> partNames = new ArrayList<String>();
		ArrayList<String> speedGrades = new ArrayList<String>();
		try{
			for(int i=0; i < familyNames.length; i++){
				// Run PartGen for each family
				p = Runtime.getRuntime().exec("partgen -arch " + familyNames[i]);
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				tokens = null;
				lastPartName = null;
				while((line = input.readLine()) != null){
					tokens = line.split("\\s+");
					if(tokens.length > 0 && tokens[0].startsWith("xc")){
						lastPartName = tokens[0];
						if(includeSpeedGrades && tokens[1].equals("SPEEDS:")){
							speedGrades.clear();
							int j = 2;
							while(j < tokens.length && !tokens[j].equals("(Minimum") && tokens[j].startsWith("-")){
								speedGrades.add(tokens[j]);
								j++;
							}
						}
					}
					else if(lastPartName != null){
						if(includeSpeedGrades && !speedGrades.isEmpty()){
							for(String speedGrade : speedGrades){
								partNames.add(lastPartName+tokens[1]+speedGrade);
							}
						}
						else{
							partNames.add(lastPartName+tokens[1]);							
						}
					}
				}
				
				if(p.waitFor() != 0){
					MessageGenerator.briefError("Part name generation failed: pargen failed to execute " +
							"correctly.  Check spelling and make sure the families: " + 
							MessageGenerator.createStringFromArray(familyNames) + 
							System.getProperty("line.separator")+"are installed.");
					return null;				 
				}
			}
		} 
		catch (IOException e){
			MessageGenerator.briefError("Part name generation failed: error reading partgen output.");
			return null;
		}
		catch (InterruptedException e){
			MessageGenerator.briefError("Part name generation failed: interruption during pargen.");
			return null;
		}
		return partNames;
	}
	
	/**
	 * Gets and returns the file separator character for the given OS
	 */
	public static String getDirectorySeparator(){
		if(RunXilinxTools.cygwinInstalled()){
			return "/";
		}
		else{
			return File.separator;
		}
	}

	/**
	 * Checks if Cygwin is installed on the system
	 */
	public static boolean cygwinInstalled(){
		return System.getenv("CYGWIN") != null;
	}
}

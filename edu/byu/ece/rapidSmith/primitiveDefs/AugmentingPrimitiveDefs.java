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
package edu.byu.ece.rapidSmith.primitiveDefs;

import edu.byu.ece.rapidSmith.device.PrimitiveType;
import edu.byu.ece.rapidSmith.util.FamilyType;
import edu.byu.ece.rapidSmith.util.FileTools;

public class AugmentingPrimitiveDefs {
	
	private static PrimitiveType[] virtex56IOBTypes = {PrimitiveType.IOB, PrimitiveType.IOBM, PrimitiveType.IOBS};
	private static String[] virtex5IOBStandards = {
		"BLVDS_25", "DIFF_HSTL_I", "DIFF_HSTL_I_18", "DIFF_HSTL_I_DCI",        
		"DIFF_HSTL_I_DCI_18", "DIFF_HSTL_II", "DIFF_HSTL_II_18", 
		"DIFF_HSTL_II_DCI", "DIFF_HSTL_II_DCI_18", "DIFF_SSTL18_I", 
		"DIFF_SSTL18_I_DCI", "DIFF_SSTL18_II", "DIFF_SSTL18_II_DCI", 
		"DIFF_SSTL2_I", "DIFF_SSTL2_I_DCI", "DIFF_SSTL2_II", 
		"DIFF_SSTL2_II_DCI", "GTL", "GTL_DCI", "GTLP", "GTLP_DCI", 
		"HSLVDCI_15", "HSLVDCI_18", "HSLVDCI_25", "HSLVDCI_33", "HSTL_I", 
		"HSTL_I_12", "HSTL_I_18", "HSTL_I_DCI", "HSTL_I_DCI_18", "HSTL_II", 
		"HSTL_II_18", "HSTL_II_DCI", "HSTL_II_DCI_18", "HSTL_II_T_DCI", 
		"HSTL_II_T_DCI_18", "HSTL_III", "HSTL_III_18", "HSTL_III_DCI", 
		"HSTL_III_DCI_18", "HSTL_IV", "HSTL_IV_18", "HSTL_IV_DCI", 
		"HSTL_IV_DCI_18", "HT_25", "LVCMOS12", "LVCMOS15", "LVCMOS18", 
		"LVCMOS25", "LVCMOS33", "LVDCI_15", "LVDCI_18", "LVDCI_25", 
		"LVDCI_33", "LVDCI_DV2_15", "LVDCI_DV2_18", "LVDCI_DV2_25", 
		"LVDS_25", "LVDSEXT_25", "LVPECL_25", "LVTTL", "PCI33_3", "PCI66_3", 
		"PCIX", "RSDS_25", "SSTL18_I", "SSTL18_I_DCI", "SSTL18_II", 
		"SSTL18_II_DCI", "SSTL18_II_T_DCI", "SSTL2_I", "SSTL2_I_DCI", 
		"SSTL2_II", "SSTL2_II_DCI", "SSTL2_II_T_DCI"}; 
	private static String[] virtex6IOBStandards = {
		"BLVDS_25", "DIFF_HSTL_I", "DIFF_HSTL_I_18", "DIFF_HSTL_I_DCI", 
		"DIFF_HSTL_I_DCI_18", "DIFF_HSTL_II", "DIFF_HSTL_II_18", 
		"DIFF_HSTL_II_DCI", "DIFF_HSTL_II_DCI_18", "DIFF_HSTL_II_T_DCI_18", 
		"DIFF_SSTL15", "DIFF_SSTL15_DCI", "DIFF_SSTL15_T_DCI", 
		"DIFF_SSTL18_I", "DIFF_SSTL18_I_DCI", "DIFF_SSTL18_II", 
		"DIFF_SSTL18_II_DCI", "DIFF_SSTL18_II_T_DCI", "DIFF_SSTL2_I", 
		"DIFF_SSTL2_I_DCI", "DIFF_SSTL2_II", "DIFF_SSTL2_II_DCI", 
		"DIFF_SSTL2_II_T_DCI", "HSTL_I", "HSTL_I_12", "HSTL_I_18", 
		"HSTL_I_DCI", "HSTL_I_DCI_18", "HSTL_II", "HSTL_II_18", "HSTL_II_DCI",
		"HSTL_II_DCI_18", "HSTL_II_T_DCI", "HSTL_II_T_DCI_18", "HSTL_III", 
		"HSTL_III_18", "HSTL_III_DCI", "HSTL_III_DCI_18", "HT_25", "LVCMOS12", 
		"LVCMOS15", "LVCMOS18", "LVCMOS25", "LVDCI_15", "LVDCI_18", "LVDCI_25",
		"LVDCI_DV2_15", "LVDCI_DV2_18", "LVDCI_DV2_25", "LVDS_25", 
		"LVDSEXT_25", "LVPECL_25", "LVPECL_25", "RSDS_25", "SSTL15", 
		"SSTL15_DCI", "SSTL15_T_DCI", "SSTL18_I", "SSTL18_I_DCI", "SSTL18_II",
		"SSTL18_II_DCI", "SSTL18_II_T_DCI", "SSTL2_I", "SSTL2_I_DCI", 
		"SSTL2_II", "SSTL2_II_DCI", "SSTL2_II_T_DCI"};
	private static String[] driveOptions = {"2", "4", "6", "8", "12", "16", "24"};
	private static String[] slewOptions = {"SLOW", "FAST"};
	
	private static void addNewElement(PrimitiveDefList list, PrimitiveType type, String elementName, String[] cfgList){
		PrimitiveDef p = list.getPrimitiveDef(type);
		Element element = null;
		for(Element e : p.getElements()){
			if(e.getName().equals(elementName)){
				element = e;
			}
		}
		if(element == null){
			element = new Element();
			element.setName(elementName);
			p.addElement(element);
		}
		for(String option : cfgList){
			element.addCfgOption(option);
		}
	}
	
	
	public static void main(String[] args) {
		FamilyType familyType = FamilyType.VIRTEX5;
		PrimitiveDefList list = FileTools.loadPrimitiveDefs(familyType);
		
		switch(familyType){
			case VIRTEX5:
				// Add missing IOB information
				for(PrimitiveType type : virtex56IOBTypes){
					addNewElement(list, type, "ISTANDARD", virtex5IOBStandards);
					addNewElement(list, type, "OSTANDARD", virtex5IOBStandards);
					addNewElement(list, type, "DRIVE", driveOptions);
					addNewElement(list, type, "SLEW", slewOptions);
				}
				break;
			case VIRTEX6:
				// Add missing IOB information
				for(PrimitiveType type : virtex56IOBTypes){
					addNewElement(list, type, "ISTANDARD", virtex6IOBStandards);
					addNewElement(list, type, "OSTANDARD", virtex6IOBStandards);
					addNewElement(list, type, "DRIVE", driveOptions);
					addNewElement(list, type, "SLEW", slewOptions);
				}
				break;
		}
		
		
		for(Element e : list.getPrimitiveDef(PrimitiveType.IOBS).getElements()){
			System.out.print(e.getName() + " ");
			if(e.getCfgOptions() != null){
				for(String option : e.getCfgOptions()){
					System.out.print(option + " ");
				}				
			}
			System.out.println();
		}
		
		//System.out.println(list.toString());
		
	}
}

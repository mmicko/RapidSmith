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
package edu.byu.ece.rapidSmith.constraints;

import java.util.ArrayList;

/**
 * This class is to represent a constraint within a UCF file.
 * Created on: May 5, 2011
 */
public class Constraint{
	/** Statement type (starting keyword of the constraint) */
	private StatementType statementType;
	/** Name of the resource */
	private String name;
	/** Type of constraint */
	private ConstraintType constraintType;
	/** Values of the constraint */
	private ArrayList<String> values;
	/** Keeps the original string of the constraint */
	private String constraint;
	
	/** Area group ranges are only set for instances with area groups */
	/**NOTE!  WE ASSUME THAT THE AREA_GROUP IS DECLARED: 	e.g. "INST "inst_name" AREA_GROUP = "area_group_name";"
	 * BEFORE AREA_GROUP CONSTRAINTS ARE DECLARED: 			e.g. "AREA_GROUP "area_group_name" RANGE=SLICE_X0Y0:SLICE_X43Y59;"
	 */
	private ArrayList<AreaGroupRange> areaGroupRanges;
	
	/**
	 * Empty constructor
	 */
	public Constraint(){
	}
	
	/**
	 * Creates a new constraint object and populates its members based on the
	 * string provided.
	 * @param constraint The constraint text as would be found in a UCF file.
	 */
	public Constraint(String constraint){
		setConstraintString(constraint);
	}
	
	/**
	 * This will parse the current constraint string into the various members of 
	 * the constraint object.
	 * @return True if it was able to successfully parse the string correctly, or
	 * false otherwise.
	 */
	private boolean parseConstraint(){
		ArrayList<String> tokens = getConstraintTokens();
		values = new ArrayList<String>();
		// Populate StatementType
		try{
			statementType = StatementType.valueOf(tokens.get(0).toUpperCase());			
		}
		catch(IllegalArgumentException e){
			e.printStackTrace();
			return false;
		}
		String token = null;
		switch(statementType){
			case INST: 
			case NET:
			case PIN:
				// Populate Name
				name = tokens.get(1);
				// Populate the ConstraintType
				token = tokens.get(2).toUpperCase();
				if(token.equals("OFFSET")){
					token = token + "_" + tokens.get(4).toUpperCase();
				}
				try{			
					constraintType = ConstraintType.valueOf(token);
				}
				catch(IllegalArgumentException e){
					e.printStackTrace();
					System.out.println(tokens.toString());
					System.out.println(constraint);
					System.exit(1);
					return false;
				}				
				// Check for the equals sign
				if(tokens.size() >= 4){
					token = tokens.get(3).toUpperCase();
					if(!token.equals("="))return false;
					
					for(int i = 4; i < tokens.size(); i++){
						values.add(tokens.get(i));							
					}
				}
				break;
			case TIMEGRP:
				// Populate Name
				name = tokens.get(1);
				// TODO
				break;
			case TIMESPEC:
				// Populate Name
				name = tokens.get(1);
				// Check that name starts with TS
				if(!name.toUpperCase().startsWith("TS"))return false;
				
				// Check for the equals sign
				token = tokens.get(2).toUpperCase();
				if(!token.equals("="))return false;
				
				// Populate ConstraintType
				token = tokens.get(3).toUpperCase();
				if(token.equals("FROM")){
					if(constraint.toUpperCase().contains("THRU"))
						token = token + "_THRU_TO";
					else
						token = token + "_TO";
				}
				try{			
					constraintType = ConstraintType.valueOf(token);			
				}
				catch(IllegalArgumentException e){
					e.printStackTrace();
					System.out.println(tokens.toString());
					System.out.println(constraint);
					System.exit(1);
					return false;
				}

				for(int i = 4; i < tokens.size(); i++){
					if(!tokens.get(i).equals("FROM") && !tokens.get(i).equals("THRU") && !tokens.get(i).equals("TO")){
						values.add(tokens.get(i));						
					}
				}
				
				break;
			case AREA_GROUP:
				name = tokens.get(2);
				
				try{			
					constraintType = ConstraintType.valueOf(tokens.get(0));
				}
				catch(IllegalArgumentException e){
					e.printStackTrace();
					System.out.println(tokens.toString());
					System.out.println(constraint);
					System.exit(1);
					return false;
				}
				break;
		}
		return true;
	}
	
	/**
	 * This will separate a constraint string into is various parts for easier
	 * parsing. 
	 * @return A list of tokens that were found in the constraint string.
	 */
	private ArrayList<String> getConstraintTokens(){
		ArrayList<String> matchList = new ArrayList<String>();	
		int i = 0;
		char[] buffer = constraint.toCharArray();
		char[] token = new char[constraint.length()];
		boolean inQuotedString = false;
		for(int j = 0; j < buffer.length; j++){
			switch(buffer[j]){
				case ' ':
				case '\t':
				case '\r':
				case '\n':
					if(inQuotedString){
						token[i++] = buffer[j];
					}
					else if(i > 0){
						matchList.add(new String(token, 0, i));
						i=0;
					}
					break;
				case '\"':
					if(inQuotedString && i > 0){
						matchList.add(new String(token, 0, i));
						i=0;
					}
					inQuotedString = !inQuotedString;
					break;
				case '=':
					if(i > 0){
						matchList.add(new String(token, 0, i));
						i=0;
					}
					matchList.add("=");
					break;
				default:
					token[i++] = buffer[j];
			}
		}
		if(i > 0){
			matchList.add(new String(token, 0, i));
		}
		return matchList;
	}
	
	
	/**
	 * @return the statementType
	 */
	public StatementType getStatementType() {
		return statementType;
	}
	/**
	 * @param statementType the statementType to set
	 */
	public void setStatementType(StatementType statementType) {
		this.statementType = statementType;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the constraintType
	 */
	public ConstraintType getConstraintType() {
		return constraintType;
	}
	/**
	 * @param constraintType the constraintType to set
	 */
	public void setConstraintType(ConstraintType constraintType) {
		this.constraintType = constraintType;
	}
	/**
	 * @return the constraintString
	 */
	public String getConstraintString() {
		return constraint;
	}
	/**
	 * @param constraintString the constraintString to set
	 */
	public boolean setConstraintString(String constraintString){
		this.constraint = constraintString;
		return parseConstraint();
	}

	/**
	 * @return the values
	 */
	public ArrayList<String> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
	
	public void addAreaGroupRange(AreaGroupRange agr) {
		if(areaGroupRanges == null)
			areaGroupRanges = new ArrayList<AreaGroupRange>();
		areaGroupRanges.add(agr);
	}
	
	public ArrayList<AreaGroupRange> getAreaGroupRanges() {
		return areaGroupRanges;
	}

	@Override
	public String toString(){
		return statementType + " \"" + name + "\" " + constraintType + " " + values.toString() + " <" + constraint + ">"; 
	}
}

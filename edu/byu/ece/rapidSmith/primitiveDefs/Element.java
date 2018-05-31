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
package edu.byu.ece.rapidSmith.primitiveDefs;

import java.io.Serializable;
import java.util.ArrayList;

public class Element implements Serializable{

	private static final long serialVersionUID = -4173250951419860912L;

	private String name;
	private boolean bel;
	private ArrayList<PrimitiveDefPin> pins;
	private ArrayList<String> cfgOptions;
	private ArrayList<Connection> connections;
	
	public Element(){
		name = null;
		bel = false;
		pins = new ArrayList<PrimitiveDefPin>();
		cfgOptions = null;
		connections = new ArrayList<Connection>();
	}
	
	public void addPin(PrimitiveDefPin p){
		pins.add(p);
	}
	public void addConnection(Connection c){
		connections.add(c);
	}
	public void addCfgOption(String option){
		if(cfgOptions == null){
			cfgOptions = new ArrayList<String>();
		}
		cfgOptions.add(option);
	}
	
	
	// Getters and Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isBel() {
		return bel;
	}
	public void setBel(boolean bel) {
		this.bel = bel;
	}
	public ArrayList<PrimitiveDefPin> getPins() {
		return pins;
	}
	public void setPins(ArrayList<PrimitiveDefPin> pins) {
		this.pins = pins;
	}
	public ArrayList<String> getCfgOptions() {
		return cfgOptions;
	}
	public void setCfgOptions(ArrayList<String> cfgOptions) {
		this.cfgOptions = cfgOptions;
	}
	public ArrayList<Connection> getConnections() {
		return connections;
	}
	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		String nl = System.getProperty("line.separator");
		s.append("(element " + name +" "+ pins.size() +(bel ? " # BEL" : "")+nl);
		for(PrimitiveDefPin p : pins){
			s.append("\t\t\t"+p.toString() + nl);
		}
		if(cfgOptions != null){
			s.append("\t\t\t(cfg");
			for(String option : cfgOptions){
				s.append(" " + option);
			}
			s.append(")"+nl);
		}
		for(Connection c : connections){
			s.append("\t\t\t"+c.toString() + nl);
		}
		s.append("\t\t)");
		return s.toString();
	}
}

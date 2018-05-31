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

import edu.byu.ece.rapidSmith.device.PrimitiveType;

public class PrimitiveDef implements Serializable{

	private static final long serialVersionUID = -7246158565182505932L;
	private PrimitiveType type;
	private ArrayList<PrimitiveDefPin> pins;
	private ArrayList<Element> elements;

	public PrimitiveDef(){
		setType(null);
		pins = new ArrayList<PrimitiveDefPin>();
		elements = new ArrayList<Element>();
	}
	
	public void addPin(PrimitiveDefPin p){
		pins.add(p);
	}
	public void addElement(Element e){
		elements.add(e);
	}
	
	// Setters and Getters
	public void setType(PrimitiveType type) {
		this.type = type;
	}
	public PrimitiveType getType() {
		return type;
	}
	public ArrayList<PrimitiveDefPin> getPins() {
		return pins;
	}
	public void setPins(ArrayList<PrimitiveDefPin> pins) {
		this.pins = pins;
	}
	public ArrayList<Element> getElements() {
		return elements;
	}
	public void setElements(ArrayList<Element> elements) {
		this.elements = elements;
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		String nl = System.getProperty("line.separator");
		s.append("(primitive_def " + type.toString() +" "+ pins.size() + " " + elements.size() + nl);
		for(PrimitiveDefPin p : pins){
			s.append("\t\t"+p.toString()+nl);
		}
		for(Element e : elements){
			s.append("\t\t"+e.toString()+nl);
		}
		s.append("\t)");
		return s.toString();
	}
}

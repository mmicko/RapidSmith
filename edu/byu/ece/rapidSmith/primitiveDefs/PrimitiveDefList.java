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
import java.util.HashMap;

import edu.byu.ece.rapidSmith.device.PrimitiveType;

public class PrimitiveDefList  extends ArrayList<PrimitiveDef> implements Serializable{

	private static final long serialVersionUID = 4988664481262250467L;

	private HashMap<PrimitiveType,PrimitiveDef> primitiveMap;
	
	public PrimitiveDefList(){
		super();
		primitiveMap = new HashMap<PrimitiveType,PrimitiveDef>();
	}
	
	public PrimitiveDef getPrimitiveDef(PrimitiveType type){
		return primitiveMap.get(type);
	}
	
	@Override
	public boolean add(PrimitiveDef e){
		primitiveMap.put(e.getType(), e);
		return super.add(e);
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		String nl = System.getProperty("line.separator");
		s.append("(primitive_defs " + this.size() +nl);
		for(PrimitiveDef p : this){
			s.append("\t" + p.toString() + nl);
		}
		s.append(")");
		return s.toString();
	}
}

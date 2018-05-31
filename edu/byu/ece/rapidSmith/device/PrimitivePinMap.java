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
package edu.byu.ece.rapidSmith.device;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This is a helper class to help keep the Device files compact.
 * @author Chris Lavin
 */
public class PrimitivePinMap implements Serializable {

	private static final long serialVersionUID = -6398806038703155389L;
	public HashMap<String,Integer> pins;
	
	public PrimitivePinMap(HashMap<String, Integer> pins) {
		this.pins = pins;
	}

	@Override
	public int hashCode() {
		if(pins == null){
			return 0;
		}
		int hash;
		String[] names = new String[pins.keySet().size()];
		names = pins.keySet().toArray(names);
		
		Arrays.sort(names);
		hash = Arrays.deepHashCode(names);
		
		Integer[] values = new Integer[names.length];
		for(int i=0; i < names.length; i++){
			values[i] = pins.get(names[i]);
		}
		
		hash += Arrays.deepHashCode(values);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrimitivePinMap other = (PrimitivePinMap) obj;

		if(pins == null && other.pins == null){
			return true;
		}
		if(pins == null || other.pins == null){
			return false;
		}
		if(pins.size() != other.pins.size()){
			return false;
		}
		String[] map1Strings = new String[pins.size()];
		map1Strings = pins.keySet().toArray(map1Strings);
		
		String[] map2Strings = new String[other.pins.size()];
		map2Strings = other.pins.keySet().toArray(map2Strings);
		
		Arrays.sort(map1Strings);
		Arrays.sort(map2Strings);
		
		if(!Arrays.deepEquals(map1Strings, map2Strings)){
			return false;
		}
		Integer[] map1Integers = new Integer[pins.size()];
		Integer[] map2Integers = new Integer[other.pins.size()];
		
		for(int i=0; i < map1Strings.length; i++){
			map1Integers[i] = pins.get(map1Strings[i]);
			map2Integers[i] = other.pins.get(map2Strings[i]);
		}
		if(!Arrays.deepEquals(map1Integers, map2Integers)){
			return false;
		}
		return true;
	}
}

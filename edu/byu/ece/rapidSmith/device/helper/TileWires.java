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
package edu.byu.ece.rapidSmith.device.helper;

import java.io.Serializable;
import java.util.Arrays;


/**
 * A helper class to help remove duplicate objects and reduce memory usage and file
 * size of the Device class. 
 * @author Chris Lavin
 */
public class TileWires implements Serializable{

	private static final long serialVersionUID = 1884345540813741720L;
	/** Unique set of wires which belong to one or more tiles */
	public WireHashMap wires;

	/**
	 * Constructor 
	 * @param wires The wires of a tile.
	 */
	public TileWires(WireHashMap wires){
		this.wires = wires;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		if(wires == null){
			return hash;
		}
		Integer[] keys = new Integer[this.wires.keySet().size()];
		keys = this.wires.keySet().toArray(keys);
		Arrays.sort(keys);
		for(Integer i : keys){
			hash += i * 7;
			if(this.wires.get(i) != null){
				hash += Arrays.deepHashCode(this.wires.get(i)) * 13;
			}
		}
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
		TileWires other = (TileWires) obj;
		if (wires == null) {
			if (other.wires != null)
				return false;
		} 
		
		if(wires == null && other.wires == null){
			return true;
		}
		if(wires == null || other.wires == null){
			return false;
		}
		Integer[] keys = new Integer[wires.keySet().size()];
		Integer[] keys2 = new Integer[other.wires.keySet().size()];
		keys = wires.keySet().toArray(keys);
		keys2 = other.wires.keySet().toArray(keys2);
		Arrays.sort(keys);
		Arrays.sort(keys2);
		if(!Arrays.deepEquals(keys, keys2)){
			return false;
		}
		for(Integer key : keys){
			if(!Arrays.deepEquals(wires.get(key), other.wires.get(key))){
				return false;
			}
		}
		return true;
	}
}

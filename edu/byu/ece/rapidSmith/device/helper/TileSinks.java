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
import java.util.HashMap;

import edu.byu.ece.rapidSmith.device.SinkPin;

/**
 * A helper class to help reduce the memory usage and file size of
 * the Device class. 
 * @author Chris Lavin
 */
public class TileSinks implements Serializable{

	private static final long serialVersionUID = -4542976263775993364L;
	/** Sinks and mappings for the tile */
	public HashMap<Integer,SinkPin> sinks;

	/**
	 * Constructor
	 * @param sinks Mappings for this tileSink.
	 */
	public TileSinks(HashMap<Integer,SinkPin> sinks){
		this.sinks = sinks;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		
		if(sinks == null){
			return hash;
		}
		else{
			Integer[] keys = new Integer[sinks.keySet().size()];
			keys = sinks.keySet().toArray(keys);
			Arrays.sort(keys);
			hash += Arrays.deepHashCode(keys);
			for(Integer key : keys) {
				hash += sinks.get(key).hashCode() * 7;
			}
			/*Arrays.sort(sinks);
			for(Integer i : sinks){
				hash += i * 7;
			}*/
			return hash;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TileSinks other = (TileSinks) obj;
		if(other.sinks == null && sinks == null){
			return true;
		}
		if(other.sinks == null || sinks == null){
			return false;
		}
		if(other.sinks.size() != sinks.size()){
			return false;
		}
		Integer[] keys = new Integer[sinks.keySet().size()];
		Integer[] otherKeys = new Integer[sinks.keySet().size()];
		keys = sinks.keySet().toArray(keys);
		otherKeys = other.sinks.keySet().toArray(otherKeys);
		Arrays.sort(keys);
		Arrays.sort(otherKeys);
		if(!Arrays.deepEquals(keys, otherKeys)){
			return false;
		}
		for(int i = 0; i < otherKeys.length; i++){
			if(!sinks.get(keys[i]).equals(other.sinks.get(otherKeys[i]))){
				return false;
			}
		}

		return true;
	}
}

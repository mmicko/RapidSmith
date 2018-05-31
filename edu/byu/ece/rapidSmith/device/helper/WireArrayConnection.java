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

public class WireArrayConnection implements Serializable {
	
	private static final long serialVersionUID = 7838184542063263426L;
	public int wire;
	public int wireArrayEnum;
	public int enumeration;
	
	public WireArrayConnection(int w, int wae) {
		wire = w;
		wireArrayEnum = wae;
	}
	
	public Integer[] getIntegerArray(){
		Integer[] tmp = new Integer[2];
		tmp[0] = wire;
		tmp[1] = wireArrayEnum;
		return tmp;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + wire;
		result = prime * result + wireArrayEnum;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WireArrayConnection other = (WireArrayConnection) obj;
		if (wire != other.wire)
			return false;
		if (wireArrayEnum != other.wireArrayEnum)
			return false;
		return true;
	}
	
	
}

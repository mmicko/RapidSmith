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

public class PrimitiveDefPin implements Serializable {

	private static final long serialVersionUID = -5733624692261327342L;

	private String externalName;
	private String internalName;
	private boolean isOutput;
	
	public PrimitiveDefPin(){
		externalName = null;
		internalName = null;
		isOutput = false;
	}
	
	public String getExternalName() {
		return externalName;
	}
	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}
	public String getInternalName() {
		return internalName;
	}
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	public boolean isOutput() {
		return isOutput;
	}
	public void setOutput(boolean isOutput) {
		this.isOutput = isOutput;
	}
	
	@Override
	public String toString(){
		if(externalName == null){
			return "(pin " + internalName + (isOutput ? " output)" : " input)");
		}
		return "(pin " + externalName + " " + internalName + (isOutput ? " output)" : " input)");
	}
}

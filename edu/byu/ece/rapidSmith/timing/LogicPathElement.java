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
package edu.byu.ece.rapidSmith.timing;

import java.io.Serializable;
import java.util.ArrayList;

import edu.byu.ece.rapidSmith.design.Instance;

public class LogicPathElement extends PathElement implements Serializable{

	private static final long serialVersionUID = -1302954929866402566L;

	/** The instance or physical resource */
	private Instance instance;
	/** The logical resources (FFs,...) part of this path element */
	private ArrayList<String> logicalResources = new ArrayList<String>();
	
	
	/**
	 * @return the instance
	 */
	public Instance getInstance() {
		return instance;
	}
	/**
	 * @param instance the instance to set
	 */
	public void setInstance(Instance instance) {
		this.instance = instance;
	}
	/**
	 * @return the logicalResources
	 */
	public ArrayList<String> getLogicalResources() {
		return logicalResources;
	}
	/**
	 * @param logicalResources the logicalResources to set
	 */
	public void setLogicalResources(ArrayList<String> logicalResources) {
		this.logicalResources = logicalResources;
	}
	/**
	 * Adds a logical resource name to the logic path element.
	 * @param resource The resource to add.
	 */
	public void addLogicalResource(String resource){
		logicalResources.add(resource);
	}
}

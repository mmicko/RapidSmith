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

import edu.byu.ece.rapidSmith.design.Net;

public class RoutingPathElement extends PathElement implements Serializable{
	
	private static final long serialVersionUID = -8322328106512170640L;
	
	/** The net or physical resource */
	private Net net;

	/**
	 * @param net the net to set
	 */
	public void setNet(Net net){
		this.net = net;
	}

	/**
	 * @return the net
	 */
	public Net getNet(){
		return net;
	}
	
	
}

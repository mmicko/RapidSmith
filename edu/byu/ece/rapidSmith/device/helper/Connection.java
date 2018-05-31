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

import edu.byu.ece.rapidSmith.device.WireConnection;


/**
 * Small object class to help with backward edge removal.
 * @author Chris Lavin
 * Created on: Jul 15, 2010
 */
public class Connection{
	
	/** Start wire */
	private int wire;
	/** Destination wire */
	private WireConnection dest;
	
	public Connection(int wire, WireConnection dest){
		this.wire = wire;
		this.dest = dest;
	}

	/**
	 * @return the wire
	 */
	public int getWire(){
		return wire;
	}

	/**
	 * @return the dest
	 */
	public WireConnection getDestinationWire(){
		return dest;
	}
}

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

/**
 * This class is used to represent the Route-Through constructs of XDLRC/XDL
 * in association with PIPs.  Certain PIPs are defined as special configurations
 * of logic blocks which allow routing to pass through them.  This class defines
 * these kinds of routing constructs.
 * @author Chris Lavin
 */
public class PIPRouteThrough implements Serializable{

	private static final long serialVersionUID = 4112516033821686373L;
	/** The type of primitive where a route through exists */
	private PrimitiveType type;
	/** The input wire of the route through */
	private int inWire;
	/** The output wire of the route through */
	private int outWire;
	
	/**
	 * Constructor which creates a new PIPRouteThrough.
	 * @param type The type of primitive involved in the route through.
	 * @param inWire The input wire.
	 * @param outWire The output wire.
	 */
	public PIPRouteThrough(PrimitiveType type, int inWire, int outWire){
		this.type = type;
		this.inWire = inWire;
		this.outWire = outWire;
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + inWire;
		result = prime * result + outWire;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PIPRouteThrough other = (PIPRouteThrough) obj;
		if (inWire != other.inWire)
			return false;
		if (outWire != other.outWire)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/**
	 * Gets and returns the primitive type where this route through is found.
	 * @return The primitive type of this route through.
	 */
	public PrimitiveType getType(){
		return type;
	}

	/**
	 * Sets the type of primitive for this route through.
	 * @param type the type to set.
	 */
	public void setType(PrimitiveType type){
		this.type = type;
	}

	/**
	 * Gets and returns the input wire for this route through.
	 * @return the inWire The input wire for this route through.
	 */
	public int getInWire(){
		return inWire;
	}

	/**
	 * Sets the input wire for this route through.
	 * @param inWire the inWire to set.
	 */
	public void setInWire(int inWire){
		this.inWire = inWire;
	}

	/**
	 * Gets and returns the output wire for this route through.
	 * @return the inWire The output wire for this route through.
	 */
	public int getOutWire(){
		return outWire;
	}

	/**
	 * Sets the output wire for this route through.
	 * @param outWire the outWire to set
	 */
	public void setOutWire(int outWire){
		this.outWire = outWire;
	}
	
	/**
	 * Creates an XDLRC compatible string of this route through. 
	 * @param we The wire enumerator required to convert wires to names.
	 * @return The XDLRC compatible string of this route through.
	 */
	public String toString(WireEnumerator we){
		return this.type.toString() + "-" + we.getWireName(inWire) + "-" + we.getWireName(outWire);
 	}
}

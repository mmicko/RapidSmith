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
package edu.byu.ece.rapidSmith.design;

import java.io.Serializable;

import edu.byu.ece.rapidSmith.device.Tile;

/**
 *  This class represents the sources and sinks found in net declarations 
 *  (inpins and outpins)
 * @author Chris Lavin
 * Created on: Jun 22, 2010
 */
public class Pin implements Serializable, Cloneable {

	private static final long serialVersionUID = -6675131973998249758L;

	/** The type of pin (directionality), in/out/inout */
	private PinType pinType;
	/** The internal pin name on the instance this pin refers to */
	private String name;
	/** The instance where the pin is located */
	private Instance instance;
	/** The Port that references this pin, if there is one */
	private Port port;
	/** The net this pin is a member of */
	private Net net;
	
	/**
	 * Constructor setting things to null and false.
	 */
	public Pin(){
		this.pinType = null;
		this.name = null;
		this.setInstance(null);
		this.port = null;
		this.setNet(null);
	}
	
	/**
	 * Creates a pin from parameters
	 * @param isOutputPin Is the new pin an outpin?
	 * @param pinName The name of the pin on the instance (internal name)
	 * @param instance The instance where the pin resides
	 */
	public Pin(boolean isOutputPin, String pinName, Instance instance){
		this.pinType = isOutputPin ? PinType.OUTPIN : PinType.INPIN;
		this.name = pinName;
		this.setInstance(instance);
		this.port = null;
	}

	/**
	 * Creates a pin from parameters
	 * @param pinType Allows specification of an inout pin
	 * @param pinName The name of the pin on the instance (internal name)
	 * @param instance The instance where the pin resides
	 */
	public Pin(PinType pinType, String pinName, Instance instance){
		this.pinType = pinType;
		this.name = pinName;
		this.setInstance(instance);
		this.port = null;
	}
	
	/**
	 * @return True if the pin is an outpin, false otherwise.
	 */
	public boolean isOutPin(){
		return this.pinType == PinType.OUTPIN;
	}
	
	/**
	 * Gets and returns the pin name of the pin.
	 * @return The pin name (internal instance pin name)
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Gets and returns the instance where this pin resides.
	 * @return The instance where the pin resides.
	 */
	public Instance getInstance() {
		return this.instance;
	}
	
	/**
	 * Gets and returns the name of the instance where this pin resides.
	 * @return The name of the instance where this pin resides.
	 */
	public String getInstanceName(){
		return instance.getName();
	}
	
	/**
	 * Gets and returns the module instance name corresponding to the
	 * instance of this pin. 
	 * @return The name of the module instance associated with this pin or null
	 * if none exist.
	 */
	public String getModuleInstanceName(){
		return instance.getModuleInstanceName();
	}
	
	/**
	 * Gets and returns the tile where this pin resides. 
	 * @return The tile where this pin resides.
	 */
	public Tile getTile(){
		return instance.getTile();
	}
	
	/**
	 * Sets the direction of the pin.
	 * @param dir The direction (true=outpin, false=inpin)
	 */
	public void setIsOutputPin(boolean dir){
		this.pinType = dir ? PinType.OUTPIN : PinType.INPIN;
	}
	
	/**
	 * Sets the name of the pin.
	 * @param name The new name of this pin.
	 */
	public void setPinName(String name){
		this.name = name;
	}
	
	/**
	 * Sets the instance to which this pin belongs.
	 * @param instance The instance to which this pin belongs.
	 */
	public void setInstance(Instance instance){
		if(this.instance != null){
			this.instance.removePin(this);
			if(net != null && net.getPIPs().size() > 0){
				// TODO - Unroute only PIPs that are needed
				net.unroute();
			}
		}
		this.instance = instance;
		if(name != null && instance != null){
			instance.addPin(this);
		}
	}
	
	/**
	 * Removes any reference to the instance from this pin and
	 * removes the pin from the pin map in the instance.
	 */
	public void detachInstance(){
		if(instance != null){
			instance.removePin(this);
			this.instance = null;
		}
	}
	
	/**
	 * Sets the port that references this pin.
	 * @param port the port that references this pin.
	 */
	public void setPort(Port port){
		this.port = port;
	}
	
	/**
	 * Gets the port that references this pin.  Null if there is none
	 * @return The port that references this pin.
	 */
	public Port getPort(){
		return this.port;
	}
	
	/**
	 * @param net the net to set
	 */
	public void setNet(Net net) {
		this.net = net;
	}

	/**
	 * @return the net
	 */
	public Net getNet() {
		return net;
	}
	
	/**
	 * Get the concatenated primitiveSiteName.PinName (ex. SLICE_X1Y2.C1)
	 * name for the pin.  This pin name is unique throughout the device.
	 * @return The primitive site name . pin name
	 */
	public String getPrimitiveSitePinName(){
		return instance.getPrimitiveSiteName() + "." + name;
	}

	/**
	 * Generates an equivalent XDL string representation of the pin.
	 */
	@Override
	public String toString(){
		return this.pinType.toString().toLowerCase() + 
			instance.getName() + "\" " + this.name + " ," + 
			System.getProperty("line.separator");
	}

	/**
	 * Generates a hashCode based on the instance, direction and pinName.
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instance == null) ? 0 : instance.hashCode());
		result = prime * result + pinType.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Checks if obj is a pin and if equal to this pin by comparing instance, direction and pinName.
	 */
	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Pin other = (Pin) obj;
		if(instance == null){
			if(other.instance != null)
				return false;
		}
		else if(!instance.equals(other.instance))
			return false;
		if(pinType != other.pinType)
			return false;
		if(name == null){
			if(other.name != null)
				return false;
		}
		else if(!name.equals(other.name))
			return false;
		return true;
	}

	public PinType getPinType(){
		return this.pinType;
	}
	
	public void setPinType(PinType pinType){
		this.pinType = pinType;
	}
}

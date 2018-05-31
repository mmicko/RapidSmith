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

import java.util.ArrayList;

import edu.byu.ece.rapidSmith.design.Net;

public abstract class Path {
	/** The source of this connection delay */
	private String source;
	/** The destination of this connection delay */
	private String destination;
	/** Data path delay in nanoseconds */
	private float dataPathDelay;
	/** Number of logic levels the data path traverses */
	private int levelsOfLogic;
	/** The net driving the clock on the destination register */
	private Net destinationClock;
	/** The uncertainty in the clock as determined by Xilinx trce */
	private float clockUncertainty;
	/** List of physical / logical resources */
	private ArrayList<PathElement> maxDataPath = new ArrayList<PathElement>();	
	
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the dataPathDelay
	 */
	public float getDataPathDelay() {
		return dataPathDelay;
	}
	/**
	 * @param dataPathDelay the dataPathDelay to set
	 */
	public void setDataPathDelay(float dataPathDelay) {
		this.dataPathDelay = dataPathDelay;
	}
	/**
	 * @return the levelsOfLogic
	 */
	public int getLevelsOfLogic() {
		return levelsOfLogic;
	}
	/**
	 * @param levelsOfLogic the levelsOfLogic to set
	 */
	public void setLevelsOfLogic(int levelsOfLogic) {
		this.levelsOfLogic = levelsOfLogic;
	}
	/**
	 * @return the destinationClock
	 */
	public Net getDestinationClock() {
		return destinationClock;
	}
	/**
	 * @param destinationClock the destinationClock to set
	 */
	public void setDestinationClock(Net destinationClock) {
		this.destinationClock = destinationClock;
	}
	/**
	 * @return the clockUncertainty
	 */
	public float getClockUncertainty() {
		return clockUncertainty;
	}
	/**
	 * @param clockUncertainty the clockUncertainty to set
	 */
	public void setClockUncertainty(float clockUncertainty) {
		this.clockUncertainty = clockUncertainty;
	}
	/**
	 * @param maxDataPath the maxDataPath to set
	 */
	public void setMaxDataPath(ArrayList<PathElement> maxDataPath) {
		this.maxDataPath = maxDataPath;
	}
	/**
	 * @return the maxDataPath
	 */
	public ArrayList<PathElement> getMaxDataPath() {
		return maxDataPath;
	}

}

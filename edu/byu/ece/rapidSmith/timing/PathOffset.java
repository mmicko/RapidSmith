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


public class PathOffset extends Path{
	/** Offset in nanoseconds (data path - clock path skew + uncertainty) */
	private float offset;
	/** Number of logic levels the connection traverses in the clock */
	private int clockLevelsOfLogic;
	/** Delay estimated for the clock path */
	private float clockPathDelay;
	/** List of physical / logical resources */
	private ArrayList<PathElement> minDataPath = new ArrayList<PathElement>();	
	
	/**
	 * @return the offset
	 */
	public float getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(float offset) {
		this.offset = offset;
	}
	/**
	 * @return the clockLevelsOfLogic
	 */
	public int getClockLevelsOfLogic() {
		return clockLevelsOfLogic;
	}
	/**
	 * @param clockLevelsOfLogic the clockLevelsOfLogic to set
	 */
	public void setClockLevelsOfLogic(int clockLevelsOfLogic) {
		this.clockLevelsOfLogic = clockLevelsOfLogic;
	}
	/**
	 * @return the clockPathDelay
	 */
	public float getClockPathDelay() {
		return clockPathDelay;
	}
	/**
	 * @param clockPathDelay the clockPathDelay to set
	 */
	public void setClockPathDelay(float clockPathDelay) {
		this.clockPathDelay = clockPathDelay;
	}
	/**
	 * @param minDataPath the minDataPath to set
	 */
	public void setMinDataPath(ArrayList<PathElement> minDataPath) {
		this.minDataPath = minDataPath;
	}
	/**
	 * @return the minDataPath
	 */
	public ArrayList<PathElement> getMinDataPath() {
		return minDataPath;
	}
}

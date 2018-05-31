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


import edu.byu.ece.rapidSmith.design.Net;

public class PathDelay extends Path {
	/** Delay or offset in nanoseconds (data path - clock path skew + uncertainty) */
	private float delay;
	/** Skew estimated for the clock path */
	private float clockPathSkew;
	/** The net driving the clock on the source register */
	private Net sourceClock;
	
	
	/**
	 * @return the delay
	 */
	public float getDelay() {
		return delay;
	}
	/**
	 * @param delay the delay to set
	 */
	public void setDelay(float delay) {
		this.delay = delay;
	}

	/**
	 * @return the clockPathSkew
	 */
	public float getClockPathSkew() {
		return clockPathSkew;
	}
	/**
	 * @param clockPathSkew the clockPathSkew to set
	 */
	public void setClockPathSkew(float clockPathSkew) {
		this.clockPathSkew = clockPathSkew;
	}
	/**
	 * @return the sourceClock
	 */
	public Net getSourceClock() {
		return sourceClock;
	}
	/**
	 * @param sourceClock the sourceClock to set
	 */
	public void setSourceClock(Net sourceClock) {
		this.sourceClock = sourceClock;
	}
}

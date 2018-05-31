/*
 * Copyright (c) 2010-2011 Brigham Young University
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
package edu.byu.ece.rapidSmith.bitstreamTools.configuration;

/**
 * Represents a configuration frame within a Xilinx FPGA. This object has
 * a frame address, frame data, and a configuration tag indicating
 * whether the frame has been configured or not.
 *
 */
public class Frame {

	public Frame(int frameSize, int frameAddress) {
	    data = new FrameData(frameSize);
		this.frameAddress = frameAddress;
		configured = false;
	}

	public boolean isConfigured() {
		return configured;
	}
	
	public void configure(FrameData frameData) {
		data = new FrameData(frameData);
		configured = true;
	}
	
	public int getFrameAddress() {
		return frameAddress;
	}

	/**
	 * Reset the frame. This is the equivalent of hitting the PROG pin.
	 */
	public void reset() {
		data = null;
		configured = false;
	}
	
	public void clear() {
		if (configured)
			data.zeroData();
	}
	
	// TODO: should I return something that the user cannot change?
	// The user could mess around with the data by accessing
	// this object.
	public FrameData getData() {
		return data;
	}
	
	public void setData(FrameData data){
		this.data = data;
	}
	
	public String toString() {
		StringBuffer string = new StringBuffer();
		if (!configured) {
			return "Not Configured";
		}

		if (configured)
			string.append(data.toString());
		else
			string.append("\t<Not Configured>\n");
		string.append("\n");		
		return string.toString();		
	}
	
	
	// Remove?
	public String toXML()
	{
		StringBuffer string = new StringBuffer();
		string.append("<frame>");
		if (configured)
			string.append(data.toString());
		else
			string.append("\t<Not Configured>\n");
		string.append("\n</frame>");		
		return string.toString();
	}	

	protected boolean configured;
	protected int frameAddress;
	protected FrameData data;
	
}

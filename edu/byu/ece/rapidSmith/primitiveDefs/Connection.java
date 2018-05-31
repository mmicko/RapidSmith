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

public class Connection implements Serializable{

	private static final long serialVersionUID = 179925256225262449L;

	private String element0;
	private String pin0;
	private boolean forwardConnection;
	private String element1;
	private String pin1;
	
	public String getElement0() {
		return element0;
	}

	public void setElement0(String element0) {
		this.element0 = element0;
	}

	public String getPin0() {
		return pin0;
	}

	public void setPin0(String pin0) {
		this.pin0 = pin0;
	}

	public String getElement1() {
		return element1;
	}

	public void setElement1(String element1) {
		this.element1 = element1;
	}

	public String getPin1() {
		return pin1;
	}

	public void setPin1(String pin1) {
		this.pin1 = pin1;
	}

	public Connection(){
		element0 = null;
		pin0 = null;
		forwardConnection = false;
		element1 = null;
		pin1 = null;
	}
	
	public boolean isForwardConnection() {
		return forwardConnection;
	}
	public void setForwardConnection(boolean forwardConnection) {
		this.forwardConnection = forwardConnection;
	}

	@Override
	public String toString(){
		return "(conn " + element0 + " " + pin0 +
		(forwardConnection ? " ==> " : " <== ") + element1 + " " + 
		pin1 + ")";
	}
	
}

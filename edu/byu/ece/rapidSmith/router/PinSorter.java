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
package edu.byu.ece.rapidSmith.router;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import edu.byu.ece.rapidSmith.design.NetType;
import edu.byu.ece.rapidSmith.design.Pin;
import edu.byu.ece.rapidSmith.device.WireEnumerator;
import edu.byu.ece.rapidSmith.util.FamilyType;
import edu.byu.ece.rapidSmith.util.MessageGenerator;


/**
 * This class is used by the StaticSourceHandler to sort pins based on
 * how they should be allocated for TIEOFFs.
 * @author Chris Lavin
 * Created on: Jul 13, 2010
 */
public abstract class PinSorter{
	/** These are the pins that are the most needy of the TIEOFF source pins */
	LinkedList<StaticSink> useTIEOFF;
	/** These are pins that could be source by the TIEOFF, but not as necessary */
	ArrayList<StaticSink> attemptTIEOFF;
	/** These are pins that will be sourced by a slice */
	ArrayList<StaticSink> useSLICE;
	/** This is a list of pin names that require a HARD1 if being driven by a TIEOFF */
	static HashSet<String> needHARD1;
	/** This is a list of pins that require a SLICE to source them */
	static HashSet<String> needSLICESource;
	/** Reference to the wire enumerator class */
	static WireEnumerator we;
	/**
	 * Initializes data structures
	 */
	public PinSorter(){
		useTIEOFF = new LinkedList<StaticSink>();
		attemptTIEOFF = new ArrayList<StaticSink>();
		useSLICE = new ArrayList<StaticSink>();
	}
	
	/**
	 * Creates the proper PinSorter subclass based on the family type provided
	 * @param type The family type to create a pin sorter for
	 * @return A new PinSorter subclass, or null if unsupported.
	 */
	public static PinSorter createPinSorter(FamilyType type){
		switch(type){
			case VIRTEX4:
				return new V4PinSorter();
			case VIRTEX5:
				return new V5PinSorter();
			default:
				MessageGenerator.briefError("Sorry, " +	type.name() + 
					" is unsupported by the PinSorter class.");
				return null;
		}
	}
	
	/**
	 * This methods sorts the pins as they are added.
	 * @param switchMatrixSink The switch matrix sink node.
	 * @param pin The current sink pin being sorted.
	 */
	public void addPin(Node switchMatrixSink, Pin pin){
		StaticSink ss = new StaticSink(switchMatrixSink, pin);
		String wireName = we.getWireName(switchMatrixSink.wire);
		
		if(needHARD1.contains(wireName)){
			useTIEOFF.addFirst(ss);
		}
		else if(needSLICESource.contains(wireName)){
			useSLICE.add(ss);
		}
		else if(pin.getNet().getType().equals(NetType.GND)){
			useTIEOFF.addLast(ss);
		}
		else {
			attemptTIEOFF.add(ss);
		}
	}
	
	public void addPinToSliceList(Node node, Pin pin){
		StaticSink ss = new StaticSink(node, pin);
		useSLICE.add(ss);
	}
	
	/**
	 * This is just a small class to help the PinSorter class keep track of things.
	 * @author Chris Lavin
	 */
	public class StaticSink{
		public Node switchMatrixSink;
		public Pin pin;
		public StaticSink(Node switchMatrixSink, Pin pin){
			this.switchMatrixSink = switchMatrixSink;
			this.pin = pin;
		}
		public String toString(WireEnumerator we){
			return "["+switchMatrixSink.toString(we) + ", pin=" + pin.getName() + ", instance=" + pin.getInstanceName() +"]";
		}
	}
}

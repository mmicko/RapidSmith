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

import edu.byu.ece.rapidSmith.router.Node;

/**		
 * A Wire is describes simply as an integer representing the wire and
 * a row/column tile offset from the source wire. It makes little sense
 * by itself and is only understood in the context of the HashMap found in
 * Tile.HashMap<Integer,Wire[]> which provides all of the wire connections
 * from a wire existing to an array of Wire objects which describe wires 
 * that maybe in the same tile or other tiles nearby depending on the 
 * row and column offset.  These connections also may be PIP connections
 * if the boolean isPIP is true.  
 * @author Chris Lavin
 *
 */
public class WireConnection implements Serializable, Comparable<WireConnection>{
	/** Allows Serializable functionality  */
	private static final long serialVersionUID = -2329077568909337716L;
	/** The wire enumeration value of the wire to be connected to */
	private int wire;
	/** The tile row offset from the source wire's tile */
	private int rowOffset;
	/** The tile column offset from the source wire's tile */
	private int columnOffset;
	/** Does the source wire connected to this wire make a PIP? */
	private boolean isPIP;
	
	public WireConnection(){
		this.wire = -1;
		this.rowOffset = 0;
		this.columnOffset = 0;
		this.setPIP(false);
	}
	
	public WireConnection(int wire, int rowOffset, int columnOffset,boolean pip){
		this.wire = wire;
		this.rowOffset = rowOffset;
		this.columnOffset = columnOffset;
		this.setPIP(pip);
	}
	
	/**
	 * @param wire the wire to set
	 */
	public void setWire(int wire) {
		this.wire = wire;
	}
	/**
	 * @return the wire
	 */
	public int getWire() {
		return wire;
	}
	
	public Node createNode(Node srcNode){
		return new Node(getTile(srcNode.getTile()), wire, srcNode, srcNode.getLevel()+1, isPIP);
	}
	
	public Node createNode(Tile currTile){
		return new Node(getTile(currTile), wire, null, 0, isPIP);
	}
	
	public Node createNode(Tile currTile, Node parent){
		return new Node(getTile(currTile), wire, parent, 0, isPIP);
	}
	
	/**
	 * @return the tile
	 */
	public Tile getTile(Tile currTile) {
		return currTile.getDevice().getTile(currTile.getRow()-this.rowOffset, currTile.getColumn()-this.columnOffset);
	}
		
	
	public Tile getWireCacheTile(Device dev, Tile currTile){
		String name = currTile.getName().substring(0, currTile.getName().lastIndexOf("_")+1) +
				"X" + (currTile.getTileXCoordinate()+this.columnOffset) +
				"Y" + (currTile.getTileYCoordinate()+this.rowOffset); 
		return dev.getTile(name);
	}
	
	public int getRowOffset() {
		return rowOffset;
	}

	public void setRowOffset(int rowOffset) {
		this.rowOffset = rowOffset;
	}

	public int getColumnOffset() {
		return columnOffset;
	}

	public void setColumnOffset(int columnOffset) {
		this.columnOffset = columnOffset;
	}

	/**
	 * @param isPIP the isPIP to set
	 */
	public void setPIP(boolean isPIP) {
		this.isPIP = isPIP;
	}

	/**
	 * Does the source wire connected to this wire make a PIP?
	 */
	public boolean isPIP() {
		return isPIP;
	}
	
	@Override
	public int hashCode(){
		return  ((this.rowOffset << 24) & 0xFF000000) | ((this.columnOffset << 16) & 0x00FF0000) |(this.wire);
	}	
	
	public int compareTo(WireConnection w){
		return (this.columnOffset + this.rowOffset + this.wire) - (w.columnOffset + w.rowOffset + w.wire);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WireConnection other = (WireConnection) obj;
		if (columnOffset != other.columnOffset)
			return false;
		if (isPIP != other.isPIP)
			return false;
		if (rowOffset != other.rowOffset)
			return false;
		if (wire != other.wire)
			return false;
		return true;
	}

	@Override
	public String toString(){
		return this.wire +" "+ this.rowOffset +" "+ this.columnOffset + " ";
	}
	
	public String toString(WireEnumerator we){
		return we.getWireName(this.wire) +"("+ this.rowOffset +","+ this.columnOffset +","+ this.isPIP + ")";
	}
}

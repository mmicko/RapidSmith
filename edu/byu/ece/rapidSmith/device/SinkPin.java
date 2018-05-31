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
 * Keeps track of tile offsets for switch matrix sinks in Device/Tile class.
 * @author Chris Lavin
 * Created on: Jul 13, 2010
 */
public class SinkPin implements Serializable{
	
	private static final long serialVersionUID = 741616772060187287L;
	
	/** Keeps track of the wire which drives this sink from the nearest switch matrix */
	public int switchMatrixSinkWire;
	
	/** Keeps track of the switch matrix which drives this sink <31-16: X Tile Offset, 15-0: Y Tile Offset> */
	public int switchMatrixTileOffset;
	
	/**
	 * @param switchMatrixSinkWire
	 * @param switchMatrixTileOffset
	 */
	public SinkPin(int switchMatrixSinkWire, int switchMatrixTileOffset){
		this.switchMatrixSinkWire = switchMatrixSinkWire;
		this.switchMatrixTileOffset = switchMatrixTileOffset;
	}
	
	/**
	 * Return the X offset of the switch matrix tile
	 */
	public int getXSwitchMatrixTileOffset() {
		return switchMatrixTileOffset >> 16;
	}
	
	/**
	 * Return the Y offset of the switch matrix tile
	 */
	public int getYSwitchMatrixTileOffset() {
		// The Y tile offset is the lowest 16 bits. 
		// This needs to be trimmed and signed extended
		int y = (switchMatrixTileOffset << 16) >> 16; 
		return y;
	}

	/**
	 * Return the switch matrix tile (relative to the tile used as a parameter).
	 */
	public Tile getSwitchMatrixTile(Tile tile) {
		int tileRow = tile.getRow()+getYSwitchMatrixTileOffset();
		int tileColumn = tile.getColumn()+getXSwitchMatrixTileOffset();
		Device dev = tile.getDevice();
		return dev.getTile(tileRow, tileColumn);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + switchMatrixSinkWire;
		result = prime * result + switchMatrixTileOffset;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SinkPin other = (SinkPin) obj;
		if (switchMatrixSinkWire != other.switchMatrixSinkWire)
			return false;
		if (switchMatrixTileOffset != other.switchMatrixTileOffset)
			return false;
		return true;
	}
	
	public String toString(WireEnumerator we){
		return we.getWireName(switchMatrixSinkWire) + " " + switchMatrixTileOffset;
	}
}

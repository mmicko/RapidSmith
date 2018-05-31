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
package edu.byu.ece.rapidSmith.design.explorer;

import edu.byu.ece.rapidSmith.design.PIP;
import edu.byu.ece.rapidSmith.device.Tile;
import edu.byu.ece.rapidSmith.device.WireEnumerator;

public class Connection {
	
	Tile startTile;
	
	Tile endTile;
	
	int startWire;
	
	int endWire;

	public Connection(PIP p){
		this.startTile = p.getTile();
		this.endTile = p.getTile();
		this.startWire = p.getStartWire();
		this.endWire = p.getEndWire();
	}
	
	public Connection(Tile startTile, Tile endTile, int startWire, int endWire){
		this.startTile = startTile;
		this.endTile = endTile;
		this.startWire = startWire;
		this.endWire = endWire;
	}
	
	/**
	 * @return the startTile
	 */
	public Tile getStartTile() {
		return startTile;
	}

	/**
	 * @param startTile the startTile to set
	 */
	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	/**
	 * @return the endTile
	 */
	public Tile getEndTile() {
		return endTile;
	}

	/**
	 * @param endTile the endTile to set
	 */
	public void setEndTile(Tile endTile) {
		this.endTile = endTile;
	}

	/**
	 * @return the startWire
	 */
	public int getStartWire() {
		return startWire;
	}

	/**
	 * @param startWire the startWire to set
	 */
	public void setStartWire(int startWire) {
		this.startWire = startWire;
	}

	/**
	 * @return the endWire
	 */
	public int getEndWire() {
		return endWire;
	}

	/**
	 * @param endWire the endWire to set
	 */
	public void setEndWire(int endWire) {
		this.endWire = endWire;
	}

	public String toString(WireEnumerator we){
		return startTile + " " + we.getWireName(startWire) + " --> "  + endTile + " " + we.getWireName(endWire);
	}
	
	
	
}

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
package edu.byu.ece.rapidSmith.constraints;

import edu.byu.ece.rapidSmith.device.PrimitiveType;

public class AreaGroupRange {
	protected AreaGroupCoordinate lowerLeftCoordinate;
	protected AreaGroupCoordinate upperRightCoordinate;
	protected PrimitiveType rangeType;
	protected String areaGroupName;
	
	public AreaGroupRange(PrimitiveType rangeType, int ll_x, int ll_y, int ur_x, int ur_y) {
		this.rangeType = rangeType;
		setLLCoordinate(ll_x, ll_y);
		setURCoordinate(ur_x, ur_y);
	}
	
	public AreaGroupRange(String areaGroupName, PrimitiveType rangeType, int ll_x, int ll_y, int ur_x, int ur_y) {
		this.areaGroupName = areaGroupName;
		this.rangeType = rangeType;
		setLLCoordinate(ll_x, ll_y);
		setURCoordinate(ur_x, ur_y);
	}
	
	//TODO: are the x/y boundaries for area_groups inclusive, or exclusive?  Guessing inclusive for now; might cause problems later
	public boolean containsPoint(int x, int y) {
		if(x >= lowerLeftCoordinate.getX() && x <= upperRightCoordinate.getX() && 
				y >= lowerLeftCoordinate.getY() && y <= upperRightCoordinate.getY())
			return true;
		else
			return false;
	}
	
	public void setAreaGroupName(String areaGroupName) {
		this.areaGroupName = areaGroupName;
	}
	
	public String getAreaGroupName() {
		return areaGroupName;
	}
	
	public void setLLCoordinate(int x, int y) {
		lowerLeftCoordinate = new AreaGroupCoordinate(x, y);
	}
	
	public void setURCoordinate(int x, int y) {
		upperRightCoordinate = new AreaGroupCoordinate(x, y);
	}
	
	public void setPrimitiveType(PrimitiveType rangeType) {
		this.rangeType = rangeType;
	}
	
	public PrimitiveType getPrimitiveType() {
		return rangeType;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(areaGroupName + " ");
		sb.append(rangeType + "_");
		sb.append(lowerLeftCoordinate.toString());
		sb.append(":");
		sb.append(upperRightCoordinate.toString());
		
		return sb.toString();
	}

	protected class AreaGroupCoordinate {
		private int x;
		private int y;
		
		public AreaGroupCoordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public String toString() {
			return "X" + x + "Y" + y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
	}
}

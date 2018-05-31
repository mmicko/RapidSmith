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

import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QSlider;

public class TimingSlider extends QSlider{

	private DesignTileScene scene;
	
	private QLineEdit textBox;
	
	private float maxDelay;
	
	private float minDelay;

	private boolean selfCall = false;
	
	/**
	 * @param scene
	 */
	public TimingSlider(DesignTileScene scene, QLineEdit textBox) {
		super();
		this.scene = scene;
		this.textBox = textBox;
		
	}

	public void setDelays(){
		minDelay = scene.getCurrLines().get(0).getPath().getDelay();
		maxDelay = scene.getCurrLines().get(scene.getCurrLines().size()-1).getPath().getDelay();
		updatePaths(99);
		setTickPosition(TickPosition.TicksBothSides);
	}
	
	public void updateText(String text){
		if(selfCall == true){
			selfCall = false;
			return;
		}
		try{
			float constraint = Float.parseFloat(text);
			int index = (int) ((constraint-minDelay)/(maxDelay-minDelay) * 100.0);
			if(index > 99) index = 99;
			if(index < 0) index = 0;
			setSliderPosition(index);
			updateLines(constraint);
		}
		catch(NumberFormatException e){
			return;
		}
	}
	

	public void updatePaths(Integer value){
		float multiplier = scene.getCurrLines().size()/100;
		int index = (int)(value*multiplier);
		if(value == 0) index = 0;
		if(value >= 99) index = scene.getCurrLines().size()-1;
		float constraint = scene.getCurrLines().get(index).getPath().getDelay();
		selfCall = true;
		textBox.setText(String.format("%5.3f", constraint));
		updateLines(constraint);
	}
	
	public void updateLines(float constraint){
		for(PathItem item : scene.getCurrLines()){
			if(item.getPath().getDelay() > constraint){
				item.setHighlighted();
			}
			else{
				item.setUnhighlighted();
			}
		}
	}
}

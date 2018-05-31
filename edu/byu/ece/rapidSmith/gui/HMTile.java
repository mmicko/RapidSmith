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
/**
 * 
 */
package edu.byu.ece.rapidSmith.gui;

import java.io.File;

import com.trolltech.qt.gui.QBitmap;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsRectItem;
import com.trolltech.qt.gui.QPen;
import edu.byu.ece.rapidSmith.device.Tile;
import edu.byu.ece.rapidSmith.gui.TileScene;
import edu.byu.ece.rapidSmith.util.FileTools;

/**
 * @author marc
 *
 */
public class HMTile extends QGraphicsRectItem {
	
	private Tile tile;
	private boolean containsSLICEM;
	private boolean isAnchor;
	static QColor GREEN = new QColor(0, 255, 0, 190);
	static QColor ORANGE = new QColor(255, 153, 51, 190);
	static QColor RED = new QColor(255, 0, 0, 190);
	static QBitmap anchorPixmap = new QBitmap(FileTools.getRapidSmithPath()+File.separator+"images"+File.separator+"anchor.bmp");
	static QBrush ANCHOR_GREEN = new QBrush(GREEN, anchorPixmap);
	static QBrush ANCHOR_ORANGE = new QBrush(ORANGE, anchorPixmap);
	static QBrush ANCHOR_RED = new QBrush(RED, anchorPixmap);
	
	
		
	public HMTile(Tile newTile, TileScene scene, QGraphicsItemInterface parent, boolean hasSLICEM, boolean isAnchor)
	{
		super(0,0,scene.tileSize - 2, scene.tileSize - 2, parent);
		this.tile = newTile;
		this.containsSLICEM = hasSLICEM;
		this.isAnchor = isAnchor;
		
	}
	
	public HMTile(Tile newTile, TileScene scene, QGraphicsItemInterface parent)
	{
		this(newTile,scene,parent,false,false);
	}
	
	public Tile getTile()
	{
		return tile;
	}
	
	public boolean containsSLICEM(){
		return containsSLICEM;
	}
	
	public void setState(GuiShapeState newState){
		
		switch (newState) {
			case VALID:
				this.setPen(new QPen(GREEN));
				if(isAnchor) 
					this.setBrush(ANCHOR_GREEN);
				else
					this.setBrush(new QBrush(GREEN));
				break;
			case COLLIDING:
				this.setPen(new QPen(ORANGE));
				if(isAnchor) 
					this.setBrush(ANCHOR_ORANGE);
				else
					this.setBrush(new QBrush(ORANGE));
				break;
			case INVALID:
				this.setPen(new QPen(RED));
				if(isAnchor) 
					this.setBrush(ANCHOR_RED);
				else
					this.setBrush(new QBrush(RED));
				break;
			default:
				break;
		}
	}
}

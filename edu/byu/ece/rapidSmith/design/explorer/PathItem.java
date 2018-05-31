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

import com.trolltech.qt.core.Qt.MouseButton;
import com.trolltech.qt.core.Qt.PenStyle;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsPathItem;
import com.trolltech.qt.gui.QGraphicsSceneHoverEvent;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;

import edu.byu.ece.rapidSmith.timing.PathDelay;

@SuppressWarnings("rawtypes")
public class PathItem extends QGraphicsPathItem implements Comparable{
	/** Keeps a green pen handy for selected wire connections on mouse over */
	private static QPen selectedPen  = new QPen(QColor.fromRgb(255, 125, 0), 1.5, PenStyle.SolidLine);
	/** Keeps a red pen handy for highlighting wire connections on mouse over */
	private static QPen highlighted  = new QPen(QColor.red, 1.0, PenStyle.SolidLine);
	/** Keeps a yellow pen for drawing the wire connections */
	private static QPen unHighlighted = new QPen(QColor.yellow, 1.0, PenStyle.SolidLine);
	
	private QPen constraintPen;
	
	private boolean selected = false;
	
	private PathDelay pd;
	
	public PathItem(QPainterPath path, PathDelay pd){
		super(path);
		this.setPath(pd);
		this.setZValue(this.zValue()+10);
		constraintPen = unHighlighted;
	}

	@Override
	public void hoverEnterEvent(QGraphicsSceneHoverEvent event){
		this.setPen(selectedPen);
		this.setZValue(this.zValue()+1);
	}
	
	@Override
	public void hoverLeaveEvent(QGraphicsSceneHoverEvent event){
		if(!selected){
			this.setPen(constraintPen);
			this.setZValue(this.zValue()-1);			
		}
	}
	
	public void setHighlighted(){
		constraintPen = highlighted;
		if(!selected){
			this.setPen(constraintPen);
		}
	}
	
	public void setUnhighlighted(){
		constraintPen = unHighlighted;
		if(!selected){
			this.setPen(constraintPen);							
		}
	}
	
	@Override
	public void mousePressEvent(QGraphicsSceneMouseEvent event){
		if(!event.button().equals(MouseButton.LeftButton)) return;
		if(selected){
			this.setPen(constraintPen);
			selected = false;			
		}
		else{
			this.setPen(selectedPen);
			selected = true;
		}
	}

	/**
	 * @param pd the pd to set
	 */
	public void setPath(PathDelay pd) {
		this.pd = pd;
	}

	/**
	 * @return the pd
	 */
	public PathDelay getPath() {
		return pd;
	}

	@Override
	public int compareTo(Object arg0) {
		return (int) ((pd.getDelay() - ((PathItem)arg0).pd.getDelay())*1000.0);
	}
}

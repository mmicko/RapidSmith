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
package edu.byu.ece.rapidSmith.gui;

import java.util.HashMap;

import com.trolltech.qt.core.Qt.ItemDataRole;
import com.trolltech.qt.gui.QTreeWidget;
import com.trolltech.qt.gui.QTreeWidgetItem;

import edu.byu.ece.rapidSmith.util.FamilyType;
import edu.byu.ece.rapidSmith.util.FileTools;
import edu.byu.ece.rapidSmith.util.PartNameTools;

public class WidgetMaker {
	
	
	public static QTreeWidget createAvailablePartTreeWidget(String header){
		QTreeWidget treeWidget = new QTreeWidget();
		treeWidget.setColumnCount(1);
		treeWidget.setHeaderLabel(header);
		
		HashMap<FamilyType, QTreeWidgetItem> familyItems = new HashMap<FamilyType, QTreeWidgetItem>();
		HashMap<String, QTreeWidgetItem> subFamilyItems = new HashMap<String, QTreeWidgetItem>();
		
		for(String partName : FileTools.getAvailableParts()){
			FamilyType type = PartNameTools.getFamilyTypeFromPart(partName);
			QTreeWidgetItem familyItem = familyItems.get(type);
			if(familyItem == null){
				familyItem = new QTreeWidgetItem(treeWidget);
				familyItem.setText(0, PartNameTools.getFormalFamilyNameFromType(type));
				familyItems.put(type, familyItem);
			}
			String subFamilyName = PartNameTools.getSubFamilyFromPart(partName);
			QTreeWidgetItem partItem = null;
			QTreeWidgetItem parent = familyItem;
			if(subFamilyName != null){
				QTreeWidgetItem subFamilyItem = subFamilyItems.get(type.toString() + subFamilyName);
				if(subFamilyItem == null){
					subFamilyItem = new QTreeWidgetItem(parent);
					subFamilyItem.setText(0, subFamilyName);
					subFamilyItems.put(type.toString() + subFamilyName, subFamilyItem);
				}
				parent = subFamilyItem;
			}
			partItem = new QTreeWidgetItem(parent);
			partItem.setText(0, partName);
	        partItem.setData(0, ItemDataRole.AccessibleDescriptionRole, partName);
		}
		return treeWidget;
	}
}

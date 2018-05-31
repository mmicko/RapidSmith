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
package edu.byu.ece.rapidSmith.router;

import java.util.HashSet;

import edu.byu.ece.rapidSmith.util.FamilyType;
import edu.byu.ece.rapidSmith.util.FileTools;

public class V4PinSorter extends PinSorter{
	static {
		needHARD1 = new HashSet<String>();
		needHARD1.add("SR_B0");
		needHARD1.add("SR_B1");
		needHARD1.add("SR_B2");
		needHARD1.add("SR_B3");
		needHARD1.add("CE_B0");
		needHARD1.add("CE_B1");
		needHARD1.add("CE_B2");
		needHARD1.add("CE_B3");
		
		needSLICESource = new HashSet<String>();
		needSLICESource.add("CLK_B0");
		needSLICESource.add("CLK_B1");
		needSLICESource.add("CLK_B2");
		needSLICESource.add("CLK_B3");
		
		we = FileTools.loadWireEnumerator(FamilyType.VIRTEX4);
	}
}

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
package edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

import edu.byu.ece.rapidSmith.bitstreamTools.configuration.V6BitstreamGenerator;

public class V6ConfigurationSpecification extends V56ConfigurationSpecification {

    public V6ConfigurationSpecification() {
        super();
        _blockTypes = new ArrayList<BlockType>(Arrays.asList(new BlockType[] {LOGIC_INTERCONNECT_BLOCKTYPE, BRAM_CONTENT_BLOCKTYPE}));
        _deviceFamily = V6_FAMILY_NAME;
        _frameSize = V6_FRAME_SIZE;
        _bramContentBlockType = BRAM_CONTENT_BLOCKTYPE;
        _logicBlockType = LOGIC_INTERCONNECT_BLOCKTYPE;
        _bitstreamGenerator = V6BitstreamGenerator.getSharedInstance();
    }
    
	public final static String V6_FAMILY_NAME = "Virtex6";
	public final static int V6_FRAME_SIZE = 81;

	public static final BlockSubType CLB = new BlockSubType("CLB",36); 
	public static final BlockSubType IOB = new BlockSubType("IOB",44); 
	public static final BlockSubType DSP = new BlockSubType("DSP",28); 
	public static final BlockSubType CLK = new BlockSubType("CLK",38); 
	public static final BlockSubType GTX = new BlockSubType("GTX",30);
	public static final BlockSubType GTH = new BlockSubType("GTH",30);
	public static final BlockSubType LOGIC_OVERHEAD = new BlockSubType("LOGIC_OVERHEAD",2); 
	public static final BlockSubType BRAMINTERCONNECT = new BlockSubType("BRAMINTERCONNECT",28); 
	public static final BlockSubType BRAMCONTENT = new BlockSubType("BRAMCONTENT",128); 
	public static final BlockSubType BRAMOVERHEAD = new BlockSubType("BRAMOVERHEAD",2); 

	public static final BlockType LOGIC_INTERCONNECT_BLOCKTYPE = new BlockType("LOGIC", new LinkedHashSet<BlockSubType>(Arrays.asList(
			new BlockSubType[]{
			IOB, 
			CLB, 
			DSP, 
			CLK, 
			GTX,
			GTH,
			BRAMINTERCONNECT,
			LOGIC_OVERHEAD })));
	
	public static final BlockType BRAM_CONTENT_BLOCKTYPE = new BlockType("BRAM", new LinkedHashSet<BlockSubType>(Arrays.asList(
			new BlockSubType[]{
			BRAMCONTENT, 
			BRAMOVERHEAD })));	
}

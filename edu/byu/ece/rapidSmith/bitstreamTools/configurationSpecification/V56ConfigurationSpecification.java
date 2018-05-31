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

import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.DummySyncData;

/**
 * Configuration specification common between the V5 and V6 families. 
 */
public abstract class V56ConfigurationSpecification extends AbstractConfigurationSpecification {
	
    public V56ConfigurationSpecification() {
        _dummySyncData = DummySyncData.V5_V6_STANDARD_DUMMY_SYNC_DATA;
        _minorMask = V56_MINOR_MASK;
        _minorBitPos = V56_MINOR_BIT_POS;
        _columnMask = V56_COLUMN_MASK;
        _columnBitPos = V56_COLUMN_BIT_POS;
        _rowMask = V56_ROW_MASK;
        _rowBitPos = V56_ROW_BIT_POS;
        _topBottomMask = V56_TOP_BOTTOM_MASK;
        _topBottomBitPos = V56_TOP_BOTTOM_BIT_POS;
        _blockTypeMask = V56_BLOCK_TYPE_MASK;
        _blockTypeBitPos = V56_BLOCK_TYPE_BIT_POS;
    }
    
	public static final int V56_TOP_BOTTOM_BIT_POS = 20;
	public static final int V56_TOP_BOTTOM_MASK = 0x1 << V56_TOP_BOTTOM_BIT_POS;
	public static final int V56_BLOCK_TYPE_BIT_POS = 21;
	public static final int V56_BLOCK_TYPE_MASK = 0x7 << V56_BLOCK_TYPE_BIT_POS;
	public static final int V56_ROW_BIT_POS = 15;
	public static final int V56_ROW_MASK = 0x1F << V56_ROW_BIT_POS;
	public static final int V56_COLUMN_BIT_POS = 7;
	public static final int V56_COLUMN_MASK = 0xFF << V56_COLUMN_BIT_POS;
	public static final int V56_MINOR_BIT_POS = 0;
	public static final int V56_MINOR_MASK = 0x7F << V56_MINOR_BIT_POS;
	
}


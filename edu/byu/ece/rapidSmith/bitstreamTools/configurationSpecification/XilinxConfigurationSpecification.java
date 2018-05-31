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

import java.util.List;

import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.DummySyncData;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.BitstreamGenerator;
import edu.byu.ece.rapidSmith.bitstreamTools.configuration.FrameAddressRegister;

/**
 * Specifies the parameters for the configuration data associated
 * with a Xilinx FPGA. This interface is independent of the family
 * and the part. Classes that implement this interface provide
 * all the appropriate parameter data for querying the configuration
 * bitstream. 
 * 
 * 
 * @author wirthlin
 *
 */
public interface XilinxConfigurationSpecification {

    // Specific for a given device family
    public String getDeviceFamily();
    public int getFrameSize();
    public List<BlockType> getBlockTypes(); 
    public DummySyncData getSyncData();
    public BlockType getBRAMContentBlockType();
    public BlockType getLogicBlockType();
    
    public BitstreamGenerator getBitstreamGenerator();
    
    // FAR addressing
    public int getMinorMask();
    public int getMinorBitPos();
    public int getColumnMask();
    public int getColumnBitPos();
    public int getRowMask();
    public int getRowBitPos();
    public int getTopBottomMask();
    public int getTopBottomBitPos();
    public int getBlockTypeMask();
    public int getBlockTypeBitPos();    
    
    // specific for a given device within a family  
    public String getDeviceName();
    public String[] getValidPackages();
    public String[] getValidSpeedGrades();
    public String getStringDeviceIDCode();
    public int getIntDeviceIDCode();
    public int getTopNumberOfRows();
    public int getBottomNumberOfRows();
    
    public List<BlockSubType> getBlockSubTypeLayout(BlockType bt);
    public int getBlockTypeFromFAR(int farAddress);
    public int getTopBottomFromFAR(int farAddress);
    public int getRowFromFAR(int farAddress);
    public int getColumnFromFAR(int farAddress);
    public int getMinorFromFAR(int farAddress);
    public List<BlockSubType> getOverallColumnLayout();
    public List<BlockTypeInstance> getBlockTypeInstances();
    
    public BlockSubType getBlockSubtype(XilinxConfigurationSpecification spec, FrameAddressRegister far);
}

/* Ideas for making this more general:
 * 1. Create an abstract class that represents a block type (Logic, BRAM, BRAM interconnect)
 *    - Indicates the block code used to define the block (in the FAR)
 *    - Has a string associated with it
 *    - Returns an array of valid block sub types (see below). BlockSubType[] getValidBlockSubTypes()
 * 2. Create an abstract class that represents a block sub-type (IO, CLB, DSP, CLK, MGT, etc.)
 *    - Has a name associated with it
 *    - Points to a super block type
 *    - Indicates how many frames are assocaited with this block subtype
 * 3. Each family architecture specification defines a block type for all valid block types
 *    - Returns an array of block types (BlockType[] getValidBlockTypes())
 * 4. Each device indicates how many columns of each block type there are
 *    - int getBlockColumns(BlockType b) 
 * 5. Each device indicates the layout of block sub types for each block type
 *      BlockSubType[] getBlockTypeLayout(Blocktype b)
 *      
 */

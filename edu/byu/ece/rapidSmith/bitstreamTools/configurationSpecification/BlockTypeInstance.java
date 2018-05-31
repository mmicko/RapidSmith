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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * The BlockTypeInstance class represents the layout of a block type for
 * a particular FPGA part. Each part should have as many BlockTypeInstances
 * as there are BlockTypes in the family.
 * 
 * The column layout is represented as a list of BlockSubTypes.
 */
public class BlockTypeInstance {
    
    public BlockTypeInstance(BlockType blockType, List<BlockSubType> columnLayout) {
        _blockType = blockType;
        _columnLayout = new ArrayList<BlockSubType>(columnLayout);
    }
    
    public BlockTypeInstance(BlockType blockType, BlockSubType[] columnLayout) {
        _blockType = blockType;
        _columnLayout = new ArrayList<BlockSubType>(Arrays.asList(columnLayout));
    }
    
    public int getNumColumns() {
        return _columnLayout.size();
    }
    
    public BlockSubType getBlockSubType(int index) {
        if (index >= 0 && index < _columnLayout.size()) {
            return _columnLayout.get(index);
        }
        else {
            return null;
        }
    }
    
    public List<BlockSubType> getColumnLayout() {
        return Collections.unmodifiableList(_columnLayout);
    }
    
    public String toString() {
        String result = _blockType.toString() + " : {";
        Iterator<BlockSubType> it = _columnLayout.iterator();
        while (it.hasNext()) {
            BlockSubType subType = it.next();
            result += subType;
            if (it.hasNext()) {
                result += ", ";
            }
        }
        
        result += "}";
        return result;
    }
    
    protected BlockType _blockType;
    protected List<BlockSubType> _columnLayout;
}

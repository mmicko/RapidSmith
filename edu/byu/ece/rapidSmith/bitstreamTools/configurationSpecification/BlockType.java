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

import java.util.Collections;
import java.util.Set;

/**
 * The BlockType class represents one of the block types for a specific FPGA
 * family (i.e. V4, V5, V6). Each family should have a Set of BlockTypes that
 * indicates the valid block types that are used by the family. Each BlockType
 * has a List of SubBlockTypes indicating the valid column types for the block.
 */
public class BlockType {

    BlockType(String name, Set<BlockSubType> validBlockSubTypes) {
        _name = name;
        _validBlockSubTypes = validBlockSubTypes;
    }
    
    public String getName() {
        return _name;
    }
    
    public String toString() {
        return _name;
    }
    
    public Set<BlockSubType> getValidBlockSubTypes() {
        return Collections.unmodifiableSet(_validBlockSubTypes);
    }
    
    protected String _name;
    protected Set<BlockSubType> _validBlockSubTypes;
}

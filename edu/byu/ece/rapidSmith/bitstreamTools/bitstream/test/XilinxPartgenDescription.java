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
package edu.byu.ece.rapidSmith.bitstreamTools.bitstream.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class XilinxPartgenDescription {

    public XilinxPartgenDescription(List<String> partNames, List<List<String>> validPackages, List<List<String>> validSpeedGrades) {
        _partNames = new ArrayList<String>(partNames);
        
        _validPackages = new LinkedHashMap<String, List<String>>();
        _validSpeedGrades = new LinkedHashMap<String, List<String>>();
        int i = 0;
        for (String partName : _partNames) {
            _validPackages.put(partName, new ArrayList<String>(validPackages.get(i)));
            _validSpeedGrades.put(partName, new ArrayList<String>(validSpeedGrades.get(i)));
            i++;
        }
    }

    public List<String> getPartNames() {
        return new ArrayList<String>(_partNames);
    }
    
    public List<String> getValidPackagesForPart(String partName) {
        List<String> result = null;
        List<String> packages = _validPackages.get(partName);
        if (packages != null) {
            result = new ArrayList<String>(packages);
        }
        return result;
    }
    
    public List<String> getValidSpeedGradesForPart(String partName) {
        List<String> result = null;
        List<String> speedGrades = _validSpeedGrades.get(partName);
        if (speedGrades != null) {
            result = new ArrayList<String>(speedGrades);
        }
        return result;
    }
    
    protected List<String> _partNames;
    protected Map<String, List<String>> _validPackages;
    protected Map<String, List<String>> _validSpeedGrades;
}

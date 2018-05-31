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
import java.util.Iterator;
import java.util.List;

/**
 * Contains a List of related configuration specification parts.
 *
 */
public abstract class PartLibrary {

	public PartLibrary() {
		_parts = new ArrayList<XilinxConfigurationSpecification>();
		addParts();
	}

	public XilinxConfigurationSpecification getPartFromDeviceName(String name) {		
		for (Iterator<XilinxConfigurationSpecification> i = _parts.iterator(); i.hasNext(); ) {
			XilinxConfigurationSpecification spec = i.next();
			if (spec.getDeviceName().equalsIgnoreCase(name))
				return spec;
		}
		return null;
	}

	public XilinxConfigurationSpecification getPartFromDeviceNameIgnoreCase(String name) {
		for (Iterator<XilinxConfigurationSpecification> i = _parts.iterator(); i.hasNext(); ) {
			XilinxConfigurationSpecification spec = i.next();
			if (spec.getDeviceName().equalsIgnoreCase(name))
				return spec;
		}
		return null;
	}

	// TODO: This method should take a large part name that includes the appended package
	// and find the correct part. This is mostly parsing of the name to separate the 
	// device name from the package name.
	public XilinxConfigurationSpecification getPartFromDevicePackageName(String name) {
		for (Iterator<XilinxConfigurationSpecification> i = _parts.iterator(); i.hasNext(); ) {
			XilinxConfigurationSpecification spec = i.next();
			if (spec.getDeviceName().equals(name))
				return spec;
		}
		return null;
	}

	public XilinxConfigurationSpecification getPartFromIDCode(String name) {		
		for (Iterator<XilinxConfigurationSpecification> i = _parts.iterator(); i.hasNext(); ) {
			XilinxConfigurationSpecification spec = i.next();
			if (spec.getStringDeviceIDCode().equalsIgnoreCase(name))
				return spec;
		}
		return null;
	}

	public List<XilinxConfigurationSpecification> getParts() {
		return new ArrayList<XilinxConfigurationSpecification>(_parts);
	}
	
	protected void addPart(XilinxConfigurationSpecification spec) {
		_parts.add(spec);
	}
	
	protected abstract void addParts();

	protected ArrayList<XilinxConfigurationSpecification> _parts;
	

}

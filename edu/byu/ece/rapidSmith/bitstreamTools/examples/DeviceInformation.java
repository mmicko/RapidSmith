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
package edu.byu.ece.rapidSmith.bitstreamTools.examples;

import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.DeviceLookup;
import edu.byu.ece.rapidSmith.bitstreamTools.configurationSpecification.XilinxConfigurationSpecification;

/**
 * Provides a main method that prints out information about a particular device.
 */
public class DeviceInformation {

	static public void main(String args[]) {

		if (args.length < 1) {
			System.err.println("usage: <executable> <part name>\n");
			DeviceLookup.printAvailableParts(System.err);
			System.exit(1);
		}

		String partname = args[0];

		XilinxConfigurationSpecification spec = DeviceLookup.lookupPartV4V5V6(partname);
		if (spec == null) {
			DeviceLookup.printAvailableParts(System.err);
			System.exit(1);
		}

		System.out.println(spec.toString());

	}

}

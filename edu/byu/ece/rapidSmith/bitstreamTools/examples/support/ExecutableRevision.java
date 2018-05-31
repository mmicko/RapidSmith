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
package edu.byu.ece.rapidSmith.bitstreamTools.examples.support;

/**
 * Maintains a revision number for all executables. This file should be updated and
 * committed each time a release is given to distinguish releases for the users
 * of the executables.
 * 
 */
public class ExecutableRevision {

	/** A revision tag maintained by SVN. Do not edit manually. **/
	public static String SVN_VERSION = "$Revision: 1877 $";
	
	/** A date tag maintained by SVN. Do not edit manually. **/
	public static String SVN_DATE = "$Date: 2010-03-19 09:01:50 -0600 (Fri, 19 Mar 2010) $";
	
	public static String REVISION = "0.4.1";
	
	public static String BUILD_DATE = "3-19-2010";
	
	/**
	 * Parses the SVN_VERSION String and returns the value without the extra characters.
	 */
	public static String getSVNVersion() {
		String svn = SVN_VERSION;
		
		int beginIndex = new String("$Revision:").length() + 1;
		int endIndex = SVN_VERSION.length()-2;
		return svn.substring(beginIndex, endIndex);
	}
	
	public static String getSVNDate() {
		String svn = SVN_DATE;
		
		int beginIndex = new String("$Date:").length() + 1;
		int endIndex = SVN_DATE.length()-2;
		return svn.substring(beginIndex, endIndex);		
	}
	
	public static String getRevisionString() {
		return "Version " + REVISION + " (svn:" + getSVNVersion() + ") " + getSVNDate();
	}
	
}

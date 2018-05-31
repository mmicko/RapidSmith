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
/**
 * 
 */
package edu.byu.ece.rapidSmith.bitstreamTools.bitstream;

import java.util.List;

/**
 * abstract class meant to be the base class of anything that can produce
 * configuration data (DummySyncData, Packet, and PacketList).
 * 
 * With this base class, tools can use any of the sub classes to send
 * configuration data to a configuration port, regardless of the specific source
 * of the data.
 * 
 * @author Peter Lieber
 * 
 */
public abstract class ConfigurationData {
	public abstract List<Byte> toByteArray();
}

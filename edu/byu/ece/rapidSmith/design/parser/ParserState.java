/*
 * Copyright (c) 2010 Brigham Young University
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
package edu.byu.ece.rapidSmith.design.parser;

public enum ParserState{
	BEGIN_DESIGN,
	DESIGN_NAME,
	PART_NAME,
	NCD_VERSION,
	CFG_STRING,
	ATTRIBUTE,
	XDL_STATEMENT,
	INSTANCE_NAME,
	INSTANCE_TYPE,
	INSTANCE_PLACED,
	INSTANCE_TILE,
	INSTANCE_SITE,
	INSTANCE_BONDED,
	MODULE_INSTANCE_TOKEN,
	MODULE_INSTANCE_NAME,
	MODULE_TEMPLATE_NAME,
	MODULE_TEMPLATE_INSTANCE_NAME,
	NET_NAME,
	NET_TYPE,
	NET_STATEMENT,
	PIN_INSTANCE_NAME,
	PIN_NAME,
	PIP_TILE,
	PIP_WIRE0,
	PIP_CONN_TYPE,
	PIP_WIRE1,
	MODULE_NAME,
	MODULE_ANCHOR_NAME,
	MODULE_STATEMENT,
	PORT_NAME,
	PORT_INSTANCE_NAME,
	PORT_PIN_NAME,
	END_PORT,
	END_MODULE_NAME,
	END_MODULE
}

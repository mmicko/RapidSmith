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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.Bitstream;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.BitstreamParseException;
import edu.byu.ece.rapidSmith.bitstreamTools.bitstream.BitstreamParser;

public class ParserGenerator {

    /**
     * This class parses a bitstream (.bit) and creates a Java represtation of it. It then outputs
     * the bitstream as a .bit file and a .mcs file. The .bit file should match the original .bit file
     * exactly. The .mcs file should match exactly an .mcs generated from the original .bit file using
     * promgen -u 0 &lt;bitfile.bit&gt;
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        if (args.length != 3) {
            System.err.println("Usage: java edu.byu.ece.bitstreamTools.bitstream.test.ParserGenerator <input.bit> <output.bit> <output.mcs>");
            System.exit(1);
        }
        
        String inputName = args[0];
        String outputName = args[1];
        String mcsName = args[2];
        
        Bitstream bs = null;
        try {
            bs = BitstreamParser.parseBitstream(inputName);
        } catch (BitstreamParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        FileOutputStream os_bit = null;
        try {
            os_bit = new FileOutputStream(new File(outputName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            bs.outputHeaderBitstream(os_bit);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        FileOutputStream os_mcs = null;
        try {
            os_mcs = new FileOutputStream(new File(mcsName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            bs.writeBitstreamToMCS(os_mcs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

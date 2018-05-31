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

import java.io.IOException;
import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class CreateEmptyBitstreams {

    public static void main(String[] args) {

        OptionParser parser = new OptionParser() {
            {
                accepts("a", "Name of the architecture to create empty bitstreams for").withRequiredArg().ofType(String.class);
            }
        };


        OptionSet options = null;
        try {
            options = parser.parse(args);
        }
        catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }


        List<String> arguments = options.nonOptionArguments();

        // Print help options
        if(options.has("h") || (arguments.size() != 1)){
            try {
                System.out.println("Usage: java edu.byu.ece.bitstreamTools.bitstream.test.CreateEmptyBitstreams <architecture>\n");
                parser.printHelpOn(System.out);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            System.exit(0);
        }

        TestTools.generateBlankBitstreams(arguments.get(0), false);        

    }
}

////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.regexp.RESyntaxException;

/**
 * Wrapper command line program for the Checker.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
public final class Main
{
    /**
     * Loops over the files specified checking them for errors. The exit code
     * is the number of errors found in all the files.
     * @param aArgs the command line arguments
     **/
    public static void main(String[] aArgs)
    {
        if (aArgs.length == 0) {
            usage();
        }

        // be brain dead about arguments parsing
        String format = "plain";
        String output = null;
        String[] files = null;
        for (int i = 0; i < aArgs.length; i++) {
            if ("-f".equals(aArgs[i])) {
                format = aArgs[++i];
            }
            else if ("-o".equals(aArgs[i])) {
                output = aArgs[++i];
            }
            else {
                files = new String[aArgs.length - i];
                System.arraycopy(aArgs, i, files, 0, files.length);
                break;
            }
        }

        // create the appropriate listener
        OutputStream out = System.out;
        if (output != null) {
            try {
                out = new FileOutputStream(output);
            }
            catch (FileNotFoundException e) {
                System.out.println("Could not find file: '" + output + "'");
                System.exit(1);
            }
        }
        AuditListener listener = null;
        if ("xml".equals(format)) {
            listener = new XMLLogger(out);
        }
        else if ("plain".equals(format)) {
            listener = new DefaultLogger(out);
        }
        else {
            System.out.println("Invalid format: (" + format +
                               "). Must be 'plain' or 'xml'.");
            usage();
        }

        Checker c = null;
        try {
            c = new Checker(new Configuration(System.getProperties(),
                                              System.out));
            c.addListener(listener);
        }
        catch (RESyntaxException rese) {
            System.out.println("Unable to create an regexp object: " +
                               rese.getMessage());
            rese.printStackTrace(System.out);
            System.exit(1);
        }
        catch (IOException ex) {
            System.out.println("I/O error occurred: " + ex.getMessage());
            ex.printStackTrace(System.out);
            System.exit(1);
        }

        final int numErrs = c.process(files);

        c.destroy();
        System.exit(numErrs);
    }

    /** Prints the usage information. **/
    private static void usage()
    {
        System.out.println(
            "Usage: java " +
            Main.class.getName() + " <options> <file1> <file2>......");
        System.out.println("Options");
        System.out.println(
            "\t-f <format>\tsets output format. (plain|xml). " +
            "Default to plain.");
        System.out.println("\t-o <file>\tsets output file name. " +
                           "Defaults to stdout");
        System.exit(1);
    }
}

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

        Checker c = null;
        try {
            c = new Checker(System.getProperties(), System.out);
        }
        catch (RESyntaxException rese) {
            System.out.println("Unable to create an regexp object: " +
                               rese.getMessage());
            rese.printStackTrace(System.out);
            System.exit(1);
        }

        int numErrors = 0;
        for (int i = 0; i < aArgs.length; i++) {
            numErrors += c.process(aArgs[i]);
        }

        System.exit(numErrors);
    }

    /** Prints the usage information. **/
    private static void usage()
    {
        System.out.println(
            "Usage: java " +
            com.puppycrawl.tools.checkstyle.Main.class.getName() + " file...");
        System.exit(1);
    }
}

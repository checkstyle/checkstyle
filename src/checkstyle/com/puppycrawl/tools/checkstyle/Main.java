////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
        Properties properties = System.getProperties();
        final ArrayList files = new ArrayList();
        for (int i = 0; i < aArgs.length; i++) {
            if ("-f".equals(aArgs[i])) {
                format = aArgs[++i];
            }
            else if ("-o".equals(aArgs[i])) {
                output = aArgs[++i];
            }
            else if ("-r".equals(aArgs[i])) {
                traverse(new File(aArgs[++i]), files);
            }
            else if ("-p".equals(aArgs[i])) {
                properties = loadProperties(new File(aArgs[++i]));
            }
            else {
                files.add(aArgs[i]);
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
            System.out.println("Invalid format: (" + format
                               + "). Must be 'plain' or 'xml'.");
            usage();
        }

        Checker c = null;
        try {
            c = new Checker(new Configuration(properties, System.out));
            c.addListener(listener);
        }
        catch (RESyntaxException rese) {
            System.out.println("Unable to create an regexp object: "
                               + rese.getMessage());
            rese.printStackTrace(System.out);
            System.exit(1);
        }
        catch (IOException ex) {
            System.out.println("I/O error occurred: " + ex.getMessage());
            ex.printStackTrace(System.out);
            System.exit(1);
        }

        final int numErrs =
            c.process((String[]) files.toArray(new String[files.size()]));
        c.destroy();
        System.exit(numErrs);
    }

    /** Prints the usage information. **/
    private static void usage()
    {
        System.out.println(
            "Usage: java " + Main.class.getName() + " <options> <file>......");
        System.out.println("Options");
        System.out.println(
            "\t-f <format>\tsets output format. (plain|xml). "
            + "Default to plain.");
        System.out.println("\t-o <file>\tsets output file name. "
                           + "Defaults to stdout");
        System.out.println("\t-r <dir>\ttraverses the directory for Java"
                           + " source files.");
        System.out.println("\t-p <file>\tuses a properties file"
                           + " instead of the system properties.");
        System.exit(1);
    }

    /**
     * Traverses a specified node looking for Java source files. Found Java
     * source files are added to a specified list. Subdirectories are also
     * traversed.
     *
     * @param aNode the node to process
     * @param aFiles list to add found files to
     */
    private static void traverse(File aNode, List aFiles)
    {
        if (aNode.canRead()) {
            if (aNode.isDirectory()) {
                final File[] nodes = aNode.listFiles();
                for (int i = 0; i < nodes.length; i++) {
                    traverse(nodes[i], aFiles);
                }
            }
            else if (aNode.isFile() && aNode.getPath().endsWith(".java")) {
                aFiles.add(aNode.getPath());
            }
        }
    }

    /**
     * Loads properties from a File.
     * @param aFile the properties file
     * @return the properties in aFile
     */
    private static Properties loadProperties(File aFile)
    {
        Properties properties = new Properties();
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(aFile);
            properties.load(fis);
            fis.close();
        }
        catch (IOException ex) {
            System.out.println("Unable to load properties from file: "
                               + aFile.getAbsolutePath());
            ex.printStackTrace(System.out);
            System.exit(1);
        }
        return properties;
    }
}

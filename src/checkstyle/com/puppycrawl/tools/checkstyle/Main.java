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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Wrapper command line program for the Checker.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
public final class Main
{
    /** the options to the command line */
    private static final Options OPTS = new Options();
    static {
        OPTS.addOption("c", true, "The check configuration file to use.");
        OPTS.addOption("r", true, "Traverse the directory for source files");
        OPTS.addOption("o", true, "Sets the output file. Defaults to stdout");
        OPTS.addOption("p", true, "Loads the properties file");
        OPTS.addOption(
            "f",
            true,
            "Sets the output format. (plain|xml). Defaults to plain");
    }
    
    /**
     * Loops over the files specified checking them for errors. The exit code
     * is the number of errors found in all the files.
     * @param aArgs the command line arguments
     **/
    public static void main(String[] aArgs)
    {
        // parse the parameters
        final CommandLineParser clp = new PosixParser();
        CommandLine line = null;
        try {
            line = clp.parse(OPTS, aArgs);
        }
        catch (ParseException e) {
            e.printStackTrace();
            usage();
        }
        
        // setup the properties
        final Properties props =
            line.hasOption("p")
                ? loadProperties(new File(line.getOptionValue("p")))
                : System.getProperties();

        // ensure a config file is specified
        if (!line.hasOption("c")) {
            System.out.println("Must specify a config XML file.");
            usage();
        }
        
        // Load the config file
        CheckConfiguration[] checkConfigs = null;
        try {
            checkConfigs =
                ConfigurationLoader.loadConfigs(line.getOptionValue("c"));
        }
        catch (CheckstyleException e) {
            System.out.println("Error loading configuration file");
            e.printStackTrace(System.out);
            System.exit(1);
        }

        // setup the output stream
        OutputStream out = null;
        boolean closeOut = false;
        if (line.hasOption("o")) {
            final String fname = line.getOptionValue("o"); 
            try {
                out = new FileOutputStream(fname);
                closeOut = true;
            }
            catch (FileNotFoundException e) {
                System.out.println("Could not find file: '" + fname + "'");
                System.exit(1);
            }
        }
        else {
            out = System.out;
            closeOut = false;
        }
        
        // create the appropriate listener
        final String format =
            line.hasOption("f") ? line.getOptionValue("f") : "plain";
        
        AuditListener listener = null;
        if ("xml".equals(format)) {
            listener = new XMLLogger(out, closeOut);
        }
        else if ("plain".equals(format)) {
            listener = new DefaultLogger(out, closeOut);
        }
        else {
            System.out.println("Invalid format: (" + format
                               + "). Must be 'plain' or 'xml'.");
            usage();
        }

        // Get all the Java files
        final List files = new ArrayList();
        if (line.hasOption("r")) {
            final String[] values = line.getOptionValues("r");
            for (int i = 0; i < values.length; i++) {
                traverse(new File(values[i]), files);
            }
        }
 
        final String[] remainingArgs = line.getArgs();
        for (int i = 0; i < remainingArgs.length; i++) {
            files.add(remainingArgs[i]);
        } 

        if (files.isEmpty()) {
            System.out.println("Must specify files to process");
            usage();
        }         

        // create the checker
        Checker c = null;
        try {
            c = new Checker(new Configuration(props, System.out), checkConfigs);
            c.addListener(listener);
        }
        catch (Exception e) {
            System.out.println("Unable to create Checker: "
                               + e.getMessage());
            e.printStackTrace(System.out);
            System.exit(1);
        }

        final int numErrs =
            c.processNEW((String[]) files.toArray(new String[files.size()]));
        c.destroy();
        System.exit(numErrs);
    }

    /** Prints the usage information. **/
    private static void usage()
    {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp(
            "java "
                + Main.class.getName()
                + " [options] -c <config.xml> file...",
            OPTS);
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

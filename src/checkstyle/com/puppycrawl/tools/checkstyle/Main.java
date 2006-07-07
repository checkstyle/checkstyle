////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
import java.util.List;
import java.util.Properties;
import java.util.LinkedList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Wrapper command line program for the Checker.
 * @author Oliver Burn
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
        OPTS.addOption("n", true, "Loads the package names file");
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
        catch (final ParseException e) {
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

        final Configuration config = loadConfig(line, props);

        //Load the set of package names
        ModuleFactory moduleFactory = null;
        if (line.hasOption("n")) {
            moduleFactory = loadPackages(line);
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
            catch (final FileNotFoundException e) {
                System.out.println("Could not find file: '" + fname + "'");
                System.exit(1);
            }
        }
        else {
            out = System.out;
            closeOut = false;
        }

        final AuditListener listener = createListener(line, out, closeOut);
        final List files = getFilesToProcess(line);
        final Checker c = createChecker(config, moduleFactory, listener);

        final File[] processedFiles = new File[files.size()];
        files.toArray(processedFiles);
        final int numErrs = c.process(processedFiles);
        c.destroy();
        System.exit(numErrs);
    }

    /**
     * Creates the Checker object.
     *
     * @param aConfig the configuration to use
     * @param aFactory the module factor to use
     * @param aNosy the sticky beak to track what happens
     * @return a nice new fresh Checker
     */
    private static Checker createChecker(Configuration aConfig,
                                         ModuleFactory aFactory,
                                         AuditListener aNosy)
    {
        Checker c = null;
        try {
            c = new Checker();
            c.setModuleFactory(aFactory);
            c.configure(aConfig);
            c.addListener(aNosy);
        }
        catch (final Exception e) {
            System.out.println("Unable to create Checker: "
                               + e.getMessage());
            e.printStackTrace(System.out);
            System.exit(1);
        }
        return c;
    }

    /**
     * Determines the files to process.
     *
     * @param aLine the command line options specifying what files to process
     * @return list of files to process
     */
    private static List getFilesToProcess(CommandLine aLine)
    {
        final List files = new LinkedList();
        if (aLine.hasOption("r")) {
            final String[] values = aLine.getOptionValues("r");
            for (int i = 0; i < values.length; i++) {
                traverse(new File(values[i]), files);
            }
        }

        final String[] remainingArgs = aLine.getArgs();
        for (int i = 0; i < remainingArgs.length; i++) {
            files.add(new File(remainingArgs[i]));
        }

        if (files.isEmpty()) {
            System.out.println("Must specify files to process");
            usage();
        }
        return files;
    }

    /**
     * Create the audit listener
     *
     * @param aLine command line options supplied
     * @param aOut the stream to log to
     * @param aCloseOut whether the stream should be closed
     * @return a fresh new <code>AuditListener</code>
     */
    private static AuditListener createListener(CommandLine aLine,
                                                OutputStream aOut,
                                                boolean aCloseOut)
    {
        final String format =
            aLine.hasOption("f") ? aLine.getOptionValue("f") : "plain";

        AuditListener listener = null;
        if ("xml".equals(format)) {
            listener = new XMLLogger(aOut, aCloseOut);
        }
        else if ("plain".equals(format)) {
            listener = new DefaultLogger(aOut, aCloseOut);
        }
        else {
            System.out.println("Invalid format: (" + format
                               + "). Must be 'plain' or 'xml'.");
            usage();
        }
        return listener;
    }

    /**
     * Loads the packages, or exists if unable to.
     *
     * @param aLine the supplied command line options
     * @return a fresh new <code>ModuleFactory</code>
     */
    private static ModuleFactory loadPackages(CommandLine aLine)
    {
        try {
            return PackageNamesLoader.loadModuleFactory(
                aLine.getOptionValue("n"));
        }
        catch (final CheckstyleException e) {
            System.out.println("Error loading package names file");
            e.printStackTrace(System.out);
            System.exit(1);
            return null; // never get here
        }
    }

    /**
     * Loads the configuration file. Will exit if unable to load.
     *
     * @param aLine specifies the location of the configuration
     * @param aProps the properties to resolve with the configuration
     * @return a fresh new configuration
     */
    private static Configuration loadConfig(CommandLine aLine,
                                            Properties aProps)
    {
        try {
            return ConfigurationLoader.loadConfiguration(
                    aLine.getOptionValue("c"), new PropertiesExpander(aProps));
        }
        catch (final CheckstyleException e) {
            System.out.println("Error loading configuration file");
            e.printStackTrace(System.out);
            System.exit(1);
            return null; // can never get here
        }
    }

    /** Prints the usage information. **/
    private static void usage()
    {
        final HelpFormatter hf = new HelpFormatter();
        hf.printHelp(
            "java "
                + Main.class.getName()
                + " [options] -c <config.xml> file...",
            OPTS);
        System.exit(1);
    }

    /**
     * Traverses a specified node looking for files to check. Found
     * files are added to a specified list. Subdirectories are also
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
            else if (aNode.isFile()) {
                aFiles.add(aNode);
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
        final Properties properties = new Properties();
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(aFile);
            properties.load(fis);
            fis.close();
        }
        catch (final IOException ex) {
            System.out.println("Unable to load properties from file: "
                               + aFile.getAbsolutePath());
            ex.printStackTrace(System.out);
            System.exit(1);
        }
        return properties;
    }
}

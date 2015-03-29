////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
 * @author Damian Szczepanik (damianszczepanik@github)
 **/
public final class Main
{
    /**
     * Loops over the files specified checking them for errors. The exit code
     * is the number of errors found in all the files.
     * @param args the command line arguments
     * @exception UnsupportedEncodingException if there is a problem to use UTF-8
     **/
    public static void main(String[] args) throws UnsupportedEncodingException
    {
        boolean parseResult = false;
        try {
            processCommandLine(parseCli(args));
            parseResult = true;
        }
        catch (final ParseException e) {
            System.err.println("Could not parse parameters: " + e.getMessage());
            e.printStackTrace();
        }
        catch (final Exception e) {
            System.err.println("Could not execute Checkstyle: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            if (!parseResult) {
                usage();
            }
            // provide proper exit code based on results.
            System.exit(parseResult ? 0 : 1);
        }
    }

    /**
     * Parses and executes Checkstyle based on passed arguments.
     * @param args
     *        command line parameters
     * @throws ParseException
     *         when passed arguments are not valid
     * @exception CheckstyleException when provided parameters are not supported
     * @return parsed information about passed parameters
     */
    private static CommandLine parseCli(String[] args)
            throws ParseException
    {
        // parse the parameters
        final CommandLineParser clp = new PosixParser();
        // always returns not null value
        return clp.parse(buildOptions(), args);
    }

    /**
     * Executes required Checkstyle actions based on passed parameters.
     * @param line
     *        list of actions to execute
     * @throws CheckstyleException
     *         when could not create checker
     * @throws UnsupportedEncodingException
     *         if there is problem to use UTf-8
     */
    private static void processCommandLine(CommandLine line)
            throws CheckstyleException, UnsupportedEncodingException
    {
        // show version and exit
        if (line.hasOption("v")) {
            System.out.println("Checkstyle version: "
                    + Main.class.getPackage().getImplementationVersion());
            return;
        }

        // ensure a configuration file is specified
        if (!line.hasOption("c")) {
            System.out.println("Must specify a config XML file.");
            return;
        }

        // setup the properties
        final Properties props =
                line.hasOption("p")
                        ? loadProperties(new File(line.getOptionValue("p")))
                        : System.getProperties();
        final String configFormat = line.getOptionValue("c");
        final Configuration config = loadConfig(configFormat, props);

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
                throw new CheckstyleException(String.format(
                        "Could not find file '%s': %s ", fname, e.getMessage()));
            }
        }
        else {
            out = System.out;
            closeOut = false;
        }

        final String format = line.hasOption("f") ? line.getOptionValue("f") : "plain";
        final AuditListener listener = createListener(format, out, closeOut);
        final List<File> files = getFilesToProcess(line.getArgs());
        if (files.isEmpty()) {
            throw new CheckstyleException("Must specify files to process, found 0.");
        }

        final Checker checker = createChecker(config, listener);
        final int errorCounter = checker.process(files);
        checker.destroy();
        if (errorCounter != 0) {
            throw new CheckstyleException(String.format(
                    "Checkstyle ends with %d errors.", errorCounter));
        }
    }

    /** Don't create instance of this class, use {@link #main(String[])} method instead. */
    private Main()
    {
    }

    /**
     * Creates the Checker object.
     * @param config
     *        the configuration to use
     * @param auditListener
     *        the sticky beak to track what happens
     * @return a nice new fresh Checker
     * @throws CheckstyleException when could not create checker
     */
    private static Checker createChecker(Configuration config,
            AuditListener auditListener)
            throws CheckstyleException
    {
        final Checker checker = new Checker();

        final ClassLoader moduleClassLoader = Checker.class.getClassLoader();
        checker.setModuleClassLoader(moduleClassLoader);
        checker.configure(config);
        checker.addListener(auditListener);

        return checker;
    }

    /**
     * Determines the files to process.
     * @param filesToProcess
     *        arguments that were not processed yet but shall be
     * @return list of files to process
     * @throws CheckstyleException
     *         when there is no file to process
     */
    private static List<File> getFilesToProcess(String[] filesToProcess)
            throws CheckstyleException
    {
        final List<File> files = Lists.newLinkedList();
        for (String element : filesToProcess) {
            files.addAll(listFiles(new File(element)));
        }

        return files;
    }

    /**
     * Creates the audit listener.
     *
     * @param format format of the auditt listener
     * @param out the stream to log to
     * @param closeOut whether the stream should be closed
     * @return a fresh new <code>AuditListener</code>
     * @exception UnsupportedEncodingException if there is problem to use UTf-8
     * @exception CheckstyleException when provided parameters are not supported
     */
    private static AuditListener createListener(String format,
                                                OutputStream out,
                                                boolean closeOut)
            throws UnsupportedEncodingException, CheckstyleException
    {
        AuditListener listener = null;
        switch (format) {
            case "xml":
                listener = new XMLLogger(out, closeOut);
                break;

            case "plain":
                listener = new DefaultLogger(out, closeOut);
                break;

            default:
                throw new CheckstyleException("Invalid output format. Found '" + format
                        + "' but expected 'plain' or 'xml'.");
        }

        return listener;
    }

    /**
     * Loads the configuration file. Will exit if unable to load.
     *
     * @param format
     *            specifies the location of the configuration
     * @param props
     *            the properties to resolve with the configuration
     * @return a fresh new configuration
     * @throws CheckstyleException
     *             when could not load properties file
     */
    private static Configuration loadConfig(String format,
                                            Properties props)
            throws CheckstyleException
    {
        return ConfigurationLoader.loadConfiguration(format, new PropertiesExpander(props));
    }

    /** Prints the usage information. **/
    private static void usage()
    {
        final HelpFormatter hf = new HelpFormatter();
        hf.printHelp(String.format("java %s [options] -c <config.xml> file...",
                Main.class.getName()), buildOptions());
    }

    /**
     * Builds and returns list of parameters supported by cli Checkstyle.
     * @return available options
     */
    private static Options buildOptions()
    {
        final Options options = new Options();
        options.addOption("c", true, "Sets the check configuration file to use.");
        options.addOption("o", true, "Sets the output file. Defaults to stdout");
        options.addOption("p", true, "Loads the properties file");
        options.addOption("f", true, "Sets the output format. (plain|xml). Defaults to plain");
        options.addOption("v", false, "Print product version and exit");
        return options;
    }

    /**
     * Traverses a specified node looking for files to check. Found files are added to a specified
     * list. Subdirectories are also traversed.
     * @param node
     *        the node to process
     * @return found files
     */
    private static List<File> listFiles(File node)
    {
        // could be replaced with org.apache.commons.io.FileUtils.list() method
        // if only we add commons-io library
        final List<File> files = Lists.newLinkedList();

        if (node.canRead()) {
            if (node.isDirectory()) {
                for (File element : node.listFiles()) {
                    files.addAll(listFiles(element));
                }
            }
            else if (node.isFile()) {
                files.add(node);
            }
        }
        return files;
    }

    /**
     * Loads properties from a File.
     * @param file
     *        the properties file
     * @return the properties in file
     * @throws CheckstyleException
     *         when could not load properties file
     */
    private static Properties loadProperties(File file)
            throws CheckstyleException
    {
        final Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        }
        catch (final IOException e) {
            throw new CheckstyleException(String.format(
                    "Unable to load properties from file '%s'.", file.getAbsolutePath()), e);
        }

        return properties;
    }
}

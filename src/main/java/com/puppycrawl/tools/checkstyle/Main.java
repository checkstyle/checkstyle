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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.common.collect.Lists;
import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

/**
 * Wrapper command line program for the Checker.
 * @author the original author or authors.
 *
 **/
public final class Main {
    /** Don't create instance of this class, use {@link #main(String[])} method instead. */
    private Main() {
    }

    /**
     * Loops over the files specified checking them for errors. The exit code
     * is the number of errors found in all the files.
     * @param args the command line arguments.
     * @throws UnsupportedEncodingException if there is a problem to use UTF-8
     * @throws FileNotFoundException if there is a problem with files access
     **/
    public static void main(String... args) throws UnsupportedEncodingException,
            FileNotFoundException {
        int errorCounter = 0;
        boolean cliViolations = false;
        // provide proper exit code based on results.
        final int exitWithCliViolation = -1;
        int exitStatus = 0;

        try {
            //parse CLI arguments
            final CommandLine commandLine = parseCli(args);

            // show version and exit if it is requested
            if (commandLine.hasOption("v")) {
                System.out.println("Checkstyle version: "
                        + Main.class.getPackage().getImplementationVersion());
                exitStatus = 0;
            }
            else {
                // return error is smth is wrong in arguments
                final List<String> messages = validateCli(commandLine);
                cliViolations = !messages.isEmpty();
                if (cliViolations) {
                    exitStatus = exitWithCliViolation;
                    errorCounter = 1;
                    for (String message : messages) {
                        System.out.println(message);
                    }
                }
                else {
                    // create config helper object
                    final CliOptions config = convertCliToPojo(commandLine);
                    // run Checker
                    errorCounter = runCheckstyle(config);
                    exitStatus = errorCounter;
                }
            }
        }
        catch (ParseException pex) {
            // smth wrong with arguments - print error and manual
            cliViolations = true;
            exitStatus = exitWithCliViolation;
            errorCounter = 1;
            System.out.println(pex.getMessage());
            printUsage();
        }
        catch (CheckstyleException e) {
            final int exitWithCheckstyleException = -2;
            exitStatus = exitWithCheckstyleException;
            errorCounter = 1;
            System.out.println(e.getMessage());
        }
        finally {
            // return exit code base on validation of Checker
            if (errorCounter != 0 && !cliViolations) {
                System.out.println(String.format("Checkstyle ends with %d errors.", errorCounter));
            }
            if (exitStatus != 0) {
                System.exit(exitStatus);
            }
        }
    }

    /**
     * Parses and executes Checkstyle based on passed arguments.
     * @param args
     *        command line parameters
     * @return parsed information about passed parameters
     * @throws ParseException
     *         when passed arguments are not valid
     */
    private static CommandLine parseCli(String... args)
            throws ParseException {
        // parse the parameters
        final CommandLineParser clp = new DefaultParser();
        // always returns not null value
        return clp.parse(buildOptions(), args);
    }

    /**
     * Do validation of Command line options
     * @param cmdLine command line object
     * @return list of violations
     */
    private static List<String> validateCli(CommandLine cmdLine) {
        final List<String> result = new ArrayList<>();
        // ensure a configuration file is specified
        if (cmdLine.hasOption("c")) {
            // validate optional parameters
            if (cmdLine.hasOption("f")) {
                final String format = cmdLine.getOptionValue("f");
                if (!"plain".equals(format) && !"xml".equals(format)) {
                    result.add(String.format("Invalid output format."
                            + " Found '%s' but expected 'plain' or 'xml'.", format));
                }
            }
            if (cmdLine.hasOption("p")) {
                final String propertiesLocation = cmdLine.getOptionValue("p");
                final File file = new File(propertiesLocation);
                if (!file.exists()) {
                    result.add(String.format("Could not find file '%s'.", propertiesLocation));
                }
            }
            if (cmdLine.hasOption("o")) {
                final String outputLocation = cmdLine.getOptionValue("o");
                final File file = new File(outputLocation);
                if (file.exists() && !file.canWrite()) {
                    result.add(String.format("Permission denied : '%s'.", outputLocation));
                }
            }
            final List<File> files = getFilesToProcess(cmdLine.getArgs());
            if (files.isEmpty()) {
                result.add("Must specify files to process, found 0.");
            }
        }
        else {
            result.add("Must specify a config XML file.");
        }

        return result;
    }

    /**
     * Util method to convert ComandLine type to POJO object
     * @param cmdLine command line object
     * @return command line option as POJO object
     */
    private static CliOptions convertCliToPojo(CommandLine cmdLine) {
        final CliOptions conf = new CliOptions();
        conf.format = cmdLine.getOptionValue("f");
        if (conf.format == null) {
            conf.format = "plain";
        }
        conf.outputLocation = cmdLine.getOptionValue("o");
        conf.configLocation = cmdLine.getOptionValue("c");
        conf.propertiesLocation = cmdLine.getOptionValue("p");
        conf.files = getFilesToProcess(cmdLine.getArgs());
        return conf;
    }

    /**
     * Executes required Checkstyle actions based on passed parameters.
     * @param cliOptions
     *        pojo object that contains all options
     * @return number of violations of ERROR level
     * @throws FileNotFoundException
     *         when output file could not be found
     * @throws CheckstyleException
     *         when properties file could not be loaded
     * @throws UnsupportedEncodingException
     *         if there is problem to use UTf-8
     */
    private static int runCheckstyle(CliOptions cliOptions)
            throws CheckstyleException, UnsupportedEncodingException, FileNotFoundException {
        // setup the properties
        final Properties props;

        if (cliOptions.propertiesLocation == null) {
            props = System.getProperties();
        }
        else {
            props = loadProperties(new File(cliOptions.propertiesLocation));
        }

        // create a configuration
        final Configuration config = ConfigurationLoader.loadConfiguration(
                cliOptions.configLocation, new PropertiesExpander(props));

        // create a listener for output
        final AuditListener listener = createListener(cliOptions.format, cliOptions.outputLocation);

        // create Checker object and run it
        int errorCounter = 0;
        final Checker checker = new Checker();

        try {

            final ClassLoader moduleClassLoader = Checker.class.getClassLoader();
            checker.setModuleClassLoader(moduleClassLoader);
            checker.configure(config);
            checker.addListener(listener);

            // run Checker
            errorCounter = checker.process(cliOptions.files);

        }
        finally {
            checker.destroy();
        }

        return errorCounter;
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
            throws CheckstyleException {
        final Properties properties = new Properties();

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            properties.load(fis);
        }
        catch (final IOException e) {
            throw new CheckstyleException(String.format(
                    "Unable to load properties from file '%s'.", file.getAbsolutePath()), e);
        }
        finally {
            Closeables.closeQuietly(fis);
        }

        return properties;
    }

    /**
     * Creates the audit listener.
     *
     * @param format format of the audit listener
     * @param outputLocation the location of output
     * @return a fresh new {@code AuditListener}
     * @exception UnsupportedEncodingException if there is problem to use UTf-8
     * @exception FileNotFoundException when provided output location is not found
     */
    private static AuditListener createListener(String format,
                                                String outputLocation)
            throws UnsupportedEncodingException, FileNotFoundException {

        // setup the output stream
        OutputStream out;
        boolean closeOutputStream;
        if (outputLocation != null) {
            out = new FileOutputStream(outputLocation);
            closeOutputStream = true;
        }
        else {
            out = System.out;
            closeOutputStream = false;
        }

        // setup a listener
        AuditListener listener;
        if ("xml".equals(format)) {
            listener = new XMLLogger(out, closeOutputStream);

        }
        else if ("plain".equals(format)) {
            listener = new DefaultLogger(out, closeOutputStream);

        }
        else {
            if (closeOutputStream) {
                Utils.close(out);
            }
            throw new IllegalStateException("Invalid output format. Found '" + format
                    + "' but expected 'plain' or 'xml'.");
        }

        return listener;
    }

    /**
     * Determines the files to process.
     * @param filesToProcess
     *        arguments that were not processed yet but shall be
     * @return list of files to process
     */
    private static List<File> getFilesToProcess(String... filesToProcess) {
        final List<File> files = Lists.newLinkedList();
        for (String element : filesToProcess) {
            files.addAll(listFiles(new File(element)));
        }

        return files;
    }

    /**
     * Traverses a specified node looking for files to check. Found files are added to a specified
     * list. Subdirectories are also traversed.
     * @param node
     *        the node to process
     * @return found files
     */
    private static List<File> listFiles(File node) {
        // could be replaced with org.apache.commons.io.FileUtils.list() method
        // if only we add commons-io library
        final List<File> result = Lists.newLinkedList();

        if (node.canRead()) {
            if (node.isDirectory()) {
                final File[] files = node.listFiles();
                // listFiles() can return null, so we need to check it
                if (files != null) {
                    for (File element : files) {
                        result.addAll(listFiles(element));
                    }
                }
            }
            else if (node.isFile()) {
                result.add(node);
            }
        }
        return result;
    }

    /** Prints the usage information. **/
    private static void printUsage() {
        final HelpFormatter hf = new HelpFormatter();
        hf.printHelp(String.format("java %s [options] -c <config.xml> file...",
                Main.class.getName()), buildOptions());
    }

    /**
     * Builds and returns list of parameters supported by cli Checkstyle.
     * @return available options
     */
    private static Options buildOptions() {
        final Options options = new Options();
        options.addOption("c", true, "Sets the check configuration file to use.");
        options.addOption("o", true, "Sets the output file. Defaults to stdout");
        options.addOption("p", true, "Loads the properties file");
        options.addOption("f", true, "Sets the output format. (plain|xml). Defaults to plain");
        options.addOption("v", false, "Print product version and exit");
        return options;
    }

    /** Helper structure to clear show what is required for Checker to run. **/
    private static class CliOptions {
        /** properties file location */
        private String propertiesLocation;
        /** config file location */
        private String configLocation;
        /** output format */
        private String format;
        /** output file location */
        private String outputLocation;
        /** list of file to validate */
        private List<File> files;
    }
}

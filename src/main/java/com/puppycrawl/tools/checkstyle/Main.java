////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.common.collect.Lists;
import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Wrapper command line program for the Checker.
 * @author the original author or authors.
 *
 **/
public final class Main {
    /** Width of CLI help option. */
    private static final int HELP_WIDTH = 100;

    /** Exit code returned when execution finishes with {@link CheckstyleException}. */
    private static final int EXIT_WITH_CHECKSTYLE_EXCEPTION_CODE = -2;

    /** Name for the option 'v'. */
    private static final String OPTION_V_NAME = "v";

    /** Name for the option 'c'. */
    private static final String OPTION_C_NAME = "c";

    /** Name for the option 'f'. */
    private static final String OPTION_F_NAME = "f";

    /** Name for the option 'p'. */
    private static final String OPTION_P_NAME = "p";

    /** Name for the option 'o'. */
    private static final String OPTION_O_NAME = "o";

    /** Name for the option 't'. */
    private static final String OPTION_T_NAME = "t";

    /** Name for the option '--tree'. */
    private static final String OPTION_TREE_NAME = "tree";

    /** Name for the option '-T'. */
    private static final String OPTION_CAPITAL_T_NAME = "T";

    /** Name for the option '--treeWithComments'. */
    private static final String OPTION_TREE_COMMENT_NAME = "treeWithComments";

    /** Name for the option '-j'. */
    private static final String OPTION_J_NAME = "j";

    /** NAme for the option '--javadocTree'. */
    private static final String OPTION_JAVADOC_TREE_NAME = "javadocTree";

    /** Name for the option '-J'. */
    private static final String OPTION_CAPITAL_J_NAME = "J";

    /** Name for the option '--treeWithJavadoc'. */
    private static final String OPTION_TREE_JAVADOC_NAME = "treeWithJavadoc";

    /** Name for 'xml' format. */
    private static final String XML_FORMAT_NAME = "xml";

    /** Name for 'plain' format. */
    private static final String PLAIN_FORMAT_NAME = "plain";

    /** Don't create instance of this class, use {@link #main(String[])} method instead. */
    private Main() {
    }

    /**
     * Loops over the files specified checking them for errors. The exit code
     * is the number of errors found in all the files.
     * @param args the command line arguments.
     * @throws IOException if there is a problem with files access
     * @noinspection CallToPrintStackTrace
     **/
    public static void main(String... args) throws IOException {
        int errorCounter = 0;
        boolean cliViolations = false;
        // provide proper exit code based on results.
        final int exitWithCliViolation = -1;
        int exitStatus = 0;

        try {
            //parse CLI arguments
            final CommandLine commandLine = parseCli(args);

            // show version and exit if it is requested
            if (commandLine.hasOption(OPTION_V_NAME)) {
                System.out.println("Checkstyle version: "
                        + Main.class.getPackage().getImplementationVersion());
                exitStatus = 0;
            }
            else {
                final List<File> filesToProcess = getFilesToProcess(commandLine.getArgs());

                // return error if something is wrong in arguments
                final List<String> messages = validateCli(commandLine, filesToProcess);
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
                    final CliOptions config = convertCliToPojo(commandLine, filesToProcess);
                    if (commandLine.hasOption(OPTION_T_NAME)) {
                        // print AST
                        final File file = config.files.get(0);
                        final String stringAst = AstTreeStringPrinter.printFileAst(file, false);
                        System.out.print(stringAst);
                    }
                    else if (commandLine.hasOption(OPTION_CAPITAL_T_NAME)) {
                        final File file = config.files.get(0);
                        final String stringAst = AstTreeStringPrinter.printFileAst(file, true);
                        System.out.print(stringAst);
                    }
                    else if (commandLine.hasOption(OPTION_J_NAME)) {
                        final File file = config.files.get(0);
                        final String stringAst = DetailNodeTreeStringPrinter.printFileAst(file);
                        System.out.print(stringAst);
                    }
                    else if (commandLine.hasOption(OPTION_CAPITAL_J_NAME)) {
                        final File file = config.files.get(0);
                        final String stringAst = AstTreeStringPrinter.printJavaAndJavadocTree(file);
                        System.out.print(stringAst);
                    }
                    else {
                        // run Checker
                        errorCounter = runCheckstyle(config);
                        exitStatus = errorCounter;
                    }
                }
            }
        }
        catch (ParseException pex) {
            // something wrong with arguments - print error and manual
            cliViolations = true;
            exitStatus = exitWithCliViolation;
            errorCounter = 1;
            System.out.println(pex.getMessage());
            printUsage();
        }
        catch (CheckstyleException ex) {
            exitStatus = EXIT_WITH_CHECKSTYLE_EXCEPTION_CODE;
            errorCounter = 1;
            ex.printStackTrace();
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
     * Do validation of Command line options.
     * @param cmdLine command line object
     * @param filesToProcess List of files to process found from the command line.
     * @return list of violations
     */
    private static List<String> validateCli(CommandLine cmdLine, List<File> filesToProcess) {
        final List<String> result = new ArrayList<>();

        if (filesToProcess.isEmpty()) {
            result.add("Files to process must be specified, found 0.");
        }
        // ensure there is no conflicting options
        else if (cmdLine.hasOption(OPTION_T_NAME) || cmdLine.hasOption(OPTION_CAPITAL_T_NAME)
                || cmdLine.hasOption(OPTION_J_NAME) || cmdLine.hasOption(OPTION_CAPITAL_J_NAME)) {
            if (cmdLine.hasOption(OPTION_C_NAME) || cmdLine.hasOption(OPTION_P_NAME)
                    || cmdLine.hasOption(OPTION_F_NAME) || cmdLine.hasOption(OPTION_O_NAME)) {
                result.add("Option '-t' cannot be used with other options.");
            }
            else if (filesToProcess.size() > 1) {
                result.add("Printing AST is allowed for only one file.");
            }
        }
        // ensure a configuration file is specified
        else if (cmdLine.hasOption(OPTION_C_NAME)) {
            final String configLocation = cmdLine.getOptionValue(OPTION_C_NAME);
            try {
                // test location only
                CommonUtils.getUriByFilename(configLocation);
            }
            catch (CheckstyleException ignored) {
                result.add(String.format("Could not find config XML file '%s'.", configLocation));
            }

            // validate optional parameters
            if (cmdLine.hasOption(OPTION_F_NAME)) {
                final String format = cmdLine.getOptionValue(OPTION_F_NAME);
                if (!PLAIN_FORMAT_NAME.equals(format) && !XML_FORMAT_NAME.equals(format)) {
                    result.add(String.format("Invalid output format."
                            + " Found '%s' but expected '%s' or '%s'.",
                            format, PLAIN_FORMAT_NAME, XML_FORMAT_NAME));
                }
            }
            if (cmdLine.hasOption(OPTION_P_NAME)) {
                final String propertiesLocation = cmdLine.getOptionValue(OPTION_P_NAME);
                final File file = new File(propertiesLocation);
                if (!file.exists()) {
                    result.add(String.format("Could not find file '%s'.", propertiesLocation));
                }
            }
            if (cmdLine.hasOption(OPTION_O_NAME)) {
                final String outputLocation = cmdLine.getOptionValue(OPTION_O_NAME);
                final File file = new File(outputLocation);
                if (file.exists() && !file.canWrite()) {
                    result.add(String.format("Permission denied : '%s'.", outputLocation));
                }
            }
        }
        else {
            result.add("Must specify a config XML file.");
        }

        return result;
    }

    /**
     * Util method to convert CommandLine type to POJO object.
     * @param cmdLine command line object
     * @param filesToProcess List of files to process found from the command line.
     * @return command line option as POJO object
     */
    private static CliOptions convertCliToPojo(CommandLine cmdLine, List<File> filesToProcess) {
        final CliOptions conf = new CliOptions();
        conf.format = cmdLine.getOptionValue(OPTION_F_NAME);
        if (conf.format == null) {
            conf.format = PLAIN_FORMAT_NAME;
        }
        conf.outputLocation = cmdLine.getOptionValue(OPTION_O_NAME);
        conf.configLocation = cmdLine.getOptionValue(OPTION_C_NAME);
        conf.propertiesLocation = cmdLine.getOptionValue(OPTION_P_NAME);
        conf.files = filesToProcess;
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
     */
    private static int runCheckstyle(CliOptions cliOptions)
            throws CheckstyleException, FileNotFoundException {
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
        catch (final IOException ex) {
            throw new CheckstyleException(String.format(
                    "Unable to load properties from file '%s'.", file.getAbsolutePath()), ex);
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
     * @exception FileNotFoundException when provided output location is not found
     */
    private static AuditListener createListener(String format,
                                                String outputLocation)
            throws FileNotFoundException {

        // setup the output stream
        final OutputStream out;
        final boolean closeOutputStream;
        if (outputLocation == null) {
            out = System.out;
            closeOutputStream = false;
        }
        else {
            out = new FileOutputStream(outputLocation);
            closeOutputStream = true;
        }

        // setup a listener
        final AuditListener listener;
        if (XML_FORMAT_NAME.equals(format)) {
            listener = new XMLLogger(out, closeOutputStream);

        }
        else if (PLAIN_FORMAT_NAME.equals(format)) {
            listener = new DefaultLogger(out, closeOutputStream, out, false);

        }
        else {
            if (closeOutputStream) {
                CommonUtils.close(out);
            }
            throw new IllegalStateException(String.format(
                    "Invalid output format. Found '%s' but expected '%s' or '%s'.",
                    format, PLAIN_FORMAT_NAME, XML_FORMAT_NAME));
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
        final HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(HELP_WIDTH);
        formatter.printHelp(String.format("java %s [options] -c <config.xml> file...",
                Main.class.getName()), buildOptions());
    }

    /**
     * Builds and returns list of parameters supported by cli Checkstyle.
     * @return available options
     */
    private static Options buildOptions() {
        final Options options = new Options();
        options.addOption(OPTION_C_NAME, true, "Sets the check configuration file to use.");
        options.addOption(OPTION_O_NAME, true, "Sets the output file. Defaults to stdout");
        options.addOption(OPTION_P_NAME, true, "Loads the properties file");
        options.addOption(OPTION_F_NAME, true, String.format(
                "Sets the output format. (%s|%s). Defaults to %s",
                PLAIN_FORMAT_NAME, XML_FORMAT_NAME, PLAIN_FORMAT_NAME));
        options.addOption(OPTION_V_NAME, false, "Print product version and exit");
        options.addOption(OPTION_T_NAME, OPTION_TREE_NAME, false,
                "Print Abstract Syntax Tree(AST) of the file");
        options.addOption(OPTION_CAPITAL_T_NAME, OPTION_TREE_COMMENT_NAME, false,
                "Print Abstract Syntax Tree(AST) of the file including comments");
        options.addOption(OPTION_J_NAME, OPTION_JAVADOC_TREE_NAME, false,
                "Print Parse tree of the Javadoc comment");
        options.addOption(OPTION_CAPITAL_J_NAME, OPTION_TREE_JAVADOC_NAME, false,
                "Print full Abstract Syntax Tree of the file");
        return options;
    }

    /** Helper structure to clear show what is required for Checker to run. **/
    private static class CliOptions {
        /** Properties file location. */
        private String propertiesLocation;
        /** Config file location. */
        private String configLocation;
        /** Output format. */
        private String format;
        /** Output file location. */
        private String outputLocation;
        /** List of file to validate. */
        private List<File> files;
    }
}

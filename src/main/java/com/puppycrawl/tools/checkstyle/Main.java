////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.logging.ConsoleHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.RootModule;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

/**
 * Wrapper command line program for the Checker.
 * @noinspection UseOfSystemOutOrSystemErr
 **/
@Command(name = "checkstyle", description = "Checkstyle verifies that the specified " +
        "source code files adhere to the specified rules. By default errors are " +
        "reported to standard out in plain format. Checkstyle requires a configuration " +
        "XML file that configures the checks to apply.",
    versionProvider = Main.VersionProvider.class)
public final class Main implements Callable<Integer> {

    /**
     * A key pointing to the error counter
     * message in the "messages.properties" file.
     */
    public static final String ERROR_COUNTER = "Main.errorCounter";
    /**
     * A key pointing to the load properties exception
     * message in the "messages.properties" file.
     */
    public static final String LOAD_PROPERTIES_EXCEPTION = "Main.loadProperties";
    /**
     * A key pointing to the create listener exception
     * message in the "messages.properties" file.
     */
    public static final String CREATE_LISTENER_EXCEPTION = "Main.createListener";

    /** Name for the moduleConfig attribute 'tabWidth'. */
    private static final String ATTRIB_TAB_WIDTH_NAME = "tabWidth";

    /** Logger for Main. */
    private static final Log LOG = LogFactory.getLog(Main.class);

    /** Width of CLI help option. */
    private static final int HELP_WIDTH = 100;

    /** Exit code returned when user specified invalid command line arguments. */
    private static final int EXIT_WITH_INVALID_USER_INPUT_CODE = -1;

    /** Exit code returned when execution finishes with {@link CheckstyleException}. */
    private static final int EXIT_WITH_CHECKSTYLE_EXCEPTION_CODE = -2;

    /** The default number of threads to use for checker and the tree walker. */
    private static final int DEFAULT_THREAD_COUNT = 1;

    /** Default distance between tab stops. */
    private static final int DEFAULT_TAB_WIDTH = 8;

    /** Default output format. */
    private static final OutputFormat DEFAULT_OUTPUT_FORMAT = OutputFormat.plain;

    /** Config file location. */
    @Option(names = "-c", description = "Sets the check configuration file to use.")
    private String configurationFile;

    /** Output file location. */
    @Option(names = "-o", description = "Sets the output file. Defaults to stdout")
    private Path outputPath;

    /** Whether user requested version information on the command line. */
    @Option(names = "-v", versionHelp = true, description = "Print product version and exit")
    private boolean versionHelpRequested;

    /** Whether user requested usage help on the command line. */
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Print usage help and exit")
    private boolean usageHelpRequested;

    /** Properties file location. */
    @Option(names = "-p", description = "Loads the properties file")
    private File propertiesFile;

    /** LineNo and columnNo for the suppression. */
    @Option(names = "-s", description = "Print xpath suppressions at the file's line and column position. "
            + "Argument is the line and column number (separated by a : ) in the file "
            + "that the suppression should be generated for")
    private String suppressionLineColumnNumber;

    /** Tab character length. */
    @Option(names = "-tabWidth", description = "Sets the length of the tab character. " +
            "Used only with \"-s\" option. Default value is ${DEFAULT-VALUE}")
    private int tabWidth = DEFAULT_TAB_WIDTH;

    /** Switch whether to generate suppressions file or not. */
    @Option(names = {"-gxs", "--generate-xpath-suppression"}, description =
            "Generates to output a suppression.xml to use to suppress all violations from user's config")
    private boolean generateXpathSuppressionsFile;

    /** Output format. */
    @Option(names = "-f", description = "Sets the output format. Valid values: " +
            "${COMPLETION-CANDIDATES}. Defaults to ${DEFAULT-VALUE}")
    private OutputFormat format = DEFAULT_OUTPUT_FORMAT;

    @Option(names = {"-t", "--tree"}, description = "Print Abstract Syntax Tree(AST) of the file")
    private boolean printAst;

    @Option(names = {"-T", "--treeWithComments"}, description = "Print Abstract Syntax Tree(AST) of the file including comments")
    private boolean printAstWithComments;

    @Option(names = {"-j", "--javadocTree"}, description = "Print Parse tree of the Javadoc comment")
    private boolean printJavadocTree;

    @Option(names = {"-J", "--treeWithJavadoc"}, description = "Print full Abstract Syntax Tree of the file")
    private boolean printTreeWithJavadoc;

    @Option(names = {"-d", "--debug"}, description = "Print all debug logging of CheckStyle utility")
    private boolean debug;

    @Option(names = {"-e", "--exclude"}, description = "Directory path to exclude from CheckStyle")
    private List<File> exclude = new ArrayList<>();

    @Option(names = {"-x", "--exclude-regexp"}, description = "Regular expression of directory to exclude from CheckStyle")
    private List<Pattern> excludeRegex = new ArrayList<>();

    /** Switch whether to execute ignored modules or not. */
    @Option(names = "--executeIgnoredModules", description = "Allows ignored modules to be run.")
    private boolean executeIgnoredModules;

    /** The checker threads number. */
    @Option(names = {"-C", "--checker-threads-number"}, description = "(experimental) The number of Checker threads (must be greater than zero)")
    private int checkerThreadsNumber = DEFAULT_THREAD_COUNT;

    /** The tree walker threads number. */
    @Option(names = {"-W", "--tree-walker-threads-number"}, description = "(experimental) The number of TreeWalker threads (must be greater than zero)")
    private int treeWalkerThreadsNumber = DEFAULT_THREAD_COUNT;

    /** List of file to validate. */
    @Parameters(arity = "1..*", description = "One or more source files to verify")
    private List<File> files = new ArrayList<>();

    /** The files to process (the specified source files without the exclusions). */
    private List<File> filesToProcess;

    /** Allows access to the command line parser model. */
    @Spec
    CommandSpec commandSpec;

    /** Client code should not create instances of this class, but use
     * {@link #main(String[])} method instead. */
    private Main() {
    }

    /**
     * Loops over the files specified checking them for errors. The exit code
     * is the number of errors found in all the files.
     * @param args the command line arguments.
     * @throws IOException if there is a problem with files access
     * @noinspection CallToPrintStackTrace, CallToSystemExit
     **/
    public static void main(String... args) throws IOException {
        int errorCounter = 0;
        // provide proper exit code based on results.
        int exitStatus = 0;

        try {
            //parse CLI arguments
            CommandLine commandLine = new CommandLine(new Main());
            commandLine.setUsageHelpWidth(HELP_WIDTH);
            List<Object> result = commandLine.parseWithHandlers(new CommandLine.RunLast(),
                    CommandLine.defaultExceptionHandler().andExit(EXIT_WITH_INVALID_USER_INPUT_CODE),
                    args);
            if (result != null) { // result is null if help or version was requested
                exitStatus = (int) result.get(0);
                if (exitStatus > 0) {
                    errorCounter = exitStatus;
                }
            }
        }
        catch (CommandLine.ExecutionException ex) {
            if (ex.getCause() instanceof CheckstyleException) {
                exitStatus = EXIT_WITH_CHECKSTYLE_EXCEPTION_CODE;
                errorCounter = 1;
                ex.getCause().printStackTrace();
            }
            else {
                if (ex.getCause() instanceof RuntimeException) {
                    throw (RuntimeException) ex.getCause();
                }
                throw new IllegalStateException(ex.getCause().getMessage(), ex.getCause());
            }
        }

        // return exit code base on validation of Checker
        if (errorCounter > 0) {
            final LocalizedMessage errorCounterMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, ERROR_COUNTER,
                    new String[] {String.valueOf(errorCounter)}, null, Main.class, null);
            System.out.println(errorCounterMessage.getMessage());
        }
        if (exitStatus != 0) {
            System.exit(exitStatus);
        }
    }

    /**
     * Validates the user input and returns {@value EXIT_WITH_INVALID_USER_INPUT_CODE} if
     * invalid, otherwise executes CheckStyle and returns the number of violations.
     * @return number of violations
     * @throws IOException if a file could not be read.
     * @throws CheckstyleException if something happens processing the files.
     */
    public Integer call() throws IOException, CheckstyleException {
        filesToProcess = getFilesToProcess(getExclusions(), files);

        int exitStatus = 0;

        // return error if something is wrong in arguments
        final List<String> messages = validateCli();
        if (!messages.isEmpty()) {
            messages.forEach(System.out::println);
            exitStatus = EXIT_WITH_INVALID_USER_INPUT_CODE;
        }
        else {
            exitStatus = runCli();
        }
        return exitStatus;
    }

    /**
     * Gets the list of exclusions provided through the command line arguments.
     * @return List of exclusion patterns.
     */
    private List<Pattern> getExclusions() {
        final List<Pattern> result = new ArrayList<>();
        exclude.forEach(f -> result.add(Pattern.compile("^" +
                Pattern.quote(f.getAbsolutePath()) + "$")));
        result.addAll(excludeRegex);
        return result;
    }

    /**
     * Do validation of Command line options.
     * @return list of violations
     */
    // -@cs[CyclomaticComplexity] Breaking apart will damage encapsulation
    private List<String> validateCli() {
        final List<String> result = new ArrayList<>();

        if (filesToProcess.isEmpty()) {
            result.add("Files to process must be specified, found 0.");
        }
        // ensure there is no conflicting options
        else if (printAst || printAstWithComments || printJavadocTree || printTreeWithJavadoc) {
            if (suppressionLineColumnNumber != null || configurationFile != null
                    || propertiesFile != null || userSpecifiedFormatOption()
                    || outputPath != null) {
                result.add("Option '-t' cannot be used with other options.");
            }
            else if (filesToProcess.size() > 1) {
                result.add("Printing AST is allowed for only one file.");
            }
        }
        else if (suppressionLineColumnNumber != null) {
            if (configurationFile != null || propertiesFile != null
                    || userSpecifiedFormatOption() || outputPath != null) {
                result.add("Option '-s' cannot be used with other options.");
            }
            else if (filesToProcess.size() > 1) {
                result.add("Printing xpath suppressions is allowed for only one file.");
            }
        }
        else if (configurationFile != null) {
            try {
                // test location only
                CommonUtil.getUriByFilename(configurationFile);
            }
            catch (CheckstyleException ignored) {
                result.add(String.format("Could not find config XML file '%s'.", configurationFile));
            }

            // validate optional parameters
            if (propertiesFile != null && !propertiesFile.exists()) {
                result.add(String.format("Could not find file '%s'.", propertiesFile));
            }
            if (checkerThreadsNumber < 1) {
                result.add("Checker threads number must be greater than zero");
            }
            if (treeWalkerThreadsNumber < 1) {
                result.add("TreeWalker threads number must be greater than zero");
            }
        }
        else {
            result.add("Must specify a config XML file.");
        }

        return result;
    }

    private boolean userSpecifiedFormatOption() {
        return commandSpec.commandLine().getParseResult().hasMatchedOption("-f");
    }

    /**
     * Do execution of CheckStyle based on Command line options.
     * @return number of violations
     * @throws IOException if a file could not be read.
     * @throws CheckstyleException if something happens processing the files.
     */
    private int runCli() throws IOException, CheckstyleException {
        int result = 0;

        // create config helper object
        if (printAst) {
            // print AST
            final File file = filesToProcess.get(0);
            final String stringAst = AstTreeStringPrinter.printFileAst(file,
                    JavaParser.Options.WITHOUT_COMMENTS);
            System.out.print(stringAst);
        }
        else if (printAstWithComments) {
            final File file = filesToProcess.get(0);
            final String stringAst = AstTreeStringPrinter.printFileAst(file,
                    JavaParser.Options.WITH_COMMENTS);
            System.out.print(stringAst);
        }
        else if (printJavadocTree) {
            final File file = filesToProcess.get(0);
            final String stringAst = DetailNodeTreeStringPrinter.printFileAst(file);
            System.out.print(stringAst);
        }
        else if (printTreeWithJavadoc) {
            final File file = filesToProcess.get(0);
            final String stringAst = AstTreeStringPrinter.printJavaAndJavadocTree(file);
            System.out.print(stringAst);
        }
        else if (suppressionLineColumnNumber != null) {
            final File file = filesToProcess.get(0);
            final String stringSuppressions =
                    SuppressionsStringPrinter.printSuppressions(file,
                            suppressionLineColumnNumber, tabWidth);
            System.out.print(stringSuppressions);
        }
        else {
            if (debug) {
                final Logger parentLogger = Logger.getLogger(Main.class.getName()).getParent();
                final ConsoleHandler handler = new ConsoleHandler();
                handler.setLevel(Level.FINEST);
                handler.setFilter(new Filter() {
                    private final String packageName = Main.class.getPackage().getName();

                    @Override
                    public boolean isLoggable(LogRecord record) {
                        return record.getLoggerName().startsWith(packageName);
                    }
                });
                parentLogger.addHandler(handler);
                parentLogger.setLevel(Level.FINEST);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Checkstyle debug logging enabled");
                LOG.debug("Running Checkstyle with version: "
                        + Main.class.getPackage().getImplementationVersion());
            }

            // run Checker
            result = runCheckstyle();
        }

        return result;
    }

    /**
     * Executes required Checkstyle actions based on passed parameters.
     * @return number of violations of ERROR level
     * @throws IOException
     *         when output file could not be found
     * @throws CheckstyleException
     *         when properties file could not be loaded
     */
    private int runCheckstyle()
            throws CheckstyleException, IOException {
        // setup the properties
        final Properties props;

        if (propertiesFile == null) {
            props = System.getProperties();
        }
        else {
            props = loadProperties(propertiesFile);
        }

        // create a configuration
        final ThreadModeSettings multiThreadModeSettings =
                new ThreadModeSettings(checkerThreadsNumber, treeWalkerThreadsNumber);

        final ConfigurationLoader.IgnoredModulesOptions ignoredModulesOptions =
                executeIgnoredModules
                        ? ConfigurationLoader.IgnoredModulesOptions.EXECUTE
                        : ConfigurationLoader.IgnoredModulesOptions.OMIT;

        final Configuration config = ConfigurationLoader.loadConfiguration(
                configurationFile, new PropertiesExpander(props),
                ignoredModulesOptions, multiThreadModeSettings);

        // create RootModule object and run it
        final int errorCounter;
        final ClassLoader moduleClassLoader = Checker.class.getClassLoader();
        final RootModule rootModule = getRootModule(config.getName(), moduleClassLoader);

        try {
            final AuditListener listener;
            if (generateXpathSuppressionsFile) {
                // create filter to print generated xpath suppressions file
                final Configuration treeWalkerConfig = getTreeWalkerConfig(config);
                if (treeWalkerConfig != null) {
                    final DefaultConfiguration moduleConfig =
                            new DefaultConfiguration(XpathFileGeneratorAstFilter.class.getName());
                    moduleConfig.addAttribute(ATTRIB_TAB_WIDTH_NAME, String.valueOf(tabWidth));
                    ((DefaultConfiguration) treeWalkerConfig).addChild(moduleConfig);
                }

                listener = new XpathFileGeneratorAuditListener(System.out,
                        AutomaticBean.OutputStreamOptions.NONE);
            }
            else {
                listener = createListener(format, outputPath);
            }

            rootModule.setModuleClassLoader(moduleClassLoader);
            rootModule.configure(config);
            rootModule.addListener(listener);

            // run RootModule
            errorCounter = rootModule.process(filesToProcess);
        }
        finally {
            rootModule.destroy();
        }

        return errorCounter;
    }

    /**
     * Returns {@code TreeWalker} module configuration.
     * @param config The configuration object.
     * @return The {@code TreeWalker} module configuration.
     */
    private static Configuration getTreeWalkerConfig(Configuration config) {
        Configuration result = null;

        final Configuration[] children = config.getChildren();
        for (Configuration child : children) {
            if ("TreeWalker".equals(child.getName())) {
                result = child;
                break;
            }
        }
        return result;
    }

    /**
     * Creates a new instance of the root module that will control and run
     * Checkstyle.
     * @param name The name of the module. This will either be a short name that
     *        will have to be found or the complete package name.
     * @param moduleClassLoader Class loader used to load the root module.
     * @return The new instance of the root module.
     * @throws CheckstyleException if no module can be instantiated from name
     */
    private static RootModule getRootModule(String name, ClassLoader moduleClassLoader)
            throws CheckstyleException {
        final ModuleFactory factory = new PackageObjectFactory(
                Checker.class.getPackage().getName(), moduleClassLoader);

        return (RootModule) factory.createModule(name);
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

        try (InputStream stream = Files.newInputStream(file.toPath())) {
            properties.load(stream);
        }
        catch (final IOException ex) {
            final LocalizedMessage loadPropertiesExceptionMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, LOAD_PROPERTIES_EXCEPTION,
                    new String[] {file.getAbsolutePath()}, null, Main.class, null);
            throw new CheckstyleException(loadPropertiesExceptionMessage.getMessage(), ex);
        }

        return properties;
    }

    /** Enumeration over the possible output formats. */
    enum OutputFormat {
        xml, plain;

        public AuditListener createListener(OutputStream out,
                                            AutomaticBean.OutputStreamOptions options) {
            return this == xml ? new XMLLogger(out, options) : new DefaultLogger(out, options);
        }
    }

    /**
     * This method creates in AuditListener an open stream for validation data, it must be closed by
     * {@link RootModule} (default implementation is {@link Checker}) by calling
     * {@link AuditListener#auditFinished(AuditEvent)}.
     * @param format format of the audit listener
     * @param outputLocation the location of output
     * @return a fresh new {@code AuditListener}
     * @exception IOException when provided output location is not found
     */
    private static AuditListener createListener(OutputFormat format, Path outputLocation)
            throws IOException {
        final OutputStream out = getOutputStream(outputLocation);
        final AutomaticBean.OutputStreamOptions closeOutputStreamOption =
                getOutputStreamOptions(outputLocation);
        return format.createListener(out, closeOutputStreamOption);
    }

    /**
     * Create output stream or return System.out
     * @param outputPath output location
     * @return output stream
     * @throws IOException might happen
     */
    @SuppressWarnings("resource")
    private static OutputStream getOutputStream(Path outputPath) throws IOException {
        final OutputStream result;
        if (outputPath == null) {
            result = System.out;
        }
        else {
            result = Files.newOutputStream(outputPath);
        }
        return result;
    }

    /**
     * Create {@link AutomaticBean.OutputStreamOptions} for the given location.
     * @param outputPath output location
     * @return output stream options
     */
    private static AutomaticBean.OutputStreamOptions getOutputStreamOptions(Path outputPath) {
        final AutomaticBean.OutputStreamOptions result;
        if (outputPath == null) {
            result = AutomaticBean.OutputStreamOptions.NONE;
        }
        else {
            result = AutomaticBean.OutputStreamOptions.CLOSE;
        }
        return result;
    }

    /**
     * Determines the files to process.
     * @param patternsToExclude The list of directory patterns to exclude from searching.
     * @param filesToProcess
     *        arguments that were not processed yet but shall be
     * @return list of files to process
     */
    private static List<File> getFilesToProcess(List<Pattern> patternsToExclude,
            List<File> filesToProcess) {

        final List<File> result = new LinkedList<>();
        for (File f : filesToProcess) {
            result.addAll(listFiles(f, patternsToExclude));
        }
        return result;
    }

    /**
     * Traverses a specified node looking for files to check. Found files are added to a specified
     * list. Subdirectories are also traversed.
     * @param node
     *        the node to process
     * @param patternsToExclude The list of directory patterns to exclude from searching.
     * @return found files
     */
    private static List<File> listFiles(File node, List<Pattern> patternsToExclude) {
        // could be replaced with org.apache.commons.io.FileUtils.list() method
        // if only we add commons-io library
        final List<File> result = new LinkedList<>();

        if (node.canRead()) {
            if (node.isDirectory()) {
                if (!isDirectoryExcluded(node.getAbsolutePath(), patternsToExclude)) {
                    final File[] files = node.listFiles();
                    // listFiles() can return null, so we need to check it
                    if (files != null) {
                        for (File element : files) {
                            result.addAll(listFiles(element, patternsToExclude));
                        }
                    }
                }
            }
            else if (node.isFile()) {
                result.add(node);
            }
        }
        return result;
    }

    /**
     * Checks if a directory {@code path} should be excluded based on if it matches one of the
     * patterns supplied.
     * @param path The path of the directory to check
     * @param patternsToExclude The list of directory patterns to exclude from searching.
     * @return True if the directory matches one of the patterns.
     */
    private static boolean isDirectoryExcluded(String path, List<Pattern> patternsToExclude) {
        boolean result = false;

        for (Pattern pattern : patternsToExclude) {
            if (pattern.matcher(path).find()) {
                result = true;
                break;
            }
        }

        return result;
    }

    /** Helper class to manage version information in a single location. */
    static class VersionProvider implements IVersionProvider {

        /**
         * Returns the version string printed when the user requests version help (-v).
         * @return a version string based on the package implementation version
         */
        @Override
        public String[] getVersion() {
            return new String[] {
                    "Checkstyle version: " + Main.class.getPackage().getImplementationVersion() };
        }
    }
}

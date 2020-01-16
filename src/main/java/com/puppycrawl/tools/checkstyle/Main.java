////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
import com.puppycrawl.tools.checkstyle.utils.XpathUtil;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParseResult;

/**
 * Wrapper command line program for the Checker.
 */
public final class Main {

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

    /** Logger for Main. */
    private static final Log LOG = LogFactory.getLog(Main.class);

    /** Exit code returned when user specified invalid command line arguments. */
    private static final int EXIT_WITH_INVALID_USER_INPUT_CODE = -1;

    /** Exit code returned when execution finishes with {@link CheckstyleException}. */
    private static final int EXIT_WITH_CHECKSTYLE_EXCEPTION_CODE = -2;

    /**
     * Client code should not create instances of this class, but use
     * {@link #main(String[])} method instead.
     */
    private Main() {
    }

    /**
     * Loops over the files specified checking them for errors. The exit code
     * is the number of errors found in all the files.
     * @param args the command line arguments.
     * @throws IOException if there is a problem with files access
     * @noinspection UseOfSystemOutOrSystemErr, CallToPrintStackTrace, CallToSystemExit
     **/
    public static void main(String... args) throws IOException {

        final CliOptions cliOptions = new CliOptions();
        final CommandLine commandLine = new CommandLine(cliOptions);
        commandLine.setUsageHelpWidth(CliOptions.HELP_WIDTH);
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);

        // provide proper exit code based on results.
        int exitStatus = 0;
        int errorCounter = 0;
        try {
            final ParseResult parseResult = commandLine.parseArgs(args);
            if (parseResult.isVersionHelpRequested()) {
                System.out.println(getVersionString());
            }
            else if (parseResult.isUsageHelpRequested()) {
                commandLine.usage(System.out);
            }
            else {
                exitStatus = execute(parseResult, cliOptions);
                errorCounter = exitStatus;
            }
        }
        catch (ParameterException ex) {
            exitStatus = EXIT_WITH_INVALID_USER_INPUT_CODE;
            System.err.println(ex.getMessage());
            System.err.println("Usage: checkstyle [OPTIONS]... FILES...");
            System.err.println("Try 'checkstyle --help' for more information.");
        }
        catch (CheckstyleException ex) {
            exitStatus = EXIT_WITH_CHECKSTYLE_EXCEPTION_CODE;
            errorCounter = 1;
            ex.printStackTrace();
        }
        finally {
            // return exit code base on validation of Checker
            if (errorCounter > 0) {
                final LocalizedMessage errorCounterMessage = new LocalizedMessage(1,
                        Definitions.CHECKSTYLE_BUNDLE, ERROR_COUNTER,
                        new String[] {String.valueOf(errorCounter)}, null, Main.class, null);
                // print error count statistic to error output stream,
                // output stream might be used by validation report content
                System.err.println(errorCounterMessage.getMessage());
            }
            if (exitStatus != 0) {
                System.exit(exitStatus);
            }
        }
    }

    /**
     * Returns the version string printed when the user requests version help (--version or -V).
     * @return a version string based on the package implementation version
     */
    private static String getVersionString() {
        return "Checkstyle version: " + Main.class.getPackage().getImplementationVersion();
    }

    /**
     * Validates the user input and returns {@value #EXIT_WITH_INVALID_USER_INPUT_CODE} if
     * invalid, otherwise executes CheckStyle and returns the number of violations.
     * @param parseResult generic access to options and parameters found on the command line
     * @param options encapsulates options and parameters specified on the command line
     * @return number of violations
     * @throws IOException if a file could not be read.
     * @throws CheckstyleException if something happens processing the files.
     * @noinspection UseOfSystemOutOrSystemErr
     */
    private static int execute(ParseResult parseResult, CliOptions options)
            throws IOException, CheckstyleException {

        final int exitStatus;

        // return error if something is wrong in arguments
        final List<File> filesToProcess = getFilesToProcess(options);
        final List<String> messages = options.validateCli(parseResult, filesToProcess);
        final boolean hasMessages = !messages.isEmpty();
        if (hasMessages) {
            messages.forEach(System.out::println);
            exitStatus = EXIT_WITH_INVALID_USER_INPUT_CODE;
        }
        else {
            exitStatus = runCli(options, filesToProcess);
        }
        return exitStatus;
    }

    /**
     * Determines the files to process.
     * @param options the user-specified options
     * @return list of files to process
     */
    private static List<File> getFilesToProcess(CliOptions options) {
        final List<Pattern> patternsToExclude = options.getExclusions();

        final List<File> result = new LinkedList<>();
        for (File file : options.files) {
            result.addAll(listFiles(file, patternsToExclude));
        }
        return result;
    }

    /**
     * Traverses a specified node looking for files to check. Found files are added to
     * a specified list. Subdirectories are also traversed.
     * @param node
     *        the node to process
     * @param patternsToExclude The list of patterns to exclude from searching or being added as
     *        files.
     * @return found files
     */
    private static List<File> listFiles(File node, List<Pattern> patternsToExclude) {
        // could be replaced with org.apache.commons.io.FileUtils.list() method
        // if only we add commons-io library
        final List<File> result = new LinkedList<>();

        if (node.canRead() && !isPathExcluded(node.getAbsolutePath(), patternsToExclude)) {
            if (node.isDirectory()) {
                final File[] files = node.listFiles();
                // listFiles() can return null, so we need to check it
                if (files != null) {
                    for (File element : files) {
                        result.addAll(listFiles(element, patternsToExclude));
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
     * Checks if a directory/file {@code path} should be excluded based on if it matches one of the
     * patterns supplied.
     * @param path The path of the directory/file to check
     * @param patternsToExclude The list of patterns to exclude from searching or being added as
     *        files.
     * @return True if the directory/file matches one of the patterns.
     */
    private static boolean isPathExcluded(String path, List<Pattern> patternsToExclude) {
        boolean result = false;

        for (Pattern pattern : patternsToExclude) {
            if (pattern.matcher(path).find()) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Do execution of CheckStyle based on Command line options.
     * @param options user-specified options
     * @param filesToProcess the list of files whose style to check
     * @return number of violations
     * @throws IOException if a file could not be read.
     * @throws CheckstyleException if something happens processing the files.
     * @noinspection UseOfSystemOutOrSystemErr
     */
    private static int runCli(CliOptions options, List<File> filesToProcess)
            throws IOException, CheckstyleException {
        int result = 0;
        final boolean hasSuppressionLineColumnNumber = options.suppressionLineColumnNumber != null;

        // create config helper object
        if (options.printAst) {
            // print AST
            final File file = filesToProcess.get(0);
            final String stringAst = AstTreeStringPrinter.printFileAst(file,
                    JavaParser.Options.WITHOUT_COMMENTS);
            System.out.print(stringAst);
        }
        else if (Objects.nonNull(options.xpath)) {
            final String branch = XpathUtil.printXpathBranch(options.xpath, filesToProcess.get(0));
            System.out.print(branch);
        }
        else if (options.printAstWithComments) {
            final File file = filesToProcess.get(0);
            final String stringAst = AstTreeStringPrinter.printFileAst(file,
                    JavaParser.Options.WITH_COMMENTS);
            System.out.print(stringAst);
        }
        else if (options.printJavadocTree) {
            final File file = filesToProcess.get(0);
            final String stringAst = DetailNodeTreeStringPrinter.printFileAst(file);
            System.out.print(stringAst);
        }
        else if (options.printTreeWithJavadoc) {
            final File file = filesToProcess.get(0);
            final String stringAst = AstTreeStringPrinter.printJavaAndJavadocTree(file);
            System.out.print(stringAst);
        }
        else if (hasSuppressionLineColumnNumber) {
            final File file = filesToProcess.get(0);
            final String stringSuppressions =
                    SuppressionsStringPrinter.printSuppressions(file,
                            options.suppressionLineColumnNumber, options.tabWidth);
            System.out.print(stringSuppressions);
        }
        else {
            if (options.debug) {
                final Logger parentLogger = Logger.getLogger(Main.class.getName()).getParent();
                final ConsoleHandler handler = new ConsoleHandler();
                handler.setLevel(Level.FINEST);
                handler.setFilter(new OnlyCheckstyleLoggersFilter());
                parentLogger.addHandler(handler);
                parentLogger.setLevel(Level.FINEST);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Checkstyle debug logging enabled");
                LOG.debug("Running Checkstyle with version: "
                        + Main.class.getPackage().getImplementationVersion());
            }

            // run Checker
            result = runCheckstyle(options, filesToProcess);
        }

        return result;
    }

    /**
     * Executes required Checkstyle actions based on passed parameters.
     * @param options user-specified options
     * @param filesToProcess the list of files whose style to check
     * @return number of violations of ERROR level
     * @throws IOException
     *         when output file could not be found
     * @throws CheckstyleException
     *         when properties file could not be loaded
     */
    private static int runCheckstyle(CliOptions options, List<File> filesToProcess)
            throws CheckstyleException, IOException {
        // setup the properties
        final Properties props;

        if (options.propertiesFile == null) {
            props = System.getProperties();
        }
        else {
            props = loadProperties(options.propertiesFile);
        }

        // create a configuration
        final ThreadModeSettings multiThreadModeSettings =
                new ThreadModeSettings(options.checkerThreadsNumber,
                        options.treeWalkerThreadsNumber);

        final ConfigurationLoader.IgnoredModulesOptions ignoredModulesOptions;
        if (options.executeIgnoredModules) {
            ignoredModulesOptions = ConfigurationLoader.IgnoredModulesOptions.EXECUTE;
        }
        else {
            ignoredModulesOptions = ConfigurationLoader.IgnoredModulesOptions.OMIT;
        }

        final Configuration config = ConfigurationLoader.loadConfiguration(
                options.configurationFile, new PropertiesExpander(props),
                ignoredModulesOptions, multiThreadModeSettings);

        // create RootModule object and run it
        final int errorCounter;
        final ClassLoader moduleClassLoader = Checker.class.getClassLoader();
        final RootModule rootModule = getRootModule(config.getName(), moduleClassLoader);

        try {
            final AuditListener listener;
            if (options.generateXpathSuppressionsFile) {
                // create filter to print generated xpath suppressions file
                final Configuration treeWalkerConfig = getTreeWalkerConfig(config);
                if (treeWalkerConfig != null) {
                    final DefaultConfiguration moduleConfig =
                            new DefaultConfiguration(
                                    XpathFileGeneratorAstFilter.class.getName());
                    moduleConfig.addAttribute(CliOptions.ATTRIB_TAB_WIDTH_NAME,
                            String.valueOf(options.tabWidth));
                    ((DefaultConfiguration) treeWalkerConfig).addChild(moduleConfig);
                }

                listener = new XpathFileGeneratorAuditListener(getOutputStream(options.outputPath),
                        getOutputStreamOptions(options.outputPath));
            }
            else {
                listener = createListener(options.format, options.outputPath);
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
            final LocalizedMessage loadPropertiesExceptionMessage = new LocalizedMessage(1,
                    Definitions.CHECKSTYLE_BUNDLE, LOAD_PROPERTIES_EXCEPTION,
                    new String[] {file.getAbsolutePath()}, null, Main.class, null);
            throw new CheckstyleException(loadPropertiesExceptionMessage.getMessage(), ex);
        }

        return properties;
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
     * This method creates in AuditListener an open stream for validation data, it must be
     * closed by {@link RootModule} (default implementation is {@link Checker}) by calling
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
     * @noinspection UseOfSystemOutOrSystemErr
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
     * Enumeration over the possible output formats.
     *
     * @noinspection PackageVisibleInnerClass
     */
    // Package-visible for tests.
    enum OutputFormat {
        /** XML output format. */
        XML,
        /** Plain output format. */
        PLAIN;

        /**
         * Returns a new AuditListener for this OutputFormat.
         * @param out the output stream
         * @param options the output stream options
         * @return a new AuditListener for this OutputFormat
         */
        public AuditListener createListener(OutputStream out,
                                            AutomaticBean.OutputStreamOptions options) {
            final AuditListener result;
            if (this == XML) {
                result = new XMLLogger(out, options);
            }
            else {
                result = new DefaultLogger(out, options);
            }
            return result;
        }

        /**
         * Returns the name in lowercase.
         *
         * @return the enum name in lowercase
         */
        @Override
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    /** Log Filter used in debug mode. */
    private static final class OnlyCheckstyleLoggersFilter implements Filter {
        /** Name of the package used to filter on. */
        private final String packageName = Main.class.getPackage().getName();

        /**
         * Returns whether the specified record should be logged.
         * @param record the record to log
         * @return true if the logger name is in the package of this class or a subpackage
         */
        @Override
        public boolean isLoggable(LogRecord record) {
            return record.getLoggerName().startsWith(packageName);
        }
    }

    /**
     * Command line options.
     * @noinspection unused, FieldMayBeFinal, CanBeFinal,
     *              MismatchedQueryAndUpdateOfCollection, LocalCanBeFinal
     */
    @Command(name = "checkstyle", description = "Checkstyle verifies that the specified "
            + "source code files adhere to the specified rules. By default violations are "
            + "reported to standard out in plain format. Checkstyle requires a configuration "
            + "XML file that configures the checks to apply.",
            mixinStandardHelpOptions = true)
    private static class CliOptions {

        /** Width of CLI help option. */
        private static final int HELP_WIDTH = 100;

        /** The default number of threads to use for checker and the tree walker. */
        private static final int DEFAULT_THREAD_COUNT = 1;

        /** Name for the moduleConfig attribute 'tabWidth'. */
        private static final String ATTRIB_TAB_WIDTH_NAME = "tabWidth";

        /** Default output format. */
        private static final OutputFormat DEFAULT_OUTPUT_FORMAT = OutputFormat.PLAIN;

        /** Option name for output format. */
        private static final String OUTPUT_FORMAT_OPTION = "-f";

        /** List of file to validate. */
        @Parameters(arity = "1..*", description = "One or more source files to verify")
        private List<File> files;

        /** Config file location. */
        @Option(names = "-c", description = "Sets the check configuration file to use.")
        private String configurationFile;

        /** Output file location. */
        @Option(names = "-o", description = "Sets the output file. Defaults to stdout")
        private Path outputPath;

        /** Properties file location. */
        @Option(names = "-p", description = "Loads the properties file")
        private File propertiesFile;

        /** LineNo and columnNo for the suppression. */
        @Option(names = "-s",
                description = "Print xpath suppressions at the file's line and column position. "
                        + "Argument is the line and column number (separated by a : ) in the file "
                        + "that the suppression should be generated for")
        private String suppressionLineColumnNumber;

        /**
         * Tab character length.
         * Suppression: CanBeFinal - we use picocli and it use  reflection to manage such fields
         *
         * @noinspection CanBeFinal
         */
        @Option(names = {"-w", "--tabWidth"}, description = "Sets the length of the tab character. "
                + "Used only with \"-s\" option. Default value is ${DEFAULT-VALUE}")
        private int tabWidth = CommonUtil.DEFAULT_TAB_WIDTH;

        /** Switch whether to generate suppressions file or not. */
        @Option(names = {"-g", "--generate-xpath-suppression"},
                description = "Generates to output a suppression xml to use to suppress all"
                        + " violations from user's config")
        private boolean generateXpathSuppressionsFile;

        /**
         * Output format.
         * Suppression: CanBeFinal - we use picocli and it use  reflection to manage such fields
         *
         * @noinspection CanBeFinal
         */
        @Option(names = "-f", description = "Sets the output format. Valid values: "
                + "${COMPLETION-CANDIDATES}. Defaults to ${DEFAULT-VALUE}")
        private OutputFormat format = DEFAULT_OUTPUT_FORMAT;

        /** Option that controls whether to print the AST of the file. */
        @Option(names = {"-t", "--tree"},
                description = "Print Abstract Syntax Tree(AST) of the file")
        private boolean printAst;

        /** Option that controls whether to print the AST of the file including comments. */
        @Option(names = {"-T", "--treeWithComments"},
                description = "Print Abstract Syntax Tree(AST) of the file including comments")
        private boolean printAstWithComments;

        /** Option that controls whether to print the parse tree of the javadoc comment. */
        @Option(names = {"-j", "--javadocTree"},
                description = "Print Parse tree of the Javadoc comment")
        private boolean printJavadocTree;

        /** Option that controls whether to print the full AST of the file. */
        @Option(names = {"-J", "--treeWithJavadoc"},
                description = "Print full Abstract Syntax Tree of the file")
        private boolean printTreeWithJavadoc;

        /** Option that controls whether to print debug info. */
        @Option(names = {"-d", "--debug"},
                description = "Print all debug logging of CheckStyle utility")
        private boolean debug;

        /**
         * Option that allows users to specify a list of paths to exclude.
         * Suppression: CanBeFinal - we use picocli and it use  reflection to manage such fields
         *
         * @noinspection CanBeFinal
         */
        @Option(names = {"-e", "--exclude"},
                description = "Directory/File path to exclude from CheckStyle")
        private List<File> exclude = new ArrayList<>();

        /**
         * Option that allows users to specify a regex of paths to exclude.
         * Suppression: CanBeFinal - we use picocli and it use  reflection to manage such fields
         *
         * @noinspection CanBeFinal
         */
        @Option(names = {"-x", "--exclude-regexp"},
                description = "Regular expression of directory/file to exclude from CheckStyle")
        private List<Pattern> excludeRegex = new ArrayList<>();

        /** Switch whether to execute ignored modules or not. */
        @Option(names = {"-E", "--executeIgnoredModules"},
                description = "Allows ignored modules to be run.")
        private boolean executeIgnoredModules;

        /**
         * The checker threads number.
         * Suppression: CanBeFinal - we use picocli and it use  reflection to manage such fields
         *
         * @noinspection CanBeFinal
         */
        @Option(names = {"-C", "--checker-threads-number"}, description = "(experimental) The "
                + "number of Checker threads (must be greater than zero)")
        private int checkerThreadsNumber = DEFAULT_THREAD_COUNT;

        /**
         * The tree walker threads number.
         * Suppression: CanBeFinal - we use picocli and it use  reflection to manage such fields
         *
         * @noinspection CanBeFinal
         */
        @Option(names = {"-W", "--tree-walker-threads-number"}, description = "(experimental) The "
                + "number of TreeWalker threads (must be greater than zero)")
        private int treeWalkerThreadsNumber = DEFAULT_THREAD_COUNT;

        /** Show AST branches that match xpath. */
        @Option(names = {"-b", "--branch-matching-xpath"},
            description = "Show Abstract Syntax Tree(AST) branches that match XPath")
        private String xpath;

        /**
         * Gets the list of exclusions provided through the command line arguments.
         * @return List of exclusion patterns.
         */
        private List<Pattern> getExclusions() {
            final List<Pattern> result = exclude.stream()
                    .map(File::getAbsolutePath)
                    .map(Pattern::quote)
                    .map(pattern -> Pattern.compile("^" + pattern + "$"))
                    .collect(Collectors.toCollection(ArrayList::new));
            result.addAll(excludeRegex);
            return result;
        }

        /**
         * Validates the user-specified command line options.
         * @param parseResult used to verify if the format option was specified on the command line
         * @param filesToProcess the list of files whose style to check
         * @return list of violations
         */
        // -@cs[CyclomaticComplexity] Breaking apart will damage encapsulation
        private List<String> validateCli(ParseResult parseResult, List<File> filesToProcess) {
            final List<String> result = new ArrayList<>();
            final boolean hasConfigurationFile = configurationFile != null;
            final boolean hasSuppressionLineColumnNumber = suppressionLineColumnNumber != null;

            if (filesToProcess.isEmpty()) {
                result.add("Files to process must be specified, found 0.");
            }
            // ensure there is no conflicting options
            else if (printAst || printAstWithComments || printJavadocTree || printTreeWithJavadoc
                || xpath != null) {
                if (suppressionLineColumnNumber != null || configurationFile != null
                        || propertiesFile != null || outputPath != null
                        || parseResult.hasMatchedOption(OUTPUT_FORMAT_OPTION)) {
                    result.add("Option '-t' cannot be used with other options.");
                }
                else if (filesToProcess.size() > 1) {
                    result.add("Printing AST is allowed for only one file.");
                }
            }
            else if (hasSuppressionLineColumnNumber) {
                if (configurationFile != null || propertiesFile != null
                        || outputPath != null
                        || parseResult.hasMatchedOption(OUTPUT_FORMAT_OPTION)) {
                    result.add("Option '-s' cannot be used with other options.");
                }
                else if (filesToProcess.size() > 1) {
                    result.add("Printing xpath suppressions is allowed for only one file.");
                }
            }
            else if (hasConfigurationFile) {
                try {
                    // test location only
                    CommonUtil.getUriByFilename(configurationFile);
                }
                catch (CheckstyleException ignored) {
                    final String msg = "Could not find config XML file '%s'.";
                    result.add(String.format(Locale.ROOT, msg, configurationFile));
                }
                result.addAll(validateOptionalCliParametersIfConfigDefined());
            }
            else {
                result.add("Must specify a config XML file.");
            }

            return result;
        }

        /**
         * Validates optional command line parameters that might be used with config file.
         * @return list of violations
         */
        private List<String> validateOptionalCliParametersIfConfigDefined() {
            final List<String> result = new ArrayList<>();
            if (propertiesFile != null && !propertiesFile.exists()) {
                result.add(String.format(Locale.ROOT,
                        "Could not find file '%s'.", propertiesFile));
            }
            if (checkerThreadsNumber < 1) {
                result.add("Checker threads number must be greater than zero");
            }
            if (treeWalkerThreadsNumber < 1) {
                result.add("TreeWalker threads number must be greater than zero");
            }
            return result;
        }
    }
}

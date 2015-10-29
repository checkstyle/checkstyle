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
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck;

public class AllChecksTest extends BaseCheckTestSupport {
    private static final String CONFIG_PATH = "config" + File.separator
            + "checkstyle_checks.xml";

    @Test
    public void testAllChecksWithDefaultConfiguration() throws Exception {

        final Set<Class<?>> checkstyleChecks = getCheckstyleChecks();
        final String inputFilePath = getNonCompilablePath("InputDefaultConfig.java");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        for (Class<?> check : checkstyleChecks) {
            final DefaultConfiguration checkConfig = createCheckConfig(check);
            final Checker checker;
            if (Check.class.isAssignableFrom(check)) {
                // Checks which have Check as a parent.
                if (check.equals(ImportControlCheck.class)) {
                    // ImportControlCheck must have the import control configuration file to avoid
                    // violation.
                    checkConfig.addAttribute("file", getPath("import-control_complete.xml"));
                }
                checker = createChecker(checkConfig);
            }
            else {
                // Checks which have TreeWalker as a parent.
                BaseCheckTestSupport testSupport = new BaseCheckTestSupport() {
                    @Override
                    protected DefaultConfiguration createCheckerConfig(Configuration config) {
                        final DefaultConfiguration dc = new DefaultConfiguration("root");
                        dc.addChild(checkConfig);
                        return dc;
                    }
                };
                checker = testSupport.createChecker(checkConfig);
            }
            verify(checker, inputFilePath, expected);
        }
    }

    @Test
    public void testDefaultTokensAreSubsetOfAcceptableTokens() throws Exception {
        final Set<Class<?>> checkstyleChecks = getCheckstyleChecks();

        for (Class<?> check : checkstyleChecks) {
            if (Check.class.isAssignableFrom(check)) {
                final Check testedCheck = (Check) check.getDeclaredConstructor().newInstance();
                final int[] defaultTokens = testedCheck.getDefaultTokens();
                final int[] acceptableTokens = testedCheck.getAcceptableTokens();

                if (!isSubset(defaultTokens, acceptableTokens)) {
                    final String errorMessage = String.format(Locale.ROOT,
                            "%s's default tokens must be a subset"
                            + " of acceptable tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
            }
        }
    }

    @Test
    public void testRequiredTokensAreSubsetOfAcceptableTokens() throws Exception {
        final Set<Class<?>> checkstyleChecks = getCheckstyleChecks();

        for (Class<?> check : checkstyleChecks) {
            if (Check.class.isAssignableFrom(check)) {
                final Check testedCheck = (Check) check.getDeclaredConstructor().newInstance();
                final int[] requiredTokens = testedCheck.getRequiredTokens();
                final int[] acceptableTokens = testedCheck.getAcceptableTokens();

                if (!isSubset(requiredTokens, acceptableTokens)) {
                    final String errorMessage = String.format(Locale.ROOT,
                            "%s's required tokens must be a subset"
                            + " of acceptable tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
            }
        }
    }

    @Test
    public void testRequiredTokensAreSubsetOfDefaultTokens() throws Exception {
        final Set<Class<?>> checkstyleChecks = getCheckstyleChecks();

        for (Class<?> check : checkstyleChecks) {
            if (Check.class.isAssignableFrom(check)) {
                final Check testedCheck = (Check) check.getDeclaredConstructor().newInstance();
                final int[] defaultTokens = testedCheck.getDefaultTokens();
                final int[] requiredTokens = testedCheck.getRequiredTokens();

                if (!isSubset(requiredTokens, defaultTokens)) {
                    final String errorMessage = String.format(Locale.ROOT,
                            "%s's required tokens must be a subset"
                            + " of default tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
            }
        }
    }

    @Test
    public void testAllChecksAreReferencedInConfigFile() throws Exception {
        final Set<Class<?>> checksFromClassPath = getCheckstyleChecks();
        final Set<String> checksReferencedInConfig =
                getCheckStyleChecksReferencedInConfig(CONFIG_PATH);
        final Set<String> checksNames = getSimpleNames(checksFromClassPath);

        for (String check : checksNames) {
            if (!checksReferencedInConfig.contains(check)) {
                final String errorMessage = String.format(Locale.ROOT,
                        "%s is not referenced in checkstyle_checks.xml", check);
                Assert.fail(errorMessage);
            }
        }

    }

    @Test
    public void testAllCheckstyleModulesHaveXdocDocumentation() throws Exception {
        final Set<Class<?>> checkstyleModules = getCheckstyleModules();
        final Set<String> checkstyleModulesNames = getSimpleNames(checkstyleModules);
        final String xdocsDirectoryPath = "src" + File.separator + "xdocs";
        final Set<String> modulesNamesWhichHaveXdocs =
            getModulesNamesWhichHaveXdoc(xdocsDirectoryPath);

        for (String moduleName : checkstyleModulesNames) {
            if (!modulesNamesWhichHaveXdocs.contains(moduleName)) {
                final String missingModuleMessage = String.format(Locale.ROOT,
                    "Module %s does not have xdoc documentation.",
                    moduleName);
                Assert.fail(missingModuleMessage);
            }
        }
    }

    @Test
    public void testAllCheckstyleModulesInCheckstyleConfig() throws Exception {
        final Set<String> configChecks = getCheckStyleChecksReferencedInConfig(CONFIG_PATH);

        for (String moduleName : getSimpleNames(getCheckstyleModules())) {
            if ("SuppressionCommentFilter".equals(moduleName)
                || "SeverityMatchFilter".equals(moduleName)
                || "SuppressWithNearbyCommentFilter".equals(moduleName)
                || "SuppressWarningsFilter".equals(moduleName)) {
                continue;
            }

            Assert.assertTrue("checkstyle_checks.xml is missing module: " + moduleName,
                    configChecks.contains(moduleName));
        }
    }

    /**
     * Gets the checkstyle's non abstract checks.
     * @return the set of checkstyle's non abstract check classes.
     * @throws IOException if the attempt to read class path resources failed.
     */
    private static Set<Class<?>> getCheckstyleChecks() throws IOException {
        final Set<Class<?>> checkstyleChecks = new HashSet<>();

        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final ClassPath classpath = ClassPath.from(loader);
        final String packageName = "com.puppycrawl.tools.checkstyle";
        final ImmutableSet<ClassPath.ClassInfo> checkstyleClasses =
            classpath.getTopLevelClassesRecursive(packageName);

        for (ClassPath.ClassInfo clazz : checkstyleClasses) {
            final String className = clazz.getSimpleName();
            final Class<?> loadedClass = clazz.load();
            if (isCheckstyleNonAbstractCheck(loadedClass, className)) {
                checkstyleChecks.add(loadedClass);
            }
        }
        return checkstyleChecks;
    }

    /**
     * Gets the checkstyle's modules.
     * Checkstyle's modules are nonabstract classes from com.puppycrawl.tools.checkstyle package
     * which names end with 'Check', do not contain the word 'Input' (are not input files for UTs),
     * checkstyle's filters and SuppressWarningsHolder class.
     * @return a set of checkstyle's modules names.
     * @throws IOException if the attempt to read class path resources failed.
     */
    private static Set<Class<?>> getCheckstyleModules() throws IOException {
        final Set<Class<?>> checkstyleModules = new HashSet<>();

        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final ClassPath classpath = ClassPath.from(loader);
        final String packageName = "com.puppycrawl.tools.checkstyle";
        final ImmutableSet<ClassPath.ClassInfo> checkstyleClasses =
            classpath.getTopLevelClassesRecursive(packageName);

        for (ClassPath.ClassInfo clazz : checkstyleClasses) {
            final Class<?> loadedClass = clazz.load();
            if (isCheckstyleModule(loadedClass)) {
                checkstyleModules.add(loadedClass);
            }
        }
        return checkstyleModules;
    }

    /**
     * Checks whether a class may be considered as the checkstyle check.
     * Checkstyle's checks are nonabstract classes which names end with 'Check',
     * do not contain the word 'Input' (are not input files for UTs).
     * @param loadedClass class to check.
     * @param className class name to check.
     * @return true if a class may be considered as the checkstyle check.
     */
    private static boolean isCheckstyleNonAbstractCheck(Class<?> loadedClass, String className) {
        return !Modifier.isAbstract(loadedClass.getModifiers())
            && className.endsWith("Check")
            && !className.contains("Input");
    }

    /**
     * Checks whether a class may be considered as the checkstyle module.
     * Checkstyle's modules are nonabstract classes which names end with 'Check',
     * do not contain the word 'Input' (are not input files for UTs),
     * checkstyle's filters and SuppressWarningsHolder class.
     * @param loadedClass class to check.
     * @return true if the class may be considered as the checkstyle module.
     */
    private static boolean isCheckstyleModule(Class<?> loadedClass) {
        final String className = loadedClass.getSimpleName();
        return isCheckstyleNonAbstractCheck(loadedClass, className)
            || isFilterModule(loadedClass, className)
            || "SuppressWarningsHolder".equals(className)
            || "FileContentsHolder".equals(className);
    }

    /**
     * Checks whether a class may be considered as the checkstyle filter.
     * Checkstyle's filters are classes which are subclasses of AutomaticBean,
     * implement 'Filter' interface, and which names end with 'Filter'.
     * @param loadedClass class to check.
     * @param className class name to check.
     * @return true if a class may be considered as the checkstyle filter.
     */
    private static boolean isFilterModule(Class<?> loadedClass, String className) {
        return Filter.class.isAssignableFrom(loadedClass)
            && AutomaticBean.class.isAssignableFrom(loadedClass)
            && className.endsWith("Filter");
    }

    /**
     * Checks that an array is a subset of other array.
     * @param array to check whether it is a subset.
     * @param arrayToCheckIn array to check in.
     */
    private static boolean isSubset(int[] array, int... arrayToCheckIn) {
        Arrays.sort(arrayToCheckIn);
        for (final int element : array) {
            if (Arrays.binarySearch(arrayToCheckIn, element) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets a set of names of checkstyle's checks which are referenced in checkstyle_checks.xml.
     * @param configFilePath file path of checkstyle_checks.xml.
     * @return names of checkstyle's checks which are referenced in checkstyle_checks.xml.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies
     *              the configuration requested.
     * @throws IOException if any IO errors occur.
     * @throws SAXException if any parse errors occur.
     */
    private static Set<String> getCheckStyleChecksReferencedInConfig(String configFilePath)
        throws ParserConfigurationException, IOException, SAXException {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Validations of XML file make parsing too slow, that is why we disable all validations.
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        factory.setFeature("http://xml.org/sax/features/namespaces", false);
        factory.setFeature("http://xml.org/sax/features/validation", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document document = builder.parse(new File(configFilePath));

        // optional, but recommended
        // FYI: http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-
        // how-does-it-work
        document.getDocumentElement().normalize();

        final NodeList nodeList = document.getElementsByTagName("module");

        final Set<String> checksReferencedInCheckstyleChecksXML = new HashSet<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                final Element module = (Element) currentNode;
                final String checkName = module.getAttribute("name");
                if (!"Checker".equals(checkName)
                    && !"TreeWalker".equals(checkName)) {
                    checksReferencedInCheckstyleChecksXML.add(checkName);
                }
            }
        }
        return checksReferencedInCheckstyleChecksXML;
    }

    /**
     * Gets names of checkstyle's modules which are documented in xdocs.
     * @param xdocsDirectoryPath xdocs directory path.
     * @return a set of checkstyle's modules which have xdoc documentation.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies
     *              the configuration requested.
     * @throws IOException if any IO errors occur.
     * @throws SAXException if any parse errors occur.
     */
    private static Set<String> getModulesNamesWhichHaveXdoc(String xdocsDirectoryPath)
        throws ParserConfigurationException, IOException, SAXException {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Validations of XML file make parsing too slow, that is why we disable all validations.
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        factory.setFeature("http://xml.org/sax/features/namespaces", false);
        factory.setFeature("http://xml.org/sax/features/validation", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        final Set<Path> xdocsFilePaths = getXdocsFilePaths(xdocsDirectoryPath);
        final Set<String> modulesNamesWhichHaveXdoc = new HashSet<>();

        for (Path path : xdocsFilePaths) {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(path.toFile());

            // optional, but recommended
            // FYI: http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-
            // java-how-does-it-work
            document.getDocumentElement().normalize();

            final NodeList nodeList = document.getElementsByTagName("section");

            for (int i = 0; i < nodeList.getLength(); i++) {
                final Node currentNode = nodeList.item(i);
                if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                    final Element module = (Element) currentNode;
                    final String moduleName = module.getAttribute("name");
                    if (!"Content".equals(moduleName)
                            && !"Overview".equals(moduleName)) {
                        modulesNamesWhichHaveXdoc.add(moduleName);
                    }
                }
            }
        }
        return modulesNamesWhichHaveXdoc;
    }

    /**
     * Gets xdocs documentation file paths.
     * @param xdocsDirectoryPath xdocs directory path.
     * @return a list of xdocs file paths.
     * @throws IOException if an I/O error occurs.
     */
    private static Set<Path> getXdocsFilePaths(String xdocsDirectoryPath)
        throws IOException {

        final Path directory = Paths.get(xdocsDirectoryPath);
        final Set<Path> xdocs = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.xml")) {
            for (Path entry : stream) {
                final String fileName = entry.getFileName().toString();
                if (fileName.startsWith("config_")) {
                    xdocs.add(entry);
                }
            }
            return xdocs;
        }
    }

    /**
     * Removes 'Check' suffix from each class name in the set.
     * @param checks class instances.
     * @return a set of simple names.
     */
    private static Set<String> getSimpleNames(Set<Class<?>> checks) {
        final Set<String> checksNames = new HashSet<>();
        for (Class<?> check : checks) {
            checksNames.add(check.getSimpleName().replace("Check", ""));
        }
        return checksNames;
    }
}

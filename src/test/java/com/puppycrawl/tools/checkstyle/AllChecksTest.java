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
import java.util.Arrays;
import java.util.HashSet;
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
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck;

public class AllChecksTest extends BaseCheckTestSupport {

    @Test
    public void testAllChecksWithDefaultConfiguration() throws Exception {

        final Set<Class<?>> checkstyleChecks = getCheckstyleChecks();
        final String inputFilePath = "src/test/resources-noncompilable/"
            + "com/puppycrawl/tools/checkstyle/InputDefaultConfig.java";
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        for (Class<?> check : checkstyleChecks) {
            final DefaultConfiguration checkConfig = createCheckConfig(check);
            final Checker checker;
            if (Check.class.isAssignableFrom(check)) {
                // Checks which have Check as a parent.
                if (check.equals(ImportControlCheck.class)) {
                    // ImportControlCheck must have the import control configuration file to avoid violation.
                    checkConfig.addAttribute("file",
                        "src/test/resources/com/puppycrawl/tools/checkstyle/imports/import-control_complete.xml");
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
        Set<Class<?>> checkstyleChecks = getCheckstyleChecks();

        for (Class<?> check : checkstyleChecks) {
            if (Check.class.isAssignableFrom(check)) {
                final Check testedCheck = (Check) check.getDeclaredConstructor().newInstance();
                final int[] defaultTokens = testedCheck.getDefaultTokens();
                final int[] acceptableTokens = testedCheck.getAcceptableTokens();

                if (!isSubset(defaultTokens, acceptableTokens)) {
                    String errorMessage = String.format("%s's default tokens must be a subset"
                        + " of acceptable tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
            }
        }
    }

    @Test
    public void testRequiredTokensAreSubsetOfAcceptableTokens() throws Exception {
        Set<Class<?>> checkstyleChecks = getCheckstyleChecks();

        for (Class<?> check : checkstyleChecks) {
            if (Check.class.isAssignableFrom(check)) {
                final Check testedCheck = (Check) check.getDeclaredConstructor().newInstance();
                final int[] requiredTokens = testedCheck.getRequiredTokens();
                final int[] acceptableTokens = testedCheck.getAcceptableTokens();

                if (!isSubset(requiredTokens, acceptableTokens)) {
                    String errorMessage = String.format("%s's required tokens must be a subset"
                        + " of acceptable tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
            }
        }
    }

    @Test
    public void testRequiredTokensAreSubsetOfDefaultTokens() throws Exception {
        Set<Class<?>> checkstyleChecks = getCheckstyleChecks();

        for (Class<?> check : checkstyleChecks) {
            if (Check.class.isAssignableFrom(check)) {
                final Check testedCheck = (Check) check.getDeclaredConstructor().newInstance();
                final int[] defaultTokens = testedCheck.getDefaultTokens();
                final int[] requiredTokens = testedCheck.getRequiredTokens();

                if (!isSubset(requiredTokens, defaultTokens)) {
                    String errorMessage = String.format("%s's required tokens must be a subset"
                        + " of default tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
            }
        }
    }

    @Test
    public void testAllChecksAreReferencedInConfigFile() throws Exception {
        final String configFilePath = "config/checkstyle_checks.xml";
        final Set<Class<?>> checksFromClassPath = getCheckstyleChecks();
        final Set<String> checksReferencedInConfig = getCheckStyleChecksReferencedInConfig(configFilePath);
        final Set<String> checksNames = getChecksNames(checksFromClassPath);

        for (String check : checksNames) {
            if (!checksReferencedInConfig.contains(check)) {
                String errorMessage = String.format("%s is not refferenced in checkstyle_checks.xml", check);
                Assert.fail(errorMessage);
            }
        }

    }

    /**
     * Gets the checkstyle's non abstract checks.
     * @return the set of checkstyle's non abstract check classes.
     * @throws IOException if the attempt to read class path resources failed.
     */
    private static Set<Class<?>> getCheckstyleChecks() throws IOException {
        Set<Class<?>> checkstyleChecks = new HashSet<>();

        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final ClassPath classpath = ClassPath.from(loader);
        final String packageName = "com.puppycrawl.tools.checkstyle";
        final ImmutableSet<ClassPath.ClassInfo> checkstyleClasses =
            classpath.getTopLevelClassesRecursive(packageName);

        for (ClassPath.ClassInfo clazz : checkstyleClasses) {
            final String className = clazz.getSimpleName();
            final Class<?> loadedClass = clazz.load();
            if (!Modifier.isAbstract(loadedClass.getModifiers())
                && !className.contains("Input")
                && className.endsWith("Check")) {

                checkstyleChecks.add(loadedClass);
            }
        }
        return checkstyleChecks;
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
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies the configuration requested.
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
        // FYI: http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        document.getDocumentElement().normalize();

        final NodeList nodeList = document.getElementsByTagName("module");

        Set<String> checksReferencedInCheckstyleChecksXML = new HashSet<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                final Element module = (Element) currentNode;
                final String checkName = module.getAttribute("name");
                if (!"Checker".equals(checkName)
                    && !"TreeWalker".equals(checkName)
                    && !"SuppressionFilter".equals(checkName)) {
                    checksReferencedInCheckstyleChecksXML.add(checkName);
                }
            }
        }
        return checksReferencedInCheckstyleChecksXML;
    }

    /**
     * Gets checks names from a set of checks which are Class instances.
     * @param checks are Class instances.
     * @return a set of checks names.
     */
    private static Set<String> getChecksNames(Set<Class<?>> checks) {
        final Set<String> checksNames = new HashSet<>();
        for (Class<?> check : checks) {
            checksNames.add(check.getSimpleName().replace("Check", ""));
        }
        return checksNames;
    }
}

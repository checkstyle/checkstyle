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

package com.puppycrawl.tools.checkstyle.internal;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

public final class CheckUtil {
    private CheckUtil() {
    }

    public static Set<String> getConfigCheckStyleChecks() throws Exception {
        return getCheckStyleChecksReferencedInConfig("config/checkstyle_checks.xml");
    }

    public static Set<String> getConfigSunStyleChecks() throws Exception {
        return getCheckStyleChecksReferencedInConfig("src/main/resources/sun_checks.xml");
    }

    public static Set<String> getConfigGoogleStyleChecks() throws Exception {
        return getCheckStyleChecksReferencedInConfig("src/main/resources/google_checks.xml");
    }

    /**
     * Gets a set of names of checkstyle's checks which are referenced in checkstyle_checks.xml.
     *
     * @param configFilePath
     *            file path of checkstyle_checks.xml.
     * @return names of checkstyle's checks which are referenced in checkstyle_checks.xml.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies
     *              the configuration requested.
     * @throws IOException if any IO errors occur.
     * @throws SAXException if any parse errors occur.
     */
    private static Set<String> getCheckStyleChecksReferencedInConfig(
            String configFilePath) throws Exception {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Validations of XML file make parsing too slow, that is why we disable all
        // validations.
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        factory.setFeature("http://xml.org/sax/features/namespaces", false);
        factory.setFeature("http://xml.org/sax/features/validation", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
                false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",
                false);

        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document document = builder.parse(new File(configFilePath));

        // optional, but recommended
        // FYI:
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-
        // does-it-work
        document.getDocumentElement().normalize();

        final NodeList nodeList = document.getElementsByTagName("module");

        final Set<String> checksReferencedInCheckstyleChecksXml = new HashSet<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                final Element module = (Element) currentNode;
                final String checkName = module.getAttribute("name");
                if (!"Checker".equals(checkName) && !"TreeWalker".equals(checkName)) {
                    checksReferencedInCheckstyleChecksXml.add(checkName);
                }
            }
        }
        return checksReferencedInCheckstyleChecksXml;
    }

    /**
     * Gets the checkstyle's non abstract checks.
     * @return the set of checkstyle's non abstract check classes.
     * @throws IOException if the attempt to read class path resources failed.
     */
    public static Set<Class<?>> getCheckstyleChecks() throws IOException {
        final Set<Class<?>> checkstyleChecks = new HashSet<>();

        final ClassLoader loader = Thread.currentThread()
                .getContextClassLoader();
        final ClassPath classpath = ClassPath.from(loader);
        final String packageName = "com.puppycrawl.tools.checkstyle";
        final ImmutableSet<ClassPath.ClassInfo> checkstyleClasses = classpath
                .getTopLevelClassesRecursive(packageName);

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
    public static Set<Class<?>> getCheckstyleModules() throws IOException {
        final Set<Class<?>> checkstyleModules = new HashSet<>();

        final ClassLoader loader = Thread.currentThread()
                .getContextClassLoader();
        final ClassPath classpath = ClassPath.from(loader);
        final String packageName = "com.puppycrawl.tools.checkstyle";
        final ImmutableSet<ClassPath.ClassInfo> checkstyleClasses = classpath
                .getTopLevelClassesRecursive(packageName);

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

    public static String getTokenText(int[] tokens, int... subtractions) {
        if (Arrays.equals(tokens, TokenUtils.getAllTokenIds()) && subtractions.length == 0) {
            return "TokenTypes.";
        }
        else {
            final StringBuilder result = new StringBuilder();
            boolean first = true;

            for (int token : tokens) {
                boolean found = false;

                for (int subtraction : subtractions) {
                    if (subtraction == token) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    continue;
                }

                if (first) {
                    first = false;
                }
                else {
                    result.append(", ");
                }

                result.append(TokenUtils.getTokenName(token));
            }

            if (result.length() == 0) {
                result.append("empty");
            }
            else {
                result.append(".");
            }

            return result.toString();
        }
    }
}

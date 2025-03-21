///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.reflect.ClassPath;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.checks.coding.AbstractSuperCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractAccessControlNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpMultilineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineJavaCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

public final class CheckUtil {

    public static final String CRLF = "\r\n";

    private CheckUtil() {
    }

    public static Set<String> getConfigCheckStyleModules() {
        return getCheckStyleModulesReferencedInConfig("config/checkstyle-checks.xml");
    }

    public static Set<String> getConfigSunStyleModules() {
        return getCheckStyleModulesReferencedInConfig("src/main/resources/sun_checks.xml");
    }

    public static Set<String> getConfigGoogleStyleModules() {
        return getCheckStyleModulesReferencedInConfig("src/main/resources/google_checks.xml");
    }

    /**
     * Retrieves a set of class names, removing 'Check' from the end if the class is
     * a checkstyle check.
     *
     * @param checks class instances.
     * @return a set of simple names.
     */
    public static Set<String> getSimpleNames(Set<Class<?>> checks) {
        return checks.stream().map(check -> {
            String name = check.getSimpleName();

            if (name.endsWith("Check")) {
                name = name.substring(0, name.length() - 5);
            }

            return name;
        }).collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Gets a set of names of checkstyle's checks which are referenced in checkstyle-checks.xml.
     *
     * @param configFilePath
     *            file path of checkstyle-checks.xml.
     * @return names of checkstyle's checks which are referenced in checkstyle-checks.xml.
     */
    private static Set<String> getCheckStyleModulesReferencedInConfig(String configFilePath) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Validations of XML file make parsing too slow, that is why we
            // disable all validations.
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
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-
            // how-does-it-work
            document.getDocumentElement().normalize();

            final NodeList nodeList = document.getElementsByTagName("module");

            final Set<String> checksReferencedInCheckstyleChecksXml = new HashSet<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                final Node currentNode = nodeList.item(i);
                if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                    final Element module = (Element) currentNode;
                    final String checkName = module.getAttribute("name");
                    checksReferencedInCheckstyleChecksXml.add(checkName);
                }
            }
            return checksReferencedInCheckstyleChecksXml;
        }
        catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    /**
     * Gets all checkstyle's non-abstract checks.
     *
     * @return the set of checkstyle's non-abstract check classes.
     * @throws IOException if the attempt to read class path resources failed.
     */
    public static Set<Class<?>> getCheckstyleChecks() throws IOException {
        final ClassLoader loader = Thread.currentThread()
                .getContextClassLoader();
        final String packageName = "com.puppycrawl.tools.checkstyle";
        return getCheckstyleModulesRecursive(packageName, loader).stream()
                .filter(ModuleReflectionUtil::isCheckstyleTreeWalkerCheck)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Gets all checkstyle's modules.
     *
     * @return the set of checkstyle's module classes.
     * @throws IOException if the attempt to read class path resources failed.
     */
    public static Set<Class<?>> getCheckstyleModules() throws IOException {
        final ClassLoader loader = Thread.currentThread()
                .getContextClassLoader();
        final String packageName = "com.puppycrawl.tools.checkstyle";
        return getCheckstyleModulesRecursive(packageName, loader);
    }

    /**
     * Gets checkstyle's modules in the given package recursively.
     *
     * @param packageName the package name to use
     * @param loader the class loader used to load Checkstyle package name
     * @return the set of checkstyle's module classes
     * @throws IOException if the attempt to read class path resources failed
     * @see ModuleReflectionUtil#isCheckstyleModule(Class)
     */
    private static Set<Class<?>> getCheckstyleModulesRecursive(
            String packageName, ClassLoader loader) throws IOException {
        final ClassPath classPath = ClassPath.from(loader);
        return classPath.getTopLevelClassesRecursive(packageName).stream()
                .map(ClassPath.ClassInfo::load)
                .filter(ModuleReflectionUtil::isCheckstyleModule)
                .filter(CheckUtil::isFromAllowedPackages)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks that class is from allowed packages.
     *
     * @param cls class to check
     * @return true if class is from allowed packages, false otherwise
     */
    private static boolean isFromAllowedPackages(Class<?> cls) {
        final String canonicalName = cls.getCanonicalName();
        return !canonicalName.startsWith("com.puppycrawl.tools.checkstyle.packageobjectfactory")
            && !canonicalName.startsWith("com.puppycrawl.tools.checkstyle.internal.testmodules")
            && !canonicalName.startsWith("com.puppycrawl.tools.checkstyle.site");
    }

    /**
     * Gets the check's messages.
     *
     * @param module class to examine.
     * @param deepScan scan subclasses.
     * @return a set of checkstyle's module message fields.
     * @throws ClassNotFoundException if the attempt to read a protected class fails.
     * @noinspection BooleanParameter
     * @noinspectionreason BooleanParameter - boolean parameter eases testing
     */
    public static Set<Field> getCheckMessages(Class<?> module, boolean deepScan)
            throws ClassNotFoundException {
        final Set<Field> checkstyleMessages = new HashSet<>();

        // get all fields from current class
        final Field[] fields = module.getDeclaredFields();

        for (Field field : fields) {
            if (field.getName().startsWith("MSG_")) {
                checkstyleMessages.add(field);
            }
        }

        // deep scan class through hierarchy
        final Class<?> superModule = module.getSuperclass();

        if (superModule != null && (deepScan || shouldScanDeepClassForMessages(superModule))) {
            checkstyleMessages.addAll(getCheckMessages(superModule, deepScan));
        }

        // special cases that require additional classes
        if (module == RegexpMultilineCheck.class) {
            checkstyleMessages.addAll(getCheckMessages(Class
                    .forName("com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector"),
                    deepScan));
        }
        else if (module == RegexpSinglelineCheck.class
                || module == RegexpSinglelineJavaCheck.class) {
            checkstyleMessages.addAll(getCheckMessages(Class
                    .forName("com.puppycrawl.tools.checkstyle.checks.regexp.SinglelineDetector"),
                    deepScan));
        }

        return checkstyleMessages;
    }

    /**
     * Should the class be deep scanned for messages.
     *
     * @param superModule The class to examine.
     * @return {@code true} if the class should be deeply scanned.
     */
    private static boolean shouldScanDeepClassForMessages(Class<?> superModule) {
        return superModule == AbstractNameCheck.class
                || superModule == AbstractAccessControlNameCheck.class
                || superModule == AbstractParenPadCheck.class
                || superModule == AbstractSuperCheck.class;
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties'
     * file.
     *
     * @param module The package the message is located in.
     * @param locale the locale to get the message for.
     * @param messageKey the key of message in 'messages*.properties' file.
     * @param arguments the arguments of message in 'messages*.properties' file.
     * @return the check's formatted message.
     */
    public static String getCheckMessage(Class<?> module, Locale locale, String messageKey,
            Object... arguments) {
        String checkMessage;
        try {
            final Properties pr = new Properties();
            if (locale == Locale.ENGLISH) {
                pr.load(module.getResourceAsStream("messages.properties"));
            }
            else {
                pr.load(module
                        .getResourceAsStream("messages_" + locale.getLanguage() + ".properties"));
            }
            final MessageFormat formatter = new MessageFormat(pr.getProperty(messageKey), locale);
            checkMessage = formatter.format(arguments);
        }
        catch (IOException ignored) {
            checkMessage = null;
        }
        return checkMessage;
    }

    public static String getTokenText(int[] tokens, int... subtractions) {
        final String tokenText;
        if (subtractions.length == 0 && Arrays.equals(tokens, TokenUtil.getAllTokenIds())) {
            tokenText = "TokenTypes.";
        }
        else {
            final StringBuilder result = new StringBuilder(50);
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

                result.append(TokenUtil.getTokenName(token));
            }

            if (result.length() != 0) {
                result.append('.');
            }

            tokenText = result.toString();
        }
        return tokenText;
    }

    public static Set<String> getTokenNameSet(int... tokens) {
        final Set<String> result = new HashSet<>();

        for (int token : tokens) {
            result.add(TokenUtil.getTokenName(token));
        }

        return result;
    }

    public static String getJavadocTokenText(int[] tokens, int... subtractions) {
        final StringBuilder result = new StringBuilder(50);
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

            result.append(JavadocUtil.getTokenName(token));
        }

        if (result.length() != 0) {
            result.append('.');
        }

        return result.toString();
    }
    @SuppressWarnings("ReturnValueIgnored")
    public static String getLineSeparatorForFile(String filepath, Charset charset)
            throws IOException {
        final boolean[] crFound = {false};
        new FileText(new File(filepath), charset.name())
                .getFullText()
                .chars()
                .peek(character -> {
                    if (character == '\r') {
                        crFound[0] = true;
                    }
                })
                .filter(character -> character == '\n')
                .findFirst().isPresent();

        final String result;
        if (crFound[0]) {
            result = CRLF;
        }
        else {
            result = "\n";
        }
        return result;
    }
}

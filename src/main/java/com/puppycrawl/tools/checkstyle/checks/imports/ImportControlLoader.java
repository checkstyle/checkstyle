////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Responsible for loading the contents of an import control configuration file.
 * @author Oliver Burn
 */
final class ImportControlLoader extends AbstractLoader {
    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_0 =
        "-//Puppy Crawl//DTD Import Control 1.0//EN";

    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_1 =
        "-//Puppy Crawl//DTD Import Control 1.1//EN";

    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_2 =
        "-//Puppy Crawl//DTD Import Control 1.2//EN";

    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_3 =
            "-//Puppy Crawl//DTD Import Control 1.3//EN";

    /** The resource for the configuration dtd. */
    private static final String DTD_RESOURCE_NAME_1_0 =
        "com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_0.dtd";

    /** The resource for the configuration dtd. */
    private static final String DTD_RESOURCE_NAME_1_1 =
        "com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_1.dtd";

    /** The resource for the configuration dtd. */
    private static final String DTD_RESOURCE_NAME_1_2 =
        "com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_2.dtd";

    /** The resource for the configuration dtd. */
    private static final String DTD_RESOURCE_NAME_1_3 =
            "com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_3.dtd";

    /** The map to lookup the resource name by the id. */
    private static final Map<String, String> DTD_RESOURCE_BY_ID = new HashMap<>();

    /** Name for attribute 'pkg'. */
    private static final String PKG_ATTRIBUTE_NAME = "pkg";

    /** Name for attribute 'strategyOnMismatch'. */
    private static final String STRATEGY_ON_MISMATCH_ATTRIBUTE_NAME = "strategyOnMismatch";

    /** Value "allowed" for attribute 'strategyOnMismatch'. */
    private static final String STRATEGY_ON_MISMATCH_ALLOWED_VALUE = "allowed";

    /** Value "disallowed" for attribute 'strategyOnMismatch'. */
    private static final String STRATEGY_ON_MISMATCH_DISALLOWED_VALUE = "disallowed";

    /** Qualified name for element 'subpackage'. */
    private static final String SUBPACKAGE_ELEMENT_NAME = "subpackage";

    /** Qualified name for element 'allow'. */
    private static final String ALLOW_ELEMENT_NAME = "allow";

    /** Used to hold the {@link ImportControl} objects. */
    private final Deque<ImportControl> stack = new ArrayDeque<>();

    static {
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_0, DTD_RESOURCE_NAME_1_0);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_1, DTD_RESOURCE_NAME_1_1);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_2, DTD_RESOURCE_NAME_1_2);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_3, DTD_RESOURCE_NAME_1_3);
    }

    /**
     * Constructs an instance.
     * @throws ParserConfigurationException if an error occurs.
     * @throws SAXException if an error occurs.
     */
    private ImportControlLoader() throws ParserConfigurationException,
            SAXException {
        super(DTD_RESOURCE_BY_ID);
    }

    @Override
    public void startElement(String namespaceUri,
                             String localName,
                             String qName,
                             Attributes attributes)
            throws SAXException {
        if ("import-control".equals(qName)) {
            final String pkg = safeGet(attributes, PKG_ATTRIBUTE_NAME);
            final MismatchStrategy strategyOnMismatch = getStrategyForImportControl(attributes);
            final boolean regex = containsRegexAttribute(attributes);
            stack.push(new ImportControl(pkg, regex, strategyOnMismatch));
        }
        else if (SUBPACKAGE_ELEMENT_NAME.equals(qName)) {
            final String name = safeGet(attributes, "name");
            final MismatchStrategy strategyOnMismatch = getStrategyForSubpackage(attributes);
            final boolean regex = containsRegexAttribute(attributes);
            final ImportControl parentImportControl = stack.peek();
            final ImportControl importControl = new ImportControl(parentImportControl, name,
                    regex, strategyOnMismatch);
            parentImportControl.addChild(importControl);
            stack.push(importControl);
        }
        else if (ALLOW_ELEMENT_NAME.equals(qName) || "disallow".equals(qName)) {
            // Need to handle either "pkg" or "class" attribute.
            // May have "exact-match" for "pkg"
            // May have "local-only"
            final boolean isAllow = ALLOW_ELEMENT_NAME.equals(qName);
            final boolean isLocalOnly = attributes.getValue("local-only") != null;
            final String pkg = attributes.getValue(PKG_ATTRIBUTE_NAME);
            final boolean regex = containsRegexAttribute(attributes);
            final AbstractImportRule rule;
            if (pkg == null) {
                // handle class names which can be normal class names or regular
                // expressions
                final String clazz = safeGet(attributes, "class");
                rule = new ClassImportRule(isAllow, isLocalOnly, clazz, regex);
            }
            else {
                final boolean exactMatch =
                        attributes.getValue("exact-match") != null;
                rule = new PkgImportRule(isAllow, isLocalOnly, pkg, exactMatch, regex);
            }
            stack.peek().addImportRule(rule);
        }
    }

    /**
     * Check if the given attributes contain the regex attribute.
     * @param attributes the attributes.
     * @return if the regex attribute is contained.
     */
    private static boolean containsRegexAttribute(Attributes attributes) {
        return attributes.getValue("regex") != null;
    }

    @Override
    public void endElement(String namespaceUri, String localName,
        String qName) {
        if (SUBPACKAGE_ELEMENT_NAME.equals(qName)) {
            stack.pop();
        }
    }

    /**
     * Loads the import control file from a file.
     * @param uri the uri of the file to load.
     * @return the root {@link ImportControl} object.
     * @throws CheckstyleException if an error occurs.
     */
    public static ImportControl load(URI uri) throws CheckstyleException {

        InputStream inputStream = null;
        try {
            inputStream = uri.toURL().openStream();
            final InputSource source = new InputSource(inputStream);
            return load(source, uri);
        }
        catch (MalformedURLException ex) {
            throw new CheckstyleException("syntax error in url " + uri, ex);
        }
        catch (IOException ex) {
            throw new CheckstyleException("unable to find " + uri, ex);
        }
        finally {
            closeStream(inputStream);
        }
    }

    /**
     * Loads the import control file from a {@link InputSource}.
     * @param source the source to load from.
     * @param uri uri of the source being loaded.
     * @return the root {@link ImportControl} object.
     * @throws CheckstyleException if an error occurs.
     */
    private static ImportControl load(InputSource source,
        URI uri) throws CheckstyleException {
        try {
            final ImportControlLoader loader = new ImportControlLoader();
            loader.parseInputSource(source);
            return loader.getRoot();
        }
        catch (ParserConfigurationException | SAXException ex) {
            throw new CheckstyleException("unable to parse " + uri
                    + " - " + ex.getMessage(), ex);
        }
        catch (IOException ex) {
            throw new CheckstyleException("unable to read " + uri, ex);
        }
    }

    /**
     * This method exists only due to bug in cobertura library
     * https://github.com/cobertura/cobertura/issues/170
     * @param inputStream the InputStream to close
     * @throws CheckstyleException if an error occurs.
     */
    private static void closeStream(InputStream inputStream) throws CheckstyleException {
        if (inputStream != null) {
            try {
                inputStream.close();
            }
            catch (IOException ex) {
                throw new CheckstyleException("unable to close input stream", ex);
            }
        }
    }

    /**
     * Returns root ImportControl.
     * @return the root {@link ImportControl} object loaded.
     */
    private ImportControl getRoot() {
        return stack.peek();
    }

    /**
     * Utility to get a strategyOnMismatch property for "import-control" tag.
     * @param attributes collect to get attribute from.
     * @return the value of the attribute.
     */
    private static MismatchStrategy getStrategyForImportControl(Attributes attributes) {
        final String returnValue = attributes.getValue(STRATEGY_ON_MISMATCH_ATTRIBUTE_NAME);
        MismatchStrategy strategyOnMismatch = MismatchStrategy.DISALLOWED;
        if (STRATEGY_ON_MISMATCH_ALLOWED_VALUE.equals(returnValue)) {
            strategyOnMismatch = MismatchStrategy.ALLOWED;
        }
        return strategyOnMismatch;
    }

    /**
     * Utility to get a strategyOnMismatch property for "subpackage" tag.
     * @param attributes collect to get attribute from.
     * @return the value of the attribute.
     */
    private static MismatchStrategy getStrategyForSubpackage(Attributes attributes) {
        final String returnValue = attributes.getValue(STRATEGY_ON_MISMATCH_ATTRIBUTE_NAME);
        MismatchStrategy strategyOnMismatch = MismatchStrategy.DELEGATE_TO_PARENT;
        if (STRATEGY_ON_MISMATCH_ALLOWED_VALUE.equals(returnValue)) {
            strategyOnMismatch = MismatchStrategy.ALLOWED;
        }
        else if (STRATEGY_ON_MISMATCH_DISALLOWED_VALUE.equals(returnValue)) {
            strategyOnMismatch = MismatchStrategy.DISALLOWED;
        }
        return strategyOnMismatch;
    }

    /**
     * Utility to safely get an attribute. If it does not exist an exception
     * is thrown.
     * @param attributes collect to get attribute from.
     * @param name name of the attribute to get.
     * @return the value of the attribute.
     * @throws SAXException if the attribute does not exist.
     */
    private static String safeGet(Attributes attributes, String name)
            throws SAXException {
        final String returnValue = attributes.getValue(name);
        if (returnValue == null) {
            // -@cs[IllegalInstantiation] SAXException is in the overridden method signature
            // of the only method which calls the current one
            throw new SAXException("missing attribute " + name);
        }
        return returnValue;
    }
}

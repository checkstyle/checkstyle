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

import com.puppycrawl.tools.checkstyle.XmlLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Responsible for loading the contents of an import control configuration file.
 */
public final class ImportControlLoader extends XmlLoader {

    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_0 =
        "-//Puppy Crawl//DTD Import Control 1.0//EN";

    /** The new public ID for version 1_0 of the configuration dtd. */
    private static final String DTD_PUBLIC_CS_ID_1_0 =
        "-//Checkstyle//DTD ImportControl Configuration 1.0//EN";

    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_1 =
        "-//Puppy Crawl//DTD Import Control 1.1//EN";

    /** The new public ID for version 1_1 of the configuration dtd. */
    private static final String DTD_PUBLIC_CS_ID_1_1 =
        "-//Checkstyle//DTD ImportControl Configuration 1.1//EN";

    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_2 =
        "-//Puppy Crawl//DTD Import Control 1.2//EN";

    /** The new public ID for version 1_2 of the configuration dtd. */
    private static final String DTD_PUBLIC_CS_ID_1_2 =
        "-//Checkstyle//DTD ImportControl Configuration 1.2//EN";

    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_3 =
        "-//Puppy Crawl//DTD Import Control 1.3//EN";

    /** The new public ID for version 1_3 of the configuration dtd. */
    private static final String DTD_PUBLIC_CS_ID_1_3 =
        "-//Checkstyle//DTD ImportControl Configuration 1.3//EN";

    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_4 =
        "-//Puppy Crawl//DTD Import Control 1.4//EN";

    /** The new public ID for version 1_4 of the configuration dtd. */
    private static final String DTD_PUBLIC_CS_ID_1_4 =
        "-//Checkstyle//DTD ImportControl Configuration 1.4//EN";

    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_5 =
        "-//Puppy Crawl//DTD Import Control 1.5//EN";

    /** The new public ID for version 1_5 of the configuration dtd. */
    private static final String DTD_PUBLIC_CS_ID_1_5 =
        "-//Checkstyle//DTD ImportControl Configuration 1.5//EN";

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

    /** The resource for the configuration dtd. */
    private static final String DTD_RESOURCE_NAME_1_4 =
        "com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_4.dtd";

    /** The resource for the configuration dtd. */
    private static final String DTD_RESOURCE_NAME_1_5 =
        "com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_5.dtd";

    /** The map to look up the resource name by the id. */
    private static final Map<String, String> DTD_RESOURCE_BY_ID = new HashMap<>();

    /** Name for attribute 'pkg'. */
    private static final String PKG_ATTRIBUTE_NAME = "pkg";

    /** Name for attribute 'name'. */
    private static final String NAME_ATTRIBUTE_NAME = "name";

    /** Name for attribute 'strategyOnMismatch'. */
    private static final String STRATEGY_ON_MISMATCH_ATTRIBUTE_NAME = "strategyOnMismatch";

    /** Value "allowed" for attribute 'strategyOnMismatch'. */
    private static final String STRATEGY_ON_MISMATCH_ALLOWED_VALUE = "allowed";

    /**
     * Value "disallowed" for attribute 'strategyOnMismatch'.
     *
     * @deprecated Use {@link #STRATEGY_ON_MISMATCH_FORBIDDEN_VALUE}
     */
    @Deprecated(since = "10.23.1")
    private static final String STRATEGY_ON_MISMATCH_DISALLOWED_VALUE = "disallowed";

    /** Value "forbidden" for attribute 'strategyOnMismatch'. */
    private static final String STRATEGY_ON_MISMATCH_FORBIDDEN_VALUE = "forbidden";

    /** Qualified name for element 'subpackage'. */
    private static final String SUBPACKAGE_ELEMENT_NAME = "subpackage";

    /** Qualified name for element 'file'. */
    private static final String FILE_ELEMENT_NAME = "file";

    /** Qualified name for element 'allow'. */
    private static final String ALLOW_ELEMENT_NAME = "allow";

    /** Used to hold the {@link AbstractImportControl} objects. */
    private final Deque<AbstractImportControl> stack = new ArrayDeque<>();

    static {
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_0, DTD_RESOURCE_NAME_1_0);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_1, DTD_RESOURCE_NAME_1_1);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_2, DTD_RESOURCE_NAME_1_2);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_3, DTD_RESOURCE_NAME_1_3);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_4, DTD_RESOURCE_NAME_1_4);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_5, DTD_RESOURCE_NAME_1_5);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_CS_ID_1_0, DTD_RESOURCE_NAME_1_0);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_CS_ID_1_1, DTD_RESOURCE_NAME_1_1);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_CS_ID_1_2, DTD_RESOURCE_NAME_1_2);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_CS_ID_1_3, DTD_RESOURCE_NAME_1_3);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_CS_ID_1_4, DTD_RESOURCE_NAME_1_4);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_CS_ID_1_5, DTD_RESOURCE_NAME_1_5);
    }

    /**
     * Constructs an instance.
     *
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
            stack.push(new PkgImportControl(pkg, regex, strategyOnMismatch));
        }
        else if (SUBPACKAGE_ELEMENT_NAME.equals(qName)) {
            final String name = safeGet(attributes, NAME_ATTRIBUTE_NAME);
            final MismatchStrategy strategyOnMismatch = getStrategyForSubpackage(attributes);
            final boolean regex = containsRegexAttribute(attributes);
            final PkgImportControl parentImportControl = (PkgImportControl) stack.peek();
            final AbstractImportControl importControl = new PkgImportControl(parentImportControl,
                    name, regex, strategyOnMismatch);
            parentImportControl.addChild(importControl);
            stack.push(importControl);
        }
        else if (FILE_ELEMENT_NAME.equals(qName)) {
            final String name = safeGet(attributes, NAME_ATTRIBUTE_NAME);
            final boolean regex = containsRegexAttribute(attributes);
            final PkgImportControl parentImportControl = (PkgImportControl) stack.peek();
            final AbstractImportControl importControl = new FileImportControl(parentImportControl,
                    name, regex);
            parentImportControl.addChild(importControl);
            stack.push(importControl);
        }
        else {
            final AbstractImportRule rule = createImportRule(qName, attributes);
            stack.peek().addImportRule(rule);
        }
    }

    /**
     * Constructs an instance of an import rule based on the given {@code name} and
     * {@code attributes}.
     *
     * @param qName The qualified name.
     * @param attributes The attributes attached to the element.
     * @return The created import rule.
     * @throws SAXException if an error occurs.
     */
    private static AbstractImportRule createImportRule(String qName, Attributes attributes)
            throws SAXException {
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
        return rule;
    }

    /**
     * Check if the given attributes contain the regex attribute.
     *
     * @param attributes the attributes.
     * @return if the regex attribute is contained.
     */
    private static boolean containsRegexAttribute(Attributes attributes) {
        return attributes.getValue("regex") != null;
    }

    @Override
    public void endElement(String namespaceUri, String localName,
        String qName) {
        if (SUBPACKAGE_ELEMENT_NAME.equals(qName) || FILE_ELEMENT_NAME.equals(qName)) {
            stack.pop();
        }
    }

    /**
     * Loads the import control file from a file.
     *
     * @param uri the uri of the file to load.
     * @return the root {@link PkgImportControl} object.
     * @throws CheckstyleException if an error occurs.
     */
    public static PkgImportControl load(URI uri) throws CheckstyleException {
        return loadUri(uri);
    }

    /**
     * Loads the import control file from a {@link InputSource}.
     *
     * @param source the source to load from.
     * @param uri uri of the source being loaded.
     * @return the root {@link PkgImportControl} object.
     * @throws CheckstyleException if an error occurs.
     */
    private static PkgImportControl load(InputSource source,
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
     * Loads the import control file from a URI.
     *
     * @param uri the uri of the file to load.
     * @return the root {@link PkgImportControl} object.
     * @throws CheckstyleException if an error occurs.
     */
    private static PkgImportControl loadUri(URI uri) throws CheckstyleException {
        try (InputStream inputStream = uri.toURL().openStream()) {
            final InputSource source = new InputSource(inputStream);
            return load(source, uri);
        }
        catch (MalformedURLException ex) {
            throw new CheckstyleException("syntax error in url " + uri, ex);
        }
        catch (IOException ex) {
            throw new CheckstyleException("unable to find " + uri, ex);
        }
    }

    /**
     * Returns root PkgImportControl.
     *
     * @return the root {@link PkgImportControl} object loaded.
     */
    private PkgImportControl getRoot() {
        return (PkgImportControl) stack.peek();
    }

    /**
     * Utility to get a strategyOnMismatch property for "import-control" tag.
     *
     * @param attributes collect to get attribute from.
     * @return the value of the attribute.
     */
    private static MismatchStrategy getStrategyForImportControl(Attributes attributes) {
        final String returnValue = attributes.getValue(STRATEGY_ON_MISMATCH_ATTRIBUTE_NAME);
        MismatchStrategy strategyOnMismatch = MismatchStrategy.FORBIDDEN;
        if (STRATEGY_ON_MISMATCH_ALLOWED_VALUE.equals(returnValue)) {
            strategyOnMismatch = MismatchStrategy.ALLOWED;
        }
        return strategyOnMismatch;
    }

    /**
     * Utility to get a strategyOnMismatch property for "subpackage" tag.
     *
     * @param attributes collect to get attribute from.
     * @return the value of the attribute.
     */
    private static MismatchStrategy getStrategyForSubpackage(Attributes attributes) {
        final String returnValue = attributes.getValue(STRATEGY_ON_MISMATCH_ATTRIBUTE_NAME);
        MismatchStrategy strategyOnMismatch = MismatchStrategy.DELEGATE_TO_PARENT;
        if (STRATEGY_ON_MISMATCH_ALLOWED_VALUE.equals(returnValue)) {
            strategyOnMismatch = MismatchStrategy.ALLOWED;
        }
        else if (STRATEGY_ON_MISMATCH_FORBIDDEN_VALUE.equals(returnValue)
            || STRATEGY_ON_MISMATCH_DISALLOWED_VALUE.equals(returnValue)
        ) {
            strategyOnMismatch = MismatchStrategy.FORBIDDEN;
        }
        return strategyOnMismatch;
    }

    /**
     * Utility to safely get an attribute. If it does not exist an exception
     * is thrown.
     *
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

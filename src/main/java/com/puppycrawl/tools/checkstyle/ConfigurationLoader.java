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
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Loads a configuration from a standard configuration XML file.
 *
 * @author Oliver Burn
 */
public final class ConfigurationLoader {
    /** Logger for ConfigurationLoader. */
    private static final Log LOG = LogFactory.getLog(ConfigurationLoader.class);

    /** the public ID for version 1_0 of the configuration dtd */
    private static final String DTD_PUBLIC_ID_1_0 =
        "-//Puppy Crawl//DTD Check Configuration 1.0//EN";

    /** the resource for version 1_0 of the configuration dtd */
    private static final String DTD_RESOURCE_NAME_1_0 =
        "com/puppycrawl/tools/checkstyle/configuration_1_0.dtd";

    /** the public ID for version 1_1 of the configuration dtd */
    private static final String DTD_PUBLIC_ID_1_1 =
        "-//Puppy Crawl//DTD Check Configuration 1.1//EN";

    /** the resource for version 1_1 of the configuration dtd */
    private static final String DTD_RESOURCE_NAME_1_1 =
        "com/puppycrawl/tools/checkstyle/configuration_1_1.dtd";

    /** the public ID for version 1_2 of the configuration dtd */
    private static final String DTD_PUBLIC_ID_1_2 =
        "-//Puppy Crawl//DTD Check Configuration 1.2//EN";

    /** the resource for version 1_2 of the configuration dtd */
    private static final String DTD_RESOURCE_NAME_1_2 =
        "com/puppycrawl/tools/checkstyle/configuration_1_2.dtd";

    /** the public ID for version 1_3 of the configuration dtd */
    private static final String DTD_PUBLIC_ID_1_3 =
        "-//Puppy Crawl//DTD Check Configuration 1.3//EN";

    /** the resource for version 1_3 of the configuration dtd */
    private static final String DTD_RESOURCE_NAME_1_3 =
        "com/puppycrawl/tools/checkstyle/configuration_1_3.dtd";

    /** the SAX document handler */
    private final InternalLoader saxHandler;

    /** property resolver **/
    private final PropertyResolver overridePropsResolver;
    /** the loaded configurations **/
    private final Deque<DefaultConfiguration> configStack = new ArrayDeque<>();
    /** the Configuration that is being built */
    private Configuration configuration;

    /** flags if modules with the severity 'ignore' should be omitted. */
    private final boolean omitIgnoredModules;

    /**
     * Creates a new {@code ConfigurationLoader} instance.
     * @param overrideProps resolver for overriding properties
     * @param omitIgnoredModules {@code true} if ignored modules should be
     *         omitted
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     */
    private ConfigurationLoader(final PropertyResolver overrideProps,
                                final boolean omitIgnoredModules)
        throws ParserConfigurationException, SAXException {
        saxHandler = new InternalLoader();
        overridePropsResolver = overrideProps;
        this.omitIgnoredModules = omitIgnoredModules;
    }

    /**
     * Creates mapping between local resources and dtd ids.
     * @return map between local resources and dtd ids.
     */
    private static Map<String, String> createIdToResourceNameMap() {
        final Map<String, String> map = Maps.newHashMap();
        map.put(DTD_PUBLIC_ID_1_0, DTD_RESOURCE_NAME_1_0);
        map.put(DTD_PUBLIC_ID_1_1, DTD_RESOURCE_NAME_1_1);
        map.put(DTD_PUBLIC_ID_1_2, DTD_RESOURCE_NAME_1_2);
        map.put(DTD_PUBLIC_ID_1_3, DTD_RESOURCE_NAME_1_3);
        return map;
    }

    /**
     * Parses the specified input source loading the configuration information.
     * The stream wrapped inside the source, if any, is NOT
     * explicitely closed after parsing, it is the responsibility of
     * the caller to close the stream.
     *
     * @param source the source that contains the configuration data
     * @throws IOException if an error occurs
     * @throws SAXException if an error occurs
     */
    private void parseInputSource(InputSource source)
        throws IOException, SAXException {
        saxHandler.parseInputSource(source);
    }

    /**
     * Returns the module configurations in a specified file.
     * @param config location of config file, can be either a URL or a filename
     * @param overridePropsResolver overriding properties
     * @return the check configurations
     * @throws CheckstyleException if an error occurs
     */
    public static Configuration loadConfiguration(String config,
            PropertyResolver overridePropsResolver) throws CheckstyleException {
        return loadConfiguration(config, overridePropsResolver, false);
    }

    /**
     * Returns the module configurations in a specified file.
     *
     * @param config location of config file, can be either a URL or a filename
     * @param overridePropsResolver overriding properties
     * @param omitIgnoredModules {@code true} if modules with severity
     *            'ignore' should be omitted, {@code false} otherwise
     * @return the check configurations
     * @throws CheckstyleException if an error occurs
     */
    public static Configuration loadConfiguration(String config,
        PropertyResolver overridePropsResolver, boolean omitIgnoredModules)
        throws CheckstyleException {
        // figure out if this is a File or a URL
        URI uri;
        try {
            final URL url = new URL(config);
            uri = url.toURI();
        }
        catch (final URISyntaxException | MalformedURLException ignored) {
            uri = null;
        }

        if (uri == null) {
            final File file = new File(config);
            if (file.exists()) {
                uri = file.toURI();
            }
            else {
                // check to see if the file is in the classpath
                try {
                    final URL configUrl = ConfigurationLoader.class
                            .getResource(config);
                    if (configUrl == null) {
                        throw new CheckstyleException("unable to find " + config);
                    }
                    uri = configUrl.toURI();
                }
                catch (final URISyntaxException e) {
                    throw new CheckstyleException("unable to find " + config, e);
                }
            }
        }
        final InputSource source = new InputSource(uri.toString());
        return loadConfiguration(source, overridePropsResolver,
                omitIgnoredModules);
    }

    /**
     * Returns the module configurations from a specified input stream.
     * Note that clients are required to close the given stream by themselves
     *
     * @param configStream the input stream to the Checkstyle configuration
     * @param overridePropsResolver overriding properties
     * @param omitIgnoredModules {@code true} if modules with severity
     *            'ignore' should be omitted, {@code false} otherwise
     * @return the check configurations
     * @throws CheckstyleException if an error occurs
     *
     * @deprecated As this method does not provide a valid system ID,
     *   preventing resolution of external entities, a
     *   {@link #loadConfiguration(InputSource,PropertyResolver,boolean)
     *          version using an InputSource}
     *   should be used instead
     */
    @Deprecated
    public static Configuration loadConfiguration(InputStream configStream,
        PropertyResolver overridePropsResolver, boolean omitIgnoredModules)
        throws CheckstyleException {
        return loadConfiguration(new InputSource(configStream),
                                 overridePropsResolver, omitIgnoredModules);
    }

    /**
     * Returns the module configurations from a specified input source.
     * Note that if the source does wrap an open byte or character
     * stream, clients are required to close that stream by themselves
     *
     * @param configSource the input stream to the Checkstyle configuration
     * @param overridePropsResolver overriding properties
     * @param omitIgnoredModules {@code true} if modules with severity
     *            'ignore' should be omitted, {@code false} otherwise
     * @return the check configurations
     * @throws CheckstyleException if an error occurs
     */
    public static Configuration loadConfiguration(InputSource configSource,
        PropertyResolver overridePropsResolver, boolean omitIgnoredModules)
        throws CheckstyleException {
        try {
            final ConfigurationLoader loader =
                new ConfigurationLoader(overridePropsResolver,
                                        omitIgnoredModules);
            loader.parseInputSource(configSource);
            return loader.getConfiguration();
        }
        catch (final SAXParseException e) {
            throw new CheckstyleException("unable to parse configuration stream"
                    + " - " + e.getMessage() + ":" + e.getLineNumber()
                    + ":" + e.getColumnNumber(), e);
        }
        catch (final ParserConfigurationException | IOException | SAXException e) {
            throw new CheckstyleException("unable to parse configuration stream", e);
        }
    }

    /**
     * Returns the configuration in the last file parsed.
     * @return Configuration object
     */
    private Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Replaces {@code ${xxx}} style constructions in the given value
     * with the string value of the corresponding data types.
     *
     * The method is package visible to facilitate testing.
     *
     * @param value The string to be scanned for property references.
     *              May be {@code null}, in which case this
     *              method returns immediately with no effect.
     * @param props Mapping (String to String) of property names to their
     *              values. Must not be {@code null}.
     * @param defaultValue default to use if one of the properties in value
     *              cannot be resolved from props.
     *
     * @return the original string with the properties replaced, or
     *         {@code null} if the original string is {@code null}.
     * @throws CheckstyleException if the string contains an opening
     *                           {@code ${} without a closing
     *                           {@code }}
     *
     * Code copied from ant -
     * http://cvs.apache.org/viewcvs/jakarta-ant/src/main/org/apache/tools/ant/ProjectHelper.java
     */
    // Package visible for testing purposes
    static String replaceProperties(
            String value, PropertyResolver props, String defaultValue)
        throws CheckstyleException {
        if (value == null) {
            return null;
        }

        final List<String> fragments = Lists.newArrayList();
        final List<String> propertyRefs = Lists.newArrayList();
        parsePropertyString(value, fragments, propertyRefs);

        final StringBuilder sb = new StringBuilder();
        final Iterator<String> fragmentsIterator = fragments.iterator();
        final Iterator<String> propertyRefsIterator = propertyRefs.iterator();
        while (fragmentsIterator.hasNext()) {
            String fragment = fragmentsIterator.next();
            if (fragment == null) {
                final String propertyName = propertyRefsIterator.next();
                fragment = props.resolve(propertyName);
                if (fragment == null) {
                    if (defaultValue != null) {
                        return defaultValue;
                    }
                    throw new CheckstyleException(
                        "Property ${" + propertyName + "} has not been set");
                }
            }
            sb.append(fragment);
        }

        return sb.toString();
    }

    /**
     * Parses a string containing {@code ${xxx}} style property
     * references into two lists. The first list is a collection
     * of text fragments, while the other is a set of string property names.
     * {@code null} entries in the first list indicate a property
     * reference from the second list.
     *
     * @param value     Text to parse. Must not be {@code null}.
     * @param fragments List to add text fragments to.
     *                  Must not be {@code null}.
     * @param propertyRefs List to add property names to.
     *                     Must not be {@code null}.
     *
     * @throws CheckstyleException if the string contains an opening
     *                           {@code ${} without a closing
     *                           {@code }}
     * Code copied from ant -
     * http://cvs.apache.org/viewcvs/jakarta-ant/src/main/org/apache/tools/ant/ProjectHelper.java
     */
    private static void parsePropertyString(String value,
                                           List<String> fragments,
                                           List<String> propertyRefs)
        throws CheckstyleException {
        int prev = 0;
        //search for the next instance of $ from the 'prev' position
        int pos = value.indexOf('$', prev);
        while (pos >= 0) {

            //if there was any text before this, add it as a fragment
            if (pos > 0) {
                fragments.add(value.substring(prev, pos));
            }
            //if we are at the end of the string, we tack on a $
            //then move past it
            if (pos == value.length() - 1) {
                fragments.add("$");
                prev = pos + 1;
            }
            else if (value.charAt(pos + 1) != '{') {
                //peek ahead to see if the next char is a property or not
                //not a property: insert the char as a literal
                /*
                fragments.addElement(value.substring(pos + 1, pos + 2));
                prev = pos + 2;
                */
                if (value.charAt(pos + 1) == '$') {
                    //backwards compatibility two $ map to one mode
                    fragments.add("$");
                    prev = pos + 2;
                }
                else {
                    //new behaviour: $X maps to $X for all values of X!='$'
                    fragments.add(value.substring(pos, pos + 2));
                    prev = pos + 2;
                }

            }
            else {
                //property found, extract its name or bail on a typo
                final int endName = value.indexOf('}', pos);
                if (endName < 0) {
                    throw new CheckstyleException("Syntax error in property: "
                                                    + value);
                }
                final String propertyName = value.substring(pos + 2, endName);
                fragments.add(null);
                propertyRefs.add(propertyName);
                prev = endName + 1;
            }

            //search for the next instance of $ from the 'prev' position
            pos = value.indexOf('$', prev);
        }
        //no more $ signs found
        //if there is any tail to the file, append it
        if (prev < value.length()) {
            fragments.add(value.substring(prev));
        }
    }

    /**
     * Implements the SAX document handler interfaces, so they do not
     * appear in the public API of the ConfigurationLoader.
     */
    private final class InternalLoader
        extends AbstractLoader {
        /** module elements */
        private static final String MODULE = "module";
        /** name attribute */
        private static final String NAME = "name";
        /** property element */
        private static final String PROPERTY = "property";
        /** value attribute */
        private static final String VALUE = "value";
        /** default attribute */
        private static final String DEFAULT = "default";
        /** name of the severity property */
        private static final String SEVERITY = "severity";
        /** name of the message element */
        private static final String MESSAGE = "message";
        /** name of the message element */
        private static final String METADATA = "metadata";
        /** name of the key attribute */
        private static final String KEY = "key";

        /**
         * Creates a new InternalLoader.
         * @throws SAXException if an error occurs
         * @throws ParserConfigurationException if an error occurs
         */
        public InternalLoader()
            throws SAXException, ParserConfigurationException {
            // super(DTD_PUBLIC_ID_1_1, DTD_RESOURCE_NAME_1_1);
            super(createIdToResourceNameMap());
        }

        @Override
        public void startElement(String namespaceURI,
                                 String localName,
                                 String qName,
                                 Attributes atts)
            throws SAXException {
            if (qName.equals(MODULE)) {
                //create configuration
                final String name = atts.getValue(NAME);
                final DefaultConfiguration conf =
                    new DefaultConfiguration(name);

                if (configuration == null) {
                    configuration = conf;
                }

                //add configuration to it's parent
                if (!configStack.isEmpty()) {
                    final DefaultConfiguration top =
                        configStack.peek();
                    top.addChild(conf);
                }

                configStack.push(conf);
            }
            else if (qName.equals(PROPERTY)) {
                //extract value and name
                final String value;
                try {
                    value = replaceProperties(atts.getValue(VALUE),
                        overridePropsResolver, atts.getValue(DEFAULT));
                }
                catch (final CheckstyleException ex) {
                    throw new SAXException(ex);
                }
                final String name = atts.getValue(NAME);

                //add to attributes of configuration
                final DefaultConfiguration top =
                    configStack.peek();
                top.addAttribute(name, value);
            }
            else if (qName.equals(MESSAGE)) {
                //extract key and value
                final String key = atts.getValue(KEY);
                final String value = atts.getValue(VALUE);

                //add to messages of configuration
                final DefaultConfiguration top = configStack.peek();
                top.addMessage(key, value);
            }
            else {
                if (!qName.equals(METADATA)) {
                    throw new IllegalStateException("Unknown name:" + qName + ".");
                }
            }
        }

        @Override
        public void endElement(String namespaceURI,
                               String localName,
                               String qName) {
            if (qName.equals(MODULE)) {

                final Configuration recentModule =
                    configStack.pop();

                // remove modules with severity ignore if these modules should
                // be omitted
                SeverityLevel level = null;
                try {
                    final String severity = recentModule.getAttribute(SEVERITY);
                    level = SeverityLevel.getInstance(severity);
                }
                catch (final CheckstyleException e) {
                    LOG.debug("Severity not set, ignoring exception", e);
                }

                // omit this module if these should be omitted and the module
                // has the severity 'ignore'
                final boolean omitModule = omitIgnoredModules
                    && level == SeverityLevel.IGNORE;

                if (omitModule && !configStack.isEmpty()) {
                    final DefaultConfiguration parentModule =
                        configStack.peek();
                    parentModule.removeChild(recentModule);
                }
            }
        }
    }
}

////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Loads a configuration from a configuration XML file.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
class ConfigurationLoader
    extends AbstractLoader
{
    /** the public ID for the configuration dtd */
    private static final String DTD_PUBLIC_ID =
        "-//Puppy Crawl//DTD Check Configuration 1.0//EN";

    /** the resource for the configuration dtd */
    private static final String DTD_RESOURCE_NAME =
        "com/puppycrawl/tools/checkstyle/configuration_1_0.dtd";

    /** overriding properties **/
    private final Properties mOverrideProps;
    /** the loaded configurations **/
    private final Stack mConfigStack = new Stack();
    /** the Configuration that is being built */
    private Configuration mConfiguration = null;

    /**
     * Creates a new <code>ConfigurationLoader</code> instance.
     * @param aOverrideProps overriding properties
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     */
    private ConfigurationLoader(Properties aOverrideProps)
        throws ParserConfigurationException, SAXException
    {
        super(DTD_PUBLIC_ID, DTD_RESOURCE_NAME);
        mOverrideProps = aOverrideProps;
    }

    /**
     * Parses the specified file loading the configuration information.
     * @param aFilename the file to parse
     * @throws FileNotFoundException if an error occurs
     * @throws IOException if an error occurs
     * @throws SAXException if an error occurs
     */
    void parseFile(String aFilename)
        throws FileNotFoundException, IOException, SAXException
    {
        parseInputSource(new InputSource(new FileReader(aFilename)));
    }

    ///////////////////////////////////////////////////////////////////////////
    // Document handler methods
    ///////////////////////////////////////////////////////////////////////////

    /** @see org.xml.sax.helpers.DefaultHandler **/
    public void startElement(String aNamespaceURI,
                             String aLocalName,
                             String aQName,
                             Attributes aAtts)
            throws SAXException
    {
        // TODO: debug logging for support puposes
        if (aQName.equals("module")) {
            //create configuration
            final String name = aAtts.getValue("name");
            final DefaultConfiguration conf = new DefaultConfiguration(name);
            if (mConfiguration == null) {
                mConfiguration = conf;
            }

            //add configuration to it's parent
            if (!mConfigStack.isEmpty()) {
                final DefaultConfiguration top =
                        (DefaultConfiguration) mConfigStack.peek();
                top.addChild(conf);
            }

            mConfigStack.push(conf);
        }
        else if (aQName.equals("property")) {
            //extract name and value
            final String name = aAtts.getValue("name");
            final String value;
            try {
                value = replaceProperties(aAtts.getValue("value"),
                    mOverrideProps);
            }
            catch (CheckstyleException ex) {
                throw new SAXException(ex.getMessage());
            }

            //add to attributes of configuration
            final DefaultConfiguration top =
                (DefaultConfiguration) mConfigStack.peek();
            top.addAttribute(name, value);
        }
    }

    /** @see org.xml.sax.helpers.DefaultHandler **/
    public void endElement(String aNamespaceURI,
                           String aLocalName,
                           String aQName)
    {
        if (aQName.equals("module")) {
            mConfigStack.pop();
        }
    }

    /**
     * Returns the check configurations in a specified file.
     * @param aConfigFname name of config file
     * @param aOverrideProps overriding properties
     * @return the check configurations
     * @throws CheckstyleException if an error occurs
     */
    public static Configuration loadConfiguration(String aConfigFname,
                                                  Properties aOverrideProps)
        throws CheckstyleException
    {
        try {
            final ConfigurationLoader loader =
                new ConfigurationLoader(aOverrideProps);
            loader.parseFile(aConfigFname);
            return loader.getConfiguration();
        }
        catch (FileNotFoundException e) {
            throw new CheckstyleException("unable to find " + aConfigFname);
        }
        catch (ParserConfigurationException e) {
            throw new CheckstyleException("unable to parse " + aConfigFname);
        }
        catch (SAXException e) {
            throw new CheckstyleException("unable to parse "
                    + aConfigFname + " - " + e.getMessage());
        }
        catch (IOException e) {
            throw new CheckstyleException("unable to read " + aConfigFname);
        }
    }

    /**
     * Returns the configuration in the last file parsed.
     * @return Configuration object
     */
    private Configuration getConfiguration()
    {
        return mConfiguration;
    }

    /**
     * Replaces <code>${xxx}</code> style constructions in the given value
     * with the string value of the corresponding data types.
     *
     * The method is package visible to facilitate testing.
     *
     * @param aValue The string to be scanned for property references.
     *              May be <code>null</code>, in which case this
     *              method returns immediately with no effect.
     * @param aProps  Mapping (String to String) of property names to their
     *              values. Must not be <code>null</code>.
     *
     * @throws CheckstyleException if the string contains an opening
     *                           <code>${</code> without a closing
     *                           <code>}</code>
     * @return the original string with the properties replaced, or
     *         <code>null</code> if the original string is <code>null</code>.
     *
     * Code copied from ant -
     * http://cvs.apache.org/viewcvs/jakarta-ant/src/main/org/apache/tools/ant/ProjectHelper.java
     */
    static String replaceProperties(
        String aValue, Properties aProps)
            throws CheckstyleException
    {
        if (aValue == null) {
            return null;
        }

        final List fragments = new ArrayList();
        final List propertyRefs = new ArrayList();
        parsePropertyString(aValue, fragments, propertyRefs);

        final StringBuffer sb = new StringBuffer();
        final Iterator i = fragments.iterator();
        final Iterator j = propertyRefs.iterator();
        while (i.hasNext()) {
            String fragment = (String) i.next();
            if (fragment == null) {
                final String propertyName = (String) j.next();
                if (!aProps.containsKey(propertyName)) {
                    throw new CheckstyleException("Property ${"
                        + propertyName + "} has not been set");
                }
                fragment = aProps.getProperty(propertyName);
            }
            sb.append(fragment);
        }

        return sb.toString();
    }

    /**
     * Parses a string containing <code>${xxx}</code> style property
     * references into two lists. The first list is a collection
     * of text fragments, while the other is a set of string property names.
     * <code>null</code> entries in the first list indicate a property
     * reference from the second list.
     *
     * @param aValue     Text to parse. Must not be <code>null</code>.
     * @param aFragments List to add text fragments to.
     *                  Must not be <code>null</code>.
     * @param aPropertyRefs List to add property names to.
     *                     Must not be <code>null</code>.
     *
     * @throws CheckstyleException if the string contains an opening
     *                           <code>${</code> without a closing
     *                           <code>}</code>
     * Code copied from ant -
     * http://cvs.apache.org/viewcvs/jakarta-ant/src/main/org/apache/tools/ant/ProjectHelper.java
     */
    private static void parsePropertyString(String aValue,
                                           List aFragments,
                                           List aPropertyRefs)
        throws CheckstyleException
    {
        int prev = 0;
        int pos;
        //search for the next instance of $ from the 'prev' position
        while ((pos = aValue.indexOf("$", prev)) >= 0) {

            //if there was any text before this, add it as a fragment
            //TODO, this check could be modified to go if pos>prev;
            //seems like this current version could stick empty strings
            //into the list
            if (pos > 0) {
                aFragments.add(aValue.substring(prev, pos));
            }
            //if we are at the end of the string, we tack on a $
            //then move past it
            if (pos == (aValue.length() - 1)) {
                aFragments.add("$");
                prev = pos + 1;
            }
            else if (aValue.charAt(pos + 1) != '{') {
                //peek ahead to see if the next char is a property or not
                //not a property: insert the char as a literal
                /*
                fragments.addElement(value.substring(pos + 1, pos + 2));
                prev = pos + 2;
                */
                if (aValue.charAt(pos + 1) == '$') {
                    //backwards compatibility two $ map to one mode
                    aFragments.add("$");
                    prev = pos + 2;
                }
                else {
                    //new behaviour: $X maps to $X for all values of X!='$'
                    aFragments.add(aValue.substring(pos, pos + 2));
                    prev = pos + 2;
                }

            }
            else {
                //property found, extract its name or bail on a typo
                final int endName = aValue.indexOf('}', pos);
                if (endName < 0) {
                    throw new CheckstyleException("Syntax error in property: "
                                                    + aValue);
                }
                final String propertyName = aValue.substring(pos + 2, endName);
                aFragments.add(null);
                aPropertyRefs.add(propertyName);
                prev = endName + 1;
            }
        }
        //no more $ signs found
        //if there is any tail to the file, append it
        if (prev < aValue.length()) {
            aFragments.add(aValue.substring(prev));
        }
    }

}

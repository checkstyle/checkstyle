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
import java.util.Properties;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

// TODO: Fix the loader so it doesn't validate the document
/**
 * Describe class <code>ConfigurationLoader</code> here.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
class ConfigurationLoader
    extends DefaultHandler
{
    /** overriding properties **/
    private Properties mOverrideProps = new Properties();
    /** parser to read XML files **/
    private final XMLReader mParser;
    /** the loaded configuration **/
    private Stack mConfigStack = new Stack();
    /** the Configuration that is beeing built */
    private Configuration mConfiguration = null;

    /**
     * Creates a new <code>ConfigurationLoader</code> instance.
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     */
    private ConfigurationLoader()
        throws ParserConfigurationException, SAXException
    {
        mParser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        mParser.setContentHandler(this);
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
        mParser.parse(new InputSource(new FileReader(aFilename)));
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
        DefaultConfiguration conf = new DefaultConfiguration(aQName);
        System.out.println("aQName = " + aQName);
        final int attCount = aAtts.getLength();
        for (int i = 0; i < attCount; i++) {
            String name = aAtts.getQName(i);
            String value = aAtts.getValue(i);
            System.out.println("  value of " + name + " is " + value);

            // expand properties
            if (value.startsWith("${") && value.endsWith("}")) {
                String propName = value.substring(2, value.length() - 1);
                value = mOverrideProps.getProperty(propName);
                if (value == null) {
                    throw new SAXException("missing external property " + propName);
                }
                System.out.println("  propName = " + propName);
                System.out.println("  new value = " + value);
            }

            conf.addAttribute(name, value);
        }

        if (mConfiguration == null) {
            mConfiguration = conf;
        }

        if (!mConfigStack.isEmpty()) {
            DefaultConfiguration top = (DefaultConfiguration) mConfigStack.peek();
            top.addChild(conf);
        }

        mConfigStack.push(conf);
    }

    /** @see org.xml.sax.helpers.DefaultHandler **/
    public void endElement(String aNamespaceURI,
                           String aLocalName,
                           String aQName)
    {
        mConfigStack.pop();
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
            final ConfigurationLoader loader = new ConfigurationLoader();
            loader.mOverrideProps = aOverrideProps;
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
}

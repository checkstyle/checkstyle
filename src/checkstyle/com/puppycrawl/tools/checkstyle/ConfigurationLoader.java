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
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Describe class <code>ConfigurationLoader</code> here.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
class ConfigurationLoader
    extends DefaultHandler
{
    /** parser to read XML files */
    private final XMLReader mParser;
    /** the loaded configurations */
    private final List mCheckConfigs = new ArrayList();
    /** the current configuration being created */
    private CheckConfiguration mCurrent;
    /** buffer for collecting text **/
    private final StringBuffer mBuf = new StringBuffer();

    /**
     * Creates a new <code>ConfigurationLoader</code> instance.
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     */
    ConfigurationLoader() throws ParserConfigurationException, SAXException
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
        System.out.println("aFilename = " + aFilename);
        mParser.parse(new InputSource(new FileReader(aFilename)));
    }

    /**
     * Returns the configuration information in the last file parsed.
     * @return list of CheckConfiguration objects
     */
    List getCheckConfigs()
    {
        return mCheckConfigs;
    }


    ///////////////////////////////////////////////////////////////////////////
    // Document handler methods
    ///////////////////////////////////////////////////////////////////////////

    /** @see org.xml.sax.helpers.DefaultHandler **/
    public void startDocument()
        throws SAXException
    {
        mCheckConfigs.clear();
    }

    /** @see org.xml.sax.helpers.DefaultHandler **/
    public void characters(char[] aChars, int aStart, int aLength)
    {
        mBuf.append(String.valueOf(aChars, aStart, aLength));
    }

    /** @see org.xml.sax.helpers.DefaultHandler **/
    public void startElement(String aNamespaceURI,
                             String aLocalName,
                             String aQName,
                             Attributes aAtts)
    {
        mBuf.setLength(0);
        if ("check".equals(aQName)) {
            mCurrent = new CheckConfiguration();
            mCurrent.setClassname(aAtts.getValue("classname"));
        }
        else if ("set-property".equals(aQName)) {
            mCurrent.addProperty(aAtts.getValue("name"),
                                 aAtts.getValue("value"));
        }
    }

    /** @see org.xml.sax.helpers.DefaultHandler **/
    public void endElement(String aNamespaceURI,
                           String aLocalName,
                           String aQName)
    {
        if ("check".equals(aQName)) {
            mCheckConfigs.add(mCurrent);
            mCurrent = null;
        }
        else if ("tokens".equals(aQName)) {
            mCurrent.addTokens(mBuf.toString());
        }
    }


}

////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Contains the common implementation of a loader, for loading a configuration
 * from an XML file.
 * <p>
 * The error handling policy can be described as being austere, dead set,
 * disciplinary, dour, draconian, exacting, firm, forbidding, grim, hard, hard-
 * boiled, harsh, harsh, in line, iron-fisted, no-nonsense, oppressive,
 * persnickety, picky, prudish, punctilious, puritanical, rigid, rigorous,
 * scrupulous, set, severe, square, stern, stickler, straight, strait-laced,
 * stringent, stuffy, stuffy, tough, unpermissive, unsparing and uptight.
 *
 * @author Oliver Burn
 */
public abstract class AbstractLoader
    extends DefaultHandler
{
    /** the public id to resolve */
    private final String mPublicId;
    /** the resource name for the DTD */
    private final String mDtdResourceName;
    /** parser to read XML files **/
    private final XMLReader mParser;

    /**
     * Creates a new instance.
     * @param aPublicId the public ID for the DTD to resolve
     * @param aDtdResourceName the resource for the DTD
     * @throws SAXException if an error occurs
     * @throws ParserConfigurationException if an error occurs
     */
    protected AbstractLoader(String aPublicId, String aDtdResourceName)
        throws SAXException, ParserConfigurationException
    {
        mPublicId = aPublicId;
        mDtdResourceName = aDtdResourceName;
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        mParser = factory.newSAXParser().getXMLReader();
        mParser.setContentHandler(this);
        mParser.setEntityResolver(this);
        mParser.setErrorHandler(this);
    }

    /**
     * Parses the specified input source.
     * @param aInputSource the input source to parse.
     * @throws IOException if an error occurs
     * @throws SAXException in an error occurs
     */
    protected void parseInputSource(InputSource aInputSource)
        throws IOException, SAXException
    {
        mParser.parse(aInputSource);
    }

    /** {@inheritDoc} */
    public InputSource resolveEntity(String aPublicId, String aSystemId)
        throws SAXException
    {
        if (mPublicId.equals(aPublicId)) {
            final InputStream dtdIS = getClass().getClassLoader()
                .getResourceAsStream(mDtdResourceName);
            if (dtdIS == null) {
                throw new SAXException(
                    "Unable to load internal dtd " + mDtdResourceName);
            }
            return new InputSource(dtdIS);
        }
        // This is a hack to workaround problem with SAX
        // DefaultHeader.resolveEntity():
        // sometimes it throws SAX- and IO- exceptions
        // sometime SAX only :(
        try {
            if (false) {
                throw new IOException("");
            }
            return super.resolveEntity(aPublicId, aSystemId);
        }
        catch (IOException e) {
            throw new SAXException("" + e, e);
        }
    }

    /** @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException) */
    public void warning(SAXParseException aEx) throws SAXException
    {
        throw aEx;
    }

    /** @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException) */
    public void error(SAXParseException aEx) throws SAXException
    {
        throw aEx;
    }

    /** @see org.xml.sax.ErrorHandler#fatalError */
    public void fatalError(SAXParseException aEx) throws SAXException
    {
        throw aEx;
    }
}

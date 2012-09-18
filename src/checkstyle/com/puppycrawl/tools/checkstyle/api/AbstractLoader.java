////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.api;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
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
    /** maps public id to resolve to esource name for the DTD */
    private final Map<String, String> mPublicIdToResourceNameMap;
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
        this(new HashMap<String, String>(1));
        mPublicIdToResourceNameMap.put(aPublicId, aDtdResourceName);
    }

    /**
     * Creates a new instance.
     * @param aPublicIdToResourceNameMap maps public IDs to DTD resource names
     * @throws SAXException if an error occurs
     * @throws ParserConfigurationException if an error occurs
     */
    protected AbstractLoader(Map<String, String> aPublicIdToResourceNameMap)
        throws SAXException, ParserConfigurationException
    {
        mPublicIdToResourceNameMap =
            Maps.newHashMap(aPublicIdToResourceNameMap);
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);
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
    public void parseInputSource(InputSource aInputSource)
        throws IOException, SAXException
    {
        mParser.parse(aInputSource);
    }

    @Override
    public InputSource resolveEntity(String aPublicId, String aSystemId)
        throws SAXException, IOException
    {
        if (mPublicIdToResourceNameMap.keySet().contains(aPublicId)) {
            final String dtdResourceName =
                    mPublicIdToResourceNameMap.get(aPublicId);
            final ClassLoader loader =
                this.getClass().getClassLoader();
            final InputStream dtdIS =
                loader.getResourceAsStream(dtdResourceName);
            if (dtdIS == null) {
                throw new SAXException(
                    "Unable to load internal dtd " + dtdResourceName);
            }
            return new InputSource(dtdIS);
        }
        return super.resolveEntity(aPublicId, aSystemId);
    }

    @Override
    public void warning(SAXParseException aEx) throws SAXException
    {
        throw aEx;
    }

    @Override
    public void error(SAXParseException aEx) throws SAXException
    {
        throw aEx;
    }

    @Override
    public void fatalError(SAXParseException aEx) throws SAXException
    {
        throw aEx;
    }
}

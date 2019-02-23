////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
 * </p>
 *
 * @noinspection ThisEscapedInObjectConstruction
 */
public class XmlLoader
    extends DefaultHandler {

    /** Maps public id to resolve to resource name for the DTD. */
    private final Map<String, String> publicIdToResourceNameMap;
    /** Parser to read XML files. **/
    private final XMLReader parser;

    /**
     * Creates a new instance.
     * @param publicIdToResourceNameMap maps public IDs to DTD resource names
     * @throws SAXException if an error occurs
     * @throws ParserConfigurationException if an error occurs
     */
    protected XmlLoader(Map<String, String> publicIdToResourceNameMap)
            throws SAXException, ParserConfigurationException {
        this.publicIdToResourceNameMap = new HashMap<>(publicIdToResourceNameMap);
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        LoadExternalDtdFeatureProvider.setFeaturesBySystemProperty(factory);
        factory.setValidating(true);
        parser = factory.newSAXParser().getXMLReader();
        parser.setContentHandler(this);
        parser.setEntityResolver(this);
        parser.setErrorHandler(this);
    }

    /**
     * Parses the specified input source.
     * @param inputSource the input source to parse.
     * @throws IOException if an error occurs
     * @throws SAXException in an error occurs
     */
    public void parseInputSource(InputSource inputSource)
            throws IOException, SAXException {
        parser.parse(inputSource);
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException {
        final InputSource inputSource;
        if (publicIdToResourceNameMap.keySet().contains(publicId)) {
            final String dtdResourceName =
                    publicIdToResourceNameMap.get(publicId);
            final ClassLoader loader =
                getClass().getClassLoader();
            final InputStream dtdIs =
                loader.getResourceAsStream(dtdResourceName);

            inputSource = new InputSource(dtdIs);
        }
        else {
            inputSource = super.resolveEntity(publicId, systemId);
        }
        return inputSource;
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        throw exception;
    }

    /**
     * Used for setting specific for secure java installations features to SAXParserFactory.
     * Pulled out as a separate class in order to suppress Pitest mutations.
     */
    public static final class LoadExternalDtdFeatureProvider {

        /** System property name to enable external DTD load. */
        public static final String ENABLE_EXTERNAL_DTD_LOAD = "checkstyle.enableExternalDtdLoad";

        /** Feature that enables loading external DTD when loading XML files. */
        private static final String LOAD_EXTERNAL_DTD =
                "http://apache.org/xml/features/nonvalidating/load-external-dtd";
        /** Feature that enables including external general entities in XML files. */
        private static final String EXTERNAL_GENERAL_ENTITIES =
                "http://xml.org/sax/features/external-general-entities";

        /** Stop instances being created. **/
        private LoadExternalDtdFeatureProvider() {
        }

        /**
         * Configures SAXParserFactory with features required
         * to use external DTD file loading, this is not activated by default to no allow
         * usage of schema files that checkstyle do not know
         * it is even security problem to allow files from outside.
         * @param factory factory to be configured with special features
         * @throws SAXException if an error occurs
         * @throws ParserConfigurationException if an error occurs
         */
        public static void setFeaturesBySystemProperty(SAXParserFactory factory)
                throws SAXException, ParserConfigurationException {

            final boolean enableExternalDtdLoad = Boolean.valueOf(
                System.getProperty(ENABLE_EXTERNAL_DTD_LOAD, "false"));

            factory.setFeature(LOAD_EXTERNAL_DTD, enableExternalDtdLoad);
            factory.setFeature(EXTERNAL_GENERAL_ENTITIES, enableExternalDtdLoad);
        }

    }

}

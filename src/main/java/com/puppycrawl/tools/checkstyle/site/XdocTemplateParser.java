///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.site;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTML.Attribute;

import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.macro.manager.MacroNotFoundException;
import org.apache.maven.doxia.markup.HtmlMarkup;
import org.apache.maven.doxia.module.xdoc.XdocMarkup;
import org.apache.maven.doxia.module.xdoc.XdocParser;
import org.apache.maven.doxia.parser.AbstractXmlParser;
import org.apache.maven.doxia.parser.ParseException;
import org.apache.maven.doxia.parser.Parser;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.xml.pull.XmlPullParser;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

@Component(role = Parser.class, hint = "xdoc-template")
public class XdocTemplateParser extends AbstractXmlParser {
    /**
     * The source content of the input reader. Used to pass into macros.
     */
    private String sourceContent;

    /**
     * Empty elements don't write a closing tag.
     */
    private boolean isEmptyElement;

    /**
     * A macro name.
     */
    private String macroName;

    /**
     * The macro parameters.
     */
    private Map<String, Object> macroParameters = new HashMap<>();

    @Override
    public void parse(Reader source, Sink sink) throws ParseException {
        sourceContent = null;

        try (Reader reader = source) {
            StringWriter contentWriter = new StringWriter();
            reader.transferTo(contentWriter);
            sourceContent = contentWriter.toString();
        } catch (IOException ex) {
            throw new ParseException("Error reading the input source", ex);
        }

        try {
            super.parse(new StringReader(sourceContent), sink);
        } finally {
            sourceContent = null;
        }
    }

    @Override
    protected void handleStartTag(XmlPullParser parser, Sink sink)
            throws MacroExecutionException, XmlPullParserException {
        isEmptyElement = parser.isEmptyElementTag();

        if (parser.getName().equals(XdocMarkup.MACRO_TAG.toString())) {
            handleMacroStart(parser);
        }
        else if (parser.getName().equals(HtmlMarkup.PARAM.toString())) {
            handleParamStart(parser, sink);
        }
        else {
            if (isEmptyElement) {
                handleUnknown(parser, sink, HtmlMarkup.TAG_TYPE_SIMPLE);
            }
            else {
                handleUnknown(parser, sink, HtmlMarkup.TAG_TYPE_START);
            }
        }
    }

    @Override
    protected void handleEndTag(XmlPullParser parser, Sink sink) throws MacroExecutionException {
        if (parser.getName().equals(XdocMarkup.MACRO_TAG.toString())) {
            handleMacroEnd(sink);
        }
        else {
            if (!isEmptyElement) {
                handleUnknown(parser, sink, HtmlMarkup.TAG_TYPE_END);
            }
        }

        isEmptyElement = false;
    }

    private void handleMacroStart(XmlPullParser parser) throws MacroExecutionException {
        if (!isSecondParsing()) {
            macroName = parser.getAttributeValue(null, Attribute.NAME.toString());

            if (macroParameters == null) {
                macroParameters = new HashMap<>();
            }

            if (macroName == null || macroName.isEmpty()) {
                throw new MacroExecutionException("The '" + Attribute.NAME + "' attribute for the '"
                        + XdocMarkup.MACRO_TAG + "' tag is required.");
            }
        }
    }

    private void handleParamStart(XmlPullParser parser, Sink sink) throws MacroExecutionException {
        if (!isSecondParsing()) {
            if (macroName != null && !macroName.isEmpty()) {
                String paramName = parser.getAttributeValue(null, Attribute.NAME.toString());
                String paramValue = parser.getAttributeValue(null, Attribute.VALUE.toString());

                if (paramName == null || paramName.isEmpty() || paramValue == null || paramValue.isEmpty()) {
                    throw new MacroExecutionException(
                            "'" + Attribute.NAME + "' and '" + Attribute.VALUE
                                    + "' attributes for the '" + HtmlMarkup.PARAM.toString() + "' tag are required inside the '"
                                    + XdocMarkup.MACRO_TAG + "' tag.");
                }

                macroParameters.put(paramName, paramValue);
            }
        }
    }

    private void handleMacroEnd(Sink sink) throws MacroExecutionException {
        if (!isSecondParsing() && macroName != null && !macroName.isEmpty()) {
            MacroRequest request = new MacroRequest(sourceContent, new XdocParser(), macroParameters, getBasedir());

            try {
                executeMacro(macroName, request, sink);
            }
            catch (MacroNotFoundException me) {
                throw new MacroExecutionException("Macro not found: " + macroName, me);
            }
        }

        // Reinit macro
        macroName = null;
        macroParameters = null;
    }
}

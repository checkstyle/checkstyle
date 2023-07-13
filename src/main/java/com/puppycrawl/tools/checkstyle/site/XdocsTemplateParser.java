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

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.text.html.HTML.Attribute;

import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.macro.manager.MacroNotFoundException;
import org.apache.maven.doxia.module.xdoc.XdocParser;
import org.apache.maven.doxia.parser.ParseException;
import org.apache.maven.doxia.parser.Parser;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.xml.pull.XmlPullParser;

/**
 * Parser for Checkstyle's xdoc templates.
 * This parser is responsible for generating xdocs({@code .xml}) from the xdoc
 * templates({@code .xml.template}). The templates are regular xdocs with custom
 * macros for generating dynamic content - properties, examples, etc.
 * This parser behaves just like the {@link XdocParser} with the difference that all
 * elements apart from the {@code macro} element are copied as is to the output.
 *
 * @see ExampleMacro
 */
@Component(role = Parser.class, hint = "xdocs-template")
public class XdocsTemplateParser extends XdocParser {

    /** The source content of the input reader. Used to pass into macros. */
    private String sourceContent;

    /** A macro name. */
    private String macroName;

    /** The macro parameters. */
    private Map<String, Object> macroParameters = new HashMap<>();

    @Override
    public void parse(Reader source, Sink sink, String reference) throws ParseException {
        sourceContent = null;

        try (Reader reader = source) {
            final StringWriter contentWriter = new StringWriter();
            IOUtil.copy(reader, contentWriter);
            sourceContent = contentWriter.toString();
            super.parse(new StringReader(sourceContent), sink, reference);
        }
        catch (IOException ioException) {
            throw new ParseException("Error reading the input source", ioException);
        }
        finally {
            sourceContent = null;
        }
    }

    @Override
    protected void handleStartTag(XmlPullParser parser, Sink sink) throws MacroExecutionException {
        if (parser.getName().equals(DOCUMENT_TAG.toString())) {
            sink.body();
            sink.rawText(parser.getText());
        }
        else if (parser.getName().equals(MACRO_TAG.toString())) {
            processMacroStart(parser);
            setIgnorableWhitespace(true);
        }
        else if (parser.getName().equals(PARAM.toString())) {
            processParamStart(parser, sink);
        }
        else {
            sink.rawText(parser.getText());
        }
    }

    @Override
    protected void handleEndTag(XmlPullParser parser, Sink sink) throws MacroExecutionException {
        if (parser.getName().equals(DOCUMENT_TAG.toString())) {
            sink.rawText(parser.getText());
            sink.body_();
        }
        else if (parser.getName().equals(MACRO_TAG.toString())) {
            processMacroEnd(sink);
            setIgnorableWhitespace(false);
        }
        else if (!parser.getName().equals(PARAM.toString())) {
            sink.rawText(parser.getText());
        }
    }

    /**
     * Execute a macro. Creates a {@link MacroRequest} with the gathered
     * {@link #macroName} and {@link #macroParameters} and executes the macro.
     * Afterward, the macro fields are reinitialized.
     *
     * @param sink the sink object.
     * @throws MacroExecutionException if a macro is not found.
     */
    // -@cs[ReturnCount] It's a guard clause.
    private void processMacroEnd(Sink sink) throws MacroExecutionException {
        if (macroName == null || macroName.isEmpty() || isSecondParsing()) {
            return;
        }

        final MacroRequest request = new MacroRequest(sourceContent,
                new XdocsTemplateParser(), macroParameters,
                new File(System.getProperty("user.dir")));

        try {
            executeMacro(macroName, request, sink);
        }
        catch (MacroNotFoundException exception) {
            throw new MacroExecutionException("Macro not found: " + macroName, exception);
        }

        reinitializeMacroFields();
    }

    /**
     * Reinitialize the macro fields.
     */
    private void reinitializeMacroFields() {
        macroName = null;
        macroParameters = null;
    }

    /**
     * Handle the opening tag of a macro. Gather the macro name and parameters.
     *
     * @param parser the xml parser.
     * @throws MacroExecutionException if the macro name is not specified.
     */
    // -@cs[ReturnCount] It's a guard clause.
    private void processMacroStart(XmlPullParser parser) throws MacroExecutionException {
        if (isSecondParsing()) {
            return;
        }

        macroName = parser.getAttributeValue(null, Attribute.NAME.toString());

        if (macroParameters == null) {
            macroParameters = new HashMap<>();
        }

        if (macroName == null || macroName.isEmpty()) {
            throw new MacroExecutionException(String.format(Locale.ROOT,
                    "The '%s' attribute for the '%s' tag is required.",
                    Attribute.NAME, MACRO_TAG));
        }
    }

    /**
     * Handle the opening tag of a parameter. Gather the parameter name and value.
     *
     * @param parser the xml parser.
     * @param sink the sink object.
     * @throws MacroExecutionException if the parameter name or value is not specified.
     */
    // -@cs[ReturnCount] It's a guard clause.
    private void processParamStart(XmlPullParser parser, Sink sink) throws MacroExecutionException {
        if (isSecondParsing()) {
            return;
        }

        if (macroName != null && !macroName.isEmpty()) {
            final String paramName = parser
                    .getAttributeValue(null, Attribute.NAME.toString());
            final String paramValue = parser
                    .getAttributeValue(null, Attribute.VALUE.toString());

            if (paramName == null
                    || paramValue == null
                    || paramName.isEmpty()
                    || paramValue.isEmpty()) {
                throw new MacroExecutionException(String.format(Locale.ROOT,
                        "'%s' and '%s' attributes for the '%s' tag are required"
                                + "inside the '%s' tag.",
                        Attribute.NAME, Attribute.VALUE, PARAM, MACRO_TAG));
            }

            macroParameters.put(paramName, paramValue);
        }
        else {
            handleUnknown(parser, sink, TAG_TYPE_START);
        }
    }
}

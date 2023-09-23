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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;

import javax.swing.text.MutableAttributeSet;

import org.apache.maven.doxia.markup.HtmlMarkup;
import org.apache.maven.doxia.module.xdoc.XdocSink;
import org.apache.maven.doxia.sink.SinkEventAttributes;
import org.apache.maven.doxia.sink.impl.SinkEventAttributeSet;
import org.codehaus.plexus.util.xml.PrettyPrintXMLWriter;

/**
 * A sink for Checkstyle's xdoc templates.
 * This module will be removed once
 * <a href="https://github.com/checkstyle/checkstyle/issues/13426">#13426</a> is resolved.
 *
 * @see <a href="https://maven.apache.org/doxia/doxia/doxia-sink-api">Doxia Sink API</a>
 */
public class XdocsTemplateSink extends XdocSink {

    /** Encoding of the writer. */
    private final String encoding;

    /** The PrintWriter to write the result. */
    private final PrintWriter writer;

    /**
     * The stack of StringWriter to write the table result temporary,
     * so we could play with the output DOXIA-177.
     */
    private final LinkedList<StringWriter> tableContentWriterStack;

    /** Stack for Pretty formatted XML. */
    private final LinkedList<PrettyPrintXMLWriter> tableCaptionXmlWriterStack;

    /** Linux Style End of Line. */
    private final String endOfLine = "\n";

    /**
     * Create a new instance, initialize the Writer.
     *
     * @param writer not null writer to write the result.
     * @param encoding encoding of the writer.
     */
    public XdocsTemplateSink(Writer writer, String encoding) {
        super(writer);
        this.encoding = encoding;
        this.writer = new PrintWriter(writer);
        this.tableContentWriterStack = new LinkedList<>();
        this.tableCaptionXmlWriterStack = new LinkedList<>();
    }

    /**
     * Parses the given String and replaces all occurrences of
     * '\n', '\r' and '\r\n' with the Linux system EOL. All Sinks should
     * make sure that text output is filtered through this method.
     *
     * @param text the text to scan. May be null in which case null is returned.
     * @return a String that contains only Linux System EOLs.
     */
    protected String unifyToLinuxEndOfLine(String text) {
        final int length = text.length();
        final StringBuilder buffer = new StringBuilder(length);

        for (int id = 0; id < length; id++) {
            if (text.charAt(id) == '\r') {
                if ((id + 1) < length && text.charAt(id + 1) == '\n') {
                    id++;
                }
                buffer.append(this.endOfLine);
            }
            else if (text.charAt(id) == '\n') {
                buffer.append(this.endOfLine);
            }
            else {
                buffer.append(text.charAt(id));
            }
        }
        return buffer.toString();
    }

    @Override
    protected void write(String text) {
        String finalText = null;
        
        if(text != null) {
            finalText = unifyToLinuxEndOfLine(text);
        }

        if (!this.tableCaptionXmlWriterStack.isEmpty()
            && this.tableCaptionXmlWriterStack.getLast() != null) {
            this.tableCaptionXmlWriterStack.getLast().writeMarkup(finalText);
        }
        else if (!this.tableContentWriterStack.isEmpty()
                && this.tableContentWriterStack.getLast() != null) {
            this.tableContentWriterStack.getLast().write(finalText);
        }
        else {
            writer.write(finalText);
        }
    }

    /**
     * Writes a Linux endOfLine.
     */
    @Override
    protected void writeEOL() {
        write(this.endOfLine);
    }

    /**
     * Place the XML declaration at the top of the file.
     */
    @Override
    public void body() {
        write("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>");
        writeEOL();
    }

    /**
     * Place a newline at the end of the file, flush the writer, and reset the sink.
     */
    @Override
    public void body_() {
        writeEOL();
        flush();
        init();
    }

    /**
     * Write an external link. We override this method because the default implementation
     * adds a {@code class="external-link"} attribute to the link which we don't want.
     *
     * @param href the link.
     */
    @Override
    public void link(String href) {
        final MutableAttributeSet attributes = new SinkEventAttributeSet();
        attributes.addAttribute(SinkEventAttributes.HREF, href);
        writeStartTag(HtmlMarkup.A, attributes);
    }

    /**
     * Write a table row tag. We override this method because the default implementation
     * adds a {@code align="top"} attribute to the row which we don't want.
     */
    @Override
    public void tableRow() {
        writeStartTag(TR);
    }

    /**
     * Write a table tag. We override this method because the default implementation
     * adds different attributes which we don't want. We ignore the parameters
     * because we don't need them, but the default implementation will take them
     * into account once this class is removed.
     *
     * @param justification ignored
     * @param grid ignored
     */
    @Override
    public void tableRows(int[] justification, boolean grid) {
        writeStartTag(HtmlMarkup.TABLE);
    }
}

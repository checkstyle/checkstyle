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

import java.io.Writer;

import javax.swing.text.MutableAttributeSet;

import org.apache.maven.doxia.markup.HtmlMarkup;
import org.apache.maven.doxia.module.xdoc.XdocSink;
import org.apache.maven.doxia.sink.SinkEventAttributes;
import org.apache.maven.doxia.sink.impl.SinkEventAttributeSet;

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

    /**
     * Create a new instance, initialize the Writer.
     *
     * @param writer not null writer to write the result.
     * @param encoding encoding of the writer.
     */
    public XdocsTemplateSink(Writer writer, String encoding) {
        super(writer);
        this.encoding = encoding;
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
}

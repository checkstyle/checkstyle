////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.site;

import java.io.Writer;

import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.sink.SinkFactory;
import org.apache.maven.doxia.sink.impl.AbstractTextSinkFactory;
import org.codehaus.plexus.component.annotations.Component;

/**
 * Xdoc template implementation of the {@link SinkFactory}.
 * This module will be removed once
 * <a href="https://github.com/checkstyle/checkstyle/issues/13426">#13426</a> is resolved.
 */
@Component(role = SinkFactory.class, hint = "xdocs-template")
public class XdocsTemplateSinkFactory extends AbstractTextSinkFactory {

    /**
     * Create a Sink instance.
     *
     * @param writer writer to use.
     * @param encoding encoding of the writer.
     * @return Sink instance.
     */
    @Override
    public Sink createSink(Writer writer, String encoding) {
        return new XdocsTemplateSink(writer, encoding);
    }
}

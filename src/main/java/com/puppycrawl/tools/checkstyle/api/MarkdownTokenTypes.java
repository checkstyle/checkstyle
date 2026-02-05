///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.grammar.markdown.MarkdownLexer;

/**
 * Contains the constants for all the tokens contained in the Abstract
 * Syntax Tree for the Markdown grammar (/// comments).
 *
 * <p>This follows the same pattern as {@link JavadocTokenTypes}.</p>
 */
public final class MarkdownTokenTypes {

    /**
     * Root node of any Markdown comment content (synthetic, like JAVADOC_CONTENT).
     *
     * <p><b>Example tree for:</b></p>
     * <pre>{@code /// This is plain text.}</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * MARKDOWN_CONTENT -> (root)
     * `--TEXT -> This is plain text.
     * }</pre>
     */
    public static final int MARKDOWN_CONTENT = MarkdownLexer.MARKDOWN;

    /**
     * Plain text content within a Markdown comment.
     *
     * <p>This node represents any plain text, including spaces.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code /// This is plain text.}</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * |--MARKDOWN_COMMENT -> ///
     * |   `--COMMENT_CONTENT -> (parsed)
     * |       `--MARKDOWN_CONTENT -> (root)
     * |           `--TEXT -> This is plain text.
     * }</pre>
     *
     * @see #MARKDOWN_CONTENT
     */
    public static final int TEXT = MarkdownLexer.TEXT;

    /**
     * Start delimiter for inline code spans (e.g., '[' in [text]).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code /// [ConcurrentHashMap]}</pre>
     */
    public static final int CODE_SPAN_START = MarkdownLexer.CODE_SPAN_START;

    /**
     * End delimiter for inline code spans (e.g., ']' in [text]).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code /// [ConcurrentHashMap]}</pre>
     */
    public static final int CODE_SPAN_END = MarkdownLexer.CODE_SPAN_END;

    /** Empty private constructor of the current class. */
    private MarkdownTokenTypes() {
    }
}

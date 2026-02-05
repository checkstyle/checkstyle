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

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.MarkdownTokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl;
import com.puppycrawl.tools.checkstyle.grammar.markdown.MarkdownParser;
import com.puppycrawl.tools.checkstyle.grammar.markdown.MarkdownParserBaseVisitor;

/**
 * Visitor for converting ANTLR Markdown parse tree to Checkstyle DetailNode tree.
 */
public class MarkdownAstVisitor extends MarkdownParserBaseVisitor<DetailNode> {
    @Override
    public DetailNode visitDocument(MarkdownParser.DocumentContext ctx) {
        final JavadocNodeImpl root = new JavadocNodeImpl();
        root.setType(MarkdownTokenTypes.MARKDOWN_CONTENT);
        for (org.antlr.v4.runtime.tree.ParseTree child : ctx.children) {
            final DetailNode childNode = visit(child);
            if (childNode != null) {
                root.addChild(childNode);
            }
        }
        return root;
    }

    @Override
    public DetailNode visitCodeSpan(MarkdownParser.CodeSpanContext ctx) {
        final JavadocNodeImpl node = new JavadocNodeImpl();
        node.setType(MarkdownTokenTypes.CODE_SPAN_START);
        final JavadocNodeImpl textChild = new JavadocNodeImpl();
        textChild.setType(MarkdownTokenTypes.TEXT);
        textChild.setText(ctx.text().getText());
        node.addChild(textChild);
        return node;
    }

    @Override
    public DetailNode visitText(MarkdownParser.TextContext ctx) {
        final JavadocNodeImpl node = new JavadocNodeImpl();
        node.setType(MarkdownTokenTypes.TEXT);
        final StringBuilder fullText = new StringBuilder();
        for (org.antlr.v4.runtime.tree.TerminalNode text : ctx.TEXT()) {
            fullText.append(text.getText());
        }
        node.setText(fullText.toString());
        return node;
    }
}

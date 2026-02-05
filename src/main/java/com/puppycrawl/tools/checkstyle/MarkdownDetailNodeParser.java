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

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.MarkdownUtil;
import com.puppycrawl.tools.checkstyle.grammar.markdown.MarkdownLexer;
import com.puppycrawl.tools.checkstyle.grammar.markdown.MarkdownParser;

/**
 * Parser for converting Markdown comment content to DetailNode tree.
 */
public class MarkdownDetailNodeParser {

    /**
     * Parses Markdown comment content from AST node.
     *
     * @param ast the AST node containing Markdown comment
     * @return ParseResult containing DetailNode tree or parse error
     */
    public ParseResult parseMarkdownAsDetailNode(DetailAST ast) {
        final ParseResult result;
        final String content = MarkdownUtil.getMarkdownContent(ast);
        if (content == null) {
            result = new ParseResult(null, null);
        }
        else {
            final MarkdownLexer lexer = new MarkdownLexer(
                    org.antlr.v4.runtime.CharStreams.fromString(content));
            final org.antlr.v4.runtime.CommonTokenStream tokens =
                    new org.antlr.v4.runtime.CommonTokenStream(lexer);
            final MarkdownParser parser = new MarkdownParser(tokens);
            final MarkdownParser.DocumentContext tree = parser.document();
            final MarkdownAstVisitor visitor = new MarkdownAstVisitor();
            final DetailNode detailNodeTree = visitor.visit(tree);
            result = new ParseResult(detailNodeTree, null);
        }
        return result;
    }

    /**
     * Result of parsing Markdown content.
     */
    public static class ParseResult {
        /** The parsed DetailNode tree. */
        private final DetailNode ast;
        /** Any parse error that occurred. */
        private final CheckstyleException parseError;

        /**
         * Creates a new ParseResult.
         *
         * @param ast the parsed DetailNode tree
         * @param parseError any parse error that occurred
         */
        public ParseResult(DetailNode ast, CheckstyleException parseError) {
            this.ast = ast;
            this.parseError = parseError;
        }

        /**
         * Gets the parsed AST.
         *
         * @return the DetailNode tree
         */
        public DetailNode getAst() {
            return ast;
        }

        /**
         * Gets any parse error.
         *
         * @return the parse error, or null if none
         */
        public CheckstyleException getParseError() {
            return parseError;
        }
    }
}

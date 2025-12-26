///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl;
import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocCommentsLexer;
import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocCommentsParser;
import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocCommentsParserBaseVisitor;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * Visitor class used to build Checkstyle's Javadoc AST from the parse tree
 * produced by {@link JavadocCommentsParser}. Each overridden {@code visit...}
 * method visits children of a parse tree node (subrules) or creates terminal
 * nodes (tokens), and returns a {@link JavadocNodeImpl} subtree as the result.
 *
 * <p>
 * The order of {@code visit...} methods in {@code JavaAstVisitor.java} and production rules in
 * {@code JavaLanguageParser.g4} should be consistent to ease maintenance.
 * </p>
 *
 * @see JavadocCommentsLexer
 * @see JavadocCommentsParser
 * @see JavadocNodeImpl
 * @see JavaAstVisitor
 */
public class JavadocCommentsAstVisitor extends JavadocCommentsParserBaseVisitor<JavadocNodeImpl> {

    /**
     * All Javadoc tag token types.
     */
    private static final Set<Integer> JAVADOC_TAG_TYPES = Set.of(
        JavadocCommentsLexer.CODE,
        JavadocCommentsLexer.LINK,
        JavadocCommentsLexer.LINKPLAIN,
        JavadocCommentsLexer.VALUE,
        JavadocCommentsLexer.INHERIT_DOC,
        JavadocCommentsLexer.SUMMARY,
        JavadocCommentsLexer.SYSTEM_PROPERTY,
        JavadocCommentsLexer.INDEX,
        JavadocCommentsLexer.RETURN,
        JavadocCommentsLexer.LITERAL,
        JavadocCommentsLexer.SNIPPET,
        JavadocCommentsLexer.CUSTOM_NAME,
        JavadocCommentsLexer.AUTHOR,
        JavadocCommentsLexer.DEPRECATED,
        JavadocCommentsLexer.PARAM,
        JavadocCommentsLexer.THROWS,
        JavadocCommentsLexer.EXCEPTION,
        JavadocCommentsLexer.SINCE,
        JavadocCommentsLexer.VERSION,
        JavadocCommentsLexer.SEE,
        JavadocCommentsLexer.LITERAL_HIDDEN,
        JavadocCommentsLexer.USES,
        JavadocCommentsLexer.PROVIDES,
        JavadocCommentsLexer.SERIAL,
        JavadocCommentsLexer.SERIAL_DATA,
        JavadocCommentsLexer.SERIAL_FIELD
    );

    /**
     * Line number of the Block comment AST that is being parsed.
     */
    private final int blockCommentLineNumber;

    /**
     * Javadoc Ident.
     */
    private final int javadocColumnNumber;

    /**
     * Token stream to check for hidden tokens.
     */
    private final BufferedTokenStream tokens;

    /**
     * A set of token indices used to track which tokens have already had their
     * hidden tokens added to the AST.
     */
    private final Set<Integer> processedTokenIndices = new HashSet<>();

    /**
     * Accumulator for consecutive TEXT tokens.
     * This is used to merge multiple TEXT tokens into a single node.
     */
    private final TextAccumulator accumulator = new TextAccumulator();

    /**
     * The first non-tight HTML tag encountered in the Javadoc comment, if any.
     */
    private DetailNode firstNonTightHtmlTag;

    /**
     * Constructs a JavaAstVisitor with given token stream, line number, and column number.
     *
     * @param tokens the token stream to check for hidden tokens
     * @param blockCommentLineNumber the line number of the block comment being parsed
     * @param javadocColumnNumber the column number of the javadoc indent
     */
    public JavadocCommentsAstVisitor(CommonTokenStream tokens,
                                     int blockCommentLineNumber, int javadocColumnNumber) {
        this.tokens = tokens;
        this.blockCommentLineNumber = blockCommentLineNumber;
        this.javadocColumnNumber = javadocColumnNumber;
    }

    @Override
    public JavadocNodeImpl visitJavadoc(JavadocCommentsParser.JavadocContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.JAVADOC_CONTENT, ctx);
    }

    @Override
    public JavadocNodeImpl visitMainDescription(JavadocCommentsParser.MainDescriptionContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitBlockTag(JavadocCommentsParser.BlockTagContext ctx) {
        final JavadocNodeImpl blockTagNode =
                createImaginary(JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG);
        final ParseTree tag = ctx.getChild(0);

        if (tag instanceof ParserRuleContext context) {
            final Token tagName = (Token) context.getChild(1).getPayload();
            final var tokenType = tagName.getType();

            final JavadocNodeImpl specificTagNode = switch (tokenType) {
                case JavadocCommentsLexer.AUTHOR ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.AUTHOR_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.DEPRECATED ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.DEPRECATED_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.RETURN ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.RETURN_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.PARAM ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.PARAM_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.THROWS ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.THROWS_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.EXCEPTION ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.SINCE ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.SINCE_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.VERSION ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.VERSION_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.SEE ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.SEE_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.LITERAL_HIDDEN ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.HIDDEN_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.USES ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.USES_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.PROVIDES ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.PROVIDES_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.SERIAL ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.SERIAL_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.SERIAL_DATA ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.SERIAL_DATA_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.SERIAL_FIELD ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.SERIAL_FIELD_BLOCK_TAG, ctx);
                default ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.CUSTOM_BLOCK_TAG, ctx);
            };
            blockTagNode.addChild(specificTagNode);
        }

        return blockTagNode;
    }

    @Override
    public JavadocNodeImpl visitAuthorTag(JavadocCommentsParser.AuthorTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitDeprecatedTag(JavadocCommentsParser.DeprecatedTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitReturnTag(JavadocCommentsParser.ReturnTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitParameterTag(JavadocCommentsParser.ParameterTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitThrowsTag(JavadocCommentsParser.ThrowsTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitExceptionTag(JavadocCommentsParser.ExceptionTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSinceTag(JavadocCommentsParser.SinceTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitVersionTag(JavadocCommentsParser.VersionTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSeeTag(JavadocCommentsParser.SeeTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitHiddenTag(JavadocCommentsParser.HiddenTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitUsesTag(JavadocCommentsParser.UsesTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitProvidesTag(JavadocCommentsParser.ProvidesTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSerialTag(JavadocCommentsParser.SerialTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSerialDataTag(JavadocCommentsParser.SerialDataTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSerialFieldTag(JavadocCommentsParser.SerialFieldTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitCustomBlockTag(JavadocCommentsParser.CustomBlockTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitInlineTag(JavadocCommentsParser.InlineTagContext ctx) {
        final JavadocNodeImpl inlineTagNode =
                createImaginary(JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG);
        final ParseTree tagContent = ctx.inlineTagContent().getChild(0);

        if (tagContent instanceof ParserRuleContext context) {
            final Token tagName = (Token) context.getChild(0).getPayload();
            final var tokenType = tagName.getType();

            final JavadocNodeImpl specificTagNode = switch (tokenType) {
                case JavadocCommentsLexer.CODE ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.CODE_INLINE_TAG, ctx);
                case JavadocCommentsLexer.LINK ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.LINK_INLINE_TAG, ctx);
                case JavadocCommentsLexer.LINKPLAIN ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.LINKPLAIN_INLINE_TAG, ctx);
                case JavadocCommentsLexer.VALUE ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.VALUE_INLINE_TAG, ctx);
                case JavadocCommentsLexer.INHERIT_DOC ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.INHERIT_DOC_INLINE_TAG, ctx);
                case JavadocCommentsLexer.SUMMARY ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.SUMMARY_INLINE_TAG, ctx);
                case JavadocCommentsLexer.SYSTEM_PROPERTY ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.SYSTEM_PROPERTY_INLINE_TAG, ctx);
                case JavadocCommentsLexer.INDEX ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.INDEX_INLINE_TAG, ctx);
                case JavadocCommentsLexer.RETURN ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.RETURN_INLINE_TAG, ctx);
                case JavadocCommentsLexer.LITERAL ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.LITERAL_INLINE_TAG, ctx);
                case JavadocCommentsLexer.SNIPPET ->
                    buildImaginaryNode(JavadocCommentsTokenTypes.SNIPPET_INLINE_TAG, ctx);
                default -> buildImaginaryNode(JavadocCommentsTokenTypes.CUSTOM_INLINE_TAG, ctx);
            };
            inlineTagNode.addChild(specificTagNode);
        }

        return inlineTagNode;
    }

    @Override
    public JavadocNodeImpl visitInlineTagContent(
            JavadocCommentsParser.InlineTagContentContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitCodeInlineTag(JavadocCommentsParser.CodeInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitLinkPlainInlineTag(
            JavadocCommentsParser.LinkPlainInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitLinkInlineTag(JavadocCommentsParser.LinkInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitValueInlineTag(JavadocCommentsParser.ValueInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitInheritDocInlineTag(
            JavadocCommentsParser.InheritDocInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSummaryInlineTag(
            JavadocCommentsParser.SummaryInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSystemPropertyInlineTag(
            JavadocCommentsParser.SystemPropertyInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitIndexInlineTag(JavadocCommentsParser.IndexInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitReturnInlineTag(JavadocCommentsParser.ReturnInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitLiteralInlineTag(
            JavadocCommentsParser.LiteralInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSnippetInlineTag(
            JavadocCommentsParser.SnippetInlineTagContext ctx) {
        final JavadocNodeImpl dummyRoot = new JavadocNodeImpl();
        if (!ctx.snippetAttributes.isEmpty()) {
            final JavadocNodeImpl snippetAttributes =
                    createImaginary(JavadocCommentsTokenTypes.SNIPPET_ATTRIBUTES);
            ctx.snippetAttributes.forEach(snippetAttributeContext -> {
                final JavadocNodeImpl snippetAttribute = visit(snippetAttributeContext);
                snippetAttributes.addChild(snippetAttribute);
            });
            dummyRoot.addChild(snippetAttributes);
        }
        if (ctx.COLON() != null) {
            dummyRoot.addChild(create((Token) ctx.COLON().getPayload()));
        }
        if (ctx.snippetBody() != null) {
            dummyRoot.addChild(visit(ctx.snippetBody()));
        }
        return dummyRoot.getFirstChild();
    }

    @Override
    public JavadocNodeImpl visitCustomInlineTag(JavadocCommentsParser.CustomInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitReference(JavadocCommentsParser.ReferenceContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.REFERENCE, ctx);
    }

    @Override
    public JavadocNodeImpl visitTypeName(JavadocCommentsParser.TypeNameContext ctx) {
        return flattenedTree(ctx);

    }

    @Override
    public JavadocNodeImpl visitQualifiedName(JavadocCommentsParser.QualifiedNameContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitTypeArguments(JavadocCommentsParser.TypeArgumentsContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.TYPE_ARGUMENTS, ctx);
    }

    @Override
    public JavadocNodeImpl visitTypeArgument(JavadocCommentsParser.TypeArgumentContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.TYPE_ARGUMENT, ctx);
    }

    @Override
    public JavadocNodeImpl visitMemberReference(JavadocCommentsParser.MemberReferenceContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.MEMBER_REFERENCE, ctx);
    }

    @Override
    public JavadocNodeImpl visitParameterTypeList(
            JavadocCommentsParser.ParameterTypeListContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.PARAMETER_TYPE_LIST, ctx);
    }

    @Override
    public JavadocNodeImpl visitDescription(JavadocCommentsParser.DescriptionContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.DESCRIPTION, ctx);
    }

    @Override
    public JavadocNodeImpl visitSnippetAttribute(
            JavadocCommentsParser.SnippetAttributeContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.SNIPPET_ATTRIBUTE, ctx);
    }

    @Override
    public JavadocNodeImpl visitSnippetBody(JavadocCommentsParser.SnippetBodyContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.SNIPPET_BODY, ctx);
    }

    @Override
    public JavadocNodeImpl visitHtmlElement(JavadocCommentsParser.HtmlElementContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.HTML_ELEMENT, ctx);
    }

    @Override
    public JavadocNodeImpl visitVoidElement(JavadocCommentsParser.VoidElementContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.VOID_ELEMENT, ctx);
    }

    @Override
    public JavadocNodeImpl visitTightElement(JavadocCommentsParser.TightElementContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitNonTightElement(JavadocCommentsParser.NonTightElementContext ctx) {
        if (firstNonTightHtmlTag == null) {
            final ParseTree htmlTagStart = ctx.getChild(0);
            final ParseTree tagNameToken = htmlTagStart.getChild(1);
            firstNonTightHtmlTag = create((Token) tagNameToken.getPayload());
        }
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSelfClosingElement(
            JavadocCommentsParser.SelfClosingElementContext ctx) {
        final JavadocNodeImpl javadocNode =
                createImaginary(JavadocCommentsTokenTypes.VOID_ELEMENT);
        javadocNode.addChild(create((Token) ctx.TAG_OPEN().getPayload()));
        javadocNode.addChild(create((Token) ctx.TAG_NAME().getPayload()));
        if (!ctx.htmlAttributes.isEmpty()) {
            final JavadocNodeImpl htmlAttributes =
                    createImaginary(JavadocCommentsTokenTypes.HTML_ATTRIBUTES);
            ctx.htmlAttributes.forEach(htmlAttributeContext -> {
                final JavadocNodeImpl htmlAttribute = visit(htmlAttributeContext);
                htmlAttributes.addChild(htmlAttribute);
            });
            javadocNode.addChild(htmlAttributes);
        }

        javadocNode.addChild(create((Token) ctx.TAG_SLASH_CLOSE().getPayload()));
        return javadocNode;
    }

    @Override
    public JavadocNodeImpl visitHtmlTagStart(JavadocCommentsParser.HtmlTagStartContext ctx) {
        final JavadocNodeImpl javadocNode =
                createImaginary(JavadocCommentsTokenTypes.HTML_TAG_START);
        javadocNode.addChild(create((Token) ctx.TAG_OPEN().getPayload()));
        javadocNode.addChild(create((Token) ctx.TAG_NAME().getPayload()));
        if (!ctx.htmlAttributes.isEmpty()) {
            final JavadocNodeImpl htmlAttributes =
                    createImaginary(JavadocCommentsTokenTypes.HTML_ATTRIBUTES);
            ctx.htmlAttributes.forEach(htmlAttributeContext -> {
                final JavadocNodeImpl htmlAttribute = visit(htmlAttributeContext);
                htmlAttributes.addChild(htmlAttribute);
            });
            javadocNode.addChild(htmlAttributes);
        }

        final Token tagClose = (Token) ctx.TAG_CLOSE().getPayload();
        addHiddenTokensToTheLeft(tagClose, javadocNode);
        javadocNode.addChild(create(tagClose));
        return javadocNode;
    }

    @Override
    public JavadocNodeImpl visitHtmlTagEnd(JavadocCommentsParser.HtmlTagEndContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.HTML_TAG_END, ctx);
    }

    @Override
    public JavadocNodeImpl visitHtmlAttribute(JavadocCommentsParser.HtmlAttributeContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.HTML_ATTRIBUTE, ctx);
    }

    @Override
    public JavadocNodeImpl visitHtmlContent(JavadocCommentsParser.HtmlContentContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.HTML_CONTENT, ctx);
    }

    @Override
    public JavadocNodeImpl visitNonTightHtmlContent(
            JavadocCommentsParser.NonTightHtmlContentContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.HTML_CONTENT, ctx);
    }

    @Override
    public JavadocNodeImpl visitHtmlComment(JavadocCommentsParser.HtmlCommentContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.HTML_COMMENT, ctx);
    }

    @Override
    public JavadocNodeImpl visitHtmlCommentContent(
            JavadocCommentsParser.HtmlCommentContentContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.HTML_COMMENT_CONTENT, ctx);
    }

    /**
     * Creates an imaginary JavadocNodeImpl of the given token type and
     * processes all children of the given ParserRuleContext.
     *
     * @param tokenType the token type of this JavadocNodeImpl
     * @param ctx the ParserRuleContext whose children are to be processed
     * @return new JavadocNodeImpl of given type with processed children
     */
    private JavadocNodeImpl buildImaginaryNode(int tokenType, ParserRuleContext ctx) {
        final JavadocNodeImpl javadocNode = createImaginary(tokenType);
        processChildren(javadocNode, ctx.children);
        return javadocNode;
    }

    /**
     * Builds the AST for a particular node, then returns a "flattened" tree
     * of siblings.
     *
     * @param ctx the ParserRuleContext to base tree on
     * @return flattened DetailAstImpl
     */
    private JavadocNodeImpl flattenedTree(ParserRuleContext ctx) {
        final JavadocNodeImpl dummyNode = new JavadocNodeImpl();
        processChildren(dummyNode, ctx.children);
        return dummyNode.getFirstChild();
    }

    /**
     * Adds all the children from the given ParseTree or ParserRuleContext
     * list to the parent JavadocNodeImpl.
     *
     * @param parent   the JavadocNodeImpl to add children to
     * @param children the list of children to add
     */
    private void processChildren(JavadocNodeImpl parent, List<? extends ParseTree> children) {
        for (ParseTree child : children) {
            if (child instanceof TerminalNode terminalNode) {
                final Token token = (Token) terminalNode.getPayload();

                // Add hidden tokens before this token
                addHiddenTokensToTheLeft(token, parent);

                if (isTextToken(token)) {
                    accumulator.append(token);
                }
                else if (token.getType() != Token.EOF) {
                    accumulator.flushTo(parent);
                    parent.addChild(create(token));
                }
            }
            else {
                accumulator.flushTo(parent);
                final Token token = ((ParserRuleContext) child).getStart();
                addHiddenTokensToTheLeft(token, parent);
                parent.addChild(visit(child));
            }
        }

        accumulator.flushTo(parent);
    }

    /**
     * Checks whether a token is a Javadoc text token.
     *
     * @param token the token to check
     * @return true if the token is a text token, false otherwise
     */
    private static boolean isTextToken(Token token) {
        return token.getType() == JavadocCommentsTokenTypes.TEXT;
    }

    /**
     * Adds hidden tokens to the left of the given token to the parent node.
     * Ensures text accumulation is flushed before adding hidden tokens.
     * Hidden tokens are only added once per unique token index.
     *
     * @param token      the token whose hidden tokens should be added
     * @param parent     the parent node to which hidden tokens are added
     */
    private void addHiddenTokensToTheLeft(Token token, JavadocNodeImpl parent) {
        final var alreadyProcessed = !processedTokenIndices.add(token.getTokenIndex());

        if (!alreadyProcessed) {
            final var tokenIndex = token.getTokenIndex();
            final List<Token> hiddenTokens = tokens.getHiddenTokensToLeft(tokenIndex);
            if (hiddenTokens != null) {
                accumulator.flushTo(parent);
                for (Token hiddenToken : hiddenTokens) {
                    parent.addChild(create(hiddenToken));
                }
            }
        }
    }

    /**
     * Creates a JavadocNodeImpl from the given token.
     *
     * @param token the token to create the JavadocNodeImpl from
     * @return a new JavadocNodeImpl initialized with the token
     */
    private JavadocNodeImpl create(Token token) {
        final JavadocNodeImpl node = new JavadocNodeImpl();
        node.initialize(token);

        // adjust line number to the position of the block comment
        node.setLineNumber(node.getLineNumber() + blockCommentLineNumber);

        // adjust first line to indent of /**
        if (node.getLineNumber() == blockCommentLineNumber) {
            node.setColumnNumber(node.getColumnNumber() + javadocColumnNumber);
        }

        if (isJavadocTag(token.getType())) {
            node.setType(JavadocCommentsTokenTypes.TAG_NAME);
        }

        if (token.getType() == JavadocCommentsLexer.WS) {
            node.setType(JavadocCommentsTokenTypes.TEXT);
        }

        return node;
    }

    /**
     * Checks if the given token type is a Javadoc tag.
     *
     * @param type the token type to check
     * @return true if the token type is a Javadoc tag, false otherwise
     */
    private static boolean isJavadocTag(int type) {
        return JAVADOC_TAG_TYPES.contains(type);
    }

    /**
     * Create a JavadocNodeImpl from a given token and token type. This method
     * should be used for imaginary nodes only, i.e. 'JAVADOC_INLINE_TAG -&gt; JAVADOC_INLINE_TAG',
     * where the text on the RHS matches the text on the LHS.
     *
     * @param tokenType the token type of this JavadocNodeImpl
     * @return new JavadocNodeImpl of given type
     */
    private JavadocNodeImpl createImaginary(int tokenType) {
        final JavadocNodeImpl node = new JavadocNodeImpl();
        node.setType(tokenType);
        node.setText(JavadocUtil.getTokenName(tokenType));

        if (tokenType == JavadocCommentsTokenTypes.JAVADOC_CONTENT) {
            node.setLineNumber(blockCommentLineNumber);
            node.setColumnNumber(javadocColumnNumber);
        }

        return node;
    }

    /**
     * Returns the first non-tight HTML tag encountered in the Javadoc comment, if any.
     *
     * @return the first non-tight HTML tag, or null if none was found
     */
    public DetailNode getFirstNonTightHtmlTag() {
        return firstNonTightHtmlTag;
    }

    /**
     * A small utility to accumulate consecutive TEXT tokens into one node,
     * preserving the starting token for accurate location metadata.
     */
    private final class TextAccumulator {
        /**
         * Buffer to accumulate TEXT token texts.
         *
         * @noinspection StringBufferField
         * @noinspectionreason StringBufferField - We want to reuse the same buffer to avoid
         */
        private final StringBuilder buffer = new StringBuilder(256);

        /**
         * The first token in the accumulation, used for line/column info.
         */
        private Token startToken;

        /**
         * Appends a TEXT token's text to the buffer and tracks the first token.
         *
         * @param token the token to accumulate
         */
        public void append(Token token) {
            if (buffer.isEmpty()) {
                startToken = token;
            }
            buffer.append(token.getText());
        }

        /**
         * Flushes the accumulated buffer into a single {@link JavadocNodeImpl} node
         * and adds it to the given parent. Clears the buffer after flushing.
         *
         * @param parent the parent node to add the new node to
         */
        public void flushTo(JavadocNodeImpl parent) {
            if (!buffer.isEmpty()) {
                final JavadocNodeImpl startNode = create(startToken);
                startNode.setText(buffer.toString());
                parent.addChild(startNode);
                buffer.setLength(0);
            }
        }
    }
}

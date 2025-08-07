package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl;
import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocCommentsLexer;
import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocCommentsParser;
import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocCommentsParserBaseVisitor;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JavadocCommentsAstVisitor extends JavadocCommentsParserBaseVisitor<JavadocNodeImpl> {

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

    public JavadocCommentsAstVisitor(CommonTokenStream tokens, int blockCommentLineNumber, int javadocColumnNumber) {
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
        JavadocNodeImpl blockTagNode = createImaginary(JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG);
        ParseTree tag = ctx.getChild(0);
        JavadocNodeImpl specificTagNode  = null;

        if (tag instanceof ParserRuleContext prc) {
            Token tagName = (Token) prc.getChild(1).getPayload();
            int tokenType = tagName.getType();

            specificTagNode  = switch (tokenType) {
                case JavadocCommentsLexer.AUTHOR -> buildImaginaryNode(JavadocCommentsTokenTypes.AUTHOR_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.DEPRECATED -> buildImaginaryNode(JavadocCommentsTokenTypes.DEPRECATED_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.RETURN -> buildImaginaryNode(JavadocCommentsTokenTypes.RETURN_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.PARAM -> buildImaginaryNode(JavadocCommentsTokenTypes.PARAM_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.THROWS -> buildImaginaryNode(JavadocCommentsTokenTypes.THROWS_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.EXCEPTION -> buildImaginaryNode(JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.SINCE -> buildImaginaryNode(JavadocCommentsTokenTypes.SINCE_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.VERSION -> buildImaginaryNode(JavadocCommentsTokenTypes.VERSION_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.SEE -> buildImaginaryNode(JavadocCommentsTokenTypes.SEE_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.HIDDEN -> buildImaginaryNode(JavadocCommentsTokenTypes.HIDDEN_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.USES -> buildImaginaryNode(JavadocCommentsTokenTypes.USES_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.PROVIDES -> buildImaginaryNode(JavadocCommentsTokenTypes.PROVIDES_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.SERIAL -> buildImaginaryNode(JavadocCommentsTokenTypes.SERIAL_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.SERIAL_DATA -> buildImaginaryNode(JavadocCommentsTokenTypes.SERIAL_DATA_BLOCK_TAG, ctx);
                case JavadocCommentsLexer.SERIAL_FIELD -> buildImaginaryNode(JavadocCommentsTokenTypes.SERIAL_FIELD_BLOCK_TAG, ctx);
                default -> buildImaginaryNode(JavadocCommentsTokenTypes.CUSTOM_BLOCK_TAG, ctx);
            };
        }

        blockTagNode.addChild(specificTagNode);

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
        JavadocNodeImpl inlineTagNode = createImaginary(JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG);
        ParseTree tagContent = ctx.inlineTagContent().getChild(0);
        JavadocNodeImpl specificTagNode = null;

        if (tagContent instanceof ParserRuleContext prc) {
            Token tagName = (Token) prc.getChild(0).getPayload();
            int tokenType = tagName.getType();

            specificTagNode = switch (tokenType) {
                case JavadocCommentsLexer.CODE -> buildImaginaryNode(JavadocCommentsTokenTypes.CODE_INLINE_TAG, ctx);
                case JavadocCommentsLexer.LINK -> buildImaginaryNode(JavadocCommentsTokenTypes.LINK_INLINE_TAG, ctx);
                case JavadocCommentsLexer.LINKPLAIN -> buildImaginaryNode(JavadocCommentsTokenTypes.LINKPLAIN_INLINE_TAG, ctx);
                case JavadocCommentsLexer.VALUE -> buildImaginaryNode(JavadocCommentsTokenTypes.VALUE_INLINE_TAG, ctx);
                case JavadocCommentsLexer.INHERIT_DOC -> buildImaginaryNode(JavadocCommentsTokenTypes.INHERIT_DOC_INLINE_TAG, ctx);
                case JavadocCommentsLexer.SUMMARY -> buildImaginaryNode(JavadocCommentsTokenTypes.SUMMARY_INLINE_TAG, ctx);
                case JavadocCommentsLexer.SYSTEM_PROPERTY -> buildImaginaryNode(JavadocCommentsTokenTypes.SYSTEM_PROPERTY_INLINE_TAG, ctx);
                case JavadocCommentsLexer.INDEX -> buildImaginaryNode(JavadocCommentsTokenTypes.INDEX_INLINE_TAG, ctx);
                case JavadocCommentsLexer.RETURN -> buildImaginaryNode(JavadocCommentsTokenTypes.RETURN_INLINE_TAG, ctx);
                case JavadocCommentsLexer.LITERAL -> buildImaginaryNode(JavadocCommentsTokenTypes.LITERAL_INLINE_TAG, ctx);
                case JavadocCommentsLexer.SNIPPET -> buildImaginaryNode(JavadocCommentsTokenTypes.SNIPPET_INLINE_TAG, ctx);
                default -> buildImaginaryNode(JavadocCommentsTokenTypes.CUSTOM_INLINE_TAG, ctx);
            };
        }

        inlineTagNode.addChild(specificTagNode);

        return inlineTagNode;
    }

    @Override
    public JavadocNodeImpl visitInlineTagContent(JavadocCommentsParser.InlineTagContentContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitCodeInlineTag(JavadocCommentsParser.CodeInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitLinkPlainInlineTag(JavadocCommentsParser.LinkPlainInlineTagContext ctx) {
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
    public JavadocNodeImpl visitInheritDocInlineTag(JavadocCommentsParser.InheritDocInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSummaryInlineTag(JavadocCommentsParser.SummaryInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSystemPropertyInlineTag(JavadocCommentsParser.SystemPropertyInlineTagContext ctx) {
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
    public JavadocNodeImpl visitLiteralInlineTag(JavadocCommentsParser.LiteralInlineTagContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSnippetInlineTag(JavadocCommentsParser.SnippetInlineTagContext ctx) {
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
    public JavadocNodeImpl visitParameterTypeList(JavadocCommentsParser.ParameterTypeListContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.PARAMETER_TYPE_LIST, ctx);
    }

    @Override
    public JavadocNodeImpl visitDescription(JavadocCommentsParser.DescriptionContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.DESCRIPTION, ctx);
    }

    @Override
    public JavadocNodeImpl visitSnippetAttribute(JavadocCommentsParser.SnippetAttributeContext ctx) {
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
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitSelfClosingElement(JavadocCommentsParser.SelfClosingElementContext ctx) {
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

        javadocNode.addChild(create((Token) ctx.TAG_CLOSE().getPayload()));
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
    public JavadocNodeImpl visitNonTightHtmlContent(JavadocCommentsParser.NonTightHtmlContentContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.HTML_CONTENT, ctx);
    }

    private JavadocNodeImpl buildImaginaryNode(int tokenType, ParserRuleContext ctx) {
        final JavadocNodeImpl javadocNode = createImaginary(tokenType);
        processChildren(javadocNode, ctx.children);
        return javadocNode;
    }

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
        children.forEach(child -> {
            if (child instanceof TerminalNode terminalNode) {
                Token token = (Token) terminalNode.getPayload();

                // Add hidden tokens before this token
                addHiddenTokensToTheLeft(token, parent, accumulator);

                if (isTextToken(token)) {
                    accumulator.append(token);
                }
                else if (token.getType() != JavadocCommentsLexer.EOF) {
                    accumulator.flushTo(parent);
                    parent.addChild(create(token));
                }
            } else {
                accumulator.flushTo(parent);
                Token token = ((ParserRuleContext) child).getStart();
                addHiddenTokensToTheLeft(token, parent, accumulator);
                parent.addChild(visit(child));
            }
        });

        accumulator.flushTo(parent);
    }

    /**
     * Checks whether a token is a Javadoc text token.
     *
     * @param token the token to check
     * @return true if the token is a text token, false otherwise
     */
    private boolean isTextToken(Token token) {
        return token.getType() == JavadocCommentsTokenTypes.TEXT;
    }

    /**
     * Adds hidden tokens to the left of the given token to the parent node.
     * Ensures text accumulation is flushed before adding hidden tokens.
     * Hidden tokens are only added once per unique token index.
     *
     * @param token      the token whose hidden tokens should be added
     * @param parent     the parent node to which hidden tokens are added
     * @param accumulator the accumulator to flush before inserting hidden tokens
     */
    private void addHiddenTokensToTheLeft(Token token, JavadocNodeImpl parent, TextAccumulator accumulator) {
        boolean alreadyProcessed = !processedTokenIndices.add(token.getTokenIndex());

        if (!alreadyProcessed) {
            int tokenIndex = token.getTokenIndex();
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

    private boolean isJavadocTag(int type) {
        return type == JavadocCommentsLexer.CODE  || type == JavadocCommentsLexer.LINK
                || type == JavadocCommentsLexer.LINKPLAIN  || type == JavadocCommentsLexer.VALUE
                || type == JavadocCommentsLexer.INHERIT_DOC  || type == JavadocCommentsLexer.SUMMARY
                || type == JavadocCommentsLexer.SYSTEM_PROPERTY || type == JavadocCommentsLexer.INDEX
                || type == JavadocCommentsLexer.RETURN  || type == JavadocCommentsLexer.LITERAL
                || type == JavadocCommentsLexer.SNIPPET || type == JavadocCommentsLexer.CUSTOM_NAME
                || type == JavadocCommentsLexer.AUTHOR || type == JavadocCommentsLexer.DEPRECATED
                || type == JavadocCommentsLexer.PARAM  || type == JavadocCommentsLexer.THROWS
                || type == JavadocCommentsLexer.EXCEPTION || type == JavadocCommentsLexer.SINCE
                || type == JavadocCommentsLexer.VERSION || type == JavadocCommentsLexer.SEE
                || type == JavadocCommentsLexer.LITERAL_HIDDEN || type == JavadocCommentsLexer.USES
                || type == JavadocCommentsLexer.PROVIDES || type == JavadocCommentsLexer.SERIAL
                || type == JavadocCommentsLexer.SERIAL_DATA || type == JavadocCommentsLexer.SERIAL_FIELD;
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
        node.setText(JavadocUtil.getJavadocTokenName(tokenType));

        if (tokenType == JavadocCommentsTokenTypes.JAVADOC_CONTENT) {
            node.setLineNumber(blockCommentLineNumber);
            node.setColumnNumber(javadocColumnNumber);
        }

        return node;
    }

    /**
     * A small utility to accumulate consecutive TEXT tokens into one node,
     * preserving the starting token for accurate location metadata.
     */
    private final class TextAccumulator {
        private final StringBuilder buffer = new StringBuilder();
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
                JavadocNodeImpl startNode = create(startToken);
                startNode.setText(buffer.toString());
                parent.addChild(startNode);
                buffer.setLength(0);
            }
        }
    }
}

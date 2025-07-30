package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl;
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


    public JavadocCommentsAstVisitor(CommonTokenStream tokens, int blockCommentLineNumber, int javadocColumnNumber) {
        this.tokens = tokens;
        this.blockCommentLineNumber = blockCommentLineNumber;
        this.javadocColumnNumber = javadocColumnNumber;
    }

    @Override
    public JavadocNodeImpl visitJavadoc(JavadocCommentsParser.JavadocContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.JAVADOC, ctx);
    }

    @Override
    public JavadocNodeImpl visitMainDescription(JavadocCommentsParser.MainDescriptionContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitInlineTag(JavadocCommentsParser.InlineTagContext ctx) {
        return buildImaginaryNode(JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG, ctx);
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
        if (ctx.SNIPPET() != null) {
            dummyRoot.addChild(create((Token) ctx.SNIPPET().getPayload()));
        }
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
                addHiddenTokensToTheLeft(token, parent);

                if (token.getType() != -1) {
                    parent.addChild(create(token));
                }
            } else {
                Token token = ((ParserRuleContext) child).getStart();
                addHiddenTokensToTheLeft(token, parent);
                parent.addChild(visit(child));
            }
        });
    }

    /**
     * Adds hidden tokens that appear to the left of the given token to the specified parent node.
     * Ensures that hidden tokens for a token are added only once by tracking processed tokens.
     *
     * @param token  the token whose hidden tokens should be added
     * @param parent the parent node to which the hidden tokens should be added
     */
    private void addHiddenTokensToTheLeft(Token token, JavadocNodeImpl parent) {
        boolean alreadyProcessed = !processedTokenIndices.add(token.getTokenIndex());

        if (!alreadyProcessed) {
            int tokenIndex = token.getTokenIndex();
            final List<Token> hiddenTokens = tokens.getHiddenTokensToLeft(tokenIndex);
            if (hiddenTokens != null) {
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
        return node;
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

        if (tokenType == JavadocCommentsTokenTypes.JAVADOC) {
            node.setLineNumber(blockCommentLineNumber);
            node.setColumnNumber(javadocColumnNumber);
        }

        return node;
    }
}

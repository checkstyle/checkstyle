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
     * Token stream to check for hidden tokens.
     */
    private final BufferedTokenStream tokens;

    /**
     * A set of token indices used to track which tokens have already had their
     * hidden tokens added to the AST.
     */
    private final Set<Integer> processedTokenIndices = new HashSet<>();


    public JavadocCommentsAstVisitor(CommonTokenStream tokens) {
        this.tokens = tokens;
    }

    @Override
    public JavadocNodeImpl visitJavadoc(JavadocCommentsParser.JavadocContext ctx) {
        final JavadocNodeImpl javadocNode = createImaginary(JavadocCommentsTokenTypes.JAVADOC);
        processChildren(javadocNode, ctx.children);
        return javadocNode;
    }

    @Override
    public JavadocNodeImpl visitMainDescription(JavadocCommentsParser.MainDescriptionContext ctx) {
        return flattenedTree(ctx);
    }

    @Override
    public JavadocNodeImpl visitInlineTag(JavadocCommentsParser.InlineTagContext ctx) {
        final JavadocNodeImpl javadocNode =
                createImaginary(JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG);
        processChildren(javadocNode, ctx.children);
        return javadocNode;
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
        final JavadocNodeImpl javadocNode =
                createImaginary(JavadocCommentsTokenTypes.REFERENCE);
        processChildren(javadocNode, ctx.children);
        return javadocNode;
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
        final JavadocNodeImpl javadocNode =
                createImaginary(JavadocCommentsTokenTypes.TYPE_ARGUMENTS);
        processChildren(javadocNode, ctx.children);
        return javadocNode;
    }

    @Override
    public JavadocNodeImpl visitTypeArgument(JavadocCommentsParser.TypeArgumentContext ctx) {
        final JavadocNodeImpl javadocNode =
                createImaginary(JavadocCommentsTokenTypes.TYPE_ARGUMENT);
        processChildren(javadocNode, ctx.children);
        return javadocNode;
    }

    @Override
    public JavadocNodeImpl visitMemberReference(JavadocCommentsParser.MemberReferenceContext ctx) {
        final JavadocNodeImpl javadocNode =
                createImaginary(JavadocCommentsTokenTypes.MEMBER_REFERENCE);
        processChildren(javadocNode, ctx.children);
        return javadocNode;
    }

    @Override
    public JavadocNodeImpl visitParameterTypeList(JavadocCommentsParser.ParameterTypeListContext ctx) {
        final JavadocNodeImpl javadocNode =
                createImaginary(JavadocCommentsTokenTypes.PARAMETER_TYPE_LIST);
        processChildren(javadocNode, ctx.children);
        return javadocNode;
    }

    @Override
    public JavadocNodeImpl visitDescription(JavadocCommentsParser.DescriptionContext ctx) {
        final JavadocNodeImpl javadocNode =
                createImaginary(JavadocCommentsTokenTypes.DESCRIPTION);
        processChildren(javadocNode, ctx.children);
        return javadocNode;
    }

    @Override
    public JavadocNodeImpl visitSnippetAttribute(JavadocCommentsParser.SnippetAttributeContext ctx) {
        final JavadocNodeImpl javadocNode =
                createImaginary(JavadocCommentsTokenTypes.SNIPPET_ATTRIBUTE);
        processChildren(javadocNode, ctx.children);
        return javadocNode;
    }

    @Override
    public JavadocNodeImpl visitSnippetBody(JavadocCommentsParser.SnippetBodyContext ctx) {
        final JavadocNodeImpl javadocNode =
                createImaginary(JavadocCommentsTokenTypes.SNIPPET_BODY);
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
        final JavadocNodeImpl javadocNode = new JavadocNodeImpl();
        javadocNode.initialize(token);
        return javadocNode;
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
        return node;
    }
}

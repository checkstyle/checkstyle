package com.puppycrawl.tools.checkstyle.checks.annotation;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public abstract class AbstractAnnotationModifiersCheck extends AbstractCheck {

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
                TokenTypes.CLASS_DEF,
                TokenTypes.INTERFACE_DEF,
                TokenTypes.ENUM_DEF,
                TokenTypes.METHOD_DEF,
                TokenTypes.CTOR_DEF,
                TokenTypes.VARIABLE_DEF,
                TokenTypes.RECORD_DEF,
                TokenTypes.COMPACT_CTOR_DEF,
                };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
                TokenTypes.CLASS_DEF,
                TokenTypes.INTERFACE_DEF,
                TokenTypes.ENUM_DEF,
                TokenTypes.METHOD_DEF,
                TokenTypes.CTOR_DEF,
                TokenTypes.VARIABLE_DEF,
                TokenTypes.PARAMETER_DEF,
                TokenTypes.ANNOTATION_DEF,
                TokenTypes.TYPECAST,
                TokenTypes.LITERAL_THROWS,
                TokenTypes.IMPLEMENTS_CLAUSE,
                TokenTypes.TYPE_ARGUMENT,
                TokenTypes.LITERAL_NEW,
                TokenTypes.DOT,
                TokenTypes.ANNOTATION_FIELD_DEF,
                TokenTypes.RECORD_DEF,
                TokenTypes.COMPACT_CTOR_DEF,
                };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken( DetailAST ast) {
        DetailAST nodeWithAnnotations = ast;
        if (ast.getType() == TokenTypes.TYPECAST) {
            nodeWithAnnotations = ast.findFirstToken(TokenTypes.TYPE);
        }
        DetailAST modifiersNode = nodeWithAnnotations.findFirstToken(TokenTypes.MODIFIERS);
        if (modifiersNode == null) {
            modifiersNode = nodeWithAnnotations.findFirstToken(TokenTypes.ANNOTATIONS);
        }
        if (modifiersNode != null) {
            checkModifiersNode( modifiersNode );
        }
    }

    /**
     * Finds next node of ast tree.
     *
     * @param node current node
     * @return node that is next to given
     */
    protected static DetailAST getNextNode(DetailAST node) {
        DetailAST nextNode = node.getNextSibling();
        if (nextNode == null) {
            nextNode = node.getParent().getNextSibling();
        }
        return nextNode;
    }

    /**
     * Returns the name of the given annotation.
     *
     * @param annotation annotation node.
     * @return annotation name.
     */
    protected static String getAnnotationName(DetailAST annotation) {
        DetailAST identNode = annotation.findFirstToken(TokenTypes.IDENT);
        if (identNode == null) {
            identNode = annotation.findFirstToken(TokenTypes.DOT).getLastChild();
        }
        return identNode.getText();
    }

    protected abstract void checkModifiersNode(DetailAST modifiersNode);

}

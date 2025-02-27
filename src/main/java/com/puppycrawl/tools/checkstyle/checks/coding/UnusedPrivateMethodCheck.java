package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.puppycrawl.tools.checkstyle.api.Scope.PRIVATE;
import static java.util.Collections.frequency;
import static java.util.Objects.nonNull;

/**
 * Checks that a local method is declared and/or assigned, but not used.
 */
@FileStatefulCheck
public class UnusedPrivateMethodCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_UNUSED_LOCAL_METHOD = "unused.local.method";

    /**
     * Keeps track of the methods declared in the file.
     */
    private final Map<String, DetailAST> methods = new HashMap<>();

    /**
     * Keeps track of the method calls in the file.
     */
    private final Collection<String> identifications = new ArrayList<>();

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
                TokenTypes.METHOD_DEF,
                TokenTypes.IDENT,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void beginTree(DetailAST root) {
        methods.clear();
        identifications.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.METHOD_DEF
                && nonNull(ast.getFirstChild().getFirstChild())
                && ast.getFirstChild().getFirstChild().getText().equals(PRIVATE.getName())) {
            methods.put(ast.findFirstToken(TokenTypes.IDENT).getText(), ast);
        } else if (ast.getType() == TokenTypes.IDENT) {
            identifications.add(ast.getText());
        }
    }

    @Override
    public void finishTree(DetailAST ast) {
        for (String methodName : methods.keySet()) {
            if (frequency(identifications, methodName) == 1) {
                log(methods.get(methodName), MSG_UNUSED_LOCAL_METHOD, methodName);
            }
        }
    }
}

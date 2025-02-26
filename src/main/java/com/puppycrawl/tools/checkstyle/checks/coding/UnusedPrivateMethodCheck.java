package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    private final Set<String> declaredMethods = new HashSet<>();

    /**
     * Keeps track of the method calls in the file.
     */
    private final Collection<String> methodCalls = new ArrayList<>();

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
        declaredMethods.clear();
        methodCalls.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.METHOD_DEF) {
            declaredMethods.add(ast.findFirstToken(TokenTypes.IDENT).getText());
        } else if (ast.getType() == TokenTypes.IDENT) {
            methodCalls.add(ast.getText());
        }
    }

    @Override
    public void finishTree(DetailAST ast) {
        for (String methodName : declaredMethods) {
            if (Collections.frequency(methodCalls, methodName) == 1) {
                log(ast, MSG_UNUSED_LOCAL_METHOD, methodName);
            }
        }
    }
}

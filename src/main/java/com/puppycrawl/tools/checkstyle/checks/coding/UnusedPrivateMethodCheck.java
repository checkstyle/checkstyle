package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    private final Map<String, DetailAST> declaredMethods = new HashMap<>();

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
            declaredMethods.put(ast.findFirstToken(TokenTypes.IDENT).getText(), ast);
        } else if (ast.getType() == TokenTypes.IDENT) {
            methodCalls.add(ast.getText());
        }
    }

    @Override
    public void finishTree(DetailAST ast) {
        for (String methodName : declaredMethods.keySet()) {
            if (Collections.frequency(methodCalls, methodName) == 1) {
                final DetailAST ast1 = declaredMethods.get(methodName);
                log(ast1, MSG_UNUSED_LOCAL_METHOD, methodName);
//                log(ast1.getLineNo(), MSG_UNUSED_LOCAL_METHOD, methodName);
//                log(ast1.getLineNo(), ast1.getColumnNo(), MSG_UNUSED_LOCAL_METHOD, methodName);
            }
        }
    }
}

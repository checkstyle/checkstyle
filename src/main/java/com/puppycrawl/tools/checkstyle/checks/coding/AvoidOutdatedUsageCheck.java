package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Map;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

@StatelessCheck
public class AvoidOutdatedUsageCheck extends AbstractCheck {

    public static final String MSG_OUTDATED_API_USAGE = "outdated.api.usage";

    /** Set of outdated method names to check for */
    private static final Map<String,String> OUTDATED_METHODS = Map.of("toList", "Collectors.toList()");

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_REF,
            TokenTypes.METHOD_CALL,
            TokenTypes.CTOR_CALL
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST child = ast.getFirstChild();
        if (ast.getType() == TokenTypes.METHOD_CALL) {
            if (child.getFirstChild() == null) {
                logOutdated(child,child.getText());
            }
            else {
                final DetailAST nextSibling = child.getFirstChild().getNextSibling();
                logOutdated(nextSibling, nextSibling.getText());
            }
        }
    }

    private void logOutdated(DetailAST ast, String methodName) {
        if (isOutdated(methodName)) {
            log(ast, MSG_OUTDATED_API_USAGE, OUTDATED_METHODS.get(methodName));
        }
    }

    /**
     * Checks if the method name is in our outdated set.
     * @param methodName the method name to check
     * @return true if the method is outdated
     */
    private static boolean isOutdated(String methodName) {
        return OUTDATED_METHODS.containsKey(methodName);
    }
}
package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Computes the length of a lambda body. Algorithm preserved
 * byte-for-byte from the original {@code LambdaBodyLengthCheck}; only the call
 * shape changes (emit {@link Measurement} instead of {@code log(..)}).
 */
public final class LambdaBodyLengthMeasurementFilter implements Filter<AstEvent, Measurement> {

    private final int max;
    private final String messageKey;

    public LambdaBodyLengthMeasurementFilter(int max, String messageKey) {
        this.max = max;
        this.messageKey = messageKey;
    }

    @Override
    public void process(Pipe<AstEvent> in, Pipe<Measurement> out) {
        while (in.hasNext()) {
            final AstEvent event = in.read();
            if (event == null) {
                break;
            }
            if (event.getPhase() != AstEvent.Phase.VISIT) {
                continue;
            }
            
            final DetailAST ast = event.getNode();
            if (ast.getParent().getType() != TokenTypes.SWITCH_RULE) {
                final int length = getLength(ast);
                out.write(new Measurement(ast, ast.getLineNo(), ast.getColumnNo(),
                        length, messageKey, length, max));
            }
        }
    }

    /**
     * Get length of lambda body.
     *
     * @param ast lambda body node.
     * @return length of lambda body.
     */
    private static int getLength(DetailAST ast) {
        final DetailAST lambdaBody = ast.getLastChild();
        final int length;
        if (lambdaBody.getType() == TokenTypes.SLIST) {
            length = lambdaBody.getLastChild().getLineNo() - lambdaBody.getLineNo();
        }
        else {
            length = getLastNodeLineNumber(lambdaBody) - getFirstNodeLineNumber(lambdaBody);
        }
        return length + 1;
    }

    /**
     * Get last child node in the tree line number.
     *
     * @param lambdaBody lambda body node.
     * @return last child node in the tree line number.
     */
    private static int getLastNodeLineNumber(DetailAST lambdaBody) {
        DetailAST node = lambdaBody;
        int result;
        do {
            result = node.getLineNo();
            node = node.getLastChild();
        } while (node != null);
        return result;
    }

    /**
     * Get first child node in the tree line number.
     *
     * @param lambdaBody lambda body node.
     * @return first child node in the tree line number.
     */
    private static int getFirstNodeLineNumber(DetailAST lambdaBody) {
        DetailAST node = lambdaBody;
        int result;
        do {
            result = node.getLineNo();
            node = node.getFirstChild();
        } while (node != null);
        return result;
    }
}

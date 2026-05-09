package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Computes the length of an anonymous inner class. Algorithm preserved
 * byte-for-byte from the original {@code AnonInnerLengthCheck}; only the call
 * shape changes (emit {@link Measurement} instead of {@code log(..)}).
 */
public final class AnonInnerLengthMeasurementFilter implements Filter<AstEvent, Measurement> {

    private final int max;
    private final String messageKey;

    public AnonInnerLengthMeasurementFilter(int max, String messageKey) {
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
            final DetailAST openingBrace = ast.findFirstToken(TokenTypes.OBJBLOCK);
            if (openingBrace != null) {
                final DetailAST closingBrace =
                    openingBrace.findFirstToken(TokenTypes.RCURLY);
                final int length =
                    closingBrace.getLineNo() - openingBrace.getLineNo() + 1;
                
                out.write(new Measurement(ast, ast.getLineNo(), ast.getColumnNo(),
                        length, messageKey, length, max));
            }
        }
    }
}

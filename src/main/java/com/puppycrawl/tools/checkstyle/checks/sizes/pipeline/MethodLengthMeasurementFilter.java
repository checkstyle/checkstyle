package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import java.util.Objects;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Computes the length of a method or constructor body. Algorithm preserved
 * byte-for-byte from the original {@code MethodLengthCheck}; only the call
 * shape changes (emit {@link Measurement} instead of {@code log(..)}).
 */
public final class MethodLengthMeasurementFilter implements Filter<AstEvent, Measurement> {

    private final boolean countEmpty;
    private final int max;
    private final String messageKey;

    public MethodLengthMeasurementFilter(boolean countEmpty, int max, String messageKey) {
        this.countEmpty = countEmpty;
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
            final DetailAST openingBrace = ast.findFirstToken(TokenTypes.SLIST);
            if (openingBrace != null) {
                final int length;
                if (countEmpty) {
                    final DetailAST closingBrace = openingBrace.findFirstToken(TokenTypes.RCURLY);
                    length = getLengthOfBlock(openingBrace, closingBrace);
                }
                else {
                    length = countUsedLines(openingBrace);
                }
                final String methodName = ast.findFirstToken(TokenTypes.IDENT).getText();
                out.write(new Measurement(ast, ast.getLineNo(), ast.getColumnNo(),
                        length, messageKey, length, max, methodName));
            }
        }
    }

    private static int getLengthOfBlock(DetailAST openingBrace, DetailAST closingBrace) {
        final int startLineNo = openingBrace.getLineNo();
        final int endLineNo = closingBrace.getLineNo();
        return endLineNo - startLineNo + 1;
    }

    private static int countUsedLines(DetailAST ast) {
        final Deque<DetailAST> nodes = new ArrayDeque<>();
        nodes.add(ast);
        final BitSet usedLines = new BitSet();
        while (!nodes.isEmpty()) {
            final DetailAST node = nodes.removeFirst();
            final int lineIndex = node.getLineNo();
            if (node.getType() == TokenTypes.TEXT_BLOCK_LITERAL_BEGIN) {
                final int endLineIndex = node.getLastChild().getLineNo();
                usedLines.set(lineIndex, endLineIndex + 1);
            }
            else {
                usedLines.set(lineIndex);
                Stream.iterate(
                    node.getLastChild(), Objects::nonNull, DetailAST::getPreviousSibling
                ).forEach(nodes::addFirst);
            }
        }
        return usedLines.cardinality();
    }
}

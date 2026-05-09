package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Counts outer types in a file. State is private and lives only within a
 * per-file pipeline invocation. The driver rebuilds the pipeline per file so
 * the filter starts fresh.
 */
public final class OuterTypeNumberMeasurementFilter implements Filter<AstEvent, Measurement> {

    private final int max;
    private final String messageKey;

    private int currentDepth;
    private int outerNum;
    private DetailAST rootAst;

    public OuterTypeNumberMeasurementFilter(int max, String messageKey) {
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
            switch (event.getPhase()) {
                case BEGIN_TREE:
                    currentDepth = 0;
                    outerNum = 0;
                    rootAst = event.getNode();
                    break;
                case VISIT:
                    if (currentDepth == 0) {
                        outerNum++;
                    }
                    currentDepth++;
                    break;
                case LEAVE:
                    currentDepth--;
                    break;
                case FINISH_TREE:
                    final DetailAST subject = rootAst != null ? rootAst : event.getNode();
                    out.write(new Measurement(subject,
                            subject.getLineNo(), subject.getColumnNo(),
                            outerNum, messageKey, outerNum, max));
                    break;
                default:
                    break;
            }
        }
    }
}

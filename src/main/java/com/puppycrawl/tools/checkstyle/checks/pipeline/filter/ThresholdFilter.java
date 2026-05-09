package com.puppycrawl.tools.checkstyle.checks.pipeline.filter;

import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.ViolationMessage;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Compares the value carried by each {@link Measurement} against a configured
 * ceiling. Emits a {@link ViolationMessage} for every measurement whose value
 * exceeds the ceiling.
 *
 * Concentrating the comparison in this single filter is what makes the per-check
 * driver "comparison-free" (Success Criterion SC-006).
 */
public final class ThresholdFilter implements Filter<Measurement, ViolationMessage> {

    private final int max;

    public ThresholdFilter(int max) {
        this.max = max;
    }

    @Override
    public void process(Pipe<Measurement> in, Pipe<ViolationMessage> out) {
        while (in.hasNext()) {
            final Measurement m = in.read();
            if (m == null) {
                break;
            }
            if (m.getValue() > max) {
                out.write(new ViolationMessage(m.getLineNo(), m.getColNo(),
                                               m.getMessageKey(), m.getArgs()));
            }
        }
    }
}

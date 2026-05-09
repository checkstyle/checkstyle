package com.puppycrawl.tools.checkstyle.checks.pipeline.filter;

import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.ViolationMessage;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Terminal pipeline stage. Forwards every {@link ViolationMessage} on to the
 * tail pipe so the driver can drain them into {@code AbstractCheck.log(..)}.
 *
 * The sink exists so that ArchUnit and jQAssistant have a single, named
 * termination point at which to attach "only this filter feeds the driver"
 * rules.
 */
public final class ViolationSink implements Filter<ViolationMessage, ViolationMessage> {

    @Override
    public void process(Pipe<ViolationMessage> in, Pipe<ViolationMessage> out) {
        while (in.hasNext()) {
            final ViolationMessage v = in.read();
            if (v == null) {
                break;
            }
            out.write(v);
        }
    }
}

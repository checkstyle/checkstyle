package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.QueuePipe;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.SingletonPipe;

class MethodLengthMeasurementFilterTest {

    @Test
    void emitsMeasurementForMethodWithBlock() {
        final DetailAstImpl methodDef = node(TokenTypes.METHOD_DEF, 1, 0);

        final DetailAstImpl ident = node(TokenTypes.IDENT, 1, 7);
        ident.setText("foo");
        methodDef.addChild(ident);

        final DetailAstImpl slist = node(TokenTypes.SLIST, 1, 12);
        final DetailAstImpl rcurly = node(TokenTypes.RCURLY, 5, 0);
        slist.addChild(rcurly);
        methodDef.addChild(slist);

        final MethodLengthMeasurementFilter filter =
                new MethodLengthMeasurementFilter(true, 3, "maxLen.method");
        final SingletonPipe<AstEvent> in = new SingletonPipe<>();
        final QueuePipe<Measurement> out = new QueuePipe<>();
        in.write(new AstEvent(methodDef, AstEvent.Phase.VISIT));
        filter.process(in, out);

        final Measurement m = out.read();
        assertNotNull(m);
        assertEquals(5, m.getValue()); // 5 - 1 + 1
        assertEquals("maxLen.method", m.getMessageKey());
        assertEquals(1, m.getLineNo());
        assertEquals(0, m.getColNo());
    }

    @Test
    void ignoresLeavePhase() {
        final DetailAstImpl methodDef = node(TokenTypes.METHOD_DEF, 1, 0);
        final MethodLengthMeasurementFilter filter =
                new MethodLengthMeasurementFilter(true, 3, "maxLen.method");
        final SingletonPipe<AstEvent> in = new SingletonPipe<>();
        final QueuePipe<Measurement> out = new QueuePipe<>();
        in.write(new AstEvent(methodDef, AstEvent.Phase.LEAVE));
        filter.process(in, out);
        assertFalse(out.hasNext());
    }

    private static DetailAstImpl node(int type, int line, int col) {
        final DetailAstImpl n = new DetailAstImpl();
        n.setType(type);
        n.setLineNo(line);
        n.setColumnNo(col);
        return n;
    }
}

package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

public final class RecordComponentNumberMeasurementFilter implements Filter<AstEvent, Measurement> {

    private final int max;
    private final AccessModifierOption[] accessModifiers;
    private final String messageKey;

    public RecordComponentNumberMeasurementFilter(int max,
                                                  AccessModifierOption[] accessModifiers,
                                                  String messageKey) {
        this.max = max;
        this.accessModifiers = accessModifiers.clone();
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
            final AccessModifierOption am =
                    CheckUtil.getAccessModifierFromModifiersToken(ast);
            if (!matches(am)) {
                continue;
            }
            final DetailAST components = ast.findFirstToken(TokenTypes.RECORD_COMPONENTS);
            final int count = components.getChildCount(TokenTypes.RECORD_COMPONENT_DEF);
            out.write(new Measurement(ast, ast.getLineNo(), ast.getColumnNo(),
                    count, messageKey, count, max));
        }
    }

    private boolean matches(AccessModifierOption am) {
        return Arrays.stream(accessModifiers).anyMatch(m -> m == am);
    }
}

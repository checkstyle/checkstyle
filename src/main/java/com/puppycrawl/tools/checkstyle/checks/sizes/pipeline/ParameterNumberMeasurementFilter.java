package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;

import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

public final class ParameterNumberMeasurementFilter implements Filter<AstEvent, Measurement> {

    private final int max;
    private final boolean ignoreOverriddenMethods;
    private final Set<String> ignoreAnnotatedBy;
    private final String messageKey;

    public ParameterNumberMeasurementFilter(int max,
                                            boolean ignoreOverriddenMethods,
                                            Set<String> ignoreAnnotatedBy,
                                            String messageKey) {
        this.max = max;
        this.ignoreOverriddenMethods = ignoreOverriddenMethods;
        this.ignoreAnnotatedBy = ignoreAnnotatedBy;
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
            final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
            final int count = params.getChildCount(TokenTypes.PARAMETER_DEF);
            if (shouldIgnore(ast)) {
                continue;
            }
            final DetailAST name = ast.findFirstToken(TokenTypes.IDENT);
            out.write(new Measurement(ast, name.getLineNo(), name.getColumnNo(),
                    count, messageKey, max, count));
        }
    }

    private boolean shouldIgnore(DetailAST ast) {
        return (ignoreOverriddenMethods && AnnotationUtil.hasOverrideAnnotation(ast))
                || AnnotationUtil.containsAnnotation(ast, ignoreAnnotatedBy);
    }
}

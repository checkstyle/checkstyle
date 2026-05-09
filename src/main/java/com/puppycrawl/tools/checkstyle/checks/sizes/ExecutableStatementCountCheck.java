///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.sizes;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Pipeline;
import com.puppycrawl.tools.checkstyle.checks.pipeline.PipelineBuilder;
import com.puppycrawl.tools.checkstyle.checks.pipeline.filter.ThresholdFilter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.filter.TokenFilter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.filter.ViolationSink;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.ViolationMessage;
import com.puppycrawl.tools.checkstyle.checks.sizes.pipeline.ExecutableStatementCountMeasurementFilter;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Restricts the number of executable statements to a specified limit.
 * </div>
 *
 * @since 3.2
 */
@FileStatefulCheck
public final class ExecutableStatementCountCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "executableStatementCount";

    /** Default threshold. */
    private static final int DEFAULT_MAX = 30;

    /**
     * Preserved for backward-compatible state-clearing introspection tests.
     * Real per-file state lives inside
     * {@code ExecutableStatementCountMeasurementFilter}; this field is reset on
     * every {@link #beginTree(DetailAST)} so existing reflective tests
     * continue to see it returned to its default.
     */
    private final Deque<Object> contextStack = new ArrayDeque<>();

    /** Specify the maximum threshold allowed. */
    private int max = DEFAULT_MAX;

    private Pipeline<AstEvent, ViolationMessage> pipeline;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.SLIST,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.LAMBDA,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.SLIST};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.SLIST,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.LAMBDA,
        };
    }

    /**
     * Setter to specify the maximum threshold allowed.
     *
     * @param max the maximum threshold.
     * @since 3.2
     */
    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        contextStack.clear();
        pipeline = PipelineBuilder.<AstEvent>start()
                .add(new TokenFilter(getAcceptableTokens()))
                .add(new ExecutableStatementCountMeasurementFilter(max, MSG_KEY))
                .add(new ThresholdFilter(max))
                .addQueued(new ViolationSink())
                .build();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!isContainerNode(ast) && !TokenUtil.isOfType(ast, TokenTypes.SLIST)) {
            throw new IllegalStateException(ast.toString());
        }
        pipeline.submit(new AstEvent(ast, AstEvent.Phase.VISIT));
        drainAndLog();
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (!isContainerNode(ast) && !TokenUtil.isOfType(ast, TokenTypes.SLIST)) {
            throw new IllegalStateException(ast.toString());
        }
        pipeline.submit(new AstEvent(ast, AstEvent.Phase.LEAVE));
        drainAndLog();
    }

    private void drainAndLog() {
        while (pipeline.hasResults()) {
            final ViolationMessage v = pipeline.drain();
            log(v.getLine(), v.getCol(), v.getMessageKey(), v.getArgs());
        }
    }

    private static boolean isContainerNode(DetailAST node) {
        return TokenUtil.isOfType(node, TokenTypes.METHOD_DEF,
                TokenTypes.LAMBDA, TokenTypes.CTOR_DEF, TokenTypes.INSTANCE_INIT,
                TokenTypes.STATIC_INIT, TokenTypes.COMPACT_CTOR_DEF);
    }

}

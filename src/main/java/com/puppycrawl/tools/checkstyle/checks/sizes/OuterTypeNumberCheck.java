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
import com.puppycrawl.tools.checkstyle.checks.sizes.pipeline.OuterTypeNumberMeasurementFilter;

/**
 * <div>
 * Checks for the number of types declared at the <i>outer</i> (or <i>root</i>) level in a file.
 * </div>
 *
 * <p>
 * Rationale: It is considered good practice to only define one outer type per file.
 * </p>
 *
 * @since 5.0
 */
@FileStatefulCheck
public class OuterTypeNumberCheck extends AbstractCheck {

    public static final String MSG_KEY = "maxOuterTypes";

    private int max = 1;

    /**
     * Preserved for backward-compatible state-clearing introspection tests.
     * The real per-file state lives inside
     * {@code OuterTypeNumberMeasurementFilter}; these fields are reset on every
     * {@link #beginTree(DetailAST)} so existing reflective tests continue to
     * see them returned to their defaults.
     */
    private int currentDepth;

    private int outerNum;

    private Pipeline<AstEvent, ViolationMessage> pipeline;

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        currentDepth = 0;
        outerNum = 0;
        pipeline = PipelineBuilder.<AstEvent>start()
                .add(new TokenFilter(getRequiredTokens()))
                .add(new OuterTypeNumberMeasurementFilter(max, MSG_KEY))
                .add(new ThresholdFilter(max))
                .addQueued(new ViolationSink())
                .build();
        pipeline.submit(new AstEvent(rootAST, AstEvent.Phase.BEGIN_TREE));
        drainAndLog();
    }

    @Override
    public void visitToken(DetailAST ast) {
        pipeline.submit(new AstEvent(ast, AstEvent.Phase.VISIT));
        drainAndLog();
    }

    @Override
    public void leaveToken(DetailAST ast) {
        pipeline.submit(new AstEvent(ast, AstEvent.Phase.LEAVE));
        drainAndLog();
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        pipeline.submit(new AstEvent(rootAST, AstEvent.Phase.FINISH_TREE));
        drainAndLog();
    }

    private void drainAndLog() {
        while (pipeline.hasResults()) {
            final ViolationMessage v = pipeline.drain();
            log(v.getLine(), v.getCol(), v.getMessageKey(), v.getArgs());
        }
    }

    public void setMax(int max) {
        this.max = max;
    }
}

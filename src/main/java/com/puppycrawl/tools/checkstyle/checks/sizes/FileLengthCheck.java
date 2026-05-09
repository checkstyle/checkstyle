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

import java.io.File;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Pipeline;
import com.puppycrawl.tools.checkstyle.checks.pipeline.PipelineBuilder;
import com.puppycrawl.tools.checkstyle.checks.pipeline.filter.LineSplitterFilter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.filter.ThresholdFilter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.filter.ViolationSink;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.ViolationMessage;
import com.puppycrawl.tools.checkstyle.checks.sizes.pipeline.FileLengthMeasurementFilter;

/**
 * <div>
 * Checks for long source files.
 * </div>
 *
 * <p>
 * Rationale: If a source file becomes very long it is hard to understand.
 * Therefore, long classes should usually be refactored into several
 * individual classes that focus on a specific task.
 * </p>
 *
 * @since 3.2
 */
@StatelessCheck
public class FileLengthCheck extends AbstractFileSetCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxLen.file";

    /** Default maximum number of lines. */
    private static final int DEFAULT_MAX_LINES = 2000;

    /** Specify the maximum number of lines allowed. */
    private int max = DEFAULT_MAX_LINES;

    @Override
    protected void processFiltered(File file, FileText fileText) {
        final Pipeline<FileText, ViolationMessage> pipeline = PipelineBuilder.<FileText>start()
                .addQueued(new LineSplitterFilter())
                .addQueued(new FileLengthMeasurementFilter(max, MSG_KEY))
                .addQueued(new ThresholdFilter(max))
                .addQueued(new ViolationSink())
                .build();

        pipeline.submit(fileText);

        while (pipeline.hasResults()) {
            final ViolationMessage v = pipeline.drain();
            log(v.getLine(), v.getMessageKey(), v.getArgs());
        }
    }

    /**
     * Setter to specify the maximum number of lines allowed.
     *
     * @param length the maximum length of a Java source file
     * @since 3.2
     */
    public void setMax(int length) {
        max = length;
    }

}

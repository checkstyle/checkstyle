package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;

import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.FileLine;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Computes the length of a line, expanding tabs.
 */
public final class LineLengthMeasurementFilter implements Filter<FileLine, Measurement> {

    private final int tabWidth;
    private final int max;
    private final String messageKey;

    public LineLengthMeasurementFilter(int tabWidth, int max, String messageKey) {
        this.tabWidth = tabWidth;
        this.max = max;
        this.messageKey = messageKey;
    }

    @Override
    public void process(Pipe<FileLine> in, Pipe<Measurement> out) {
        while (in.hasNext()) {
            final FileLine line = in.read();
            if (line == null) {
                break;
            }
            final String text = line.getText();
            final int realLength = CommonUtil.lengthExpandedTabs(
                text, text.codePointCount(0, text.length()), tabWidth);

            out.write(new Measurement(null, line.getLineNo(), 0, realLength, messageKey, max, realLength));
        }
    }
}

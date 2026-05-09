package com.puppycrawl.tools.checkstyle.checks.pipeline.filter;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.FileLine;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Drops {@link FileLine} messages whose text matches a configured regex.
 * Used by {@code LineLengthCheck} to honour the {@code ignorePattern} property.
 */
public final class IgnorePatternFilter implements Filter<FileLine, FileLine> {

    private final Pattern pattern;

    public IgnorePatternFilter(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public void process(Pipe<FileLine> in, Pipe<FileLine> out) {
        while (in.hasNext()) {
            final FileLine line = in.read();
            if (line == null) {
                break;
            }
            if (pattern == null || !pattern.matcher(line.getText()).find()) {
                out.write(line);
            }
        }
    }
}

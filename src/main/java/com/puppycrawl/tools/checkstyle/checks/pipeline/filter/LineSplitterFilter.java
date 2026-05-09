package com.puppycrawl.tools.checkstyle.checks.pipeline.filter;

import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.FileLine;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Splits an incoming {@link FileText} into per-line {@link FileLine} messages
 * with 1-based line numbers, matching {@code AbstractFileSetCheck} conventions.
 */
public final class LineSplitterFilter implements Filter<FileText, FileLine> {

    @Override
    public void process(Pipe<FileText> in, Pipe<FileLine> out) {
        while (in.hasNext()) {
            final FileText fileText = in.read();
            if (fileText == null) {
                break;
            }
            final int size = fileText.size();
            for (int i = 0; i < size; i++) {
                out.write(new FileLine(i + 1, fileText.get(i)));
            }
        }
    }
}

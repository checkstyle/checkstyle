package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;

import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.FileLine;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Computes the number of lines in a file by consuming {@link FileLine}
 * messages. Emits a single {@link Measurement} when the pipe is exhausted.
 */
public final class FileLengthMeasurementFilter implements Filter<FileLine, Measurement> {

    private final int max;
    private final String messageKey;

    public FileLengthMeasurementFilter(int max, String messageKey) {
        this.max = max;
        this.messageKey = messageKey;
    }

    @Override
    public void process(Pipe<FileLine> in, Pipe<Measurement> out) {
        int count = 0;
        while (in.hasNext()) {
            final FileLine line = in.read();
            if (line == null) {
                break;
            }
            count++;
        }
        
        out.write(new Measurement(null, 1, 0, count, messageKey, count, max));
    }
}

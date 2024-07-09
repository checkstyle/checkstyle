package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static java.awt.Button.ABORT;
import static java.io.File.createTempFile;
// comments


// comments
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
// violation above 'Extra separation .* before 'javax.swing.WindowConstants.DISPOSE_ON_CLOSE''

// comments

import com.google.checkstyle.test.chapter2filebasic.rule21filename.InputFileName1;
// violation above ''com.google.checkstyle.test.*' should be separated .* by one line.'
import com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.SourceFileStructureTest;
// comments

import com.google.common.reflect.TypeToken;
// violation above 'Extra separation in import group before 'com.google.common.reflect.*''
import java.util.List;

// comments
import java.util.StringTokenizer;
// violation above 'Extra separation in import group before 'java.util.StringTokenizer''
// comments

// comments
import java.util.concurrent.AbstractExecutorService;
// violation above 'Extra separation .* before 'java.util.concurrent.AbstractExecutorService''

/** Some javadoc. */
public class InputOrderingAndSpacing5 {
}

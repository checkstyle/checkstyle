package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT;
// violation above '.* 'java.awt.Button.ABORT' .* Should be before 'java.io.File.createTempFile'.'

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
// violation above 'Extra separation .* before 'javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE''

import com.google.checkstyle.test.chapter2filebasic.rule21filename.FileNameTest;
import com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.SourceFileStructureTest;
import com.google.common.reflect.Invokable;
import java.util.List;


import java.util.StringTokenizer;
// violation above 'Extra separation in import group before 'java.util.StringTokenizer''

import java.util.concurrent.AbstractExecutorService;
// violation above 'Extra separation .* before 'java.util.concurrent.AbstractExecutorService''

/** Some javadoc. */
public class InputOrderingAndSpacing4 {
}

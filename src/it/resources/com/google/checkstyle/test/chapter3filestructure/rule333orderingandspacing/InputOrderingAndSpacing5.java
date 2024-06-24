package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static java.awt.Button.ABORT;
import static java.io.File.createTempFile;
// comments


// comments
import static javax.swing.WindowConstants.*;
// violation above 'Extra separation in import group before 'javax.swing.WindowConstants.*''

// comments

import com.google.checkstyle.test.chapter2filebasic.rule21filename.*;
// violation above ''com.google.checkstyle.test.*' should be separated .* by one line.'
import com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.*;
// comments

import com.google.common.reflect.*;
// violation above 'Extra separation in import group before 'com.google.common.reflect.*''
import java.util.List;

// comments
import java.util.StringTokenizer;
// violation above 'Extra separation in import group before 'java.util.StringTokenizer''
// comments

// comments
import java.util.concurrent.AbstractExecutorService;
// violation above 'Extra separation .* before 'java.util.concurrent.AbstractExecutorService''

public class InputOrderingAndSpacing5 {
}

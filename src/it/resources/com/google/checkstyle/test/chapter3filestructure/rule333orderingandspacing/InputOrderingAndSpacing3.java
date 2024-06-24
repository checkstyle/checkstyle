package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static java.awt.Button.ABORT;
import java.awt.Dialog;
// violation above ''java.awt.Dialog' should be separated from previous import group by one line.'
import static javax.swing.WindowConstants.*;
// violation above '.*'javax.swing.WindowConstants.*' .* wrong order. Should be in the 'STATIC' .*'

import com.google.checkstyle.test.chapter2filebasic.rule21filename.*;
// 2 violations above:
//                    'Extra separation in import group before .*'
//                    '.* Should be before 'java.awt.Dialog'.'
import com.google.common.reflect.*;
// violation above '.* 'com.google.common.reflect.*' .* Should be before 'java.awt.Dialog'.'
import com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.*;
// violation above '.* Should be before 'java.awt.Dialog'.'
import java.io.File;
import static java.io.File.createTempFile;
// violation above '.*'java.io.File.createTempFile' .* wrong order. Should be in the 'STATIC' .*'
import java.util.StringTokenizer;
import java.util.*;
// violation above '.* 'java.util.*' .* Should be before 'java.util.StringTokenizer'.'
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.*;
// violation above '.* 'java.util.concurrent.*' .* 'java.util.concurrent.AbstractExecutorService'.'

public class InputOrderingAndSpacing3 {
}

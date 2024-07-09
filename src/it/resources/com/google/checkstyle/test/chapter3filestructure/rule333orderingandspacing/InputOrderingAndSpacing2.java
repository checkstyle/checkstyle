package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT;
// violation above '.* 'java.awt.Button.ABORT' .* Should be before 'java.io.File.createTempFile'.'
import static javax.swing.WindowConstants.*;
// violation above 'Using the '.*' form of import should be avoided'

import java.util.List;
import java.util.StringTokenizer;
import java.util.*;
// 2 violations above:
//                    '.* 'java.util.*' .* Should be before 'java.util.StringTokenizer'.'
//                    'Using the '.*' form of import should be avoided'
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.*;
// 2 violations above:
//                    '.* Should be before 'java.util.concurrent.AbstractExecutorService'.'
//                    'Using the '.*' form of import should be avoided'

import com.google.checkstyle.test.chapter2filebasic.rule21filename.*;
// 3 violations above:
//                    'Extra separation in import group before .*'
//                    '.* Should be before 'java.util.concurrent.AbstractExecutorService'.'
//                    'Using the '.*' form of import should be avoided'

import com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.*;
// 3 violations above:
//                    'Extra separation in import group before .*'
//                    '.* Should be before 'java.util.concurrent.AbstractExecutorService'.'
//                    'Using the '.*' form of import should be avoided'

import com.google.common.reflect.*;
// 3 violations above:
//                    'Extra separation in import group before 'com.google.common.reflect.*''
//                    '.* Should be before 'java.util.concurrent.AbstractExecutorService'.'
//                    'Using the '.*' form of import should be avoided'

/** Some javadoc. */
public class InputOrderingAndSpacing2 {
}

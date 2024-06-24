package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT;
// violation above '.* 'java.awt.Button.ABORT' .* Should be before 'java.io.File.createTempFile'.'
import static javax.swing.WindowConstants.*;

import java.util.List;
import java.util.StringTokenizer;
import java.util.*;
// violation above '.* 'java.util.*' .* Should be before 'java.util.StringTokenizer'.'
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.*;
// violation above '.* 'java.util.concurrent.*' .* 'java.util.concurrent.AbstractExecutorService'.'

import com.google.checkstyle.test.chapter2filebasic.rule21filename.*;
// 2 violations above:
//                    'Extra separation in import group before .*'
//                    '.* Should be before 'java.util.concurrent.AbstractExecutorService'.'
import com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.*;
// violation above '.* Should be before 'java.util.concurrent.AbstractExecutorService'.'

import com.google.common.reflect.*;
// 2 violations above:
//                    'Extra separation in import group before 'com.google.common.reflect.*''
//                    '.* Should be before 'java.util.concurrent.AbstractExecutorService'.'

public class InputOrderingAndSpacing2 {
}

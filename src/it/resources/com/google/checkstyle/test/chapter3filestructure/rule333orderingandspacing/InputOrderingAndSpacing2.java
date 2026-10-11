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
  /** Some javadoc. */
  public static void main(String[] args) {
    try {
      createTempFile("temp", ".txt");
    } catch (Exception e) {
      e.printStackTrace();
    }
    int abortAction = ABORT;
    int closeOperation = EXIT_ON_CLOSE;

    List<String> list = new ArrayList<>();
    StringTokenizer tokenizer = new StringTokenizer("Hello World");

    AbstractExecutorService abstractExecutorService = new AbstractExecutorService() {
      @Override
      public void shutdown() {
      }

      @Override
      public List<Runnable> shutdownNow() {
        return null;
      }

      @Override
      public boolean isShutdown() {
        return false;
      }

      @Override
      public boolean isTerminated() {
        return false;
      }

      @Override
      public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) {
        return false;
      }

      @Override
      public void execute(Runnable command) {
      }
    };
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    TypeToken<?> typeToken = TypeToken.of(String.class);

    FileNameTest testing1 = new FileNameTest();

    SourceFileStructureTest testing2 = new SourceFileStructureTest();
  }
}

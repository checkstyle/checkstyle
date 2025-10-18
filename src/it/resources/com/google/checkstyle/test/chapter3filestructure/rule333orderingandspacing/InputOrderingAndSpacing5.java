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
  /** Some javadoc. */
  public static void main(String[] args) {
    // Use of static imports
    int abortAction = ABORT;
    try {
      createTempFile("temp", ".txt");
    } catch (Exception e) {
      e.printStackTrace();
    }
    int closeOperation = DISPOSE_ON_CLOSE;

    // Use of com.google classes
    InputFileName1 inputFileName1 = new InputFileName1();
    SourceFileStructureTest sourceFileStructureTest = new SourceFileStructureTest();
    TypeToken<?> typeToken = TypeToken.of(String.class);

    // Use of java.util classes
    List<String> list;
    StringTokenizer tokenizer = new StringTokenizer("Hello World");

    // Use of java.util.concurrent classes
    AbstractExecutorService abstractExecutorService = new AbstractExecutorService() {
      @Override
      public void shutdown() {
      }

      @Override
      public java.util.List<Runnable> shutdownNow() {
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
  }
}

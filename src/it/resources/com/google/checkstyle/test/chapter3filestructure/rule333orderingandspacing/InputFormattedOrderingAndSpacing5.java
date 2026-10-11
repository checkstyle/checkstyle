package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static java.awt.Button.ABORT;
import static java.io.File.createTempFile;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import com.google.checkstyle.test.chapter2filebasic.rule21filename.InputFileName1;
import com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.SourceFileStructureTest;
import com.google.common.reflect.TypeToken;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.AbstractExecutorService;

/** Some javadoc. */
public class InputFormattedOrderingAndSpacing5 {
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
    AbstractExecutorService abstractExecutorService =
        new AbstractExecutorService() {
          @Override
          public void shutdown() {}

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
          public void execute(Runnable command) {}
        };
  }
}

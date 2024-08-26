package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static java.awt.Button.ABORT;
import static java.io.File.createTempFile;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

import com.google.checkstyle.test.chapter2filebasic.rule21filename.FileNameTest;
import com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.SourceFileStructureTest;
import com.google.common.reflect.Invokable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.AbstractExecutorService;

/** Some javadoc. */
public class InputFormattedOrderingAndSpacing4 {
  /** Some javadoc. */
  public static void main(String[] args) {
    // Use of static imports
    try {
      createTempFile("temp", ".txt");
    } catch (Exception e) {
      e.printStackTrace();
    }
    int abortAction = ABORT;
    int closeOperation = DO_NOTHING_ON_CLOSE;

    // Use of com.google classes
    FileNameTest fileNameTest = new FileNameTest();
    SourceFileStructureTest sourceFileStructureTest = new SourceFileStructureTest();
    Invokable<?, ?> invokable = Invokable.from(Object.class.getDeclaredMethods()[0]);

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

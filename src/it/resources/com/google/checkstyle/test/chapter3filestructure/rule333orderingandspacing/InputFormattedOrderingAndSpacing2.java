package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static java.awt.Button.ABORT;
import static java.io.File.createTempFile;
import static javax.swing.WindowConstants.*; // violation '.*' form of import should be avoided'

import com.google.checkstyle.test.chapter2filebasic.rule21filename.*; // violation '.*'
import com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.*; // violation '.*'
import com.google.common.reflect.*; // violation 'Using the '.*' form of import should be avoided'
import java.util.*; // violation 'Using the '.*' form of import should be avoided'
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.*; // violation 'Using the '.*' form of import should be avoided'
import java.util.concurrent.AbstractExecutorService;

/** Some javadoc. */
public class InputFormattedOrderingAndSpacing2 {
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
          public boolean awaitTermination(long timeout, TimeUnit unit) {
            return false;
          }

          @Override
          public void execute(Runnable command) {}
        };
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    TypeToken<?> typeToken = TypeToken.of(String.class);

    FileNameTest testing1 = new FileNameTest();

    SourceFileStructureTest testing2 = new SourceFileStructureTest();
  }
}

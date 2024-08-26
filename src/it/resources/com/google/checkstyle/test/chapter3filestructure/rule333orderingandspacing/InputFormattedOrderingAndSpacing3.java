package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static java.awt.Button.ABORT;
import static java.io.File.createTempFile;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import com.google.checkstyle.test.chapter2filebasic.rule21filename.FileNameTest;
import com.google.checkstyle.test.chapter3filestructure.rule3sourcefile.SourceFileStructureTest;
import com.google.common.reflect.ImmutableTypeToInstanceMap;
import java.awt.Dialog;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ThreadFactory;

/** Some javadoc. */
public class InputFormattedOrderingAndSpacing3 {
  /** Some javadoc. */
  public static void main(String[] args) {
    // Use of static imports
    int abortAction = ABORT;
    int closeOperation = EXIT_ON_CLOSE;

    try {
      createTempFile("temp", ".txt");
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Use of java.awt and javax.swing classes
    Dialog dialog = new Dialog(new java.awt.Frame());

    // Use of java.io classes
    File file = new File("example.txt");

    // Use of java.util classes
    StringTokenizer tokenizer = new StringTokenizer("Hello World");
    LinkedHashMap<String, String> map = new LinkedHashMap<>();

    // Use of java.util.concurrent classes
    ThreadFactory threadFactory =
        new ThreadFactory() {
          @Override
          public Thread newThread(Runnable r) {
            return new Thread(r);
          }
        };
    AbstractExecutorService abstractExecutorService =
        new AbstractExecutorService() {
          @Override
          public void shutdown() {}

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
          public void execute(Runnable command) {}
        };

    // Use of com.google classes
    FileNameTest fileNameTest = new FileNameTest();
    ImmutableTypeToInstanceMap<Object> mapInstance = ImmutableTypeToInstanceMap.builder().build();
    SourceFileStructureTest sourceFileStructureTest = new SourceFileStructureTest();
  }
}

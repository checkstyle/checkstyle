package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;

/** Some javadoc. */
public class InputOneStatementPerLineTryResources {

  /** Some javadoc. */
  public void singleResource() throws IOException {
    try (OutputStream s1 = new PipedOutputStream()) {
      s1.flush();
    }
  }

  /** Some javadoc. */
  public void oneResourcePerLine() throws IOException {
    try (OutputStream s1 = new PipedOutputStream();
        OutputStream s2 = new PipedOutputStream()) {
      s1.flush();
    }
  }

  /** Some javadoc. */
  public void multipleResourcesOnSameLine() throws IOException {
    // violation below 'Only one statement per line allowed.'
    try (OutputStream s1 = new PipedOutputStream(); OutputStream s2 = new PipedOutputStream()) {
      s1.flush();
    }
  }

  /** Some javadoc. */
  public void lastTwoResourcesOnSameLine() throws IOException {
    try (OutputStream s1 = new PipedOutputStream();
        OutputStream s2 = new PipedOutputStream(); OutputStream s3 = new PipedOutputStream()) {
      // violation above 'Only one statement per line allowed.'
      s1.flush();
    }
  }

  /** Some javadoc. */
  public void nestedTryResources() throws IOException {
    try (OutputStream s1 = new PipedOutputStream()) {
      // violation below 'Only one statement per line allowed.'
      try (OutputStream s2 = new PipedOutputStream(); OutputStream s3 = new PipedOutputStream()) {
        s2.flush();
      }
    }
  }
}

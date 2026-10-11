package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static java.awt.Button.ABORT;
import static java.io.File.createTempFile;
import static javax.swing.WindowConstants.*; // violation '.*' form of import should be avoided'

import com.google.common.base.Ascii;
import java.awt.*; // violation 'Using the '.*' form of import should be avoided'
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import javax.swing.*; // violation 'Using the '.*' form of import should be avoided'

/** Some javadoc. */
public class InputFormattedOrderingAndSpacing1 {
  /** Some javadoc. */
  public static void main(String[] args) {
    // Use of static imports
    try {
      File tempFile = createTempFile("temp", ".txt");
    } catch (IOException e) {
      e.printStackTrace();
    }
    int abortAction = ABORT;

    Frame frame = new Frame();

    JTable table = new JTable();
    int closeOperation = EXIT_ON_CLOSE;

    File file = new File("example.txt");
    InputStream inputStream = System.in;
    Reader reader = null;

    char ascii = Ascii.BS;
  }
}

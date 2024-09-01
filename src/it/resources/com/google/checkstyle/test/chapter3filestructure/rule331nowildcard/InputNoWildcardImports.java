package com.google.checkstyle.test.chapter3filestructure.rule331nowildcard;

import static java.io.File.createTempFile;
import static java.io.File.listRoots;
import static javax.swing.WindowConstants.*;
// violation above '.* '.*' form of import should be avoided - javax.swing.WindowConstants.*.'

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Label;
import java.io.*; // violation 'Using the '.*' form of import should be avoided - java.io.*.'
import java.sql.Connection;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneLayout;

class InputNoWildcardImports {
  public static void main(String[] args) {
    // Use of static imports
    try {
      createTempFile("temp", ".txt");
      File[] roots = listRoots();
    } catch (IOException e) {
      e.printStackTrace();
    }
    int closeOperation = EXIT_ON_CLOSE;

    // Use of java.awt classes
    Component component;
    Graphics2D graphics2D;
    Label label = new Label();
    HeadlessException headlessException = new HeadlessException();

    // Use of java.io classes
    FileReader fileReader;
    BufferedReader bufferedReader;
    InputStream inputStream;
    OutputStream outputStream;

    // Use of java.lang classes
    String string = new String("Hello");
    Object obj = new Object();

    // Use of java.sql classes
    Connection connection;

    // Use of java.util classes
    List<String> list = Arrays.asList("One", "Two", "Three");
    BitSet bitSet = new BitSet();
    Calendar calendar = Calendar.getInstance();
    Date date = new Date();
    Enumeration<String> enumeration;
    Iterator<String> iterator;

    // Use of javax.swing classes
    JToggleButton toggleButton = new JToggleButton();
    JToolBar toolBar = new JToolBar();
    ScrollPaneLayout scrollPaneLayout = new ScrollPaneLayout();
    BorderFactory.createEmptyBorder();
  }
}

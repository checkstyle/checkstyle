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

class InputFormattedNoWildcardImports {
  public static void main(String[] args) {
    try {
      createTempFile("temp", ".txt");
      File[] roots = listRoots();
    } catch (IOException e) {
      e.printStackTrace();
    }
    int closeOperation = EXIT_ON_CLOSE;

    Component component;
    Graphics2D graphics2D;
    Label label = new Label();
    HeadlessException headlessException = new HeadlessException();

    FileReader fileReader;
    BufferedReader bufferedReader;
    InputStream inputStream;
    OutputStream outputStream;

    String string = new String("Hello");
    Object obj = new Object();

    Connection connection;

    List<String> list = Arrays.asList("One", "Two", "Three");
    BitSet bitSet = new BitSet();
    Calendar calendar = Calendar.getInstance();
    Date date = new Date();
    Enumeration<String> enumeration;
    Iterator<String> iterator;

    JToggleButton toggleButton = new JToggleButton();
    JToolBar toolBar = new JToolBar();
    ScrollPaneLayout scrollPaneLayout = new ScrollPaneLayout();
    BorderFactory.createEmptyBorder();
  }
}

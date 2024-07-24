package com.google.checkstyle.test.chapter3filestructure.rule331nowildcard;

import static java.io.File.createTempFile;
import static java.io.File.listRoots;
import static javax.swing.WindowConstants.*;
// violation above '.* '.*' form of import should be avoided - javax.swing.WindowConstants.*.'
import static javax.swing.WindowConstants.*;
// violation above '.* '.*' form of import should be avoided - javax.swing.WindowConstants.*.'

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Label;
import java.io.*; // violation 'Using the '.*' form of import should be avoided - java.io.*.'
import java.lang.*; // violation 'Using the '.*' form of import should be avoided - java.lang.*.'
import java.lang.String;
import java.sql.Connection;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneLayout;

class InputNoWildcardImports {}

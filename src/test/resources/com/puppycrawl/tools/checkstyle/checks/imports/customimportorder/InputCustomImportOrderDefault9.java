/*
CustomImportOrder
customImportOrderRules = (default)
standardPackageRegExp = (default)^(java|javax)\\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT;
import static java.awt.print.Paper.*;
import static javax.swing.WindowConstants.*;

import java.awt.Button; // violation 'Extra separation in import group before .*'
import java.awt.Frame;
import java.awt.Dialog;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.io.*; // violation 'Extra separation in import group before .*'

import com.google.common.collect.*; // violation 'Extra separation in import group before .*'
import org.junit.*;

public class InputCustomImportOrderDefault9 {
}

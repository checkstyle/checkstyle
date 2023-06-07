package com.puppycrawl.tools.checkstyle.api.fullident;
import java.awt.Button; // ok
import java.awt.Frame; // ok
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import java.awt.event.ActionEvent; // ok
import javax.swing.JComponent; // violation 'Wrong order for 'javax.swing.JComponent' import.'
import javax.swing.JTable; // ok

public class InputFullIdent {
    int a = 10;
}

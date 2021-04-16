package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
/*
 * Config: default
 */
public class InputVariableDeclarationUsageDistanceAnonymous {
    public void method() {
        JMenuItem prefs = new JMenuItem("Preferences..."); // violation

        nothing();
        nothing();
        nothing();
        prefs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    public void nothing() {
    }
}

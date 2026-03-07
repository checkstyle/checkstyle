/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreFinal = (default)true
ignoreVariablePattern = (default)
validateBetweenScopes = (default)false



*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;

public class InputVariableDeclarationUsageDistanceAnonymous {
    public void method() {
        JMenuItem prefs = new JMenuItem("Preferences..."); // violation 'Distance .* is 4.'

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

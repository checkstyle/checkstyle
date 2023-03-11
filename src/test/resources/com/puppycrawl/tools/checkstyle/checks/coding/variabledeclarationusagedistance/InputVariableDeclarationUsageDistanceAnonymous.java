/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = (default)false
ignoreFinal = (default)true


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

    void methodTry() {
        String a = "";
        String b = "abc";
        nothing();
        nothing();
        nothing();
        try (AutoCloseable i = new java.io.StringReader(a)) {
            b.replace(a.charAt(0),'b');
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

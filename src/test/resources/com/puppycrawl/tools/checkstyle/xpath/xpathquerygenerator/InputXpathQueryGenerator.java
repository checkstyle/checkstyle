//Test Comment
package com.puppycrawl.tools.checkstyle.xpath.xpathquerygenerator;

import javax.swing.JToolBar;
import java.io.File;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Iterator;

import static java.io.File.listRoots;

public class InputXpathQueryGenerator {

    private Class mUse1 = Connection.class;
    private Class mUse2 = java.io.File.class;
    private Class mUse3 = Iterator[].class;
    private Class mUse4 = java.util.Enumeration[].class;
    private String ftpClient = null;

    {
        int[] x = {};
        Arrays.sort(x);
        Object obj = javax.swing.BorderFactory.createEmptyBorder();
        File[] files = listRoots();
    }

    private JToolBar.Separator mSep = null;

    private Object mUse5 = new Object();

    private Object mUse6 = new javax.swing.JToggleButton.ToggleButtonModel();

    private int Component;

    public void Label() {
        int i = 23;
        switch (i) {
            default:
                break;
            case 1:
                break;
        }
    }

    public void callSomeMethod() {
        int variable = 123;
        String another = "HelloWorld";
        String[] array = new String[3];
        for (String cycle : array) {
            char a = 'b';
            char b = a;
            byte c = 1;
            short d = 1;
        }
    }

    /**
     * Returns if current node has children.
     * @return if current node has children
     */
    public String getSomeMethod() {
        return "HelloWorld";
    }

    static void foo() {
        for (int i = 0; i < 10; i++, i+=2) {

        }
        return;
    }

    private boolean saveUser(String name, String surname, int age) {
        return true;
    }
}

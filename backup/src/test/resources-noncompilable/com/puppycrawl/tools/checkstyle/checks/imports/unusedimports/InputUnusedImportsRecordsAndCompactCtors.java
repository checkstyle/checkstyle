/*
UnusedImports
processJavadoc = (default)true


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JToolBar; // violation
import javax.swing.JToggleButton; // violation

import org.w3c.dom.Node;

public class InputUnusedImportsRecordsAndCompactCtors {

    /**
     * {@link List}
     */
    record TestRecord1() {
        // import usage in record body
        static HashMap<String, Node> hashMap;
        static ArrayDeque<Integer> arrayDeque;

        /**
         * {@link Date}
         */
        public TestRecord1{}
    }

    record TestRecord2() {

        // import usage in compact ctor
        public TestRecord2{
            Arrays arrays;
            Iterator<String> it;
        }

        TestRecord2(LinkedList<String> link) {
            this();
        }
    }

    record TestRecord3(LinkedHashSet<Integer> lhs) {

    }

}

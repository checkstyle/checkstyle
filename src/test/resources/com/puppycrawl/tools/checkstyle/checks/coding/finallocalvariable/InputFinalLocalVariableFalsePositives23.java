/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.util.ArrayList;
import java.util.Arrays;

public class InputFinalLocalVariableFalsePositives23 {

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/mapred/TableRecordReaderImpl.java
    private void foo86() {
        String result;
        try {
            try {
                result = "";
                if (true) {
                    if (true) {
                    }
                }
            }
            catch (Exception e) {
                if (true) {
                }
                if (true) {
                }
                else {
                }
                result = "";
            }
        }
        catch (Exception ex) {
        }
    }

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/ipc/RpcServer.java
    private void foo87() {
        int count;
        if (true) {
            return;
        }
        try {
            count = 2;
        }
        catch (Exception ieo) {
            throw ieo;
        }
        catch (Error e) {
            count = -1;
        }
    }

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/ipc/RpcServer.java
    private void foo88() {
        String c;
        synchronized (this) {
            try {
                c = "";
            }
            catch (Exception e) {
                return;
            }
        }
        if (true) {
            if (true)
                c = null;
        }
    }

    private void foo89() {
        final int a = 8;
        String b; // violation "Variable 'b' should be declared final"
        switch (a) {
            case 8:
                b = "b";
                break;
            default:
                b = "c";
                break;
        }
    }

    private void foo90() {
        final int a = 8;
        String b;
        switch (a) {
            default:
                b = "c";
        }
        b = "b";
    }

    private void foo91() {
        Integer[] s;
        if (true) {
            s = new Integer[0];
        } else {
            s = new Integer[5];
            if (true) {
                final ArrayList<Integer> retain = new ArrayList<>();
                for (final Integer c : s) {
                    if (true) {
                        retain.add(c);
                    }
                }
                s = retain.toArray(new Integer[retain.size()]);
            }
            if (true && s.length > 0) {
                s = new Integer[] {s[0]};
            }
        }
        Arrays.toString(s);
    }
}

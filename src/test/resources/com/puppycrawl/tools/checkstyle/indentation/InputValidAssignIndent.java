package com.puppycrawl.tools.checkstyle.indentation;

public class InputValidAssignIndent
{
    void foo(String[] args)
    {
        int i = 1 +
            2 +
            3;
        String line = mIndentCheck[
            getLineNo()];
        String line1 =
            getLine();
        line1 =
            getLine();
        int i1 
            =
            1;
        i = 3;

        Integer brace =
            (candidate == SLIST)
            ? candidate : null;

        AnInterfaceFooWithALongName f =
            new AnInterfaceFooWithALongName() {
                public void bar() {
                }
            };

        AnInterfaceFooWithALongName f1
            = new AnInterfaceFooWithALongName() {
                public void bar() {
                }
            };
// XXX: need to be fixed
//         function.lastArgument().candidate = parameters;
//         function.lastArgument().candidate
//             =
//             parameters;
        // TODO: add more testing
    }

    private interface AnInterfaceFooWithALongName {
        void bar();
    }

    private static final int SLIST = 1;
    private static final int parameters = 1;
    int candidate = 0;
    private String[] mIndentCheck = null;
    private InputValidAssignIndent function = null;
    int getLineNo() {
        return 1;
    }
    String getLine() {
        return "";
    }
    InputValidAssignIndent lastArgument() {
        return this;
    }
}

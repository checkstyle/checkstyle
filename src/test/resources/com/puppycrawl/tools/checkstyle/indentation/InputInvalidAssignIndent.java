package com.puppycrawl.tools.checkstyle.indentation;

public class InputInvalidAssignIndent
{
    void foo(String[] args)
    {
        String line = mIndentCheck[
          getLineNo()];
        String line1 =
          getLine();
        line1 =
          getLine();
        int i 
         =
          1;
        // TODO: this should be illegal.
        i =
            3;
        // TODO: add more testing
    }

    private String[] mIndentCheck = null;
    int getLineNo() {
        return 1;
    }
    String getLine() {
        return "";
    }
}

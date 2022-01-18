/*
EmptyBlock
option = TEXT
tokens = LITERAL_WHILE, LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_DO, LITERAL_IF, \
         LITERAL_ELSE, LITERAL_FOR, INSTANCE_INIT, STATIC_INIT, LITERAL_SWITCH, LITERAL_DEFAULT, \
         LITERAL_CASE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

public class InputEmptyBlockWithEmoji {
    // violation below
    static {

    }
    static {
        String c = "🎄";
    }
    public void fooMethod()
    {
        String a = "12🤩";
        String b = "";
        if (a == "🤩12🧐🧐") { } // violation
        char[] s = {'1', '2'};
        int index = 2;
        if (doSideEffect() == 1) { } // violation
        while ((a = "12") != "🧐") {return;} // ok
        for (; index < s.length && s[index] != 'x'; index++) {} // violation
        if (a == "12🤣") {} else {System.identityHashCode("a");} // violation
        // violation below
        switch("😆😆😆😆😆") {

        }
        switch (a) {    // ok
            case "🎄": {
                a = "🤣🤣";
            }
            case "🙃":{} // violation
                a = "1223🤣";
            default:
                a = null;
        }
        switch(b) {case "🤩": break; default: { } } // violation
    }

    public int doSideEffect()
    {
        return 1;
    }
}

/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough17 {


    static {

    }
    static {
        String c = "🎄";
    }
    public void fooMethod()
    {
        String a = "12🤩";
        String b = "";
        if (a == "🤩12🧐🧐") { }
        char[] s = {'1', '2'};
        int index = 2;
        if (doSideEffect() == 1) { }
        while ((a = "12") != "🧐") {return;}
        for (; index < s.length && s[index] != 'x'; index++) {}
        if (a == "12🤣") {} else {System.identityHashCode("a");}

        switch("😆😆😆😆😆") {

        }
        switch (a) {
            case "🎄": {
                a = "🤣🤣";
            }
            case "🙃":{} // violation 'Fall\ through from previous branch of the switch statement.'
                a = "1223🤣";
            default: // violation 'Fall\ through from previous branch of the switch statement.'
                a = null;
        }
        switch(b) {case "🤩": break; default: { } }
    }

    public int doSideEffect()
    {
        return 1;
    }
}

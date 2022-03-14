/*
LeftCurly
option = NL
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyWithEmojiNl
{ // OK
    static Runnable r1 = () -> {String.valueOf("🥳🎄!");};
    // violation above ''{' at column 32 should be on a new line.'
    private void method() { // violation ''{' at column 27 should be on a new line.'

        String a = "🧐🧐🧐";

        String b = "🧐🧐🧐ccvb";
        if (b.equals("🧐🧐")) { // violation ''{' at column 29 should be on a new line.'

        }
        if (b.equals("s🧐d🧐a")) { // violation ''{' at column 32 should be on a new line.'
        }

        while (b == "🧐🧐🧐") { } // violation ''{' at column 28 should be on a new line'
    }

    private void method2() { // violation ''{' at column 28 should be on a new line.'
        String x = "🎄🤣";

        try
        { // OK
            if (x.equals("🎄🤣"))
            { // OK

            }
            else if (!x.equals("🎄🤣")) { // violation ''{' at column 39 should be on a new line.'
                ;
            }
            else
            { // OK

            }
            switch (x)
            { // OK
                case "🤣🤣🤣":
                    break;
                default:
                { // OK
                    break;
                }
            }

            switch("🤣🤣🤣") { // violation ''{' at column 27 should be on a new line.'
                case "qw": {return; } // violation ''{' at column 28 should be on a new line.'
                default:
                {
                    return;
                }
            }
        }
        catch (Exception e)
        { // OK
        }
    }
}
enum InputLeftCurlyMethodEnumWithEmojiNl
{ // OK
    CONSTANT1("🧐🧐dsds🧐") { // violation ''{' at column 26 should be on a new line.'
        String method1() { return "sds🧐"; } // violation ''{' at column 26 should be on a new line.'
        void method2() { // violation ''{' at column 24 should be on a new line.'
        }
        String method3()
        { // OK
            return "sds🧐";
        }
        boolean                                                              method4()
        { // OK
            return "sds🧐".equals("🧐🧐dsds🧐");
        }
    };

    InputLeftCurlyMethodEnumWithEmojiNl (String b) {
        // violation above ''{' at column 52 should be on a new line.'
    }
}




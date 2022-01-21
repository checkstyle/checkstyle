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
    static Runnable r1 = () -> {String.valueOf("🥳 🎄 !");}; // violation ''{' should be on a new line.'

    private void method() { // violation ''{' should be on a new line.'

        String a = "🧐🧐🧐";

        String b = "🧐🧐🧐ccvb";
        if (b.equals("🧐🧐")) { // violation ''{' should be on a new line.'

        }
        if (b.equals("s🧐d🧐a")) { // violation ''{' should be on a new line.'
        }

        while (b == "🧐🧐🧐") { } // violation ''{' should be on a new line'
    }

    private void method2() { // violation ''{' should be on a new line.'
        String x = "🎄🤣";

        try
        { // OK
            if (x.equals("🎄🤣"))
            { // OK

            }
            else if (!x.equals("🎄🤣")) { // violation ''{' should be on a new line.'
                ;
            }
            else
            { // OK

            }
            switch (x)
            { // OK
                case "\uD83C\uDF84\uD83E\uDD23":
                    break;
                default:
                { // OK
                    break;
                }
            }

            switch("🤣🤣🤣") { // violation
                case "qw": {return; } // violation
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
    CONSTANT1("🧐🧐dsds🧐") { // violation
        String method1() { return "sds🧐"; } // violation
        void method2() { // violation ''{' should be on a new line.'
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

    InputLeftCurlyMethodEnumWithEmojiNl (String b) { // violation ''{' should be on a new line.'
    }
}




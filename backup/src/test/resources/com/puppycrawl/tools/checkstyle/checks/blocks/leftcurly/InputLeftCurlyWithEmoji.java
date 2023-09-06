/*
LeftCurly
option = (default)eol
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyWithEmoji {
    static Runnable r1 = () -> {String.valueOf("🥳 🎄!");};
    // violation above ''{' at column 32 should have line break after.'
    private void method() { // ok

        String a = "🧐🧐🧐";

        String b = "🧐🧐🧐ccvb";
        if (b.equals("🧐🧐")) { // ok

        }
        if (b.equals("s🧐d🧐a")) { // ok
        }

        while (b == "😂🥳") { /* ok */ }
    }

    private void method2() { // ok
        String x = "🎄🤣";

        try
        { // violation ''{' at column 9 should be on the previous line'
            if (x.equals("🎄🤣"))
            { // violation ''{' at column 13 should be on the previous line'

            }
            else if (!x.equals("🎄🤣")) { // ok
                ;
            }
            else
            { // violation ''{' at column 13 should be on the previous line'

            }
            switch (x)
            { // violation ''{' at column 13 should be on the previous line'
                case "\uD83C\uDF84\uD83E\uDD23":
                    break;
                default:
                { // violation ''{' at column 17 should be on the previous line'
                    break;
                }
            }

            if (x.equals("🎄🤣🎄     🤣"))  switch ("🤣🎄🤣🎄🤣") {
                    case "qw": { return; } // violation ''{' at column 32 should have line break.'
                    default: {
                        return;
                    }
                }
        }
        catch (Exception e)
        { // violation ''{' at column 9 should be on the previous line'
        }
    }
}
    enum InputLeftCurlyMethodEnumWithEmoji
    { // violation ''{' at column 5 should be on the previous line'
        CONSTANT1("🧐🧐dsds🧐") {
            void method1() {}//ok
            void method2() { //ok
            }
            void method3()
            { // violation ''{' at column 13 should be on the previous line'
            }
            void           /* 🧐🧐dsds🧐🧐🧐dsds🧐🧐🧐dsds🧐 { */                              method4()
            { // violation ''{' at column 13 should be on the previous line'
            }
        };

        private InputLeftCurlyMethodEnumWithEmoji (String s) { // ok
        }
    }

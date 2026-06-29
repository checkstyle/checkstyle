/*
RightCurly
forbidSingleLineMultiBlock = (default)false
option = (default)same
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, \
         LITERAL_ELSE, METHOD_DEF, LITERAL_DO


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyWithEmoji {

    static Runnable r = () -> {
        String.valueOf("Hello world 🥳 🎄 !!");
    };

    private void method2() {
        String x = "🎄🤣";
        { String y = "🎄🤣🎄🤣";}
        try {
            if (x.equals("🎄🤣")) {

            } // violation ''}' at column 13 should be on the same line as .*/else'

            else if (!x.equals("🎄🤣")) {
                ;
            } // violation ''}' at column 13 should be on the same line as .*/else'

            else {
                x = "🎄🤣";
            }
            switch (x) {
                case "\uD83C\uDF84\uD83E\uDD23":
                    break;
                default: {
                    break;
                }
            }

            switch ("🤣") {
                case "qw": {
                    return;
                }
                default: {
                    return;
                }
            }
        }
        // violation above ''}' at column 9 should be on the same line as .*/catch'

        catch (Exception e) {
        }
        while (x == "🎄") {
        }

    }

    public void foo3() {
        String a = "😆🤩";
        int i = 1;
        do {
            i++;
            String.CASE_INSENSITIVE_ORDER.equals(i + " ");
        } while (a.equals("🧐"));
    }

    String method4(String a) {
        if (a.equals("🎄")) a = "😆"; return "😆🤩"; }
    // violation above ''}' at column 50 should have line break before.'

    public void foo4() {String a = "😆🤩";}
    interface Interface3 {
        void display();

        interface Interface4 {
            default boolean foo5(){
                return "qwwe".equals("🎄🤣");}}
        // violation above ''}' at column 44 should have line break before.'
    }
    public void foo5() {

        do { // violation below ''}' at column 41 should have line break before.'
            String a = new String("🤣🤣");}
         while (true);
    }
}

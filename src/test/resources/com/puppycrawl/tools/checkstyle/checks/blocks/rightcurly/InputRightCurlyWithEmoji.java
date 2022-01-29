/*
RightCurly
option = (default)same
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, \
         LITERAL_ELSE, METHOD_DEF, LITERAL_DO


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyWithEmoji {

    static Runnable r = () -> {
        String.valueOf("Hello world 🥳 🎄 !!");
    }; // OK

    private void method2() {
        String x = "🎄🤣";
        { String y = "🎄🤣🎄🤣";} // OK
        try {
            if (x.equals("🎄🤣")) {

            } // violation
              // ''}' should be on the same line as the next part of a multi-block statement'
            else if (!x.equals("🎄🤣")) {
                ;
            } // violation
              // ''}' should be on the same line as the next part of a multi-block statement'
            else {
                x = "🎄🤣";
            } // OK
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
                } // OK
                default: {
                    return;
                }
            }
        }  // violation ''}' should be on the same line as the next part of a multi-block statement'
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
        } while (a.equals("🧐")); // OK
    }

    String method4(String a) {
        if (a.equals("🎄")) a = "😆"; return "😆🤩"; } // violation ''}' should have line break before.'


    public void foo4() {String a = "😆🤩";} // OK
    interface Interface3 {
        void display();

        interface Interface4 {
            default boolean foo5(){
                return "qwwe".equals("🎄🤣");}} // violation ''}' should have line break before.'
    }
    public void foo5() {

        do {
            String a = new String("🤣🤣");} // violation ''}' should have line break before.'
         while (true);
    }
}

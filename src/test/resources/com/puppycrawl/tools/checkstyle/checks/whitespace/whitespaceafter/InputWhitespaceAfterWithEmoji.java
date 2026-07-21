/*
WhitespaceAfter
tokens = (default)COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, \
         LITERAL_DO, LITERAL_FOR, LITERAL_FINALLY, LITERAL_RETURN, LITERAL_YIELD, \
         LITERAL_CATCH, DO_WHILE, ELLIPSIS, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_CASE, LAMBDA, LITERAL_WHEN

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

class InputWhitespaceAfterWithEmoji {

    private String[] emoji = new String[]{"🤩🎄" ,"🧐","🧐🧐", // 2 violations
        "🧐🧐"};

    void foo1() {

        int i = 0, count = 0;

        do {
            count += "🎄🧐".charAt(i) == "🤩🎄".charAt(i) ? 1 : 0;
        }
        while ("🎄🧐".equals("🎄🧐weqwe"));

        while ("🤩".isEmpty()) ;
        while ("🎄".equals("0sda"));
        while (true)
            if ("🎄🎄".equals("🎄dsaewwrrw🧐")) {
                String b = "🎄🎄";return; // violation '';' is not followed by whitespace'
            } else {
                if (!!"🎄🎄".equals("🎄dsaewwrrw🧐")) return;
            }
    }

    void foo2() {
        Object obj = ("🎄dsaewwrrw🧐");
        obj = (java.lang.
                Object)"🎄"; // violation ''typecast' is not followed by whitespace'
        obj = (java.lang.
            Object) "🎄";
    }

    void foo3() {
        char[] a = ("🎄🎄🎄" +
            "asd🧐").toString(
        ).toCharArray();

        for (int i = 0;i < 5 && emoji[i].equals("🎄");i++) { // 2 violations
        }

    }
}

/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = CLASS_DEF, METHOD_DEF, LITERAL_IF, LITERAL_ELSE, LITERAL_DO, LITERAL_WHILE, \
         LITERAL_FOR, STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyWithEmojiAloneOrSingleLine {
    private String a;
    private static String b;
    {  a = "🧐😏"; } // ok
    static { b = "😧🤩"; } // ok


    void method2(java.util.HashSet<String> set) {
        java.util.Map<String, String> map1 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "😧🤩");
            put("😧🤩", "second" + "😧🤩");
            put("polysa😧🤩gene", "lubricants");
            put("alpha", "betical🎄");} // violation ''}' at column 38 should be alone on a line.'
        };

    }

    String method4(String a) {
        if (a.equals("🎄")) ; return "😆🤩"; }
    // violation above ''}' at column 43 should be alone on a line.'
    void method5(String a, int b) {
        while ("👈🏻🧐".length() > 5) { ; } // ok

        if ("👉🏻👉🏼".isEmpty()) { ; } // ok

        do {b--;} while (a.equals("🤩")); // ok

        for (int i = 1; i < 10; i++) {
            if("🎄🎄🎄".charAt(i) == 's') i++; }
            // violation above ''}' at column 45 should be alone on a line.'
        java.util.List<String> list = new java.util.ArrayList<>();
        list.stream()
                .filter(e -> {return !e.isEmpty() && !"🤩🎄".equals(e);} )
                .collect(java.util.stream.Collectors.toList());
    }

    public void foo5() {

        do {
            String a = new String("🤣🤣");} // violation ''}' at column 41 should be alone on a line.'
        while (false);
        if(true) {
            String a = ("🤣🤣");} /*some*/ else{
            // violation above''}' at column 31 should be alone on a line.'
                return;
        }
    }
}

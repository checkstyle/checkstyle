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
    {  a = "🧐😏"; } // OK
    static { b = "😧🤩"; } // OK


    void method2(java.util.HashSet<String> set) {
        java.util.Map<String, String> map1 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "😧🤩");
            put("😧🤩", "second" + "😧🤩");
            put("polysa😧🤩gene", "lubricants");
            put("alpha", "betical🎄");} // violation ''}' should be alone on a line.'
        };

    }

    String method4(String a) {
        if (a.equals("🎄")) ; return "😆🤩gckh"; } // violation ''}' should be alone on a line.'

    void method5(String a, int b) {
        while ("👈🏻🧐".length() > 5) { ; } // OK

        if ("👉🏻👉🏼".isEmpty()) { ; } // OK

        do {b--;} while (a.equals("🤩")); // OK

        for (int i = 1; i < 10; i++) {
            if("🎄🎄🎄".charAt(i) == 's') i++; } // violation ''}' should be alone on a line.'

        java.util.List<String> list = new java.util.ArrayList<>();
        list.stream()
                .filter(e -> {return !e.isEmpty() && !"🤩🎄".equals(e);} )
                .collect(java.util.stream.Collectors.toList());
    }

    public void foo5() {

        do {
            String a = new String("🤣🤣");} // violation ''}' should be alone on a line.'
        while (false);
        if(true) {
            String a = ("🤣🤣");} /* some */ else { // violation ''}' should be alone on a line.'
                return;
        }
    }
}

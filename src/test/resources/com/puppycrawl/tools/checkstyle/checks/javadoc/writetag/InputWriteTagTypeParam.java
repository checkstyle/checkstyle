/*
WriteTag
tag = @param
tagFormat = \\S
tagSeverity = (default)info
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagTypeParam {
    // violation 3 lines below 'Javadoc tag @param=<T> type {@index i}   {@linkplain java.lang.String#trim() link}'
    // violation 3 lines below 'Javadoc tag @param=var1 <p><i>html</i></p> <h2>title</h2>'
    /**
     * @param <T> type {@index i}   {@linkplain java.lang.String#trim() link}
     * @param var1 <p><i>html</i></p> <h2>title</h2>
     * @param var2 see {@link java.lang.String#trim() variable} <!-- @@ -->
     * @param var3 {@return {@code null} or {@literal "x"}}
     */
    public <T> void method(int var1, int var2, int var3) {
    // violation 4 lines above 'Javadoc tag @param=var2 see {@link java.lang.String#trim() variable} <!-- @@ -->'
    // violation 4 lines above 'Javadoc tag @param=var3 {@return {@code null} or {@literal "x"}}'

    }
}

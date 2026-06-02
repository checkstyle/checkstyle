/*
WriteTag
tag = @param
tagFormat = \\S
tagSeverity = (default)info
tokens = INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF, METHOD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagTypeParam {
    // violation 4 lines below 'Javadoc tag @param=<T> type {@index i}'
    // violation 4 lines below 'Javadoc tag @param=<U> {@linkplain java.lang.String#trim() link}'
    // violation 4 lines below 'Javadoc tag @param=var1 <p><i>html</i></p> <h2>title</h2>'
    /**
     * @param <T> type {@index i}
     * @param <U> {@linkplain java.lang.String#trim() link}
     * @param var1 <p><i>html</i></p> <h2>title</h2>
     * @param var2 {@link java.lang.String#trim() variable}
     * @param var3 {@return {@code null} or {@literal "x"}}
     * @param var4     <!-- @@ -->
     */
    public <T, U> void method(int var1, int var2, int var3, int var4) {
    // violation 5 lines above 'Javadoc tag @param=var2 {@link java.lang.String#trim() variable}'
    // violation 5 lines above 'Javadoc tag @param=var3 {@return {@code null} or {@literal "x"}}'
    // violation 5 lines above 'Javadoc tag @param=var4     <!-- @@ -->'

    }
}

/*
WhitespaceBeforeEmptyBody
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, \
         CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF, \
         LITERAL_WHILE, LITERAL_FOR, LITERAL_DO, \
         STATIC_INIT, INSTANCE_INIT, \
         LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_SYNCHRONIZED, LITERAL_SWITCH, \
         LAMBDA, LITERAL_NEW


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

class InputWhitespaceBeforeEmptyBodyTry {

    void test() {

        // violation below 'Whitespace is not present before the empty body of 'try''
        try{}
        catch (Exception e) {
            e.printStackTrace();
        }

        try {}
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            riskyOp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void riskyOp() throws Exception {}
}

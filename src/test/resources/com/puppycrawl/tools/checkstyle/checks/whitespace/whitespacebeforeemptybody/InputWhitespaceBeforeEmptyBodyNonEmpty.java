/*/*
WhitespaceBeforeEmptyBody
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, \
         CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF, \
         LITERAL_WHILE, LITERAL_FOR, LITERAL_DO, \
         STATIC_INIT, INSTANCE_INIT, \
         LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_SYNCHRONIZED, LITERAL_SWITCH, \
         LAMBDA, LITERAL_NEW


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodyNonEmpty {

    void method(){
        int x = 1;
    }

    class Inner{
        int field;
    }

    void testLoops() {
        boolean b = true;

        while (b){
            break;
        }

        for (int i = 0; i < 1; i++){
            continue;
        }

        do{
            break;
        } while (b);
    }

    static{
        int x = 1;
    }

    {
        int y = 2;
    }

    void testLambda() {
        Runnable r = () ->{
            int x = 1;
        };
    }
}

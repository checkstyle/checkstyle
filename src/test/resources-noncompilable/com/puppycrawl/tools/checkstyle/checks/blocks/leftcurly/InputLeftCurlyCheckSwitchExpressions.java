//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/* Config:
 *
 * option = eol
 * ignoreEnums = true
 * tokens = {ANNOTATION_DEF , CLASS_DEF , CTOR_DEF , ENUM_CONSTANT_DEF ,
 *  ENUM_DEF , INTERFACE_DEF , LAMBDA , LITERAL_CASE , LITERAL_CATCH ,
 *  LITERAL_DEFAULT , LITERAL_DO , LITERAL_ELSE , LITERAL_FINALLY ,
 * LITERAL_FOR , LITERAL_IF , LITERAL_SWITCH , LITERAL_SYNCHRONIZED ,
 *  LITERAL_TRY , LITERAL_WHILE , METHOD_DEF , OBJBLOCK , STATIC_INIT }
 */
public class InputLeftCurlyCheckSwitchExpressions {
    int howMany1(int k) {
        switch (k)
        { // violation
            case 1:
                { // violation

            }

            case 2:
                { // violation

            }

            case 3:
                { // violation

            }
            default:
                {   // violation

            }
        }
        return k;
    }

    int howMany2(int k) {
        return switch (k)
                { // violation
            case 1 ->
                    {// violation
                yield 2;
            }
            case 2 ->
                    {// violation
                yield 3;
            }
            case 3 ->
                    {// violation
                yield 4;
            }
            default ->
                    {// violation
                yield k;
            }
        };
    }
}

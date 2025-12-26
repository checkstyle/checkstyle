/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, DOT, LOR


*/

// someexamples of 1.5 extensions
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

@interface MyAnnotation1 {
    String name();
    int version();
}

@MyAnnotation1(name = "ABC", version = 1)
public class InputUnnecessaryParentheses15Extensions
{

}

@MyAnnotation1(name = ("ABC" + "DEF"), version = (1)) // 2 violations
class AnnotationWithUnnecessaryParentheses
{

}

enum Enum2
{
    A, B, C;
    Enum2() {}
    public String toString() {
        return ""; //some custom implementation
    }
}

interface TestRequireThisEnum
{
    enum DAY_OF_WEEK
    {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    }
}

@MyAnnotation1(name = "ABC", version = 1)
class ExtraParenAfterAnnotation {
    boolean flag = ("abc" == "done"); // violation 'parentheses around assignment right-hand side'
}

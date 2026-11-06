/*
ParenPad
option = (default)nospace
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, ENUM_CONSTANT_DEF, \
         EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_NEW, LITERAL_SWITCH, \
         LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, METHOD_DEF, QUESTION, \
         RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF, RECORD_PATTERN_DEF, ASSIGN, \
         PLUS_ASSIGN, MINUS_ASSIGN, STAR_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, SR_ASSIGN, BSR_ASSIGN, \
         SL_ASSIGN, BAND_ASSIGN, BXOR_ASSIGN, BOR_ASSIGN, LAND, LOR, BAND, BOR, BXOR, EQUAL, \
         NOT_EQUAL, LT, LE, GT, GE, SL, SR, BSR, PLUS, MINUS, STAR, DIV, MOD, UNARY_PLUS, \
         UNARY_MINUS, LNOT, BNOT, INC, DEC, POST_INC, POST_DEC, INDEX_OP, METHOD_REF, \
         LITERAL_INSTANCEOF, TYPECAST


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.util.function.Consumer;

public class InputParenPadCheckEmoji {
    void emojiFamilyRunner1(Consumer<String> testRunner, String text) {
        testRunner.accept("👩‍👩‍👧‍👧 " + text);
    }

    void emojiFamilyWithSkinToneModifierRunner1(Consumer<String> testRunner, String text) {
        testRunner.accept("👩🏻‍👩🏽‍👧🏾‍👦🏿 " + text);
    }

    void emojiFamilyRunner2(Consumer<String> testRunner, String text) {
        testRunner.accept("👩‍👩‍👧‍👧 " + text ); // violation '')' is preceded with whitespace'
    }

    void emojiFamilyWithSkinToneModifierRunner2(Consumer<String> testRunner, String text) {
        testRunner.accept("👩🏻‍👩🏽‍👧🏾‍👦🏿 " + text ); // violation '')' is preceded with whitespace'
    }

    void emojiFamilyRunner3(Consumer<String> testRunner, String text) {
        testRunner.accept( "👩‍👩‍👧‍👧 " + text); // violation ''(' is followed by whitespace'
    }

    void emojiFamilyWithSkinToneModifierRunner3(Consumer<String> testRunner, String text) {
        testRunner.accept( "👩🏻‍👩🏽‍👧🏾‍👦🏿 " + text); // violation ''(' is followed by whitespace'
    }

    void emojiFamilyWithSkinToneModifierRunnerTricky(Consumer<String> testRunner, String text) {
        testRunner
        .accept
        ( "👩🏻‍👩🏽‍👧🏾‍👦🏿 "  + "ab cdefg" + "👩🏽‍👧🏾‍👦🏿 " + text );
        // 2 violations above:
        //           ''(' is followed by whitespace.'
        //           '')' is preceded with whitespace.'
    }
}

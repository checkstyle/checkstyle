/*
ParenPad
option = (default)nospace
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, \
         ENUM_CONSTANT_DEF, EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, \
         LITERAL_NEW, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, \
         METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.util.function.Consumer;

public class InputParenPadCheckEmoji {
    void emojiFamilyRunner1(Consumer<String> testRunner, String text) {
        testRunner.accept("👩‍👩‍👧‍👧 " + text); // ok
    }

    void emojiFamilyWithSkinToneModifierRunner1(Consumer<String> testRunner, String text) {
        testRunner.accept("👩🏻‍👩🏽‍👧🏾‍👦🏿 " + text); // ok
    }

    void emojiFamilyRunner2(Consumer<String> testRunner, String text) {
        testRunner.accept("👩‍👩‍👧‍👧 " + text ); // violation
    }

    void emojiFamilyWithSkinToneModifierRunner2(Consumer<String> testRunner, String text) {
        testRunner.accept("👩🏻‍👩🏽‍👧🏾‍👦🏿 " + text ); // violation
    }

    void emojiFamilyRunner3(Consumer<String> testRunner, String text) {
        testRunner.accept( "👩‍👩‍👧‍👧 " + text); // violation
    }

    void emojiFamilyWithSkinToneModifierRunner3(Consumer<String> testRunner, String text) {
        testRunner.accept( "👩🏻‍👩🏽‍👧🏾‍👦🏿 " + text); // violation
    }

    void emojiFamilyWithSkinToneModifierRunnerTricky(Consumer<String> testRunner, String text) {
        testRunner.accept( "👩🏻‍👩🏽‍👧🏾‍👦🏿 "  + "ab cdefg" + "👩🏽‍👧🏾‍👦🏿 " + text ); // 2 violations
    }
}

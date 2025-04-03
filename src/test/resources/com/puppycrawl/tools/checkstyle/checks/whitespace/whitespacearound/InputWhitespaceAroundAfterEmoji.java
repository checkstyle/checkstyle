/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = true
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = true
ignoreEnhancedForColon = (default)true
allowEmptySwitchBlockStatements = (default)false
tokens = ASSIGN, ARRAY_INIT, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, \
         BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, LAMBDA, LAND, LCURLY, \
         LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, \
         LITERAL_WHILE, LOR, LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, NOT_EQUAL, PLUS, \
         PLUS_ASSIGN, QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR, SR_ASSIGN, STAR, \
         STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND, WILDCARD_TYPE, GENERIC_START, \
         GENERIC_END, ELLIPSIS


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundAfterEmoji {
    String a = "🎄❤️😂" + "🎅🔥😊🎁";
    String b = "🎄❤️😂"+ "🎅🔥😊🎁"; // violation ''\+' is not preceded with whitespace'
    String c = "🎄❤️😂" +"🎅🔥😊🎁"; // violation ''\+' is not followed by whitespace'
    String d = "🎄❤️😂"+"🎅🔥😊🎁"; // 2 violations
    String e = "🎄" + "❤" + "️😂" + "🎅" + "🔥" + "😊" + "🎁";
    String f = "🎄"+"❤"+"️😂"+"🎅"+"🔥"+"😊"+"🎁"; // 12 violations
}

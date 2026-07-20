/*
JavadocType
scope = (default)private
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF
authorFormat = (default)(null)
excludeScope = (default)(null)
versionFormat = (default)(null)

*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 *
 * @param <T> stuff <stuff>
 * @param value
 */
public record InputJavadocTypeRecordParamDescriptionWithAngularTags<T>(String value) {

}

/**
 *
 * @param    <T>    🐦‍🔥stuff<stuff>stu🐦‍🔥ff</stuff>stuff🐦‍🔥
 * @param     a     <stuff>
 */
record Record1<T>(int a) {}

/**
 *
 * @param <T> &lt;stuff&gt;
 * @param a stUff&lt;stuff&gt;sutff&lt;🐦‍🔥&gt;🐦‍🔥
 * @param b <>>>>><<<<
 */
record Record2<T>(int a, int b) {














}

// violation 3 lines below 'Unused @param tag for \'<P>\'.'
/**
 * @param <T>
 * @param    <P>
 */
record Record5<T, U>() {} // violation, 'Type Javadoc comment is missing @param '<U>' tag.'

// violation 3 lines below 'Unused @param tag for \'region\'.'
/**
 *
 * @param region
 */
record Record6(int a) {} // violation, 'Type Javadoc comment is missing @param 'a' tag.'

/**
 *
 * @param <T>
 * @param a
 * @param b
 */
record Record7<T>(int a, int b) {}

// violation 4 lines below 'Unused @param tag for \'e\'.'
/**
 * @param a
 * @param b
 * @param e
 */
record Record8(int a, int b, int c) { // violation, 'missing @param 'c' tag.'
}

/**
 *
 * @param a
 */
record Record9(int a) {}

/**
 *
 * @param a <><><><><><><><>
 * @param b >><>>><><><<<><<
 * @param c {@code <{[(<stu<f>f>)]}>}
 */
record Record10(int a, int b, int c) {}

/**
 * One Transaction.
 *
 * @param transactionId  unique ID of the transaction in format: {@code <first Code>:<second Code>}
 */
record MyTransaction(String transactionId) {
}

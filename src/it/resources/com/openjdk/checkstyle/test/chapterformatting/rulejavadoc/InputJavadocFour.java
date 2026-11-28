package com.openjdk.checkstyle.test.chapterformatting.rulejavadoc;

// violation first line 'Header mismatch'

public class InputJavadocFour {

    // violation below 'Prefer link javadoc inline tag over HTML anchor that references code.'
    /**
     * See <a href="#validate()">validate</a> for details.
     */
    public void methodBad1() {
    }

    /**
     * See {@link #validate()} for details.
     */
    public void methodGood1() {
    }

    // violation below 'Prefer link javadoc inline tag over HTML anchor that references code.'
    /**
     * Formats the value as described in
     * <a href="../lang/String.html#format(java.lang.String,java.lang.Object...)">format</a>.
     */
    public void methodBad2() {
    }

    /**
     * Formats the value as described in
     * {@link String#format(String, Object...)}.
     */
    public void methodGood2() {
    }

    // violation below 'Prefer link javadoc inline tag over HTML anchor that references code.'
    /**
     * Returns a <a href="../util/List.html">list</a> of values.
     */
    public void methodBad3() {
    }

    /**
     * Returns a {@linkplain java.util.List list} of values.
     */
    public void methodGood3() {
    }

    // violation below 'Prefer link javadoc inline tag over HTML anchor that references code.'
    /**
     * Uses types from the <a href="package-summary.html">current package</a>.
     */
    public void methodBad4() {
    }

    /**
     * Uses types from the {@linkplain com.openjdk.checkstyle.test.chapterformatting.rulejavadoc
     * current package}.
     */
    public void methodGood4() {
    }

    // violation below 'Prefer link javadoc inline tag over HTML anchor that references code.'
    /**
     * Checks if <A HREF="#validate()">validation</A> passes.
     */
    public void methodBad5() {
    }

    // violation below 'Prefer link javadoc inline tag over HTML anchor that references code.'
    /**
     * Returns the <a class="type" href='Object.html'>object</a>.
     */
    public void methodBad6() {
    }

    /**
     * Describes the rules in <a href="#unmodifiable">unmodifiable views</a>
     * and <a href="Character.html#unicode">Unicode representation</a>.
     */
    public void methodGood5() {
    }

    /**
     * Links to <a href="https://docs.oracle.com/javase/specs/">specifications</a>,
     * <a href="doc-files/Example.html">extra documentation</a> and
     * <a href="{@docRoot}/java.base/java/lang/doc-files/ValueBased.html">value-based</a>.
     */
    public void methodGood6() {
    }

    /**
     * Validates the input.
     */
    public void validate() {
    }

}

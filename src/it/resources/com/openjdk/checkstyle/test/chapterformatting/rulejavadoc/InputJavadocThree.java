package com.openjdk.checkstyle.test.chapterformatting.rulejavadoc;

// violation first line 'Header mismatch'

public class InputJavadocThree {

    // 5 violations 7 lines below:
    //  'Prefer literal or code javadoc inline tag over '&amp;'.'
    //  'Prefer literal or code javadoc inline tag over '&quot;'.'
    //  'Prefer literal or code javadoc inline tag over '&apos;'.'
    //  'Prefer literal or code javadoc inline tag over '&lt;'.'
    //  'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * Prefer &amp; , &quot; , &apos; &lt; &gt; for special characters.
     */
    class Bad {
    }

    /**
     * Prefer {@literal &}, {@literal "}, {@literal '}, {@literal <}, {@literal >}
     * for special characters.
     */
    class Good {
    }

    // 2 violations 4 lines below:
    //  'Prefer literal or code javadoc inline tag over '&lt;'.'
    //  'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * Type parameter is &lt;E&gt; here.
     */
    public void methodBad1() {
    }

    /**
     * Type parameter is {@code <E>} here.
     */
    public void methodGood1() {
    }

    // 2 violations 4 lines below:
    //  'Prefer literal or code javadoc inline tag over '&lt;'.'
    //  'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * &lt;T&gt; generic type.
     */
    public void methodBad2() {
    }

    /**
     * {@code <T>} generic type.
     */
    public void methodGood2() {
    }

    // violation 2 lines below 'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * &gt; is the greater than sign.
     * &apos; and &quot; is used for quotes.
     */
    public void methodBad3() {
        // 2 violations 3 lines above:
        //  'Prefer literal or code javadoc inline tag over '&apos;'.'
        //  'Prefer literal or code javadoc inline tag over '&quot;'.'
    }

    /**
     * {@literal >} is the greater than sign.
     * {@literal '} and {@literal "} are used for quotes.
     */
    public void methodGood3() {
    }

    // 2 violations 4 lines below:
    //  'Prefer literal or code javadoc inline tag over '&lt;'.'
    //  'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * <b>&lt;T&gt;</b> bold generic.
     * <p> if (a &amp; b &gt; c) then do something. </p>
     */
    public void methodBad4() {
        // 2 violations 3 lines above:
        //  'Prefer literal or code javadoc inline tag over '&amp;'.'
        //  'Prefer literal or code javadoc inline tag over '&gt;'.'
    }

    /**
     * <b>{@code <T>}</b> bold generic.
     * <p> {@code if (a & b > c) then do something. } </p>
     */
    public void methodGood4() {
    }

    // violation 2 lines below 'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * <i>&gt;</i> italic greater than.
     */
    public void methodBad5() {
    }

    /**
     * <i>{@literal >}</i> italic greater than.
     */
    public void methodGood5() {
    }

}

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.util.Collections;

/**
 * Some explanation.
 * @param <A> A type param
 * @param <B1> Another type param
 * @author Nobody
 * @version 1.0
 */
public class InputJavadocMethodClassAliasTest<A,B1 extends Collections> {

    /**
     * Some explanation.
     *
     * @param <X>  A type param
     * @param <Y1> Another type param
     * @return a string
     */
    public <X extends B1, Y1> String doSomething() {
        return null;
    }

}

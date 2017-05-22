package com.puppycrawl.tools.checkstyle.checks.annotation;

/**
 * Created by ltudor on 3/28/17.
 */
public class WrongAnnotationAndModifierCombination {
    @SuppressWarnings("something")
    final int value = 1;
}

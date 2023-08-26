/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class InputRequireThisMethodReferences {
    private Set<String> tags = Collections.unmodifiableSortedSet(
        Arrays.stream(new String[] {"br", "li", "dt", "dd", "hr", "img", "p", "td", "tr", "th",})
            .collect(Collectors.toCollection(TreeSet::new)));

    public InputRequireThisMethodReferences(Set<String> tags) {
        tags = tags; // violation 'Reference to instance variable 'tags' needs "this.".'
    }

    public InputRequireThisMethodReferences() {
        this.tags = Arrays.stream(
            new String[] {"br", "li", "dt", "dd", "hr", "img", "p", "td", "tr", "th",})
            .collect(Collectors.toCollection(TreeSet::new));
    }
}

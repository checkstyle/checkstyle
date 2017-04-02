package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class InputRequireThisMetodReferences {
    private Set<String> tags = Collections.unmodifiableSortedSet(
        Arrays.stream(new String[] {"br", "li", "dt", "dd", "hr", "img", "p", "td", "tr", "th",})
            .collect(Collectors.toCollection(TreeSet::new)));

    public InputRequireThisMetodReferences(Set<String> tags) {
        tags = tags; // violation
    }

    public InputRequireThisMetodReferences() {
        this.tags = Arrays.stream(
            new String[] {"br", "li", "dt", "dd", "hr", "img", "p", "td", "tr", "th",})
            .collect(Collectors.toCollection(TreeSet::new));
    }
}

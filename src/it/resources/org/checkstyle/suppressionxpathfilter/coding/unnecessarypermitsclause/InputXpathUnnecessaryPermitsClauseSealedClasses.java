package org.checkstyle.suppressionxpathfilter.coding.unnecessarypermitsclause;

public sealed class InputXpathUnnecessaryPermitsClauseSealedClasses
        permits A, B { // warn
}

final class A extends InputXpathUnnecessaryPermitsClauseSealedClasses {
}

final class B extends InputXpathUnnecessaryPermitsClauseSealedClasses {
}

/*
UnnecessaryPermitsClause


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarypermitsclause;

public sealed class InputXpathUnnecessaryPermitsClauseSealedClasses
        permits A, B {
        // violation above "Unnecessary 'permits' clause for classes in the same compilation unit."
}

final class A extends InputXpathUnnecessaryPermitsClauseSealedClasses {
}

final class B extends InputXpathUnnecessaryPermitsClauseSealedClasses {
}

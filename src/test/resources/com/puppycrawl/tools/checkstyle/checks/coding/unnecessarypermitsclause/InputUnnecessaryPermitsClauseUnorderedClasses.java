/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseUnorderedClasses {
}

final class UnorderedChildA extends UnorderedSealedLater {
}

final class UnorderedChildB extends UnorderedSealedLater {
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed class UnorderedSealedLater permits UnorderedChildA, UnorderedChildB {
}

final class UnorderedPartialChild extends UnorderedSealedPartial {
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed class UnorderedSealedPartial permits UnorderedPartialChild {
}


final class UnorderedImpl1 implements UnorderedSealedInterface {
}

final class UnorderedImpl2 implements UnorderedSealedInterface {
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed interface UnorderedSealedInterface permits UnorderedImpl1, UnorderedImpl2 {
}

class UnorderedNormalClass {
}

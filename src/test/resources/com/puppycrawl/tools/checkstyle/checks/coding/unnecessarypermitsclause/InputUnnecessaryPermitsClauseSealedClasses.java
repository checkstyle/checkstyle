/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseSealedClasses {
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed class SealedA permits SealedAB, SealedAC {
}

final class SealedAB extends SealedA {
}

final class SealedAC extends SealedA {
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed interface SealedI permits SealedIImpl1, SealedIImpl2 {
}

final class SealedIImpl1 implements SealedI {
}

final class SealedIImpl2 implements SealedI {
}

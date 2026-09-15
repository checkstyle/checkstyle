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

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed interface SealedInterface permits SealedSubInterface {
}

non-sealed interface SealedSubInterface extends SealedInterface {
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed class SealedOuter1 permits SealedOuter1.InnerA1, SealedOuter1.InnerB1 {
    final class InnerA1 extends SealedOuter1 {}
    final class InnerB1 extends SealedOuter1 {}
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed interface SealedWithRecord permits RecordChild {}
record RecordChild() implements SealedWithRecord {}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed interface SealedWithEnum permits EnumChild {}
enum EnumChild implements SealedWithEnum { ONE; }

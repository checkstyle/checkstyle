/*
UnnecessaryPermitsClause


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseSealedClasses {
}

// violation below 'permits clause unnecessary for 'SealedA', all subclasses in same file.'
sealed class SealedA permits B, C {
}

final class B extends SealedA {
}

final class C extends SealedA {
}

// violation below 'permits clause unnecessary for 'SealedI', all subclasses in same file.'
sealed interface SealedI permits Impl1, Impl2 {
}

final class Impl1 implements SealedI {
}

final class Impl2 implements SealedI {
}

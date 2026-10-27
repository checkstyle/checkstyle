/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseSimpleNameCollision {
}

// the permitted subclass Sub is declared in the companion input file, the
// only Sub declared in this file is an unrelated nested class of Holder
sealed class SealedForeign permits Sub {
}

class Holder {
    static class Sub {
    }
}

class Outer {
    // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
    sealed class SealedInner permits SiblingSub {
    }

    final class SiblingSub extends SealedInner {
    }
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed class SealedQualifiedLocal permits
        com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause.QualifiedChild {
}

final class QualifiedChild extends SealedQualifiedLocal {
}

// the qualified name denotes a subclass declared in the companion input file
sealed class SealedQualifiedForeign permits
        CollisionCompanion.ForeignSub {
}

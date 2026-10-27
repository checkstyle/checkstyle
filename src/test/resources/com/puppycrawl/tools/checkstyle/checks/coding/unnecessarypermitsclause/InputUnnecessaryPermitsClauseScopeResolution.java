/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseScopeResolution {
}

class GrandOuter {
    static class Middle {
        // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
        static sealed class SealedDeep permits GrandSub {
        }
    }

    static final class GrandSub extends Middle.SealedDeep {
    }
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed class SealedQualifiedSibling permits SiblingHolder.SibSub {
}

class SiblingHolder {
    static final class SibSub extends SealedQualifiedSibling {
    }
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed class SealedQualifiedOwn permits SealedQualifiedOwn.OwnSub {
    static final class OwnSub extends SealedQualifiedOwn {
    }
}

// one entry is declared in this file, the other entry is the top level class
// in the companion input file, and the only MixedForeign declared in this
// file is an unrelated nested class, so the clause is necessary
sealed class SealedMixed permits LocalSub, MixedForeign {
}

final class LocalSub extends SealedMixed {
}

class MixedHolder {
    static class MixedForeign {
    }
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed interface SealedIface permits EnumImpl, RecordImpl {
}

enum EnumImpl implements SealedIface {
    INSTANCE
}

record RecordImpl() implements SealedIface {
}

// the qualified name denotes the top level class in the companion input
// file; the method local class with the same name below does not make the
// clause unnecessary, so nothing is reported
sealed class SealedQualifiedForeignTop permits
        com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause.ForeignTop {
}

class MethodLocalHolder {
    void method() {
        class ForeignTop {
        }
    }
}

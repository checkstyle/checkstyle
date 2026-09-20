/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseSubClassesInAnotherFile2 {
    final class ExternalChild
            extends InputUnnecessaryPermitsClauseSubClassesInAnotherFile.MixedSealedClass {
        ExternalChild() {
            super();
        }
    }

    final class ExternalImpl
            implements InputUnnecessaryPermitsClauseSubClassesInAnotherFile.MixedSealedInterface {
    }

    // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
    sealed class ClearStateSealed permits ClearStateSub {
    }

    final class ClearStateSub extends ClearStateSealed {
    }
}



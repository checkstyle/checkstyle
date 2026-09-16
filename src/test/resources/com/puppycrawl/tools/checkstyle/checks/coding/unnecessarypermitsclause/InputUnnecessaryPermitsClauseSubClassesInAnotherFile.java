/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

import com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause.InputUnnecessaryPermitsClauseSubClassesInAnotherFile2.ExternalChild;
import com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause.InputUnnecessaryPermitsClauseSubClassesInAnotherFile2.ExternalImpl;

public class InputUnnecessaryPermitsClauseSubClassesInAnotherFile {
    InputUnnecessaryPermitsClauseSubClassesInAnotherFile() {
    }

    static sealed class MixedSealedClass
            permits LocalChild, ExternalChild {
        MixedSealedClass() {
        }
    }

    final class LocalChild extends MixedSealedClass {
    }

    sealed interface MixedSealedInterface
            permits LocalImpl, ExternalImpl {
    }

    final class LocalImpl implements MixedSealedInterface {
    }

    final class ClearStateSub {
    }
}


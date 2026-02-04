/*
UnnecessaryPermitsClause


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseSubClassesInAnotherFile {

    sealed class MixedSealedClass
            permits LocalChild, ExternalChild {
    }

    final class LocalChild extends MixedSealedClass {
    }

    sealed interface MixedSealedInterface
            permits LocalImpl, ExternalImpl {
    }

    final class LocalImpl implements MixedSealedInterface {
    }
}

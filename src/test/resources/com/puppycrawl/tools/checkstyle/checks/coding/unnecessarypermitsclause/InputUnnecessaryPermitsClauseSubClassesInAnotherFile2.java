/*
UnnecessaryPermitsClause


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseSubClassesInAnotherFile2 {
    final class ExternalChild
            extends InputUnnecessaryPermitsClauseMixedFiles.MixedSealedClass {
    }

    final class ExternalImpl
            implements InputUnnecessaryPermitsClauseMixedFiles.MixedSealedInterface {
    }
}

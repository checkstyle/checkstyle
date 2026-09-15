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

    final record ExternalRecordChild()
            implements InputUnnecessaryPermitsClauseRecordsAndEnums.SealedWithExternalRecord {
    }
}

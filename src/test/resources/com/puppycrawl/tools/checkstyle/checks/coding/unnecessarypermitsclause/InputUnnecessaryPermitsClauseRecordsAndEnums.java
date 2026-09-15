/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

import com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause.InputUnnecessaryPermitsClauseSubClassesInAnotherFile2.ExternalRecordChild;

public class InputUnnecessaryPermitsClauseRecordsAndEnums {

    // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
    sealed interface SealedWithRecord permits RecordChild {
    }

    record RecordChild() implements SealedWithRecord {
    }

    // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
    sealed interface SealedWithEnum permits EnumChild {
    }

    enum EnumChild implements SealedWithEnum {
        ONE;
    }

    sealed interface SealedWithExternalRecord permits ExternalRecordChild {
    }
}

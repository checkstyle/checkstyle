/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

import com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause.InputUnnecessaryPermitsClauseStateLeakSecond.SharedSealedByName;
import com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause.InputUnnecessaryPermitsClauseStateLeakSecond.SharedSealedByPath;

public class InputUnnecessaryPermitsClauseStateLeakFirst {
}

final class SharedTopSub extends SharedSealedByName {
}

class SharedHolder {
    static final class SharedNestedSub extends SharedSealedByPath {
    }
}

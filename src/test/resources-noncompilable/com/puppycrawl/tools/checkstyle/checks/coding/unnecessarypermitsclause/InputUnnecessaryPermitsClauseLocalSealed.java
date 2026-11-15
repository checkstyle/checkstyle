/*
UnnecessaryPermitsClause

*/

// non-compiled with javac: sealed local classes are not allowed
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseLocalSealed {

    static class Member {
    }

    void method() {
        // the clause below names a member type of the class whose method
        // declares the local sealed type; member types are only visible
        // through enclosing object blocks, so the clause is necessary and
        // nothing is reported for it
        sealed class LocalSealed permits Member {
        }
    }
}

// violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
sealed class TopSealed permits TopSub {
}

final class TopSub extends TopSealed {
}

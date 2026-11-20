/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseStateLeakSecond {
    static sealed class SharedSealedByName permits SharedTopSub, LocalByNameSub {
    }

    static final class LocalByNameSub extends SharedSealedByName {
    }

    static sealed class SharedSealedByPath permits SharedHolder.SharedNestedSub, LocalByPathSub {
    }

    static final class LocalByPathSub extends SharedSealedByPath {
    }

    // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
    static sealed class OwnSealed permits OwnSub {
    }

    static final class OwnSub extends OwnSealed {
    }
}

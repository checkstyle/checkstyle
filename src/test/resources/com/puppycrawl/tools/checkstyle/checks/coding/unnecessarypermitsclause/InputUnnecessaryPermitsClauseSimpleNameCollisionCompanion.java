/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseSimpleNameCollisionCompanion {
}

final class Sub extends SealedForeign {
}

class CollisionCompanion {
    final class ForeignSub extends SealedQualifiedForeign {
    }
}

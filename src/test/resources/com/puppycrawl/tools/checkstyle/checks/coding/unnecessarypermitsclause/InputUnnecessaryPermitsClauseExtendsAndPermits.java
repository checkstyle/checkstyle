/*
UnnecessaryPermitsClause


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseExtendsAndPermits {

    // violation below 'permits clause unnecessary for 'Parent', all subclasses in same file.'
    sealed class Parent permits Base {
    }

    // violation below 'permits clause unnecessary for 'Base', all subclasses in same file.'
    sealed class Base extends Parent permits ChildA, ChildB {
    }

    final class ChildA extends Base {
    }

    final class ChildB extends Base {
    }
}

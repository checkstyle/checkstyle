/*
UnnecessaryPermitsClause


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseUnorderedClasses {
}

final class ChildA extends SealedLater {
}

final class ChildB extends SealedLater {
}

// violation below 'permits clause unnecessary for 'SealedLater', all subclasses in same file.'
sealed class SealedLater permits ChildA, ChildB {
}

final class PartialChild extends SealedPartial {
}

// violation below 'permits clause unnecessary for 'SealedPartial', all subclasses in same file.'
sealed class SealedPartial permits PartialChild {
}


final class Impl1 implements SealedInterface {
}

final class Impl2 implements SealedInterface {
}

// violation below 'permits clause unnecessary for 'SealedInterface', all subclasses in same file.'
sealed interface SealedInterface permits Impl1, Impl2 {
}

class NormalClass {
}

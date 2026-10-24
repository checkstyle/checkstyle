/*
UnnecessaryPermitsClause


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarypermitsclause;

public sealed class InputXpathUnnecessaryPermitsClauseMultipleSealedClasses
        permits FirstImpl, SecondImpl {
        // violation above "Unnecessary 'permits' clause for classes in the same compilation unit."
}

final class FirstImpl
        extends InputXpathUnnecessaryPermitsClauseMultipleSealedClasses {
}

final class SecondImpl
        extends InputXpathUnnecessaryPermitsClauseMultipleSealedClasses {
}

sealed class ServiceBase {
}

final class LocalService extends ServiceBase {
}

final class RemoteService extends ServiceBase {
}

sealed interface Processor {
}

final class SyncProcessor implements Processor {
}

final class AsyncProcessor implements Processor {
}

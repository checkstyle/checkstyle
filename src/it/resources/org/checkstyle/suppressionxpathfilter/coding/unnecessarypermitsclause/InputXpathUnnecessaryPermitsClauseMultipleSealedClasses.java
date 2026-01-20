package org.checkstyle.suppressionxpathfilter.coding.unnecessarypermitsclause;

public sealed class InputXpathUnnecessaryPermitsClauseMultipleSealedClasses
        permits FirstImpl, SecondImpl {    // warn
}

final class FirstImpl
        extends InputXpathUnnecessaryPermitsClauseMultipleSealedClasses {
}

final class SecondImpl
        extends InputXpathUnnecessaryPermitsClauseMultipleSealedClasses {
}

public sealed class ServiceBase {
}

final class LocalService extends ServiceBase {
}

final class RemoteService extends ServiceBase {
}

public sealed interface Processor {
}

final class SyncProcessor implements Processor {
}

final class AsyncProcessor implements Processor {
}

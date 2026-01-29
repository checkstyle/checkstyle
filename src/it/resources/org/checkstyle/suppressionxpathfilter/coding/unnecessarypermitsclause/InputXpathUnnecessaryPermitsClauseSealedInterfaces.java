package org.checkstyle.suppressionxpathfilter.coding.unnecessarypermitsclause;

public sealed interface InputXpathUnnecessaryPermitsClauseSealedInterfaces
        permits InternalService,   // warn
        ExternalService,
        DefaultService {
}

final interface InternalService
        extends InputXpathUnnecessaryPermitsClauseSealedInterfaces {
}

final interface ExternalService
        extends InputXpathUnnecessaryPermitsClauseSealedInterfaces {
}

final interface DefaultService
        extends InputXpathUnnecessaryPermitsClauseSealedInterfaces {
}

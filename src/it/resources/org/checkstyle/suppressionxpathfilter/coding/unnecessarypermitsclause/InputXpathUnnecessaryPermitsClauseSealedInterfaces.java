/*
UnnecessaryPermitsClause


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarypermitsclause;

public sealed interface InputXpathUnnecessaryPermitsClauseSealedInterfaces
        permits InternalService,
        // violation above "Unnecessary 'permits' clause for classes in the same compilation unit."
        ExternalService,
        DefaultService {
}

non-sealed interface InternalService
        extends InputXpathUnnecessaryPermitsClauseSealedInterfaces {
}

non-sealed interface ExternalService
        extends InputXpathUnnecessaryPermitsClauseSealedInterfaces {
}

non-sealed interface DefaultService
        extends InputXpathUnnecessaryPermitsClauseSealedInterfaces {
}

package org.checkstyle.suppressionxpathfilter.metrics.classfanoutcomplexity;

import java.util.Date;

public class InputXpathClassFanOutComplexityEnum {

}

enum MyEnum { // warn
    IN_PROGRESS, DONE, NEEDS_REVIEW;
    private Date startedOn;
}

package org.checkstyle.suppressionxpathfilter.classfanoutcomplexity;

import java.util.Date;

public class InputXpathClassFanOutComplexityEnum {

}

enum MyEnum { // warn
    IN_PROGRESS, DONE, NEEDS_REVIEW;
    private Date startedOn;
}

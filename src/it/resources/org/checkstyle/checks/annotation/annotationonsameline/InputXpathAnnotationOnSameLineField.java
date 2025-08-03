package org.checkstyle.checks.annotation.annotationonsameline;

import java.util.ArrayList;
import java.util.List;

public class InputXpathAnnotationOnSameLineField {
    @Deprecated //warn
    private List<String> names = new ArrayList<>();
}

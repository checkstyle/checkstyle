//non-compiled with javac: Compilable with Java10
package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* Config:
 * max  = 0
 */
class InputClassFanOutComplexityVar { // ok
    void method() {
        var x = 1;
    }
}

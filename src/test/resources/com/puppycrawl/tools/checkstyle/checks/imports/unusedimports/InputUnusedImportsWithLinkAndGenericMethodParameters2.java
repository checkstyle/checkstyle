/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

/**
 * {@link #method(List<BigDecimal>)}
 * {@link #method(Set<List<Integer>>)}
 * {@link #method(Class<? extends BigDecimal>)}
 * {@link #method(Class<? super RoundingMode>)}
 * {@link #method(Class<? extends HashMap>)}
 * {@link #method(Map<String, ? extends ArrayDeque>)}
 * {@link #method(   Set    )}
 * {@link #method(Map<String, ? super RoundingMode>)}
 * {@link #method(Source xmlSource, Result outputTarget)}
 * {@link #method(? extends List<? extends BigDecimal>)}
 * {@link #method(? super List<? super BigDecimal>)}
 */
public class InputUnusedImportsWithLinkAndGenericMethodParameters2 {

    public int calculate() {
        return 0;
    }

}

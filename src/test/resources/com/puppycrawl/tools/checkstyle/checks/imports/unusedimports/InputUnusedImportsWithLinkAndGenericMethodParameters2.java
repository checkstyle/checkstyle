/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * {@link #method(List<BigDecimal>)}
 * {@link #method(Set<List<Integer>>)}
 * {@link #method(Class<? extends BigDecimal>)}
 * {@link #method(Class<? super RoundingMode>)}
 * {@link #method(?)}
 * {@link #method(int>)}
 * {@link #method(ArrayList>)}
 * {@link #method(   Set    )}
 */
public class InputUnusedImportsWithLinkAndGenericMethodParameters2 {

    public int calculate() {
        return 0;
    }

}

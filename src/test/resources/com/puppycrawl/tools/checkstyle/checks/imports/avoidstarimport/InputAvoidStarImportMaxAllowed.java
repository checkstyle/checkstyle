/*
AvoidStarImport
excludes = (default)
maxAllowed = 1
allowClassImports = (default)false
allowStaticMemberImports = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstarimport;

import java.util.List;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.*; // violation, 'Using the '.*' form of import should be avoided.'

class InputAvoidStarImportMaxAllowed
{
    private List<String> field = null;

    public int method() {
        return min(1, 2);
    }
}

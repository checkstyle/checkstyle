package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.a.aa.AAClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.a.ab.ABClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.b.BClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.c.CClass;

public class InputClassFanOutComplexityExcludedPackagesCommonPackage { // total: 2 violations
    public AAClass aa; // violation
    public ABClass ab; // violation

    class Inner { // total: 2 violations
        public BClass b; // violation
        public CClass c; // violation
    }
}

class InputClassFanOutComplexityExcludedPackagesCommonPackageHidden { // total: 1 violation
    public CClass c; // violation
}

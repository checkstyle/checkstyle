package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.aa.AAClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.ab.ABClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.b.BClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.c.CClass;

public class InputClassFanOutComplexityExcludedPackagesDirectPackages { // total: 2 violations
    public AAClass aa; // violation
    public ABClass ab; // violation

    class Inner { // total: ok
        public BClass b; // ok
        public CClass c; // ok
    }
}

class InputClassFanOutComplexityExcludedPackagesDirectPackagesHidden { // total: ok
    public CClass c; // ok
}

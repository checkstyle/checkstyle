package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.aa.AAClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.ab.ABClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.b.BClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.c.CClass;

public class InputClassFanOutComplexityExcludedPackagesAllIgnored { // total: ok
    public AAClass aa; // ok
    public ABClass ab; // ok

    class Inner { // total: ok
        public BClass b; // ok
        public CClass c; // ok
    }
}

class InputClassFanOutComplexityExcludedPackagesAllIgnoredHidden { // total: ok
    public CClass c; // ok
}



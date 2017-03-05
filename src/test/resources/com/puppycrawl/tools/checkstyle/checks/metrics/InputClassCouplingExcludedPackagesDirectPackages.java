package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.aa.AAClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.ab.ABClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.b.BClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.c.CClass;

public class InputClassCouplingExcludedPackagesDirectPackages { // total: 2 violations
    public AAClass aa = new AAClass(); // violation
    public ABClass ab = new ABClass(); // violation

    class Inner { // total: ok
        public BClass b = new BClass(); // ok
        public CClass c = new CClass(); // ok
    }
}

class InputClassCouplingExcludedPackagesDirectPackagesHidden { // total: ok
    public CClass c = new CClass(); // ok
}

package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.aa.AAClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.ab.ABClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.b.BClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.c.CClass;

public class InputClassCouplingExcludedPackagesCommonPackage { // total: ok
    public AAClass aa = new AAClass(); // ok
    public ABClass ab = new ABClass(); // ok

    class Inner { // total: 2 violations
        public BClass b = new BClass(); // violation
        public CClass c = new CClass(); // violation
    }
}

class InputClassCouplingExcludedPackagesCommonPackageHidden { // total: 1 violation
    public CClass c = new CClass(); // violation
}

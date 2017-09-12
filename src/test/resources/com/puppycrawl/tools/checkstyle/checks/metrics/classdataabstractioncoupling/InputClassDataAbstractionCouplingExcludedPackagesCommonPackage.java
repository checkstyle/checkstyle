package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.a.aa.AAClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.a.ab.ABClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.b.BClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.c.CClass;

public class InputClassDataAbstractionCouplingExcludedPackagesCommonPackage { // total: ok
    public AAClass aa = new AAClass(); // ok
    public ABClass ab = new ABClass(); // ok

    class Inner { // total: 2 violations
        public BClass b = new BClass(); // violation
        public CClass c = new CClass(); // violation
    }
}

class InputClassDataAbstractionCouplingExcludedPackagesCommonPackageHidden { // total: 1 violation
    public CClass c = new CClass(); // violation
}

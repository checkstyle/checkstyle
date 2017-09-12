package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.a.aa.AAClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.a.ab.ABClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.b.BClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.c.CClass;

public class InputClassDataAbstractionCouplingExcludedPackagesAllIgnored { // total: ok
    public AAClass aa = new AAClass(); // ok
    public ABClass ab = new ABClass(); // ok

    class Inner { // total: ok
        public BClass b = new BClass(); // ok
        public CClass c = new CClass(); // ok
    }
}

class InputClassDataAbstractionCouplingExcludedPackagesAllIgnoredHidden { // total: ok
    public CClass c = new CClass(); // ok
}


/*
ParameterName
format = ^h$
ignoreOverridden = (default)false
accessModifiers = public


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameAccessModifier {

    public InputParameterNameAccessModifier(int pubconstr) {} // violation

    public void v1(int h) { // ok
        new Object () {
            public void i(int inner) {} // violation
        };
    }

    protected void v4(int h) {} // ok

    void v2(int h) {} // ok

    private void v3(int h) {} // ok

    public void i1(int pubpub) {} // violation

    protected void i4(int pubprot) {} // ok

    void i2(int pubpack) {} // ok

    private void i3(int pubpriv) {} // ok

    public interface InterfaceScope {
        void v1(int h);

        void i1(int pubifc); // violation
    }
}

class PrivateScope {

    public void v1(int h) {} // ok

    protected void v4(int h) {} // ok

    void v2(int h) {} // ok

    private void v3(int h) {} // ok

    public void i1(int packpub) {} // violation

    protected void i4(int packprot) {} // ok

    void i2(int packpack) {} // ok

    private void i3(int packpriv) { // ok
        try {
            /* Make sure catch var is ignored */
        } catch (Exception exc) {
        }
    }

    interface InterfaceScope {
        void v1(int h); // ok

        void i1(int packifc); // violation
    }

    interface FuncIfc {
        void a(int h); // ok
    }

    public void l() { // ok
        FuncIfc l1 = (int lexp)->{};

        FuncIfc l2 = (limp)->{};
    }
}



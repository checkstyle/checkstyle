/*
ParameterName
format = ^h$
ignoreOverridden = (default)false
accessModifiers = public


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameAccessModifier {

    public InputParameterNameAccessModifier(int pubconstr) {} // violation

    public void v1(int h) {
        new Object () {
            public void i(int inner) {} // violation
        };
    }

    protected void v4(int h) {}

    void v2(int h) {}

    private void v3(int h) {}

    public void i1(int pubpub) {} // violation

    protected void i4(int pubprot) {}

    void i2(int pubpack) {}

    private void i3(int pubpriv) {}

    public interface InterfaceScope {
        void v1(int h);

        void i1(int pubifc); // violation
    }
}

class PrivateScope {

    public void v1(int h) {}

    protected void v4(int h) {}

    void v2(int h) {}

    private void v3(int h) {}

    public void i1(int packpub) {} // violation

    protected void i4(int packprot) {}

    void i2(int packpack) {}

    private void i3(int packpriv) {
        try {
            /* Make sure catch var is ignored */
        } catch (Exception exc) {
        }
    }

    interface InterfaceScope {
        void v1(int h);

        void i1(int packifc); // violation
    }

    interface FuncIfc {
        void a(int h);
    }

    public void l() {
        FuncIfc l1 = (int lexp)->{};

        FuncIfc l2 = (limp)->{};
    }
}

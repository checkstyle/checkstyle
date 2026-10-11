/*
ParameterName
format = ^h$
ignoreOverridden = (default)false
accessModifiers = public


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameAccessModifier {

    // violation below 'Name 'constructorParam' must match pattern'
    public InputParameterNameAccessModifier(int constructorParam) {}

    public void v1(int h) {
        new Object () {
            public void i(int inner) {} // violation 'Name 'inner' must match pattern'
        };
    }

    protected void v4(int h) {}

    void v2(int h) {}

    private void v3(int h) {}

    // violation below 'Name 'publicParam' must match pattern'
    public void i1(int publicParam) {}

    protected void i4(int pubprot) {}

    void i2(int pubpack) {}

    private void i3(int pubpriv) {}

    public interface InterfaceScope {
        void v1(int h);

        // violation below 'Name 'interfaceParam' must match pattern'
        void i1(int interfaceParam);
    }
}

class PrivateScope {

    public void v1(int h) {}

    protected void v4(int h) {}

    void v2(int h) {}

    private void v3(int h) {}

    // violation below 'Name 'packageParam' must match pattern'
    public void i1(int packageParam) {}

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

        // violation below 'Name 'paramName' must match pattern'
        void i1(int paramName);
    }

    interface FuncIfc {
        void a(int h);
    }

    public void l() {
        FuncIfc l1 = (int lexp)->{};

        FuncIfc l2 = (limp)->{};
    }
}

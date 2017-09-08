package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameAccessModifier {

    public InputParameterNameAccessModifier(int pubconstr) {}

    public void v1(int h) {
        new Object () {
            public void i(int inner) {}
        };
    }

    protected void v4(int h) {}

    void v2(int h) {}

    private void v3(int h) {}

    public void i1(int pubpub) {}

    protected void i4(int pubprot) {}

    void i2(int pubpack) {}

    private void i3(int pubpriv) {}

    public interface InterfaceScope {
        void v1(int h);

        void i1(int pubifc);
    }
}

class PrivateScope {

    public void v1(int h) {}

    protected void v4(int h) {}

    void v2(int h) {}

    private void v3(int h) {}

    public void i1(int packpub) {}

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

        void i1(int packifc);
    }

    interface FuncIfc {
        void a(int h);
    }

    public void l() {
        FuncIfc l1 = (int lexp)->{};

        FuncIfc l2 = (limp)->{};
    }
}



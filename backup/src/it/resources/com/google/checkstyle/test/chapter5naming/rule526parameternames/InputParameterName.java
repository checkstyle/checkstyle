package com.google.checkstyle.test.chapter5naming.rule526parameternames;

import java.io.*;

class InputParameterName
{

    /** Some more Javadoc. */
    public void doSomething(int aaa, int abn, String aaA,
            boolean bB) //warn
    {
        for (Object O : new java.util.ArrayList())
        {

        }
    }
}


/** Test enum for member naming check */
enum MyEnum1
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;

    public void doEnum(int aaaL,
            long llll_llll, //warn
            boolean bB) {} //warn
}

/** Test public vs private method parameter naming check. */
class InputParameterNameSimplePub
{
    /** Valid: public and more than one char Long */
    public void a(int par, int parA) {}

    /** Invalid: public and one char long */
    public void b(int p) {}

    /** Valid: private and one char long. */
    private void c(int p) {}

    /** Holder for inner anonymous classes */
    private void d(int param) {
        new Object() {
            /** Invalid: public and one char long. */
            public void e(int p) { }
        };
    }

    /** Invalid: public constructor and one char long */
    public InputParameterNameSimplePub(int p) { }

    /** Valid: private constructor and one char long */
    private InputParameterNameSimplePub(float p) { }

    void toManyArgs(
        int $arg1, //warn
        int ar$g2, //warn
        int arg3$, //warn
        int a_rg4, //warn
        int _arg5, //warn
        int arg6_, //warn
        int aArg7, //warn
        int aArg8, //warn
        int aar_g) //warn

    {}
}

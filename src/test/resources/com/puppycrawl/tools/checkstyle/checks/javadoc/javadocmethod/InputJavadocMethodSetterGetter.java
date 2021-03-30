package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * Config: default
 */
public class InputJavadocMethodSetterGetter
{
    private int mNumber; // ok

    public void setNumber(final int number) // ok
    {
        mNumber = number;
    }

    public int getNumber() // ok
    {
        return mNumber;
    }

    public void setNumber1() // ok
    {
        mNumber = mNumber;
    }

    public void setNumber2(int number) // ok
    {
        mNumber = number;
        firePropertyChanged();
    }

    public void getNumber2() // ok
    {
    }

    public int getCost1(int forMe) // ok
    {
        return 666;
    }

    public int getCost2() // ok
    {
            mNumber = 4;
        return 666;
    }

    public int getCost3() throws Exception // ok
    {
        return 666;
    }

    public boolean isSomething() // ok
    {
        return false;
    }

    private void firePropertyChanged(){} // ok

    Object setObject(Object object) { // ok
        return new Object();
    }

    Object getNext() { // ok
        throw new UnsupportedOperationException();
    }

    public void setWithoutAssignment(Object object) { // ok
        object.notify();
    }

    InputJavadocMethodSetterGetter() {} // ok

    public InputJavadocMethodSetterGetter(Object object) throws Exception {} // ok

}

interface TestInterface { // ok
    void setObject(Object object);

    Object getObject();
}

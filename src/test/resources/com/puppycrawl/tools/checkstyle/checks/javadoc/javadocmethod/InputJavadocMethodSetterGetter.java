package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodSetterGetter
{
    private int mNumber;

    public void setNumber(final int number)
    {
        mNumber = number;
    }

    public int getNumber()
    {
        return mNumber;
    }

    public void setNumber1()
    {
        mNumber = mNumber;
    }

    public void setNumber2(int number)
    {
        mNumber = number;
        firePropertyChanged();
    }

    public void getNumber2()
    {
    }

    public int getCost1(int forMe)
    {
        return 666;
    }

    public int getCost2()
    {
    	mNumber = 4;
        return 666;
    }

    public int getCost3() throws Exception
    {
        return 666;
    }

    public boolean isSomething()
    {
        return false;
    }
    
    private void firePropertyChanged(){}

    Object setObject(Object object) {
        return new Object();
    }

    Object getNext() {
        throw new UnsupportedOperationException();
    }

    public void setWithoutAssignment(Object object) {
        object.notify();
    }

    InputJavadocMethodSetterGetter() {}

    public InputJavadocMethodSetterGetter(Object object) throws Exception {}

}

interface TestInterface {
    void setObject(Object object);

    Object getObject();
}

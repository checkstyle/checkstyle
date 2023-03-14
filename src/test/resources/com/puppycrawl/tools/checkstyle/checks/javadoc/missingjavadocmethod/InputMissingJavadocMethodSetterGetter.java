/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodSetterGetter
{
    private int mNumber;

    public void setNumber(final int number) // violation
    {
        mNumber = number;
    }

    public int getNumber() // violation
    {
        return mNumber;
    }

    public void setNumber1() // violation
    {
        mNumber = mNumber;
    }

    public void setNumber2(int number) // violation
    {
        mNumber = number;
        firePropertyChanged();
    }

    public void getNumber2() // violation
    {
    }

    public int getCost1(int forMe) // violation
    {
        return 666;
    }

    public int getCost2() // violation
    {
        mNumber = 4;
        return 666;
    }

    public int getCost3() throws Exception // violation
    {
        return 666;
    }

    public boolean isSomething() // violation
    {
        return false;
    }

    private void firePropertyChanged(){} // violation

    Object setObject(Object object) { // violation
        return new Object();
    }

    Object getNext() { // violation
        throw new UnsupportedOperationException();
    }

    public void setWithoutAssignment(Object object) { // violation
        object.notify();
    }

    InputMissingJavadocMethodSetterGetter() {} // violation

    public InputMissingJavadocMethodSetterGetter(Object object) throws Exception {} // violation

}

interface TestInterface {
    void setObject(Object object); // violation

    Object getObject(); // violation
}

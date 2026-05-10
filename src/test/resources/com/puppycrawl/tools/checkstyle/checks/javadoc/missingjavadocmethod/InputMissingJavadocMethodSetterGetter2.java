/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = true
ignoreMethodNamesRegex = (default)null
ignoreMethodsWithImplementation = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodSetterGetter2
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

    public void setNumber1() // violation 'Missing a Javadoc comment.'
    {
        mNumber = mNumber;
    }

    public void setNumber2(int number) // violation 'Missing a Javadoc comment.'
    {
        mNumber = number;
        firePropertyChanged();
    }

    public void getNumber2() // violation 'Missing a Javadoc comment.'
    {
    }

    public int getCost1(int forMe) // violation 'Missing a Javadoc comment.'
    {
        return 666;
    }

    public int getCost2() // violation 'Missing a Javadoc comment.'
    {
        mNumber = 4;
        return 666;
    }

    public int getCost3() throws Exception // violation 'Missing a Javadoc comment.'
    {
        return 666;
    }

    public boolean isSomething()
    {
        return false;
    }

    private void firePropertyChanged(){} // violation 'Missing a Javadoc comment.'

    Object setObject(Object object) { // violation 'Missing a Javadoc comment.'
        return new Object();
    }

    Object getNext() { // violation 'Missing a Javadoc comment.'
        throw new UnsupportedOperationException();
    }

    public void setWithoutAssignment(Object object) { // violation 'Missing a Javadoc comment.'
        object.notify();
    }

    InputMissingJavadocMethodSetterGetter2() {} // violation 'Missing a Javadoc comment.'

    public InputMissingJavadocMethodSetterGetter2(Object object) throws Exception {}
    // violation above 'Missing a Javadoc comment.'

}

interface TestInterface2 {
    void setObject(Object object); // violation 'Missing a Javadoc comment.'

    Object getObject(); // violation 'Missing a Javadoc comment.'
}

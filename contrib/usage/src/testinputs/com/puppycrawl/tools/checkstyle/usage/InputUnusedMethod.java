package com.puppycrawl.tools.checkstyle.usage;

/** Test input for unread method check */
public class InputUnusedMethod
{

    private void methodUnused0()
    {
    }

    private void methodUsed0()
    {
    }
    
    private void methodUsed1(int aParam)
    {
    }
    
    private void methodUsed1(double aParam)
    {
    }
    
    private void methodUsed1(String aParam)
    {
    }
    
    public static void main(String[] args)
    {
        InputUnusedMethod method = new InputUnusedMethod();
        method.methodUsed0();
        method.methodUsed1(0 + 4);
        method.methodUsed1(Math.sqrt(2.0));
        method.methodUsed1("" + "a");
    }
}

interface InterfaceMethod
{
    public void method(int aParam);
}

abstract class AbstractClassMethod
{
    public abstract void method(int aParam);
}

/** Test for bug 880954: false positive when parentheses around second term
 * of ternary operator.
 */
class Ternary
{
    private int m()
    {
        return 1;
    }
    
    public void m1()
    {
        int i = 0;
        int j = (i == 0) ? (i) : m();
    }
}

class SerializableTest implements java.io.Serializable
{
    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException
    {
        // it's ok to have this method in serializable class
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException
    {
        // it's ok to have this method in serializable class
    }

    private Object writeReplace() throws java.io.ObjectStreamException
    {
        // it's ok to have this method in serializable class
        return new SerializableTest();
    }

    private Object readResolve() throws java.io.ObjectStreamException
    {
        // it's ok to have this method in serializable class
        return new SerializableTest();
    }
}

class BadSerializableTest1 implements java.io.Serializable
{
    private void writeObject(Object out) throws java.io.IOException
    {
    }

    private void writeObject(java.io.ObjectOutputStream out, int i) throws java.io.IOException
    {
    }

    private void writeObject(java.io.ObjectOutputStream out)
    {
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException
    {
    }

    private void readObject(Object in) throws java.io.IOException, ClassNotFoundException
    {
    }

    private int writeReplace() throws java.io.ObjectStreamException
    {
        return 1;
    }

    private Object writeReplace(int i) throws java.io.ObjectStreamException
    {
        return new SerializableTest();
    }

    private int readResolve() throws java.io.ObjectStreamException
    {
        return 1;
    }

    private Object readResolve(int i) throws java.io.ObjectStreamException
    {
        return new SerializableTest();
    }
}

class BadSerializableTest2 implements java.io.Serializable
{
    private int writeObject(java.io.ObjectOutputStream out) throws java.io.IOException
    {
        return 1;
    }

    private void readObject(java.io.ObjectInputStream in) throws ClassNotFoundException
    {
    }

    private Object readResolve()
    {
        return new SerializableTest();
    }
}

class BadSerializableTest3 implements java.io.Serializable
{
    private int readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException
    {
        return 1;
    }
}

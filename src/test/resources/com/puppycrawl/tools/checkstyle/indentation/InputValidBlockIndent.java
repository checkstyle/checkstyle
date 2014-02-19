/*
 * InputValidBlockIndent.java
 *
 * Created on December 8, 2002, 12:06 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputValidBlockIndent {
    
    /** Creates a new instance of InputValidBlockIndent */
    public InputValidBlockIndent() {
    }
    
    public void method1() {
        
        { }
        {
        }
        {
            int var = 3;
            
            var += 3;
        }

        
        {  int var = 5; }
    
        {
            int var = 3;
            
            var += 3;
            
            {
                int innerVar = 4;
                
                innerVar += var;
            }
        }
    
    }
    
    static { int var = 4; }

    
    static {
        int var = 4; 
    }
    
    static 
    {
        int var = 4; 
    }

    { int var = 4; }

    
    {
        int var = 4; 
    }
    
    {
        int var = 4; 
    }
    
    
}

enum EquivalenceTester {
    /**
     * An equivalence tester that decides based on {@link Object#equals(Object) equals}.
     */
    OBJECT_ATTRIBUTES {
        /**
         * {@inheritDoc}
         */
        public boolean areEqual( final Object first, final Object second ) {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        public int hashCode( final Object target ) {
            return 1;
        }
    },

    /**
     * An equivalence tester that decides based on {@code ==}.
     */
    OBJECT_IDENTITIES
    {
        /**
         * {@inheritDoc}
         */
        public boolean areEqual( final Object first, final Object second ) {
            return first == second;
        }

        /**
         * {@inheritDoc}
         */
        public int hashCode( final Object target ) {
            return System.identityHashCode( target );
        }
    };

    /**
     * Tells whether the two given objects are considered equivalent.
     *
     * @param first first comparand
     * @param second second comparand
     * @return whether {@code first} and {@code second} are considered equivalent
     */
    public abstract boolean areEqual( Object first, Object second );

    /**
     * Computes a hash code for the given object.
     *
     * @param target object to compute a hash for
     * @return the computed hash
     */
    public abstract int hashCode( Object target );
}

class bug1251988
{
    private int a;

    // non static initializer
    {
        if (a == 1)
        {
        }
    }
}

class bug1260079
{
    public bug1260079()
    {
        new Thread()
        {
            public void run()
            {
                System.out.println("ran");
            }
        }.start();
    }
}

class bug1336737 {
    private static enum Command {
        IMPORT("import"),
        LIST("list");
        private final String c;
        Command(String c) { this.c = c; }
        public String toString() { return c; }
    }
}

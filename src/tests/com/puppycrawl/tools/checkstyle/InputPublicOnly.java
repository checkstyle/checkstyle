////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

class InputPublicOnly // ignore - need javadoc
{
    private interface InnerInterface // ignore -- need javadoc
    {
        String CONST = "InnerInterface"; // ignore -- need javadoc
        void method(); // ignore -- need javadoc
        
        class InnerInnerClass // ignore -- need javadoc
        {
            private int mData; // ignore - when not relaxed about Javadoc

            private InnerInnerClass()
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }
            
            void method2() // ignore - when not relaxed about Javadoc
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }
        }
    }

    private class InnerClass // ignore
    {
        private int mDiff; // ignore - when not relaxed about Javadoc
        
        void method() // ignore - when not relaxed about Javadoc
        {
        }
    }
    
    private int mSize; // ignore - when not relaxed about Javadoc
    int mLen; // ignore - when not relaxed about Javadoc
    protected int mDeer; // ignore
    public int aFreddo; // ignore

    // ignore - need Javadoc
    private InputPublicOnly(int aA)
    {
    }

    // ignore - need Javadoc when not relaxed
    InputPublicOnly(String aA)
    {
    }

    // ignore - always need javadoc
    protected InputPublicOnly(Object aA)
    {
    }
    
    // ignore - always need javadoc
    public InputPublicOnly(Class aA)
    {
    }

    // ignore - when not relaxed about Javadoc
    private void method(int aA)
    {
    }

    // ignore - when not relaxed about Javadoc
    void method(Long aA)
    {
    }

    // ignore - need javadoc
    protected void method(Class aA)
    {
    }
    
    // ignore - need javadoc
    public void method(StringBuffer aA)
    {
    }
}

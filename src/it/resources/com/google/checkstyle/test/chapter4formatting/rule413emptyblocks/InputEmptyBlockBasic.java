////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.*; 
import java.awt.Dimension;
import java.awt.Color;

class UpdateClass
{
    static {} //ok
    
    public void fooMethod()
    {
        UpdateClass r = new UpdateClass();
        int a = 1;
        if (a == 1) {} // warn 
        char[] s = {'1', '2'};
        int index = 2;
        if (doSideEffect() == 1) {} // warn
        IO in = new IO();
        while ((r = in.read()) != null) {} // ok
        for (; index < s.length && s[index] != 'x'; index++) {} // ok
        if (a == 1) {} else {System.out.println("a");}  // warn
        do {} while(a == 1); //ok
        switch (a) {} //warn
        int[] z = {}; // ok
    }

    public int doSideEffect()
    {
        return 1;
    }
    
    public void emptyMethod() {}
}

class IO
{
    public UpdateClass read()
    {
        return new UpdateClass();
    }
}
class Empty {} //ok

interface EmptyImplement {} //ok

class WithInner
{
    static {} //ok
    
    public void emptyMethod() {}
    
    public int doSideEffect()
    {
        return 1;
    }

    class Inner
    {
        private void withEmpty()
        {
            UpdateClass r = new UpdateClass();
            int a = 1;
            if (a == 1) {} // warn 
            char[] s = {'1', '2'};
            int index = 2;
            if (doSideEffect() == 1) {} //warn
            IO in = new IO();
            while ((r = in.read()) != null) {} // ok 
            for (; index < s.length && s[index] != 'x'; index++) {} // ok
            if (a == 1) {} else {System.out.println("a");} // warn
            do {} while(a == 1); //ok
            switch (a) {} //warn
            int[] z = {}; // ok
        }
    }
}

class WithAnon
{
    interface AnonWithEmpty {
        public void fooEmpty();
    }
    
    void method()
    {
        AnonWithEmpty foo = new AnonWithEmpty() {
            
            public void emptyMethod() {}

            public void fooEmpty() {
                UpdateClass r = new UpdateClass();
                int a = 1;
                if (a == 1) {} //warn
                char[] s = {'1', '2'};
                int index = 2;
                if (doSideEffect() == 1) {} //warn
                IO in = new IO();
                while ((r = in.read()) != null) {} // ok 
                for (; index < s.length && s[index] != 'x'; index++) {} // ok
                if (a == 1) {} else {System.out.println("a");} // warn
                do {} while(a == 1); //ok
                switch (a) {} //warn
                int[] z = {}; // ok
            }
            
            public int doSideEffect()
            {
                return 1;
            }
        };
    }
}

class NewClass {

    void foo() {
        int a = 1;

        if (a == 1) {
            System.out.println("a");
        } else {} // warn

        if (a == 1) {
            System.out.println("a");
        } else {/*ignore*/} // OK

        if (a == 1) {
            /*ignore*/
        } else {
            System.out.println("a");
        } // ok
        
        if (a == 1) {
            System.out.println("a");
        } else if (a != 1) {
            /*ignore*/
        } else {
            /*ignore*/
        }
        
        if (a == 1) {
            /*ignore*/
        } else if (a != 1) {
            System.out.println("a");
        } else {
            /*ignore*/
        }
        
        if (a == 1) {
            /*ignore*/
        } else if (a != 1) {
            /*ignore*/
        } else {
            System.out.println("a");
        }
        
        if (a == 1) {
            /*ignore*/
        } else if (a != 1) {
            /*ignore*/
        } else {
            /*ignore*/
        }
        
        if (a == 1) {
            /*ignore*/
        } else if (a != 1) {} //warn
        else {} //warn
        
        if (a == 1) {} //warn
        else if (a != 1) {
            /*ignore*/
        }
        else {} //warn
        
        if (a == 1) {} //warn
        else if (a != 1) {} //warn
        else {
            /*ignore*/
        }
    }

    class NewInner {

        void foo() {
            int a = 1;

            if (a == 1) {
                System.out.println("a");
            } else {} // warn

            if (a == 1) {
                System.out.println("a");
            } else {/*ignore*/} // OK

            if (a == 1) {
                /*ignore*/
            } else {
                System.out.println("a");
            } // ok
            
            if (a == 1) {
                System.out.println("a");
            } else if (a != 1) {
                /*ignore*/
            } else {
                /*ignore*/
            }
            
            if (a == 1) {
                /*ignore*/
            } else if (a != 1) {
                System.out.println("a");
            } else {
                /*ignore*/
            }
            
            if (a == 1) {
                /*ignore*/
            } else if (a != 1) {
                /*ignore*/
            } else {
                System.out.println("a");
            }
            
            if (a == 1) {
                /*ignore*/
            } else if (a != 1) {
                /*ignore*/
            } else {
                /*ignore*/
            }
            
            if (a == 1) {
                /*ignore*/
            } else if (a != 1) {} //warn
            else {} //warn
            
            if (a == 1) {} //warn
            else if (a != 1) {
                /*ignore*/
            }
            else {} //warn
            
            if (a == 1) {} //warn
            else if (a != 1) {} //warn
            else {
                /*ignore*/
            }
        }
        
        NewInner anon = new NewInner() {

            void foo() {
                int a = 1;

                if (a == 1) {
                    System.out.println("a");
                } else {} // warn

                if (a == 1) {
                    System.out.println("a");
                } else {/*ignore*/} // OK

                if (a == 1) {
                    /*ignore*/
                } else {
                    System.out.println("a");
                } // ok
                
                if (a == 1) {
                    System.out.println("a");
                } else if (a != 1) {
                    /*ignore*/
                } else {
                    /*ignore*/
                }
                
                if (a == 1) {
                    /*ignore*/
                } else if (a != 1) {
                    System.out.println("a");
                } else {
                    /*ignore*/
                }
                
                if (a == 1) {
                    /*ignore*/
                } else if (a != 1) {
                    /*ignore*/
                } else {
                    System.out.println("a");
                }
                
                if (a == 1) {
                    /*ignore*/
                } else if (a != 1) {
                    /*ignore*/
                } else {
                    /*ignore*/
                }
                
                if (a == 1) {
                    /*ignore*/
                } else if (a != 1) {} //warn
                else {} //warn
                
                if (a == 1) {} //warn
                else if (a != 1) {
                    /*ignore*/
                }
                else {} //warn
                
                if (a == 1) {} //warn
                else if (a != 1) {} //warn
                else {
                    /*ignore*/
                }
            }
        };
    }
}

package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import java.util.HashMap;
import java.util.Map;

class InputMethodParamPad
{
    void test()
    {
        int x = 1 + 
                 2 - 
            3
            -
            4;
        x = x + 2;
        boolean y = true
            &&
            false;
         y = true && 
            false;
        y = false
            && true;
    }
    
    void testAssignment()
    {
        int x
            = 0;
        int y =
            0;
    }

    <
     T extends Comparable & 
        java.io.Serializable
    >
    void testGenerics1()
    {
        Comparable
            <
            String
            >
            c = new String();
        Map<String, String> map = new HashMap<String, String>();

        boolean flag = false;
        
        int init = 9;
        
        /*ok*/ for (Map.Entry<String, String> entry :
            map.entrySet())
        {
            int i = flag == true ?
                    1 : 2;
        }
        
         if (init !=
                9)
        {
            
        }
        
         while (init ==
                10)
        {
            
        }
        
         if (init >
                10)
        {
            
        } else {}
        
         while (init < 10 ||
                !flag) {
            
        }
    }
    
    class Inner {
        void testGenerics1
        () //warn
        {
            Comparable
                <
                String
                >
                c = new String();
            Map<String, String> map = new HashMap<String, String>();
            boolean flag = false;
            
            int init = 9;
            
            /*ok*/ for (Map.Entry<String, String> entry :
                map.entrySet())
            {
                int i = flag == true ?
                        1 : 2;
            }
            
             if (init !=
                    9)
            {
                
            }
            
             while (init ==
                    10)
            {
                
            }
            
             if (init >
                    10)
            {
                
            } else {}
            
             while (init < 10 ||
                    !flag) {
                
            }
        }
    }
    
    Inner anon = new Inner
            (){  //warn
        void testGenerics1
        () //warn
        {
            Comparable
                <
                String
                >
                c = new String();
            Map<String, String> map = new HashMap<String, String>();
            boolean flag = false;
            int init = 9;
            
            /*ok*/ for (Map.Entry<String, String> entry :
                map.entrySet())
            {
                int i = flag == true ?
                        1 : 2;
            }
            
             if (init !=
                    9)
            {
                
            }
            
             while (init ==
                    10)
            {
                
            }
            
             if (init >
                    10)
            {
                
            } else {}
            
             while (init < 10 ||
                    !flag) {
                
            }
        }
    };
}

class AsInput1 {
    int abc = 0;
    String string
        = "string";
    double PI = // ok
            3.1415;
}

class Ternary2 {
    void foo() {
        boolean flag = true;
        int i = flag == true ?
                1 : 
                2;
        int i2 = flag == true 
                ?
                1 
                : 
                2;
        int i3 = flag == true 
                ? 1
                : 2;
        
    }
}

class AssignClass3 {
    void foo() {
        int i = 0;
        int j = 0;
        i +=
                1;
        j
             += 2;
        i -=
                1;
        j
             -= 2;
        i /=
                1;
        j
             /= 2;
        i *=
                1;
        j
             *= 2;
        i %=
                1;
        j
             %= 2;
        i ^=
                1;
        j
             ^= 2;
        i |=
                1;
        j
             |= 2;
        i &=
                1;
        j
             &= 2;
        i >>=
                1;
        j
            >>= 2;
        i >>>=
                1;
        j
            >>>= 2;
        i <<=
                1;
        j
            <<= 2;
    }
    
    class InnerClass {
        void foo() {
            int i = 0;
            int j = 0;
            i +=
                    1;
            j
                 += 2;
            i -=
                    1;
            j
                 -= 2;
            i /=
                    1;
            j
                 /= 2;
            i *=
                    1;
            j
                 *= 2;
            i %=
                    1;
            j
                 %= 2;
            i ^=
                    1;
            j
                 ^= 2;
            i |=
                    1;
            j
                 |= 2;
            i &=
                    1;
            j
                 &= 2;
            i >>=
                    1;
            j
                >>= 2;
            i >>>=
                    1;
            j
                >>>= 2;
            i <<=
                    1;
            j
                <<= 2;
        }
    }
    
    InnerClass anon = new InnerClass() {
        void foo() {
            int i = 0;
            int j = 0;
            i +=
                    1;
            j
                 += 2;
            i -=
                    1;
            j
                 -= 2;
            i /=
                    1;
            j
                 /= 2;
            i *=
                    1;
            j
                 *= 2;
            i %=
                    1;
            j
                 %= 2;
            i ^=
                    1;
            j
                 ^= 2;
            i |=
                    1;
            j
                 |= 2;
            i &=
                    1;
            j
                 &= 2;
            i >>=
                    1;
            j
                >>= 2;
            i >>>=
                    1;
            j
                >>>= 2;
            i <<=
                    1;
            j
                <<= 2;
        }
    };
}

package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class InputOperatorWrap
{
    void test()
    {
        /*warn*/int x = 1 +
                /*warn*/ 2 -
            3
            -
            4;
        x = x + 2;
        boolean y = true
            &&
            false;
        /*warn*/ y = true &&
            false;
        y = false
            && true;
        /* Note: The three tests below will be used when issue #3381 is closed */
        Arrays.sort(null, String
                    ::
                    compareToIgnoreCase);
        Arrays.sort(null, String::  /*warn*/
                    compareToIgnoreCase);
        Arrays.sort(null, String
                    ::compareToIgnoreCase);
    }

    void testAssignment()
    {
        int x
            = 0;
        int y =
            0;
    }

    <
    /*warn*/ T extends Comparable &
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
            /*warn*/int i = flag == true ?
                    1 : 2;
        }

        /*warn*/ if (init !=
                9)
        {

        }

        /*warn*/ while (init ==
                10)
        {

        }

        /*warn*/ if (init >
                10)
        {

        } else {}

        /*warn*/ while (init < 10 ||
                !flag) {

        }
    }

    class Inner {
        void testGenerics1
        ()
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
                /*warn*/int i = flag == true ?
                        1 : 2;
            }

            /*warn*/ if (init !=
                    9)
            {

            }

            /*warn*/ while (init ==
                    10)
            {

            }

            /*warn*/ if (init >
                    10)
            {

            } else {}

            /*warn*/ while (init < 10 ||
                    !flag) {

            }
        }
    }

    Inner anon = new Inner
            (){
        void testGenerics1
        ()
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
                /*warn*/int i = flag == true ?
                        1 : 2;
            }

            /*warn*/ if (init !=
                    9)
            {

            }

            /*warn*/ while (init ==
                    10)
            {

            }

            /*warn*/ if (init >
                    10)
            {

            } else {}

            /*warn*/ while (init < 10 ||
                    !flag) {

            }
        }
    };
}

class AsInput {
    int abc = 0;
    String string
        = "string";
    double PI = // ok
            3.1415;
}

class Ternary {
    void foo() {
        boolean flag = true;
        /*warn*/int i = flag == true ?
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

class AssignClass {
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

    <T extends Comparable
            & java.io.Serializable>
    void testWrapBeforeOperator()
    {
    }
}

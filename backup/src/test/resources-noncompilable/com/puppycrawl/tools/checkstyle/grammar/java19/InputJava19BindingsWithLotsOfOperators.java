//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.grammar.java19;

public class InputJava19BindingsWithLotsOfOperators {
    void m1(Object o) {
        switch (o) {
            case Integer i1 when i1 == 0: m2(i1); break;
            case Integer i1 when i1 != 0: m2(i1); break;
            case Integer i1 when i1 > 0: m2(i1); break;
            case Integer i1 when i1 >= 0: m2(i1); break;
            case Integer i1 when i1 < 0: m2(i1); break;
            case Integer i1 when i1 <= 0: m2(i1); break;

            case Integer i1 when (i1 & 0) == 0: m2(i1); break;
            case Integer i1 when (i1 | 0) == 0: m2(i1); break;
            case Integer i1 when ~i1 == 0: m2(i1); break;
            case Integer i1 when i1 << 2 == 0: m2(i1); break;
            case Integer i1 when i1 >> 2 == 0: m2(i1); break;
            case Integer i1 when i1 >>> 2 == 0: m2(i1); break;
            case Integer i1 when (i1 ^ 2) == 0: m2(i1); break;
            case Integer i1 when i1 instanceof Number: m2(i1); break;
            case Integer i1 when --i1 == 0: m2(i1); break;
            case Integer i1 when ++i1 == 0: m2(i1); break;
            case Integer i1 when i1-- == 0: m2(i1); break;
            case Integer i1 when i1++ == 0: m2(i1); break;
            case Integer i1 when i1 + 2== 0: m2(i1); break;
            case Integer i1 when i1 - 2 == 0: m2(i1); break;
            case Integer i1 when i1 / 2 == 0: m2(i1); break;
            case Integer i1 when i1 * 2 == 0: m2(i1); break;
            case Integer i1 when i1 % 2 == 0: m2(i1); break;

            case Integer i1 when (+i1 & 0) == 0: m2(i1); break;
            case Integer i1 when (+i1 | 0) == 0: m2(i1); break;
            case Integer i1 when ~+i1 == 0: m2(i1); break;
            case Integer i1 when +i1 << 2 == 0: m2(i1); break;
            case Integer i1 when +i1 >> 2 == 0: m2(i1); break;
            case Integer i1 when +i1 >>> 2 == 0: m2(i1); break;
            case Integer i1 when (+i1 ^ 2) == 0: m2(i1); break;
            case Integer i1 when i1 instanceof Number: m2(i1); break;
            case Integer i1 when +i1-- == 0: m2(i1); break;
            case Integer i1 when +i1++ == 0: m2(i1); break;
            case Integer i1 when +i1 + 2== 0: m2(i1); break;
            case Integer i1 when +i1 - 2 == 0: m2(i1); break;
            case Integer i1 when +i1 / 2 == 0: m2(i1); break;
            case Integer i1 when +i1 * 2 == 0: m2(i1); break;
            case Integer i1 when +i1 % 2 == 0: m2(i1); break;
            case Integer i1 when (int)+i1 % 2 == 0: m2(i1); break;

            case Integer i1 when (-i1 & 0) == 0: m2(i1); break;
            case Integer i1 when (-i1 | 0) == 0: m2(i1); break;
            case Integer i1 when ~-i1 == 0: m2(i1); break;
            case Integer i1 when -i1 << 2 == 0: m2(i1); break;
            case Integer i1 when -i1 >> 2 == 0: m2(i1); break;
            case Integer i1 when -i1 >>> 2 == 0: m2(i1); break;
            case Integer i1 when (-i1 ^ 2) == 0: m2(i1); break;
            case Integer i1 when i1 instanceof Number: m2(i1); break;
            case Integer i1 when -i1-- == 0: m2(i1); break;
            case Integer i1 when i1++ == 0: m2(i1); break;
            case Integer i1 when -i1 + 2== 0: m2(i1); break;
            case Integer i1 when -i1 - 2 == 0: m2(i1); break;
            case Integer i1 when -i1 / 2 == 0: m2(i1); break;
            case Integer i1 when -i1 * 2 == 0: m2(i1); break;
            case Integer i1 when -i1 % 2 == 0: m2(i1); break;
            case Integer i1 when (int)-i1 % 2 == 0: m2(i1); break;

            case Boolean b1 when b1 || !b1: m2(2); break;
            case Boolean b1 when b1 && !b1: m2(2); break;
            case Boolean b1 when !b1: m2(2); break;
            case Boolean b1 when b1? true: false: m3(b1.compareTo(false)); break;

            case null, default: m2(42);
        }
    }

    void m2(int x) { System.out.println(x); }
    boolean m3(int x){ return true; }
}

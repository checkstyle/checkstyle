/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class InputGoogleRightCurlyAnonymousClass {

    private Runnable instanceRunnable = new Runnable() {
        @Override
        public void run() {
            int x = 1;
        }
    };

    private Comparator<String> instanceComparator = new Comparator<String>() {
        @Override
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    };

    void method() {
        Runnable localRunnable = new Runnable() {
            @Override
            public void run() {
                int y = 2;
            }
        };
        localRunnable.run();

        Thread thread = new Thread() {
            @Override
            public void run() {
                int z = 3;
            }
        }; thread.start();
        // violation above ''}' at column 9 should be alone on a line'
    }

    void foo() {
        method2(new HashMap<Integer, Integer>(){
                   {
                       this.put(1, 2);
                       this.put(3, 4);
                       this.put(4, 5);
                   }
               },
               3
        );
    }

    void method2(Map<Integer, Integer> map, int count) {
        return;
    }

    Object create() {
        return new Object() {
            @Override
            public String toString() {
                return "anon";
            }
        };
    }
}

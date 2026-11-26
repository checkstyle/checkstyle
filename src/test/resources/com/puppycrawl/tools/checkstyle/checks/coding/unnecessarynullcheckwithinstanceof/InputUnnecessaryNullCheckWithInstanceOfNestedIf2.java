/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfNestedIf2 {
    public void nestedIfNegativeCases(Object obj, Object other) {
        if (obj != null) {
            if (obj instanceof String) {
                String str = (String) obj;
            }
        } else {
            System.out.println("null");
        }
        if (obj != null) {
            if (obj instanceof String) {
                String str = (String) obj;
            } else {
                System.out.println("not a String");
            }
        }
        if (obj != null) {
            System.out.println(obj.hashCode());
            if (obj instanceof String) {
                String str = (String) obj;
            }
        }
        if (obj != null || true) {
            if (obj instanceof String) {
                String str = (String) obj;
            }
        }
        if (obj != null) {
            if (obj instanceof String || true) {
                String str = (String) obj;
            }
        }
        if (obj instanceof String) {
            if (obj != null) {
                String str = (String) obj;
            } else {
                System.out.println("null");
            }
        }
        if (obj instanceof String) {
            if (obj != null) {
                String str = (String) obj;
            }
        } else {
            System.out.println("not a String");
        }
        if (other != null) {
            if (obj instanceof String) {
                String str = (String) obj;
            }
        }
        if (obj instanceof String) {
            if (other != null) {
                String str = (String) obj;
            }
        }
        if (obj instanceof String || true) {
            if (obj != null) {
                String str = (String) obj;
            }
        }
        if (obj instanceof String) {
            if (obj != null) {
                String str = (String) obj;
            }
            System.out.println("after");
        }
        if (obj != null) {
            boolean b = obj instanceof String;
        }
        if (obj == null) {
            if (obj instanceof String) {
                String str = (String) obj;
            }
        }
        if (obj instanceof String) {
            if (obj == null) {
                String str = (String) obj;
            }
        }
        if (obj != null && true) {
            if (obj instanceof String) {
                String str = (String) obj;
            }
        }
    }

    public void unbracedOuterIfNonIf(Object obj) {
        if (obj instanceof String)
            System.out.println(obj);
    }

    public void unbracedIfInWhile(Object obj) {
        while (true)
            if (obj instanceof String) {
                break;
            }
    }
}

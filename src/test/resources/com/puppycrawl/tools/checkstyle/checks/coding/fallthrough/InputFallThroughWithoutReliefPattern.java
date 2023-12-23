/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = Continue with next case


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughWithoutReliefPattern {
    void method(int i, int j, boolean cond) {
        while (true) {
            switch (i) {
                case 0:
                case 1:
                    i++;
                    break;
                case 2:
                    i++;
                case 3: // violation 'Fall\ through from previous branch of the switch statement.'
                    i++;
                    break;
                case 4:
                    return;
                case 5:
                    throw new RuntimeException("");
                case 6:
                    continue;
                case 7: {
                    break;
                }
                case 8: {
                    return;
                }
                case 9: {
                    throw new RuntimeException("");
                }
                case 10: {
                    continue;
                }
                case 11: {
                    i++;
                }
                case 12: // violation 'Fall\ through from previous branch of the switch statement.'
                    if (false)
                        break;
                    else
                        break;
                case 13:
                    if (true) {
                        return;
                    }
                case 14: // violation 'Fall\ through from previous branch of the switch statement.'
                    if (true) {
                        return;
                    } else {
                        //do nothing
                    }
                case 15: // violation 'Fall\ through from previous branch of the switch statement.'
                    do {
                        System.identityHashCode("something");
                        return;
                    } while (true);
                case 16:
                    for (int j1 = 0; j1 < 10; j1++) {
                        String.valueOf("something");
                        return;
                    }
                case 17:
                    while (true)
                        throw new RuntimeException("");
                case 18:
                    while (cond) {
                        break;
                    }
                case 19: // violation 'Fall\ through from previous branch of the switch statement.'
                    try {
                        i++;
                        break;
                    } catch (RuntimeException e) {
                        break;
                    } catch (Error e) {
                        return;
                    }
                case 20:
                    try {
                        i++;
                        break;
                    } catch (RuntimeException e) {
                    } catch (Error e) {
                        return;
                    }
                case 21: // violation 'Fall\ through from previous branch of the switch statement.'
                    try {
                        i++;
                    } catch (RuntimeException e) {
                        i--;
                    } finally {
                        break;
                    }
                case 22:
                    try {
                        i++;
                        break;
                    } catch (RuntimeException e) {
                        i--;
                        break;
                    } finally {
                        i++;
                    }
                default:
                    i++;
            }
        }
    }
}

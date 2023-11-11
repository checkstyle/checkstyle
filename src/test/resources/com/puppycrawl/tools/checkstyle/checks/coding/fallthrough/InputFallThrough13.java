/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;
public class InputFallThrough13{
    void processInput(int i, int j, boolean cond) {
        while (true) {
            switch (i) {
                case 0:
                case 1: i++; break;
                case 2: i++;
                case 3: i++; break; // violation 'Fall\ through from previous branch of the switch statement.'
                case 4: i++;
                case 5: throw new RuntimeException(""); // violation 'Fall\ through from previous branch of the switch statement.'
                case 6: continue;
                case 7: break;
                case 8: return;
                case 9: throw new RuntimeException("");
                case 10: continue;
                case 11: i++;
                case 12: if (false) break; // violation 'Fall\ through from previous branch of the switch statement.'
                         else break;
                case 13: if (true) return;
                case 14: if (true) return;  // violation 'Fall\ through from previous branch of the switch statement.'
                         else {}
                case 15: do { System.identityHashCode("something"); return; } while(true); // violation 'Fall\ through from previous branch of the switch statement.'
                case 16: for (int j1 = 0; j1 < 10; j1++) { String.valueOf("something"); return; }
                case 17: while (true) throw new RuntimeException("");
                case 18: while (cond) break;
                case 19: try { i++; break; } catch (RuntimeException e) { break; } // violation 'Fall\ through from previous branch of the switch statement.'
                         catch (Error e) { return; }
                case 20: try { i++; break; } catch (RuntimeException e) {}
                         catch (Error e) { return; }
                case 21: try { i++; } catch (RuntimeException e) { i--; } finally { break; } // violation 'Fall\ through from previous branch of the switch statement.'
                case 22: try { i++; break; } catch (RuntimeException e) { i--; break; }
                          finally { i++; }
                case 23: switch (j) { case 1: continue; case 2: return; default: return; }
            }
        }
    }
}

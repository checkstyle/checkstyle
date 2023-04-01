/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

import java.io.IOException;

public class InputFallThroughTryCatchInSwitch {
    public int foo(int x) {
        switch (x) {
            case 1:
                try {
                    // Some code
                    throw new IOException("Exception occurred.");
                } catch(IOException e) {
                    // Some code
                    break;
                } catch (Exception e) {
                    // Some code
                    break;
                } finally {
                    // Some code
                    return 0;
                }

            case 2:
                try {
                    // Some code
                    throw new IOException("Exception occurred.");
                } catch(IOException e) {
                    // Some code
                } catch (Exception e) {
                    // Some code
                    break;
                } finally {
                    // Some code
                    return 0;
                }

            case 3:
                try {
                    // Some code
                    throw new Exception("Exception occurred.");
                } catch (Exception e) {
                    // Some code
                    break;
                } finally {
                    // Some code
                    return 0;
                }

            case 4:
                try {
                    // Some code
                    throw new Exception("Exception occurred.");
                } catch (Exception e) {
                    // Some code
                } finally {
                    // Some code
                    return 0;
                }

            case 5:
                try {
                    // Some code
                    return 0;
                } catch (Exception e) {
                    // Some code
                } finally {
                    // Some code
                    return 0;
                }

            case 6:
                try {
                    // Some code
                    throw new IOException("Exception occurred.");
                } catch(IOException e) {
                    // Some code
                    break;
                } catch (Exception e) {
                    // Some code
                    break;
                } finally {
                    // Some code
                }

            case 7:
                try {
                    // Some code
                    throw new IOException("Exception occurred.");
                } catch(IOException e) {
                    // Some code
                } catch (Exception e) {
                    // Some code
                    break;
                } finally {
                    // Some code
                    return 0;
                }

            case 8:
                try {
                    // Some code
                } catch (Exception e) {
                    // Some code
                    break;
                } finally {
                    // Some code
                    return 0;
                }

            case 9:
                try {
                    // Some code
                } catch (Exception e) {
                    // Some code
                } finally {
                    //Some code
                }
            default: // violation 'Fall through from previous branch of the switch statement.'
                // Some code
        }

        return 0;
    }
}

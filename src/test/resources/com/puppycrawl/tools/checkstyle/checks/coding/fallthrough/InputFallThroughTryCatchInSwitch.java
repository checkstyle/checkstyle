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
                int nestedValue = 2;
                switch (nestedValue) {
                    case 1:
                        try {
                            // Some code
                            throw new IOException("Exception occurred.");
                        } catch (IOException e) {
                            // Some code
                            break;
                        } catch (Exception e) {
                            // Some code
                            break;
                        } finally {
                            // Some code
                        }
                    case 2:
                        try {
                            // Some code
                        } catch (Exception e) {
                            // Some code
                        } finally {
                            // Some code
                        }
                    default: // violation 'Fall through.*previous branch of the switch statement.'
                        // Some code
                }
                break;
            case 2:
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
            case 3:
                try {
                    // Some code
                    throw new IOException("Exception occurred.");
                } catch (IOException e) {
                    // Some code
                    if (e.getMessage().contains("Exception")) {
                        break;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    // Some code
                    for (int i = 0; i < 3; i++) {
                        if (i == 1) {
                            break;
                        }
                    }
                } finally {
                    // Some code
                }
            case 4: // violation 'Fall through from previous branch of the switch statement.'
                try {
                    // Some code
                    throw new IOException("Exception occurred.");
                } catch (IOException e) {
                    // Some code
                    return 0;
                } catch (NullPointerException e) {
                    // Some code
                    break;
                } catch (NumberFormatException e) {
                    // Some code
                    throw new RuntimeException("Wrapped exception", e);
                } catch (Exception e) {
                    // Some code
                    break;
                } finally {
                    // Some code
                }
            case 5:
                try {
                    // Some code
                } catch (Exception e) {
                    // Some code
                } finally {
                    // Some code
                }
            default: // violation 'Fall through from previous branch of the switch statement.'
                // Some code
        }

        return 0;
    }
}

package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

/**
 * This class has methods that have an NPath complexity larger than MAXINT.
 * Test case for bug 1654769.
 */
public class InputNPathComplexityOverflow {

    /* NP = (if-range[1]=9) * (if-range[2]=9) * (if-range[3]=9) * (if-range[4]=9)
     *          (if-range[5]=9) * (if-range[6]=9) * (if-range[7]=9) * (if-range[8]=9)
     *          (if-range[9]=9) * (if-range[10]=9) = 3486784401
     */
    public void provokeNpathIntegerOverflow()
    {
        // NP = (if-range=8) + 1 + (expr=0) = 9
        if (true) {
            // NP = (if-range=7) + 1 + (expr=0) = 8
            if (true) {
                // NP = (if-range=6) + 1 + (expr=0) = 7
                if (true) {
                    // NP = (if-range=5) + 1 + (expr=0) = 6
                    if (true) {
                        // NP = (if-range=4) + 1 + (expr=0) = 5
                        if (true) {
                            // NP = (if-range=3) + 1 + (expr=0) = 4
                            if (true) {
                                // NP = (if-range=2) + 1 + (expr=0) = 3
                                if (true) {
                                    // NP = (if-range=1) + 1 + (expr=0) = 2
                                    if (true) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // NP = (if-range=8) + 1 + (expr=0) = 9
        if (true) {
            // NP = (if-range=7) + 1 + (expr=0) = 8
            if (true) {
                // NP = (if-range=6) + 1 + (expr=0) = 7
                if (true) {
                    // NP = (if-range=5) + 1 + (expr=0) = 6
                    if (true) {
                        // NP = (if-range=4) + 1 + (expr=0) = 5
                        if (true) {
                            // NP = (if-range=3) + 1 + (expr=0) = 4
                            if (true) {
                                // NP = (if-range=2) + 1 + (expr=0) = 3
                                if (true) {
                                    // NP = (if-range=1) + 1 + (expr=0) = 2
                                    if (true) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // NP = (if-range=8) + 1 + (expr=0) = 9
        if (true) {
            // NP = (if-range=7) + 1 + (expr=0) = 8
            if (true) {
                // NP = (if-range=6) + 1 + (expr=0) = 7
                if (true) {
                    // NP = (if-range=5) + 1 + (expr=0) = 6
                    if (true) {
                        // NP = (if-range=4) + 1 + (expr=0) = 5
                        if (true) {
                            // NP = (if-range=3) + 1 + (expr=0) = 4
                            if (true) {
                                // NP = (if-range=2) + 1 + (expr=0) = 3
                                if (true) {
                                    // NP = (if-range=1) + 1 + (expr=0) = 2
                                    if (true) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // NP = (if-range=8) + 1 + (expr=0) = 9
        if (true) {
            // NP = (if-range=7) + 1 + (expr=0) = 8
            if (true) {
                // NP = (if-range=6) + 1 + (expr=0) = 7
                if (true) {
                    // NP = (if-range=5) + 1 + (expr=0) = 6
                    if (true) {
                        // NP = (if-range=4) + 1 + (expr=0) = 5
                        if (true) {
                            // NP = (if-range=3) + 1 + (expr=0) = 4
                            if (true) {
                                // NP = (if-range=2) + 1 + (expr=0) = 3
                                if (true) {
                                    // NP = (if-range=1) + 1 + (expr=0) = 2
                                    if (true) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // NP = (if-range=8) + 1 + (expr=0) = 9
        if (true) {
            // NP = (if-range=7) + 1 + (expr=0) = 8
            if (true) {
                // NP = (if-range=6) + 1 + (expr=0) = 7
                if (true) {
                    // NP = (if-range=5) + 1 + (expr=0) = 6
                    if (true) {
                        // NP = (if-range=4) + 1 + (expr=0) = 5
                        if (true) {
                            // NP = (if-range=3) + 1 + (expr=0) = 4
                            if (true) {
                                // NP = (if-range=2) + 1 + (expr=0) = 3
                                if (true) {
                                    // NP = (if-range=1) + 1 + (expr=0) = 2
                                    if (true) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // NP = (if-range=8) + 1 + (expr=0) = 9
        if (true) {
            // NP = (if-range=7) + 1 + (expr=0) = 8
            if (true) {
                // NP = (if-range=6) + 1 + (expr=0) = 7
                if (true) {
                    // NP = (if-range=5) + 1 + (expr=0) = 6
                    if (true) {
                        // NP = (if-range=4) + 1 + (expr=0) = 5
                        if (true) {
                            // NP = (if-range=3) + 1 + (expr=0) = 4
                            if (true) {
                                // NP = (if-range=2) + 1 + (expr=0) = 3
                                if (true) {
                                    // NP = (if-range=1) + 1 + (expr=0) = 2
                                    if (true) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // NP = (if-range=8) + 1 + (expr=0) = 9
        if (true) {
            // NP = (if-range=7) + 1 + (expr=0) = 8
            if (true) {
                // NP = (if-range=6) + 1 + (expr=0) = 7
                if (true) {
                    // NP = (if-range=5) + 1 + (expr=0) = 6
                    if (true) {
                        // NP = (if-range=4) + 1 + (expr=0) = 5
                        if (true) {
                            // NP = (if-range=3) + 1 + (expr=0) = 4
                            if (true) {
                                // NP = (if-range=2) + 1 + (expr=0) = 3
                                if (true) {
                                    // NP = (if-range=1) + 1 + (expr=0) = 2
                                    if (true) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // NP = (if-range=8) + 1 + (expr=0) = 9
        if (true) {
            // NP = (if-range=7) + 1 + (expr=0) = 8
            if (true) {
                // NP = (if-range=6) + 1 + (expr=0) = 7
                if (true) {
                    // NP = (if-range=5) + 1 + (expr=0) = 6
                    if (true) {
                        // NP = (if-range=4) + 1 + (expr=0) = 5
                        if (true) {
                            // NP = (if-range=3) + 1 + (expr=0) = 4
                            if (true) {
                                // NP = (if-range=2) + 1 + (expr=0) = 3
                                if (true) {
                                    // NP = (if-range=1) + 1 + (expr=0) = 2
                                    if (true) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // NP = (if-range=8) + 1 + (expr=0) = 9
        if (true) {
            // NP = (if-range=7) + 1 + (expr=0) = 8
            if (true) {
                // NP = (if-range=6) + 1 + (expr=0) = 7
                if (true) {
                    // NP = (if-range=5) + 1 + (expr=0) = 6
                    if (true) {
                        // NP = (if-range=4) + 1 + (expr=0) = 5
                        if (true) {
                            // NP = (if-range=3) + 1 + (expr=0) = 4
                            if (true) {
                                // NP = (if-range=2) + 1 + (expr=0) = 3
                                if (true) {
                                    // NP = (if-range=1) + 1 + (expr=0) = 2
                                    if (true) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // NP = (if-range=8) + 1 + (expr=0) = 9
        if (true) {
            // NP = (if-range=7) + 1 + (expr=0) = 8
            if (true) {
                // NP = (if-range=6) + 1 + (expr=0) = 7
                if (true) {
                    // NP = (if-range=5) + 1 + (expr=0) = 6
                    if (true) {
                        // NP = (if-range=4) + 1 + (expr=0) = 5
                        if (true) {
                            // NP = (if-range=3) + 1 + (expr=0) = 4
                            if (true) {
                                // NP = (if-range=2) + 1 + (expr=0) = 3
                                if (true) {
                                    // NP = (if-range=1) + 1 + (expr=0) = 2
                                    if (true) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

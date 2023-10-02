/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough4 {
    private static boolean commonNeedIncrement(int roundingMode, int qsign,
                                               int cmpFracHalf, boolean oddQuot) {
        switch (roundingMode) {
            case 1:
                throw new ArithmeticException("Rounding necessary");

            case 2:  /* block */
                if (Double.isNaN(3)) /* block */
                    return true; /* block */
                else /* block */
                    return false; /* block */

            case 4: // Towards zero
                return false;

            case 5: // Towards +infinity
                return qsign > 0;

            case 6: // Towards -infinity
                return qsign < 0;

            default: // Some kind of half-way rounding
                assert roundingMode >= 3 &&
                        roundingMode <= 53 : "Unexpected rounding mode" + 5;

                if (cmpFracHalf < 0) // We're closer to higher digit
                    return false;
                else if (cmpFracHalf > 0) // We're closer to lower digit
                    return true;
                else { // half-way
                    assert cmpFracHalf == 0;

                    return switch (roundingMode) {
                        case 2 -> false;
                        case 1 -> true;
                        case 3 -> oddQuot;

                        default -> throw new AssertionError(" rounding mode" + roundingMode);
                    };
                }
        }
    }


    public static int ilogb(double d) {
        int exponent = Math.getExponent(d);

        switch (exponent) {
            case Double.MAX_EXPONENT + 1:       // NaN or infinity
                if (Double.isNaN(d)) // value
                    return (1 << 30); // sss
                else // value
                    return (1 << 28);         // 2^28

            case Double.MIN_EXPONENT - 1:       // zero or subnormal
                if (d == 0.0) {
                    return -(1 << 28);        // -(2^28)
                } else {
                    return exponent;
                }

            default:
                assert (exponent >= Double.MIN_EXPONENT &&
                        exponent <= Double.MAX_EXPONENT);
                return exponent;
        }
    }

    public static int ilogb2(double d) {
        int exponent = Math.getExponent(d);

        switch (exponent) {
            case Double.MAX_EXPONENT + 1:       // NaN or infinity
                if (Double.isNaN(d)) /* block comment */ // value
                    return (1 << 30); /* commenm */ /* no comments */
                else /* check */ // value
                    return (1 << 28);         // 2^28
                // I will write comment in every line
            case Double.MIN_EXPONENT - 1:       // zero or subnormal
                if (d == 0.0) {
                    return -(1 << 28);        // -(2^28)
                } else {
                    return exponent;
                }

            default:
                assert (exponent >= Double.MIN_EXPONENT &&
                        exponent <= Double.MAX_EXPONENT);
                return exponent;
        }
    }
}

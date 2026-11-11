/*
NPathComplexity
max = 0


*/

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
    // violation below 'NPath Complexity is 3,486,784,401 (max allowed is 0).'
    public void provokeNpathIntegerOverflow()
    {
        // NP = (if-range=8) + 1 + (expr=0) = 9 for each block of 8 nested ifs
        if (true) { if (true) { if (true) { if (true) {
            if (true) { if (true) { if (true) { if (true) {
            } } } } } } } }

        if (true) { if (true) { if (true) { if (true) {
            if (true) { if (true) { if (true) { if (true) {
            } } } } } } } }

        if (true) { if (true) { if (true) { if (true) {
            if (true) { if (true) { if (true) { if (true) {
            } } } } } } } }

        if (true) { if (true) { if (true) { if (true) {
            if (true) { if (true) { if (true) { if (true) {
            } } } } } } } }

        if (true) { if (true) { if (true) { if (true) {
            if (true) { if (true) { if (true) { if (true) {
            } } } } } } } }

        if (true) { if (true) { if (true) { if (true) {
            if (true) { if (true) { if (true) { if (true) {
            } } } } } } } }

        if (true) { if (true) { if (true) { if (true) {
            if (true) { if (true) { if (true) { if (true) {
            } } } } } } } }

        if (true) { if (true) { if (true) { if (true) {
            if (true) { if (true) { if (true) { if (true) {
            } } } } } } } }

        if (true) { if (true) { if (true) { if (true) {
            if (true) { if (true) { if (true) { if (true) {
            } } } } } } } }

        if (true) { if (true) { if (true) { if (true) {
            if (true) { if (true) { if (true) { if (true) {
            } } } } } } } }
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

/**
 * Represents the options for parameter placement.
 *
 */
public enum ParameterPlacementOption {

    /**
     * Represents the policy for placing method parameters always on their own
     * line. For example:
     *
     * <pre>
     * public void foo(int param1, long param2) {
     *     ...
     * </pre>
     * <p>
     * Checkstyle will enforce:
     * </p>
     * <pre>
     * public void foo(
     *         int param1,
     *         long param2) {
     *     ...
     * </pre>
     **/
    OWN_LINE,

    /**
     * Represents the policy for placing method parameters always on a separate
     * line. Less restrictive then {@link ParameterPlacementOption#OWN_LINE} as
     * it will allow first parameter to be on same line as method definition.
     * For example:
     *
     * <pre>
     * public void foo(int param1, long param2) {
     *     ...
     * </pre>
     * <p>
     * Checkstyle will enforce:
     * </p>
     * <pre>
     * public void foo(int param1,
     *         long param2) {
     *     ...
     * </pre>
     **/
    SEPARATE_LINE,

    /**
     * Same policy as {@link ParameterPlacementOption#OWN_LINE}, but allows all
     * parameters to be on a single line. For example:
     *
     * <pre>
     * public void foo(int param1, long param2,
     *     float param3) {
     *     ...
     * </pre>
     * <p>
     * Checkstyle will enforce:
     * </p>
     * <pre>
     * public void foo(int param1, long param2, float param3) {
     *     ...
     * </pre>
     * <p>
     * or
     * </p>
     * <pre>
     * public void foo(
     *         int param1,
     *         long param2,
     *         float param3) {
     *     ...
     * </pre>
     **/
    OWN_LINE_ALLOW_SINGLE_LINE,

    /**
     * Same policy as {@link ParameterPlacementOption#SEPARATE_LINE}, but allows all
     * parameters to be on a single line.
     * For example:
     *
     * <p>
     * Checkstyle will enforce:
     * </p>
     * <pre>
     * public void foo(int param1, long param2) {
     *     ...
     * </pre>
     * <p>
     * or
     * </p>
     * <pre>
     * public void foo(int param1,
     *         long param2) {
     *     ...
     * </pre>
     **/
    SEPARATE_LINE_ALLOW_SINGLE_LINE,

}

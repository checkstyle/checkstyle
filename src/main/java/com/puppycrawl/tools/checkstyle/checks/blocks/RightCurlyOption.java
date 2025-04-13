///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.blocks;

/**
 * Represents the options for placing the right curly brace <code>'}'</code>.
 *
 */
public enum RightCurlyOption {

    /**
     * Represents the policy that the brace must be alone on the line.
     * For example:
     *
     * <pre>
     * try {
     *     ...
     * <b>}</b>
     * finally {
     *     ...
     * <b>}</b>
     * </pre>
     **/
    ALONE,

    /**
     * Represents the policy that the brace must be alone on the line,
     * yet allows single-line format of block.
     * For example:
     *
     * <pre>
     * // Brace is alone on the line
     * try {
     *     ...
     * <b>}</b>
     * finally {
     *     ...
     * <b>}</b>
     *
     * // Single-line format of block
     * public long getId() { return id; <b>}</b>
     * </pre>
     **/
    ALONE_OR_SINGLELINE,

    /**
     * Represents the policy that the brace should follow
     * {@link RightCurlyOption#ALONE_OR_SINGLELINE} policy
     * but the brace should be on the same line as the next part of a multi-block statement
     * (one that directly contains
     * multiple blocks: if/else-if/else or try/catch/finally).
     * If no next part of a multi-block statement present, brace must be alone on line.
     * It also allows single-line format of multi-block statements.
     *
     * <p>Examples:</p>
     *
     * <pre>
     * public long getId() {return id;<b>}</b> // this is OK, it is single-line
     *
     * // try-catch-finally blocks
     * try {
     *     ...
     * <b>}</b> catch (Exception ex) { // this is OK
     *     ...
     * <b>}</b> finally { // this is OK
     *     ...
     * }
     *
     * try {
     *     ...
     * <b>}</b> // this is NOT OK, not on the same line as the next part of a multi-block statement
     * catch (Exception ex) {
     *     ...
     * <b>}</b> // this is NOT OK, not on the same line as the next part of a multi-block statement
     * finally {
     *     ...
     * }
     *
     * // if-else blocks
     * if (a &#62; 0) {
     *     ...
     * <b>}</b> else { // this is OK
     *     ...
     * }
     *
     * if (a &#62; 0) {
     *     ...
     * <b>}</b> // this is NOT OK, not on the same line as the next part of a multi-block statement
     * else {
     *     ...
     * }
     *
     * if (a &#62; 0) {
     *     ...
     * <b>}</b> int i = 5; // NOT OK, no next part of a multi-block statement, so should be alone
     *
     * Thread t = new Thread(new Runnable() {
     *  &#64;Override
     *  public void run() {
     *                ...
     *  <b>}</b> // this is OK, should be alone as next part of a multi-block statement is absent
     * <b>}</b>); // this case is out of scope of RightCurly Check (see issue #5945)
     *
     * if (a &#62; 0) { ... <b>}</b> // OK, single-line multi-block statement
     * if (a &#62; 0) { ... } else { ... <b>}</b> // OK, single-line multi-block statement
     * if (a &#62; 0) {
     *     ...
     * } else { ... <b>}</b> // OK, single-line multi-block statement
     * </pre>
     **/
    SAME,

}

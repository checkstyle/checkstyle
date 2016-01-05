////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.blocks;

/**
 * Represents the options for placing the right curly brace {@code '}'}.
 *
 * @author Oliver Burn
 */
public enum RightCurlyOption {

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
     * Represents the policy that the brace should be on the same line as the
     * the next part of a multi-block statement (one that directly contains
     * multiple blocks: if/else-if/else or try/catch/finally).
     *
     * <p>Examples:</p>
     *
     * <pre>
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
     * <b>}</b> int i = 5; // this is NOT OK, next part of a multi-block statement is absent
     *
     * // Single line blocks will rise violations, because right curly
     * // brace is not on the same line as the next part of a multi-block
     * // statement, it just ends the line.
     * public long getId() {return id;<b>}</b> // this is NOT OK
     *
     * Thread t = new Thread(new Runnable() {
     *  &#64;Override
     *  public void run() {
     *                ...
     *  <b>}</b> // this is NOT OK, not on the same line as the next part of a multi-block statement
     * <b>}</b>); // this is OK, allowed for better code readability
     * </pre>
     **/
    SAME
}

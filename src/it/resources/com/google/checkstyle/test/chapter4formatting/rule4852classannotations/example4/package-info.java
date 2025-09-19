/*
 * All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

// 3 violations 4 lines below:
//   'Annotation 'ParentPackage' should be alone on line'
//   'Annotation 'Name' should be alone on line'
//   'Annotation 'SizePackage' should be alone on line'
@ParentPackage("val") @Name("1") @SizePackage("2")
package com.google.checkstyle.test.chapter4formatting.rule4852classannotations.example4;

import com.google.checkstyle.test.chapter4formatting.rule4852classannotations.Name;
import com.google.checkstyle.test.chapter4formatting.rule4852classannotations.ParentPackage;
import com.google.checkstyle.test.chapter4formatting.rule4852classannotations.SizePackage;

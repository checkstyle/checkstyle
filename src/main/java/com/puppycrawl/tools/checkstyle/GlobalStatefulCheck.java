////
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

package com.puppycrawl.tools.checkstyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation means that the check contains global context,
 * which will be updated while Checkstyle processes files. This also means,
 * that all files will be processed by the same check instance.
 * This annotation should be used, if a check accumulates some information during the audit,
 * and processed only once at the end of the audit (however, the check still
 * can produce some messages, while collecting information).
 * The check methods and fields should be thread safe, because they may be accessed from others
 * threads at the same time.
 * Checker guarantees that there will be exactly one check instance
 * This is similar to multi-file validation, which checkstyle does not support fully yet.
 * Please refer to <a href="https://github.com/checkstyle/checkstyle/issues/3540">#3540</a>
 * for details.
 *
 * @noinspection ClassIndependentOfModule, ClassOnlyUsedInOnePackage
 * @noinspectionreason ClassIndependentOfModule - we keep this annotation at top level by design
 * @noinspectionreason ClassOnlyUsedInOnePackage - checks live in one package by design
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface GlobalStatefulCheck {

    // this annotation does not have properties

}

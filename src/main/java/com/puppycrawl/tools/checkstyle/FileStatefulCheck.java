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
 * This annotation means that the check contains file-related context and therefore
 * cannot be used from the others threads at the same time.
 * This annotation should be used when the check holds a thread-unsafe state.
 * Checker guarantees that the whole file processed inside the same thread.
 * Checker guarantees that the whole file processed with the same check instance.
 * Checker guarantees that each check instance processes only one file at the same time.
 * Checker guarantees that all check instances have equal (but not the same) configuration.
 * It means, that if a check holds a property of type "array of strings",
 * the property value will not be shared across check instances.
 * Instead, each check instance will hold its own array instance.
 * Checker does not guarantee that each file will have its own thread -
 * there might be a list of files, which will be executed on the same thread.
 * Checker does not guarantee that each file will have its own check instance -
 * there might be a list of files, which will be checked by the same instance.
 * Note: Checks with such annotation will be executed in mode how all Checks worked
 * before MT mode is introduced.
 *
 * @noinspection ClassIndependentOfModule
 * @noinspectionreason ClassIndependentOfModule - we keep this annotation at top level by design
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface FileStatefulCheck {

    // this annotation does not have properties

}

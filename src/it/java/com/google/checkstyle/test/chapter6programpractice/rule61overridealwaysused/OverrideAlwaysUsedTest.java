///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.google.checkstyle.test.chapter6programpractice.rule61overridealwaysused;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

/** Some Javadoc A {@code Foo} is a simple Javadoc. */
class OverrideAlwaysUsedTest extends AbstractGoogleModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter6programpractice/rule61overridealwaysused";
    }

    @Test
    public void testOverrideAlwaysUsedForRecordViolation() throws Exception {
        verifyWithWholeConfig(getPath("InputOverrideAlwaysUsedForRecordViolation.java"));
    }

    @Test
    public void testOverrideAlwaysUsedForRecordValid() throws Exception {
        verifyWithWholeConfig(getPath("InputOverrideAlwaysUsedForRecordValid.java"));
    }

    @Test
    public void testOverrideAlwaysUsedForRecordNested() throws Exception {
        verifyWithWholeConfig(getPath("InputOverrideAlwaysUsedForRecordNested.java"));
    }

    @Test
    public void testOverrideAlwaysUsedForRecordGeneric() throws Exception {
        verifyWithWholeConfig(getPath("InputOverrideAlwaysUsedForRecordGeneric.java"));
    }

    @Test
    public void testOverrideAlwaysUsedForRecordMixed() throws Exception {
        verifyWithWholeConfig(getPath("InputOverrideAlwaysUsedForRecordMixed.java"));
    }

    @Test
    public void testOverrideAlwaysUsedForRecordInClass() throws Exception {
        verifyWithWholeConfig(getPath("InputOverrideAlwaysUsedForRecordInClass.java"));
    }

    @Test
    public void testOverrideAlwaysUsedForRecord() throws Exception {
        verifyWithWholeConfig(getPath("InputOverrideAlwaysUsedForRecord.java"));
    }
}

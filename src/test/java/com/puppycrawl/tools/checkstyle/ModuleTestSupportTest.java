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

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.bdd.TestInputViolation;

public class ModuleTestSupportTest {

    @Test
    public void testViolationOrder() {
        AbstractModuleTestSupport.verifyViolations("input", List.of(
                new TestInputViolation(12, "second"),
                new TestInputViolation(2, "first"),
                new TestInputViolation(12, "first")),
                List.of("12:9: first message", "12:3: second message", "2: first message"));
    }

    @Test
    public void testOverlappingViolationPatterns() {
        AbstractModuleTestSupport.verifyViolations("input", List.of(
                new TestInputViolation(3, null),
                new TestInputViolation(3, "specific")),
                List.of("3:1: specific message", "3:8: other message"));
    }

    @Test
    public void testOverlappingPatternsRequireDistinctViolations() {
        getExpectedThrowable(AssertionError.class, () -> {
            AbstractModuleTestSupport.verifyViolations("input", List.of(
                    new TestInputViolation(3, null),
                    new TestInputViolation(3, "specific"),
                    new TestInputViolation(3, "specific")),
                    List.of("3:1: specific message", "3:8: other message",
                            "3:9: another message"));
        });
    }

    @Test
    public void testDuplicateViolations() {
        AbstractModuleTestSupport.verifyViolations("input", List.of(
                new TestInputViolation(3, "message"),
                new TestInputViolation(3, "message")),
                List.of("3:1: message", "3:8: message"));
    }

    @Test
    public void testMissingViolation() {
        getExpectedThrowable(AssertionError.class, () -> {
            AbstractModuleTestSupport.verifyViolations("input", List.of(
                    new TestInputViolation(3, "message"),
                    new TestInputViolation(3, "message")), List.of("3:1: message"));
        });
    }

    @Test
    public void testUnexpectedViolation() {
        getExpectedThrowable(AssertionError.class, () -> {
            AbstractModuleTestSupport.verifyViolations("input", List.of(
                    new TestInputViolation(3, "message")),
                    List.of("3:1: message", "3:8: message"));
        });
    }

    @Test
    public void testWrongMessage() {
        getExpectedThrowable(AssertionError.class, () -> {
            AbstractModuleTestSupport.verifyViolations("input", List.of(
                    new TestInputViolation(3, "expected")), List.of("3:1: different"));
        });
    }

    @Test
    public void testWrongLine() {
        getExpectedThrowable(AssertionError.class, () -> {
            AbstractModuleTestSupport.verifyViolations("input", List.of(
                    new TestInputViolation(3, "message")), List.of("4:1: message"));
        });
    }

}

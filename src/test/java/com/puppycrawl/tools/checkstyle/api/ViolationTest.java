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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_OBJECT_ARRAY;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck;
import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class ViolationTest {

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(Violation.class)
                .usingGetClass().report();
        assertWithMessage("Error: " + ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    @Test
    public void testGetSeverityLevel() {
        final Violation violation = createSampleViolation();

        assertWithMessage("Invalid severity level")
            .that(violation.getSeverityLevel())
            .isEqualTo(SeverityLevel.ERROR);
    }

    @Test
    public void testGetModuleId() {
        final Violation violation = createSampleViolation();

        assertWithMessage("Invalid module id")
            .that(violation.getModuleId())
            .isEqualTo("module");
    }

    @Test
    public void testGetSourceName() {
        final Violation violation = createSampleViolation();

        assertWithMessage("Invalid source name")
            .that(violation.getSourceName())
            .isEqualTo("com.puppycrawl.tools.checkstyle.api.Violation");
    }

    @DefaultLocale("en")
    @Test
    public void testMessageInEnglish() {
        final Violation violation = createSampleViolation();

        assertWithMessage("Invalid violation")
            .that(violation.getViolation())
            .isEqualTo("Empty statement.");
    }

    @DefaultLocale("fr")
    @Test
    public void testGetKey() {
        final Violation violation = createSampleViolation();

        assertWithMessage("Invalid violation key")
            .that(violation.getKey())
            .isEqualTo("empty.statement");
    }

    @Test
    public void testTokenType() {
        final Violation violation1 = new Violation(1, 1, TokenTypes.CLASS_DEF,
                "messages.properties", "key", null, SeverityLevel.ERROR, null,
                getClass(), null);
        final Violation violation2 = new Violation(1, 1, TokenTypes.OBJBLOCK,
                "messages.properties", "key", EMPTY_OBJECT_ARRAY, SeverityLevel.ERROR, null,
                getClass(), null);

        assertWithMessage("Invalid token type")
            .that(violation1.getTokenType())
            .isEqualTo(TokenTypes.CLASS_DEF);
        assertWithMessage("Invalid token type")
            .that(violation2.getTokenType())
            .isEqualTo(TokenTypes.OBJBLOCK);
    }

    @Test
    public void testGetColumnCharIndex() {
        final Violation violation1 = new Violation(1, 1, 123,
                TokenTypes.CLASS_DEF, "messages.properties", "key", null, SeverityLevel.ERROR,
                null, getClass(), null);

        assertWithMessage("Invalid column char index")
            .that(violation1.getColumnCharIndex())
            .isEqualTo(123);
    }

    @Test
    public void testCompareToWithDifferentModuleId() {
        final Violation message1 = createSampleViolationWithId("module1");
        final Violation message2 = createSampleViolationWithId("module2");
        final Violation messageNull = createSampleViolationWithId(null);

        assertWithMessage("Invalid comparing result")
                .that(message1.compareTo(messageNull) > 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(messageNull.compareTo(message1) < 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(message1.compareTo(message2) < 0)
                .isTrue();
    }

    @Test
    public void testCompareToWithDifferentClass() {
        final Violation message1 = createSampleViolationWithClass(AnnotationLocationCheck.class);
        final Violation message2 = createSampleViolationWithClass(AnnotationOnSameLineCheck.class);
        final Violation messageNull = createSampleViolationWithClass(null);

        assertWithMessage("Invalid comparing result")
                .that(message1.compareTo(messageNull) > 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(messageNull.compareTo(message1) < 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(message1.compareTo(message2) < 0)
                .isTrue();
    }

    @Test
    public void testCompareToWithDifferentLines() {
        final Violation message1 = createSampleViolationWithLine(1);
        final Violation message1a = createSampleViolationWithLine(1);
        final Violation message2 = createSampleViolationWithLine(2);

        assertWithMessage("Invalid comparing result")
                .that(message1.compareTo(message2) < 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(message2.compareTo(message1) > 0)
                .isTrue();
        final int actual = message1.compareTo(message1a);
        assertWithMessage("Invalid comparing result")
            .that(actual)
            .isEqualTo(0);
    }

    @Test
    public void testCompareToWithDifferentColumns() {
        final Violation message1 = createSampleViolationWithColumn(1);
        final Violation message1a = createSampleViolationWithColumn(1);
        final Violation message2 = createSampleViolationWithColumn(2);

        assertWithMessage("Invalid comparing result")
                .that(message1.compareTo(message2) < 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(message2.compareTo(message1) > 0)
                .isTrue();
        final int actual = message1.compareTo(message1a);
        assertWithMessage("Invalid comparing result")
            .that(actual)
            .isEqualTo(0);
    }

    private static Violation createSampleViolation() {
        return createSampleViolationWithId("module");
    }

    private static Violation createSampleViolationWithClass(Class<?> clss) {
        return new Violation(1, "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                "empty.statement", EMPTY_OBJECT_ARRAY, null, clss, null);
    }

    private static Violation createSampleViolationWithId(String id) {
        return new Violation(1, "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                "empty.statement", EMPTY_OBJECT_ARRAY, id, Violation.class, null);
    }

    private static Violation createSampleViolationWithLine(int line) {
        return new Violation(line, "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                "empty.statement", EMPTY_OBJECT_ARRAY, "module", Violation.class, null);
    }

    private static Violation createSampleViolationWithColumn(int column) {
        return new Violation(1, column,
                "com.puppycrawl.tools.checkstyle.checks.coding.messages", "empty.statement",
                EMPTY_OBJECT_ARRAY, "module", Violation.class, null);
    }

}

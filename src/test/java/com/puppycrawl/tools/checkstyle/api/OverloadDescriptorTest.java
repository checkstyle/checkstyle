///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;

public class OverloadDescriptorTest {

    @Test
    public void testConstructor() {
        try {
            new OverloadDescriptor(null, "foo", new DetailAstImpl(), 1);
            assertWithMessage("exception is expected").fail();
        }
        // -@cs[IllegalCatch] Required for 100% test coverage.
        catch (Exception ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("pattern may not be null");
        }

        try {
            new OverloadDescriptor(Pattern.compile(".*"), null,
                               new DetailAstImpl(), 2);
            assertWithMessage("exception is expected").fail();
        }
        // -@cs[IllegalCatch] Required for 100% test coverage.
        catch (Exception ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("methodName may not be null");
        }
    }

    @Test
    public void testGetIndex() {
        final OverloadDescriptor descriptor =
            new OverloadDescriptor(Pattern.compile(".*"), "foo",
                                   new DetailAstImpl(), 1);
        assertWithMessage("Incorrect index returned")
            .that(descriptor.getIndex())
            .isEqualTo(1);
    }

    @Test
    public void testGetMethodToken() {
        final DetailAST ast = new DetailAstImpl();
        final OverloadDescriptor descriptor =
            new OverloadDescriptor(Pattern.compile(".*"), "foo",
                                   ast, 1);

        assertWithMessage("Incorrect DetailAST token returned")
            .that(descriptor.getMethodToken())
            .isSameInstanceAs(ast);
    }

    @Test
    public void testEquals() {
        final Pattern pattern1 = Pattern.compile(".*");
        final OverloadDescriptor descriptor1 =
            new OverloadDescriptor(pattern1, "foo", null, 1);

        final Pattern pattern2 = Pattern.compile(".*");
        final OverloadDescriptor descriptor2 =
            new OverloadDescriptor(pattern2, "foo", new DetailAstImpl(), 2);
        assertWithMessage("Only pattern and method name should impact equality")
            .that(descriptor1.equals(descriptor2))
            .isTrue();

        final OverloadDescriptor descriptor3 =
            new OverloadDescriptor(pattern1, "bar", new DetailAstImpl(), 3);
        assertWithMessage("Different method name should break equality")
            .that(descriptor1.equals(descriptor3))
            .isFalse();

        final Pattern pattern3 = Pattern.compile("^static$");
        final OverloadDescriptor descriptor4 =
            new OverloadDescriptor(pattern3, "foo", new DetailAstImpl(), 4);
        assertWithMessage("Different patterns should break equality")
            .that(descriptor1.equals(descriptor4))
            .isFalse();

        final OverloadDescriptor descriptor5 = null;
        assertWithMessage("Should not equal null")
            .that(descriptor1.equals(descriptor5))
            .isFalse();

        assertWithMessage("Should not equal a different class")
            .that(descriptor1.equals(new Object()))
            .isFalse();
    }

    @Test
    public void testHashCode() {
        final Pattern pattern1 = Pattern.compile(".*");
        final OverloadDescriptor descriptor1 =
            new OverloadDescriptor(pattern1, "foo", new DetailAstImpl(), 1);

        final Pattern pattern2 = Pattern.compile(".*");
        final OverloadDescriptor descriptor2 =
            new OverloadDescriptor(pattern2, "foo", new DetailAstImpl(), 2);
        assertWithMessage("Only the pattern text and method name should "
                              + "affect hashCode")
            .that(descriptor1.hashCode())
            .isEqualTo(descriptor2.hashCode());

        final OverloadDescriptor descriptor3 =
            new OverloadDescriptor(pattern1, "bar", new DetailAstImpl(), 3);
        assertWithMessage("Different methods should cause different hashCodes")
            .that(descriptor1.hashCode())
            .isNotEqualTo(descriptor3.hashCode());

        final Pattern pattern3 = Pattern.compile("final");
        final OverloadDescriptor descriptor4 =
            new OverloadDescriptor(pattern3, "foo", new DetailAstImpl(), 4);
        assertWithMessage("Different patterns should cause different hashCodes")
            .that(descriptor1.hashCode())
            .isNotEqualTo(descriptor4.hashCode());
    }

}

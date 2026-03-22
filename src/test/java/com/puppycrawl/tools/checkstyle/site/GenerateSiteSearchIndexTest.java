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

package com.puppycrawl.tools.checkstyle.site;

import static com.google.common.truth.Truth.assertWithMessage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

class GenerateSiteSearchIndexTest {

    @Test
    /* package */ void indexContainsCheckEntries() throws Exception {
        final Path index = Path.of("target/site/search-index.json");
        Assumptions.assumeTrue(Files.exists(index),
            "search-index.json not found - run mvn post-site first");

        final String json = Files.readString(index);

        assertWithMessage("Index must contain at least one Check entry")
            .that(json).contains("\"category\":\"Check\"");
        assertWithMessage("Index must contain the EmptyBlock check")
            .that(json).contains("EmptyBlock");
        assertWithMessage("Each entry must have a url field")
            .that(json).contains("\"url\"");
    }

    @Test
    /* package */ void indexDoesNotContainReportPages() throws Exception {
        final Path index = Path.of("target/site/search-index.json");
        Assumptions.assumeTrue(Files.exists(index),
            "search-index.json not found - run mvn post-site first");

        final String json = Files.readString(index);

        assertWithMessage("Index must not contain JaCoCo report pages")
            .that(json).doesNotContain("jacoco");
        assertWithMessage("Index must not contain Surefire report pages")
            .that(json).doesNotContain("surefire");
    }
}

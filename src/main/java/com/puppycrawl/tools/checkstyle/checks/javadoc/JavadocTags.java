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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Collection;
import java.util.List;

/**
 * Value object for combining the list of valid validTags with information
 * about invalid validTags encountered in a certain Javadoc comment.
 */
public final class JavadocTags {

    /** Valid validTags. */
    private final List<JavadocTag> validTags;
    /** Invalid validTags. */
    private final List<InvalidJavadocTag> invalidTags;

    /**
     * Creates an instance.
     *
     * @param tags valid tags
     * @param invalidTags invalid tags
     */
    public JavadocTags(Collection<JavadocTag> tags, Collection<InvalidJavadocTag> invalidTags) {
        validTags = List.copyOf(tags);
        this.invalidTags = List.copyOf(invalidTags);
    }

    /**
     *  Getter for validTags field.
     *
     *  @return validTags field
     */
    public List<JavadocTag> getValidTags() {
        return validTags;
    }

    /**
     *  Getter for invalidTags field.
     *
     *  @return invalidTags field
     */
    public List<InvalidJavadocTag> getInvalidTags() {
        return invalidTags;
    }

}

////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayList;
import java.util.Collections;
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
     * @param tags the list of valid tags
     * @param invalidTags the list of invalid tags
     */
    public JavadocTags(List<JavadocTag> tags, List<InvalidJavadocTag> invalidTags) {
        final List<JavadocTag> validTagsCopy = new ArrayList<>(tags);
        validTags = Collections.unmodifiableList(validTagsCopy);
        final List<InvalidJavadocTag> invalidTagsCopy = new ArrayList<>(invalidTags);
        this.invalidTags = Collections.unmodifiableList(invalidTagsCopy);
    }

    /**
     *  Getter for validTags field.
     *
     *  @return validTags field
     */
    public List<JavadocTag> getValidTags() {
        return Collections.unmodifiableList(validTags);
    }

    /**
     *  Getter for invalidTags field.
     *
     *  @return invalidTags field
     */
    public List<InvalidJavadocTag> getInvalidTags() {
        return Collections.unmodifiableList(invalidTags);
    }

}

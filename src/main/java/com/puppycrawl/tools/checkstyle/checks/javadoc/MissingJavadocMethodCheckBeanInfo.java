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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;

/**
 * Bean info for {@link MissingJavadocMethodCheck}.
 */
public class MissingJavadocMethodCheckBeanInfo extends java.beans.SimpleBeanInfo {

    /**
     * Creates a bean info instance.
     */
    public MissingJavadocMethodCheckBeanInfo() {
        // no code by design
    }

    @Override
    public java.beans.PropertyDescriptor[] getPropertyDescriptors() {
        try {
            final java.beans.BeanInfo beanInfo = java.beans.Introspector.getBeanInfo(
                    MissingJavadocMethodCheck.class, Object.class,
                    java.beans.Introspector.IGNORE_IMMEDIATE_BEANINFO);
            return Arrays.stream(beanInfo.getPropertyDescriptors())
                    .filter(property -> {
                        return !"violateExecutionOnNonTightHtml".equals(property.getName());
                    })
                    .toArray(java.beans.PropertyDescriptor[]::new);
        }
        catch (java.beans.IntrospectionException exception) {
            throw new IllegalStateException("Failed to load bean info for "
                    + MissingJavadocMethodCheck.class.getName(), exception);
        }
    }
}

////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filters;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.FilterSet;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * This filter accepts AuditEvents according to file, check, line, and
 * column, as specified in a suppression file.
 * </p>
 * @author Rick Giles
 * @author <a href="mailto:piotr.listkiewicz@gmail.com">liscju</a>
 */
public class SuppressionFilter extends AutomaticBean implements Filter, ExternalResourceHolder {

    /** Filename of supression file. */
    private String file;
    /** Tells whether config file existence is optional. */
    private boolean optional;
    /** Set of individual suppresses. */
    private FilterSet filters = new FilterSet();

    /**
     * Sets name of the supression file.
     * @param fileName name of the suppressions file.
     */
    public void setFile(String fileName) {
        file = fileName;
    }

    /**
     * Sets whether config file existence is optional.
     * @param optional tells if config file existence is optional.
     */
    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public boolean accept(AuditEvent event) {
        return filters.accept(event);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SuppressionFilter suppressionFilter = (SuppressionFilter) obj;
        return Objects.equals(filters, suppressionFilter.filters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filters);
    }

    @Override
    protected void finishLocalSetup() throws CheckstyleException {
        if (file != null) {
            if (optional) {
                if (suppressionSourceExists(file)) {
                    filters = SuppressionsLoader.loadSuppressions(file);
                }
                else {
                    filters = new FilterSet();
                }
            }
            else {
                filters = SuppressionsLoader.loadSuppressions(file);
            }
        }
    }

    @Override
    public Set<String> getExternalResourceLocations() {
        return ImmutableSet.of(file);
    }

    /**
     * Checks if suppression source with given fileName exists.
     * @param fileName name of the suppressions file.
     * @return true if suppression file exists, otherwise false
     */
    private static boolean suppressionSourceExists(String fileName) {
        boolean suppressionSourceExists = true;
        InputStream sourceInput = null;
        try {
            final URI uriByFilename = CommonUtils.getUriByFilename(fileName);
            final URL url = uriByFilename.toURL();
            sourceInput = url.openStream();
        }
        catch (CheckstyleException | IOException ignored) {
            suppressionSourceExists = false;
        }
        finally {
            if (sourceInput != null) {
                try {
                    sourceInput.close();
                }
                catch (IOException ignored) {
                    suppressionSourceExists = false;
                }
            }
        }
        return suppressionSourceExists;
    }
}

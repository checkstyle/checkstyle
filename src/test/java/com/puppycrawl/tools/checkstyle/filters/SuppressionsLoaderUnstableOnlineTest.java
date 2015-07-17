////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assume;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FilterSet;

/**
 * Tests SuppressionsLoader.
 * @author Rick Giles
 */
public class SuppressionsLoaderUnstableOnlineTest {

    @Test
    public void testLoadFromURL()
        throws CheckstyleException, InterruptedException {
        boolean online = isInternetReachable();

        Assume.assumeTrue(online);

        FilterSet fc = null;

        int attemptCount = 0;
        final int attemptLimit = 5;
        while (attemptCount <= attemptLimit) {
            try {

                fc = SuppressionsLoader
                        .loadSuppressions("http://checkstyle.sourceforge.net/files/suppressions_none.xml");
                break;

            }
            catch (CheckstyleException ex) {
                // for some reason Travis CI failed some times(unstable) on reading this file
                if (attemptCount < attemptLimit
                        && ex.getMessage().contains("unable to read")) {
                    attemptCount++;
                    // wait for bad/disconnection time to pass
                    Thread.sleep(1000);
                }
                else {
                    throw ex;
                }
            }
        }

        final FilterSet fc2 = new FilterSet();
        assertEquals(fc, fc2);
    }

    private static boolean isInternetReachable() {
        try {
            URL url = new URL("http://checkstyle.sourceforge.net/");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            @SuppressWarnings("unused")
            Object objData = urlConnect.getContent();
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }
}

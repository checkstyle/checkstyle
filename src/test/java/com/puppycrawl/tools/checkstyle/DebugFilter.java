/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;

class DebugFilter implements Filter {
    private boolean called;

    @Override
    public boolean accept(AuditEvent event) {
        called = true;
        return true;
    }

    public boolean wasCalled() {
        return called;
    }

    public void resetFilter() {
        called = false;
    }
}

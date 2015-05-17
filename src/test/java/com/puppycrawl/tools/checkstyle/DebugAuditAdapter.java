/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

class DebugAuditAdapter implements AuditListener {
    /** keeps track of the number of errors */
    private boolean called;

    public boolean wasCalled() {
        return called;
    }

    public void resetListener() {
        called = false;
    }

    /** @see AuditListener */
    @Override
    public void addError(AuditEvent evt) {
        called = true;
    }

    /** @see AuditListener */
    @Override
    public void addException(AuditEvent evt, Throwable throwable) {
        called = true;
    }

    /** @see AuditListener */
    @Override
    public void auditStarted(AuditEvent evt) {
        called = true;
    }

    /** @see AuditListener */
    @Override
    public void fileStarted(AuditEvent evt) {
        called = true;
    }

    /** @see AuditListener */
    @Override
    public void auditFinished(AuditEvent evt) {
        called = true;
    }

    /** @see AuditListener */
    @Override
    public void fileFinished(AuditEvent evt) {
        called = true;
    }
}

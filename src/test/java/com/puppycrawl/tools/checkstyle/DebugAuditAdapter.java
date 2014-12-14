/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

class DebugAuditAdapter implements AuditListener
{
    /** keeps track of the number of errors */
    private boolean mCalled;

    public boolean wasCalled()
    {
        return mCalled;
    }

    public void resetListener()
    {
        mCalled = false;
    }

    /** @see AuditListener */
    @Override
    public void addError(AuditEvent aEvt)
    {
        mCalled = true;
    }

    /** @see AuditListener */
    @Override
    public void addException(AuditEvent aEvt, Throwable aThrowable)
    {
        mCalled = true;
    }

    /** @see AuditListener */
    @Override
    public void auditStarted(AuditEvent aEvt)
    {
        mCalled = true;
    }

    /** @see AuditListener */
    @Override
    public void fileStarted(AuditEvent aEvt)
    {
        mCalled = true;
    }

    /** @see AuditListener */
    @Override
    public void auditFinished(AuditEvent aEvt)
    {
        mCalled = true;
    }

    /** @see AuditListener */
    @Override
    public void fileFinished(AuditEvent aEvt)
    {
        mCalled = true;
    }
}

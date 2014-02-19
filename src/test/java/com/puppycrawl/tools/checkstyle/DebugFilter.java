/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;

class DebugFilter implements Filter
{
    private boolean mCalled;

    public boolean accept(AuditEvent aEvent)
    {
        mCalled = true;
        return true;
    }

    public boolean wasCalled()
    {
        return mCalled;
    }

    public void resetFilter()
    {
        mCalled = false;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

class DebugChecker extends Checker
{
    public DebugChecker() throws CheckstyleException
    {
    }

    @Override
    public void fireAuditFinished()
    {
        super.fireAuditFinished();
    }

    @Override
    public void fireAuditStarted()
    {
        super.fireAuditStarted();
    }
}

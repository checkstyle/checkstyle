/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puppycrawl.tools.checkstyle;

class DebugChecker extends Checker {
    DebugChecker() {
    }

    @Override
    public void fireAuditFinished() {
        super.fireAuditFinished();
    }

    @Override
    public void fireAuditStarted() {
        super.fireAuditStarted();
    }
}

package com.puppycrawl.tools.checkstyle.naming;

public class InputMethodNameExtra
{
    public void doit()
    {
        createNameHistoryDetails(historyDetails, previousNameService, entityId,
            new More.ViewChangeHistoryBaseAction.ChangeHistoryDisplayName(agencyName)
            {
                String getDisplayName()
                {//comment
                    return getPreviousName(TypeOfName.AGENCY_NAME);
                }
            });
    }
}

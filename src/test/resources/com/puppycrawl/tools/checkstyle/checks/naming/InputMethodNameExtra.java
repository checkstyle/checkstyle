package com.puppycrawl.tools.checkstyle.checks.naming;

public class InputMethodNameExtra
{
    public void doit()
    {
        Object historyDetails = null;
		Object previousNameService = null;
		Object entityId = null;
		Object agencyName = null;
		createNameHistoryDetails(historyDetails, previousNameService, entityId,
            new More.ViewChangeHistoryBaseAction.ChangeHistoryDisplayName(agencyName)
            {
                String getDisplayName()
                {//comment
                    return getPreviousName();
                }
            });
    }

	private
			void
			createNameHistoryDetails(Object historyDetails, Object previousNameService, 
					Object entityId, More.ViewChangeHistoryBaseAction.ChangeHistoryDisplayName 
							changeHistoryDisplayName)
	{
		
	}

	protected String getPreviousName()
	{
		return null;
	}

	private void createNameHistoryDetails(Object historyDetails, Object previousNameService, 
			Object entityId, ChangeHistoryDisplayName changeHistoryDisplayName)
	{
		
	}
	
	private static class More {
		public static class ViewChangeHistoryBaseAction {
			public static class ChangeHistoryDisplayName {

				public ChangeHistoryDisplayName(Object agencyName)
				{

				}
				
			}
		}
	}
	
	private class ChangeHistoryDisplayName {
		
	}
}

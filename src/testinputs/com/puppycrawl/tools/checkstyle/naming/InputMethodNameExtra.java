public class InputMethodNameExtra
{
    public void doit()
    {
        createNameHistoryDetails(historyDetails, previousNameService, entityId,
            new More.ViewChangeHistoryBaseAction.ChangeHistoryDisplayName(agencyName)
            {
                String getDisplayName()
                {
                    return getPreviousName(TypeOfName.AGENCY_NAME);
                }
            });
    }
}

/********************************************************************************
*   @Purpose: Utility class for Account Trigger
*   @Author: Brandon Willard
*	@Date: 8/2/19
*
*   CHANGE  HISTORY
*   =============================================================================
*   Date        Name                    Description
*   
********************************************************************************/
public class AccountTriggerUtility {
    
	/* Queries and returns Accounts with child opportunities
     * @Param: Set<Id> (Account)     
     */    
    public List<Account> getChildOpps(Set<Id> acctIdSet){
        return [SELECT Id, Name, OwnerId, (SELECT Id FROM Opportunities) From Account WHERE Id IN: acctIdSet];
    }    
    
    /* Updates Sobject List and catches any DML Exceptions
     * @Param: List<Sobject>     
     */  
    public void updateObjectLst(List<Sobject> objectLst){
        try{
            update objectLst;
        } catch (DMLException e){
            System.debug('Exception caught: '+e);
        }        
    }

}
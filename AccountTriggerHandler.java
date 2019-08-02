/********************************************************************************
*   @Purpose: Handler class for Account Trigger
*   @Author: Brandon Willard
*	@Date: 8/2/19
*
*   CHANGE  HISTORY
*   =============================================================================
*   Date        Name                    Description
*   
********************************************************************************/
public class AccountTriggerHandler {
    
    AccountTriggerHelper helper = new AccountTriggerHelper();
    
    //OnAfterUpdate
    public void onAfterUpdate(Map<Id, Account> acctMapNew, Map<Id, Account> acctMapOld){
        Set<Id> acctIdSet = new Set<Id>();
        //Iterate over new values
        for (Account acct : acctMapNew.values()){
            //Get old value
            Account oldAcct = acctMapOld.get(acct.Id);
            //Compare and add to set if Account owner changed
            if (isAccountOwnerChange(acct, oldAcct)){
                acctIdSet.add(acct.Id);
            }           
        }
        
        //If AcctIdSet has values
        if (!acctIdSet.isEmpty()){
            //Update child Opportunities
            helper.updateChildOpps(acctIdSet);
        }       
        
    }
    
    //Method to evaluate Account Owner change
    public Boolean isAccountOwnerChange (Account newAcct, Account oldAcct){        
        return newAcct.OwnerId != oldAcct.OwnerId ? true : false;        
    }    

}
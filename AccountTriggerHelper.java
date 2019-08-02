/********************************************************************************
*   @Purpose: Helper class for Account Trigger Handler
*   @Author: Brandon Willard
*	@Date: 8/2/19
*
*   CHANGE  HISTORY
*   =============================================================================
*   Date        Name                    Description
*   
********************************************************************************/
public class AccountTriggerHelper {
    
    /* Updates child opportunities owners
     * @Param: Set<Id> (Account)
     */
    public void updateChildOpps(Set<Id> acctIdSet){
        List<Opportunity> oppUpdateLst = new List<Opportunity>();
        AccountTriggerUtility util = new AccountTriggerUtility();
        List<Account> acctLstChildOpps = util.getChildOpps(acctIdSet);
        
        for (Account acct : acctLstChildOpps){
            for (Opportunity opp : acct.Opportunities){                
                opp.OwnerId = acct.OwnerId;
                oppUpdateLst.add(opp);                
            }            
        }
        
        util.updateObjectLst(oppUpdateLst);       
    }
    
}
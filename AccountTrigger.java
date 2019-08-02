/********************************************************************************
*   @Purpose: Trigger for Account
*   @Author: Brandon Willard
*	@Date: 8/2/19
*
*   CHANGE  HISTORY
*   =============================================================================
*   Date        Name                    Description
*   
********************************************************************************/
trigger AccountTrigger on Account (after update) {

    AccountTriggerHandler handler = new AccountTriggerHandler();
    
    //After
    if (trigger.isAfter){ 
        //Update
        if (trigger.isUpdate){
            handler.onAfterUpdate(Trigger.newMap, Trigger.oldMap);
        }        
    }
    
    
}
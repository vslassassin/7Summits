@isTest
/********************************************************************************
*   @Purpose: Test class for Account Trigger Helper
*   @Author: Brandon Willard
*	@Date: 8/2/19
*
*   CHANGE  HISTORY
*   =============================================================================
*   Date        Name                    Description
*   
********************************************************************************/
public class AccountTriggerHelperTest {
	
    @testSetup
    static void testSetup(){
		User u = TestDataFactory.createSysAdminUser('Test', true);
        User u2 = TestDataFactory.createSysAdminUser('Test2', true);
        Account acct = TestDataFactory.createAccount('Test', u.Id, true);
        Account acct2 = TestDataFactory.createAccount('Test2', u2.Id, true);
        TestDataFactory.createOpps(acct, acct2.OwnerId, 5, true);
        TestDataFactory.createOpps(acct, acct2.OwnerId, 7, true);  
    }
   
    //Asserts that child opps ownerId are set to related account ownerId for Account Id set passed in   
    @isTest
    static void testUpdateChildOpps(){
        
        List<Account> acctLst = [SELECT Id, OwnerId, (SELECT Id, OwnerId FROM Opportunities) FROM Account];
        Set<Id> acctIdSet = new Set<Id>();
        for (Account acct : acctLst){            
            for (Opportunity opp : acct.Opportunities){
                System.assertNotEquals(acct.OwnerId, opp.OwnerId);
            }
            acctIdSet.add(acct.Id);
        }      
        
        AccountTriggerHelper helper = new AccountTriggerHelper();
        
        Test.startTest();
        helper.updateChildOpps(acctIdSet);
        Test.stopTest();
        
        List<Account> acctLstUpdated = [SELECT Id, OwnerId, (SELECT Id, OwnerId FROM Opportunities) FROM Account];
        for (Account acct : acctLstUpdated){            
            for (Opportunity opp : acct.Opportunities){
                System.assertEquals(acct.OwnerId, opp.OwnerId);
            }            
        }      
    }
    
}
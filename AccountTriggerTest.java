/********************************************************************************
*   @Purpose: Test class for Account Trigger
*   @Author: Brandon Willard
*	@Date: 8/2/19
*
*   CHANGE  HISTORY
*   =============================================================================
*   Date        Name                    Description
*   
********************************************************************************/
@isTest
public class AccountTriggerTest {
	
    @testSetup
    static void testSetup(){
		User u = TestDataFactory.createSysAdminUser('Test', true);        
        Account acct = TestDataFactory.createAccount('Test', u.Id, true);
        Account acct2 = TestDataFactory.createAccount('Test2', u.Id, true);
        TestDataFactory.createOpps(acct, acct.OwnerId, 5, true);
        TestDataFactory.createOpps(acct2, acct2.OwnerId, 7, true); 
    }
    
    //Asserts that ownerId is changed for opps when related account owner Id is changed, using Account List update
    @isTest
    static void testAccountTrigger_List(){
        User u2 = TestDataFactory.createSysAdminUser('Test2', true);
        List<Account> acctLst = [SELECT Id, OwnerId, (SELECT Id, OwnerId FROM Opportunities) FROM Account];
        for (Account acct : acctLst){ 
            System.assertNotEquals(u2.Id, acct.OwnerId);
            for (Opportunity opp : acct.Opportunities){
                System.assertNotEquals(u2.Id, opp.OwnerId);
            }
            acct.OwnerId = u2.Id;
        }      
        
        Test.startTest();
        update acctLst;
        Test.stopTest();
        
       
       List<Account> acctLstUpdated = [SELECT Id, OwnerId, (SELECT Id, OwnerId FROM Opportunities) FROM Account];
       for (Account acct : acctLstUpdated){            
            for (Opportunity opp : acct.Opportunities){
                System.assertEquals(u2.Id, opp.OwnerId);
            }
        }      
        
    }
    
    //Asserts that ownerId is changed for opps when related account owner Id is changed, using single Account update
    @isTest
    static void testAccountTrigger_Single(){
        User u2 = TestDataFactory.createSysAdminUser('Test2', true);
        Account acct = [SELECT Id, OwnerId, (SELECT Id, OwnerId FROM Opportunities) FROM Account WHERE Name = 'Test' LIMIT 1];
        System.assertNotEquals(u2.Id, acct.OwnerId);
        for (Opportunity opp : acct.Opportunities){
            System.assertNotEquals(u2.Id, opp.OwnerId);
        }
        acct.OwnerId = u2.Id;
             
        
        Test.startTest();
        update acct;
        Test.stopTest();
        
       
        Account acctUpdated = [SELECT Id, OwnerId, (SELECT Id, OwnerId FROM Opportunities) FROM Account WHERE Name = 'Test' LIMIT 1];
        
        for (Opportunity opp : acctUpdated.Opportunities){
            System.assertEquals(u2.Id, opp.OwnerId);
        }    
        
    }
    
}
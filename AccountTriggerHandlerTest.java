/********************************************************************************
*   @Purpose: Test class for Account Trigger Handler
*   @Author: Brandon Willard
*	@Date: 8/2/19
*
*   CHANGE  HISTORY
*   =============================================================================
*   Date        Name                    Description
*   
********************************************************************************/
@isTest
public class AccountTriggerHandlerTest {
	
    @testSetup
    static void testSetup(){
		TestDataFactory.createSysAdminUser('Test', true);
        TestDataFactory.createSysAdminUser('Test2', true);
    }    
   
    //Asserts that account ownerId change is true
    @isTest
    static void testIsAccountOwnerChangeTrue(){
        
        User user = [SELECT Id FROM User WHERE LastName='Test'];
        User user2 = [SELECT Id FROM User WHERE LastName='Test2'];
        Account acct = TestDataFactory.createAccount('Test', user.Id, false);
        Account acct2 = TestDataFactory.createAccount('Test2', user2.Id, false);
        
        AccountTriggerHandler handler = new AccountTriggerHandler();
        System.assertEquals(true, handler.isAccountOwnerChange(acct, acct2));
    }
    
    //Asserts that account ownerId change is false
    @isTest
    static void testIsAccountOwnerChangeFalse(){
        
        User user1 = [SELECT Id FROM User WHERE LastName='Test'];
        Account acct = TestDataFactory.createAccount('Test', user1.Id, false);
        Account acct2 = TestDataFactory.createAccount('Test2', user1.Id, false);
        
        AccountTriggerHandler handler = new AccountTriggerHandler();
        System.assertEquals(false, handler.isAccountOwnerChange(acct, acct2));
    }
    
}
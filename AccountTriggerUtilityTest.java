/********************************************************************************
*   @Purpose: Test class for Account Trigger Utility
*   @Author: Brandon Willard
*	@Date: 8/2/19
*
*   CHANGE  HISTORY
*   =============================================================================
*   Date        Name                    Description
*   
********************************************************************************/
@isTest
public class AccountTriggerUtilityTest {
	
    @testSetup
    static void testSetup(){
        User u = TestDataFactory.createSysAdminUser('Test', true);
		Account acct = TestDataFactory.createAccount('Test', u.Id, true);
        Account acct2 = TestDataFactory.createAccount('Test2', u.Id, true);
        TestDataFactory.createOpps(acct, acct.OwnerId, 5, true);
        TestDataFactory.createOpps(acct2, acct2.OwnerId, 7, true);        
    }
    
    //Asserts that all related opps are retrieved for each Account in Account Id set passed in
    @isTest
    static void testGetChildOpps(){
        Set<Id> acctIdSet = new Set<Id>();
        acctIdSet.add([SELECT Id, Name FROM Account WHERE Name ='Test' LIMIT 1].Id);
        acctIdSet.add([SELECT Id, Name FROM Account WHERE Name ='Test2' LIMIT 1].Id);
        AccountTriggerUtility util = new AccountTriggerUtility();
        List<Account> acctLst = util.getChildOpps(acctIdSet);
        
        for (Account acct : acctLst){
            if (acct.Name == 'Test'){
                System.assertEquals(5, acct.Opportunities.size());
            } else {
               System.assertEquals(7, acct.Opportunities.size());
            }
            
        }
        
    }    
  
    //Asserts Sobject successful update
    @isTest
    static void testUpdateObjectLst_Success(){
        List<Account> acctLst = new List<Account>();
        acctLst.add([SELECT Id, Name FROM Account WHERE Name ='Test' LIMIT 1]);
        acctLst.add([SELECT Id, Name FROM Account WHERE Name ='Test2' LIMIT 1]); 
        for (Account acct : acctLst){
            acct.Name = 'testNameChange';
        }
        
        AccountTriggerUtility util = new AccountTriggerUtility();
        
        Test.startTest();
        util.updateObjectLst(acctLst);
        Test.stopTest();
        
        List<Account> acctLstUpdated = [SELECT Id, Name FROM Account];
        
        for (Account acct : acctLstUpdated){
            System.assertEquals('testNameChange', acct.Name);
        }    
        
    }
    
    //Asserts Sobject unsuccessful update
    @isTest
    static void testUpdateObjectLst_Fail(){
        List<Account> acctLst = new List<Account>();
        acctLst.add([SELECT Id, Name FROM Account WHERE Name ='Test' LIMIT 1]);
        acctLst.add([SELECT Id, Name FROM Account WHERE Name ='Test2' LIMIT 1]); 
        for (Account acct : acctLst){            
            acct.Name = null;
        }
        
        AccountTriggerUtility util = new AccountTriggerUtility();
        
        Test.startTest();
        util.updateObjectLst(acctLst);
        Test.stopTest();
        
        List<Account> acctLstUpdated = [SELECT Id, Name FROM Account];
        
        for (Account acct : acctLstUpdated){
            System.assertNotEquals(null, acct.Name);
        }    
        
    }
    
}
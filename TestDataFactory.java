/********************************************************************************
*   @Purpose: Test Data Creation Class
*   @Author: Brandon Willard
*	@Date: 8/2/19
*
*   CHANGE  HISTORY
*   =============================================================================
*   Date        Name                    Description
*   
********************************************************************************/
@isTest
public class TestDataFactory {
    
    /* Create System Admin User
     * @params: String (last name), Boolean (insert)
     * @return: User
     */
    public static User createSysAdminUser(String lastName, Boolean performInsert){
        User u = new User(ProfileId = [SELECT Id FROM Profile WHERE Name = 'System Administrator'].Id,
                        LastName = lastName,
                        Email = 'test000@test.com',
                        Username = 'test000@test.com' + System.currentTimeMillis(),
                        CompanyName = 'testCompany',
                        Title = 'title',
                        Alias = lastName,
                        TimeZoneSidKey = 'America/Los_Angeles',
                        EmailEncodingKey = 'UTF-8',
                        LanguageLocaleKey = 'en_US',
                        LocaleSidKey = 'en_US');
        if (performInsert){
            insert u;
        }
        return u;
        
        
    }

     /* Create Account
     * @params: String (name), Id (account Owner), Boolean (insert)
     * @return: Account
     */ 
    public static Account createAccount(String name, Id userId, Boolean performInsert){
        Account acct = new Account(Name=name,
                                   OwnerId = userId);
        if (performInsert){
            insert acct;
        }
        return acct;        
    }  
    
    
     /* Create Opportunities
     * @params: Account, Integer (number of Opps), Boolean (insert)
     * @return: List<Opportunity>
     */ 
    public static List<Opportunity> createOpps(Account acct, Id ownerId, Integer numOfOpps, Boolean performInsert){  
        List<Opportunity> oppLst = new List<Opportunity>();
        for (Integer i=0; i<numOfOpps; i++){
       		oppLst.add(new Opportunity(Name = 'testOpp'+i,
                                       AccountId = acct.Id,
                                       StageName ='Prospecting',
                                       OwnerId = ownerId,
                                       CloseDate = Date.today().addDays(30)));
    	}
        if (performInsert){
            insert oppLst;
        }
        return oppLst;        
    } 
    
    
    
}
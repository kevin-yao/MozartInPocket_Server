package mozartinpocket.test;

import mozartinpocket.database.DbManager;


public class Test {
// Test program(Just used for test)
	public static void main(String[] args){
       
	    DbManager.dropTable("post");
	    DbManager.dropTable("music");
        DbManager.dropTable("user");

	    DbManager.createUser();
	    DbManager.createMusic();
	    DbManager.createPost();
	}
}

package mozartinpocket.database;

import java.sql.*;
import java.util.ArrayList;
import com.mozartinpocket.entities.*;
import com.mysql.jdbc.Connection;
public class DbManager {
	public static Statement st;
	public static Connection conn;
	public static void dropTable(String tableName){
		conn = (Connection) DbConnection.getConnection();
		try{
			String sql="DROP TABLE "+tableName+";";
			st = (Statement) conn.createStatement();
			st.execute(sql);
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void createUser(){
		conn = (Connection) DbConnection.getConnection();
		try{
			String sql = "CREATE TABLE IF NOT EXISTS user(username VARCHAR(20) NOT NULL PRIMARY KEY,"
					+ "password VARCHAR(12) NOT NULL, name VARCHAR(12), age INTEGER(3), gender VARCHAR(6), email VARCHAR(100),"
					+ "interests_tag VARCHAR(100), my_music_style VARCHAR(100), photo_filename VARCHAR(100))";
			st = (Statement) conn.createStatement();
			st.execute(sql);
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
			System.err.println("Fail to create a table user");
		}
	}

	public static void createMusic(){
		conn = (Connection) DbConnection.getConnection();
		try{
			String sql = "CREATE TABLE IF NOT EXISTS music(music_id INTEGER PRIMARY KEY AUTO_INCREMENT,"
					+ "name VARCHAR(100) NOT NULL, author_username VARCHAR(20), date DATETIME, description TEXT(1024), save_path VARCHAR(100),"
					+ "CONSTRAINT MUSIC_FOREIGN_KEY FOREIGN KEY (author_username) REFERENCES user (username)  ON DELETE CASCADE ON UPDATE CASCADE)";
			st = (Statement) conn.createStatement();
			st.execute(sql);
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
			System.err.println("Fail to create a table user");
		}
	}

	public static void createPost(){
		conn = (Connection) DbConnection.getConnection();
		try{
			String sql = "CREATE TABLE IF NOT EXISTS post(post_id INTEGER PRIMARY KEY AUTO_INCREMENT,"
					+ "music_name VARCHAR(100), comment TEXT(1024),"
					+ "author_username VARCHAR(100), date DATETIME, longitude DOUBLE(20, 5), latitude DOUBLE(20, 5),"
					+ "music_url VARCHAR(100),user_photo_url VARCHAR(100),"
					+ "CONSTRAINT POST_FOREIGN_KEY1 FOREIGN KEY (author_username) REFERENCES user (username) ON DELETE CASCADE ON UPDATE CASCADE)";
			st = (Statement) conn.createStatement();
			st.execute(sql);
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
			System.err.println("Fail to create a table user");
		}
	}


	public static synchronized void addUser(User user) {  	          
		conn = (Connection) DbConnection.getConnection();	  
		try {  
			String sql = "INSERT INTO user(username, password, name, age, gender, email, interests_tag, my_music_style, photo_filename)"  
					+ " VALUES ('"+user.getUsername()+"'"+",'"+user.getPassword()+"','"+user.getName()+"',"+user.getAge()+",'"+user.getGender()+"','"+user.getEmail()+"','"
					+user.getInterestsTag()+"','"+user.getMyMusicStyle()+"','"+user.getPhotoFilename()+"')";	              
			st = (Statement) conn.createStatement();  	              
			st.executeUpdate(sql);  
			conn.close();   
		} catch (SQLException e) {  
			System.err.println("Fail to insert data" + e.getMessage());  
		}  
	}

	public static synchronized boolean checkUserExistence(String username){
		boolean flag=false;
		conn = (Connection) DbConnection.getConnection();
		try{
			String checkSql="SELECT COUNT(*) AS num FROM user WHERE username='"+username+"';";
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(checkSql);
			rs.next();
			int rowCounter = rs.getInt("num");
			if(rowCounter>0){
				flag=true;
			}
		}catch(SQLException e){
			System.err.println("Fail to get data" + e.getMessage()); 
		}
		return flag;
	}

	public static synchronized void updateUser(User user) {  	          
		conn = (Connection) DbConnection.getConnection();	  
		try {  
			String checkSql="SELECT COUNT(*) AS num FROM user WHERE username='"+user.getUsername()+"';";
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(checkSql);
			rs.next();
			int rowCounter = rs.getInt("num");
			if(rowCounter>0){
				deleteUser(user.getUsername());
			}
			conn = (Connection) DbConnection.getConnection();
			String sql = "INSERT INTO user(username, password, name, age, gender, email, interests_tag, my_music_style, photo_filename)"  
					+ " VALUES ('"+user.getUsername()+"'"+",'"+user.getPassword()+"','"+user.getName()+"',"+user.getAge()+",'"+user.getGender()+"','"+user.getEmail()+"','"
					+user.getInterestsTag()+"','"+user.getMyMusicStyle()+"','"+user.getPhotoFilename()+"')";	               	              
			st = (Statement) conn.createStatement();
			st.executeUpdate(sql);  
			conn.close();   
		} catch (SQLException e) {  
			System.err.println("Fail to insert data" + e.getMessage());  
		}  
	}

	public static synchronized User getUser(String username){
		conn = (Connection) DbConnection.getConnection();
		User user = new User();
		user.setUsername(username);
		try{
			String sql ="select * from user where username='"+username+"'";
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setAge(rs.getInt("age"));
				user.setEmail(rs.getString("email"));
				user.setGender(rs.getString("gender"));
				user.setInterestsTag(rs.getString("interests_tag"));
				user.setMyMusicStyle(rs.getString("my_music_style"));
				user.setPhotoFilename(rs.getString("photo_filename"));
			}
			conn.close();
		}catch(SQLException e){
			System.err.println("Fail to query data" + e.getMessage()); 
		}
		return user;
	}


	public static synchronized String getPassword(String username){
		conn = (Connection) DbConnection.getConnection();
		String password=null;
		try{
			String sql ="select password from user where username='"+username+"'";
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				password=rs.getString("password");
			}
			conn.close();
		}catch(SQLException e){
			System.err.println("Fail to query data" + e.getMessage()); 
		}
		return password;
	}

	public static synchronized void deleteUser(String username) {  
		conn = (Connection) DbConnection.getConnection();
		try {  
			String sql = "delete from user where username = '"+username+"';"; 
			st = (Statement) conn.createStatement();    
			st.executeUpdate(sql);   
			conn.close(); 
		} catch (SQLException e) {  
			System.err.println("Fail to delete" + e.getMessage());  
		}   
	}

	public static synchronized void addMusic(Music music) {  	          
		conn = (Connection) DbConnection.getConnection();	  
		try {  
			String sql = "INSERT INTO music(name, author_username, date, description, save_path)"  
					+ " VALUES ('"+music.getName()+"','"+music.getAuthorUsername()+"','"+music.getDate()+"','"+music.getDescription()+"','"+music.getSavePath()+"')";	              
			st = (Statement) conn.createStatement();  	              
			st.executeUpdate(sql);  
			conn.close();   
		} catch (SQLException e) {  
			System.err.println("Fail to insert data" + e.getMessage());  
		}  
	}

	public static synchronized boolean checkMusicExistence(String username){
		boolean flag=false;
		conn = (Connection) DbConnection.getConnection();
		try{
			String checkSql="SELECT COUNT(*) AS num FROM user WHERE username='"+username+"';";
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(checkSql);
			rs.next();
			int rowCounter = rs.getInt("num");
			if(rowCounter>0){
				flag=true;
			}
		}catch(SQLException e){
			System.err.println("Fail to get data" + e.getMessage()); 
		}
		return flag;
	}

	public static synchronized void updateMusic(Music music) {  	          
		conn = (Connection) DbConnection.getConnection();	  
		try {  
			String checkSql="SELECT COUNT(*) AS num FROM music WHERE name='"+music.getName()+"';";
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(checkSql);
			rs.next();
			int rowCounter = rs.getInt("num");
			if(rowCounter>0){
				deleteMusic(music.getName());
			}
			conn = (Connection) DbConnection.getConnection();

			String sql = "INSERT INTO music(name, author_username, date, description, save_path)"  
					+ " VALUES ('"+music.getName()+"','"+music.getAuthorUsername()+"','"+music.getDate()+"','"+music.getDescription()+"','"+music.getSavePath()+"')";	              
			st = (Statement) conn.createStatement();  	              
			st.executeUpdate(sql);  
			conn.close();   
		} catch (SQLException e) {  
			System.err.println("Fail to insert data" + e.getMessage());  
		}  
	}

	public static synchronized void deleteMusic(String musicName) {  
		conn = (Connection) DbConnection.getConnection();
		try {  
			String sql = "delete from music where name = '"+musicName+"'"; 
			st = (Statement) conn.createStatement();    
			st.executeUpdate(sql);   
			conn.close(); 
		} catch (SQLException e) {  
			System.err.println("Fail to delete" + e.getMessage());   
		}   
	}

	public static synchronized int getMusicId(String musicName, String musicAuthorUsername){		
		int musicId=0;
		conn = (Connection) DbConnection.getConnection();
		try{
			String sql ="select music_id from music where name='"+musicName+"' and author_username='"+musicAuthorUsername+"';";
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				musicId=rs.getInt("music_id");
			}
			conn.close();
		}catch(SQLException e){
			System.err.println("Fail to query data" + e.getMessage()); 
		}
		return musicId;
	}

	public static synchronized Music getMusic(String musicName, String musicAuthorUsername){		
		conn = (Connection) DbConnection.getConnection();
		Music music = new Music();
		music.setName(musicName);
		music.setAuthorUsername(musicAuthorUsername);
		try{
			String sql ="select * from music where name='"+musicName+"' and author_username='"+musicAuthorUsername+"';";
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				music.setMusicId(rs.getInt("music_id"));
				music.setDate(rs.getString("date"));
				music.setDescription(rs.getString("description"));
				music.setSavePath("save_path");
			}
			conn.close();
		}catch(SQLException e){
			System.err.println("Fail to query data" + e.getMessage()); 
		}
		return music;
	}

	public static synchronized Music[] getMusic(String musicName){		
		conn = (Connection) DbConnection.getConnection();
		Music[] musiclist= new Music[100];
		try{
			String sql ="select * from music where name='"+musicName+"';";
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int i=0;
			while(rs.next()){
				Music music = new Music();
				music.setName(musicName);
				music.setAuthorUsername(rs.getString("author_username"));
				music.setMusicId(rs.getInt("music_id"));
				music.setDate(rs.getString("date"));
				music.setDescription(rs.getString("description"));
				music.setSavePath("save_path");
				musiclist[i] = music;
				i++;
			}
			conn.close();
		}catch(SQLException e){
			System.err.println("Fail to query data" + e.getMessage()); 
		}
		return musiclist;
	}

	public static synchronized void addPost(Post post) {  	          	  
		try { 
			conn = (Connection) DbConnection.getConnection();
			String sql = "INSERT INTO post(music_name, comment, author_username, date, longitude, latitude, music_url,user_photo_url)"  
					+ "VALUES ('"+post.getMusicName()+"','"+post.getComment()+"','"+post.getAuthorUsername()+"','"+post.getDate()+"',"+post.getLongitude()+","+post.getLatitude()+","
					+ "'"+post.getMusicUrl()+"','"+post.getUserPhotoUrl()+"')";	              
			st = (Statement) conn.createStatement();  	              
			st.executeUpdate(sql);  
			conn.close();   
		} catch (SQLException e) {  
			System.err.println("Fail to insert data" + e.getMessage());  
		}  
	}

	public static synchronized Post getPost(int postId) {  	          	  
		conn = (Connection) DbConnection.getConnection();
		Post post = new Post();
		post.setPostId(postId);
		try{
			String sql ="select * from post where post_id="+postId;
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				post.setMusicName(rs.getString("music_name"));
				post.setDate(rs.getString("date"));
				post.setComment(rs.getString("comment"));
				post.setAuthorUsername(rs.getString("author_username"));
				post.setLongitude(rs.getInt("longitude"));
				post.setLatitude(rs.getInt("latitude"));
				post.setMusicUrl(rs.getString("music_url"));
				post.setUserPhotoUrl(rs.getString("user_photo_url"));
			}
			conn.close();
		}catch(SQLException e){
			System.err.println("Fail to query data" + e.getMessage()); 
		}
		return post;
	}

	public static synchronized ArrayList<Post> getPostList(int curPostId) {  	          	  
		conn = (Connection) DbConnection.getConnection();
		ArrayList<Post> postList = new ArrayList<Post>();	
		try{
			String sql ="select * from post where post_id >"+curPostId+" order by date desc limit 10";
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				Post post=new Post();
				post.setPostId(rs.getInt("post_id"));
				post.setMusicName(rs.getString("music_name"));
				post.setDate(rs.getString("date"));
				post.setComment(rs.getString("comment"));
				post.setAuthorUsername(rs.getString("author_username"));
				post.setLongitude(rs.getInt("longitude"));
				post.setLatitude(rs.getInt("latitude"));
				post.setMusicUrl(rs.getString("music_url"));
				post.setUserPhotoUrl(rs.getString("user_photo_url"));
				postList.add(post);
			}
			conn.close();
		}catch(SQLException e){
			System.err.println("Fail to query data" + e.getMessage()); 
		}
		return postList;
	}

	public static synchronized void deletePost(int postId) {  
		conn = (Connection) DbConnection.getConnection();
		try {  
			String sql = "delete from post where postId = "+postId; 
			st = (Statement) conn.createStatement();    
			st.executeUpdate(sql);   
			conn.close(); 
		} catch (SQLException e) {  
			System.err.println("Fail to delete" + e.getMessage());   
		}   
	}
}

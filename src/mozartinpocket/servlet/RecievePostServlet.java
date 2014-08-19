package mozartinpocket.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mozartinpocket.entities.Post;

import mozartinpocket.database.DbManager;

/**
 * Servlet implementation class RecievePostServlet
 */
public class RecievePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String MUSICURL="http://10.0.22.88:8080/MozartInPocket/Audio/";
	private static String IMAGEURL="http://10.0.22.88:8080/MozartInPocket/Image/";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			response.setCharacterEncoding("UTF-8");
			Post post = new Post(); 
			String musicName = request.getParameter("music_name");
//			System.out.println(musicName);
			post.setMusicName(musicName);
			String comment = request.getParameter("comment");
			post.setComment(comment);
			String authorUsername = request.getParameter("authorUsername");
			post.setAuthorUsername(authorUsername);
			String date = request.getParameter("date");
			post.setDate(date);
			String musicUrl = request.getParameter("musicUrl");
			musicUrl=MUSICURL+musicUrl+"_"+authorUsername+".mid";
			musicUrl=parseBlank(musicUrl);
			post.setMusicUrl(musicUrl);
			String userPhotoUrl = request.getParameter("userPhotoUrl");
			if(userPhotoUrl.equals("empty")){
				userPhotoUrl=IMAGEURL+"empty"+".jpg";
			}else{
				userPhotoUrl=IMAGEURL+userPhotoUrl+".jpg";
				userPhotoUrl=parseBlank(userPhotoUrl);
			}
//			System.out.println(userPhotoUrl);
			post.setUserPhotoUrl(userPhotoUrl);
			String latitude = request.getParameter("latitude");
//			System.out.println(latitude);
			post.setLatitude(Double.valueOf(latitude));
			String longitude = request.getParameter("longitude");
			post.setLongitude(Double.valueOf(longitude));
			DbManager.addPost(post);		
			response.getOutputStream().print("SUCCESS");
		}catch(Exception e){
			response.getOutputStream().print("FAIL");
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String parseBlank(String s){
		if(s.contains(" ")){
			String[] temp = s.split(" ");
			String output = "";
			for(int i=0;i<temp.length-1;i++){
				output = output + temp[i] + "_";
			}
			output = output+temp[temp.length-1];
			return output;
		}
		return s;
	}

}

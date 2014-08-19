package mozartinpocket.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.mozartinpocket.entities.Music;
import mozartinpocket.database.DbManager;

@MultipartConfig
public class RecieveMusicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String nullFlag="*$#";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		try{
			File musicFile=new File(request.getSession().getServletContext().getRealPath("")+"/Audio");
			if (!musicFile.exists()) {
				musicFile.mkdirs();
			}
			Music music = new Music();
			Part part=request.getPart("name");
			String musicName = parseStringBody(part);	
			music.setName(musicName);
//			System.out.println(musicName);
			part=request.getPart("date");
			String date = parseStringBody(part);
//			System.out.println(date);
			if(!date.equals(nullFlag)){
				music.setDate(date);
			}
			part=request.getPart("description");
			String description = parseStringBody(part);
//			System.out.println(description);
			if(!description.equals(nullFlag)){
				music.setDescription(description);
			}

			part=request.getPart("authorUsername");
			String authorUsername = parseStringBody(part);
			if(!authorUsername.equals(nullFlag)){
				music.setAuthorUsername(authorUsername);
			}
			
			part=request.getPart("savePath");
			String savePath = parseStringBody(part);
//			System.out.println(savePath);
			if(!savePath.equals(nullFlag)){
				savePath=parseBlank(savePath+"_"+authorUsername);
				music.setSavePath(savePath);
			}
			
//			System.out.println(authorUsername);
			DbManager.addMusic(music);
			String audioFilepath=musicFile+"/"+savePath+".mid";
			audioFilepath=parseBlank(audioFilepath);
			request.getPart("music").write(audioFilepath);
			File file1=new File("D:/Java/apache-tomcat-7.0.42/apache-tomcat-7.0.42/webapps/MozartInPocket/Audio");
			File file2=new File("D:/Java/apache-tomcat-7.0.42/Audio");
			LoginServlet.copyFile(file1, file2);
			response.getOutputStream().print("SUCCESS");
		}catch(Exception e){
			System.out.println(e.toString());
			response.getOutputStream().print("FAIL");
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String parseStringBody(Part part){
		String str=null;
		try{
			InputStream is = part.getInputStream();
			ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream(); 
			int   i=-1; 
			while((i=is.read())!=-1){ 
				baos.write(i); 
			} 
			str=baos.toString();
			is.close();
			baos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
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

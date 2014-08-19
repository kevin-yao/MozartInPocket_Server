package mozartinpocket.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.mozartinpocket.entities.User;

import mozartinpocket.database.DbManager;

/**
 * Servlet implementation class SaveUserProfileServlet
 */
@WebServlet("/SaveUserProfileServlet")
@MultipartConfig
public class SaveUserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String nullFlag="*$#";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		try{
			File imageFile=new File(request.getSession().getServletContext().getRealPath("")+"/Image");
			if (!imageFile.exists()) {
				imageFile.mkdirs();
			}
			Part part=request.getPart("updateFlag");
			String updateFlag = parseStringBody(part);	
//			System.out.println(updateFlag);
			User user = new User();
			part=request.getPart("username");
			String username = parseStringBody(part);	
			user.setUsername(username);
//			System.out.println(username);
			part=request.getPart("password");
			String password = parseStringBody(part);
//			System.out.println(password);
			user.setPassword(password);
			part=request.getPart("name");
			String name = parseStringBody(part);
//			System.out.println(name);
			if(!name.equals(nullFlag)){
				user.setName(name);
			}
			part=request.getPart("gender");
			String gender = parseStringBody(part);
//			System.out.println(gender);
			if(!gender.equals(nullFlag)){
				user.setGender(gender);
			}
			part=request.getPart("email");
			String email = parseStringBody(part);
//			System.out.println(email);
			if(!email.equals(nullFlag)){
				user.setEmail(email);
			}
			part=request.getPart("age");
			String age = parseStringBody(part);
//			System.out.println(age);
			user.setAge(Integer.parseInt(age));
			part=request.getPart("interestsTag");
			String interestsTag = parseStringBody(part);
//			System.out.println(interestsTag);
			if(!interestsTag.equals(nullFlag)){
				user.setInterestsTag(interestsTag);
			}
			
			part=request.getPart("myMusicStyle");
			String myMusicStyle = parseStringBody(part);
//			System.out.println(myMusicStyle);
			if(!myMusicStyle.equals(nullFlag)){
				user.setMyMusicStyle(myMusicStyle);
			}
			part=request.getPart("photoFilename");
			String photoFilename = parseStringBody(part);
//			System.out.println(photoFilename);
			part=request.getPart("empty");
			String empty = parseStringBody(part);
//			System.out.println(empty);

			if(!photoFilename.equals(nullFlag)){
				photoFilename=parseBlank(photoFilename);
				user.setPhotoFilename(photoFilename);
			}
			
			if(updateFlag.equals("true")){
				DbManager.updateUser(user);
				if(empty.equals("false")){
					String imageFilepath=imageFile+"/"+photoFilename+".jpg";
					request.getPart("photo").write(imageFilepath);
					File file1=new File("D:/Java/apache-tomcat-7.0.42/apache-tomcat-7.0.42/webapps/MozartInPocket/Image");
					File file2=new File("D:/Java/apache-tomcat-7.0.42/Image");
					LoginServlet.copyFile(file1, file2);
				}
				response.getOutputStream().print("SUCCESS");
			}
			else{

				if(DbManager.checkUserExistence(user.getUsername())){
					response.getOutputStream().print("USER EXISTS");
				}else{
					DbManager.addUser(user);
					if(empty.equals("false")){
						String imageFilepath=imageFile+"/"+photoFilename+".jpg";
						request.getPart("photo").write(imageFilepath);
						File file1=new File("D:/Java/apache-tomcat-7.0.42/apache-tomcat-7.0.42/webapps/MozartInPocket/Image");
						File file2=new File("D:/Java/apache-tomcat-7.0.42/Image");
						LoginServlet.copyFile(file1, file2);
					}
					response.getOutputStream().print("SUCCESS");
				}
			}

		}  catch(Exception e){
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

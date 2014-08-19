package mozartinpocket.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.mozartinpocket.entities.User;
import mozartinpocket.database.DbManager;
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");

		String username = request.getParameter("username");
		String password = request.getParameter("password");
//		System.out.println(username);
//		System.out.println(password);
		String passwordRecord = DbManager.getPassword(username);
//		System.out.println(passwordRecord);
		if(!password.equals(passwordRecord)){
			response.getOutputStream().println("FAIL");
		}else{
			response.getOutputStream().println("SUCCESS");
			User user=DbManager.getUser(username);
			response.getOutputStream().println(String.valueOf(user.getAge()));
			response.getOutputStream().println(user.getName());
			response.getOutputStream().println(user.getEmail());
			response.getOutputStream().println(user.getGender());
			response.getOutputStream().println(user.getMyMusicStyle());
			response.getOutputStream().println(user.getPhotoFilename());
			response.getOutputStream().println(user.getInterestsTag());
		}
		File file1=new File("D:/Java/apache-tomcat-7.0.42/Image");
		File file2=new File("D:/Java/apache-tomcat-7.0.42/apache-tomcat-7.0.42/webapps/MozartInPocket/Image");
		copyFile(file1, file2);
		file1=new File("D:/Java/apache-tomcat-7.0.42/Audio");
		file2=new File("D:/Java/apache-tomcat-7.0.42/apache-tomcat-7.0.42/webapps/MozartInPocket/Audio");
		copyFile(file1, file2);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public static void copyFile(File file1, File file2){
		if(!file2.exists()){
			file2.mkdir();
		}
		try{
			if(!file1.isFile()){
				File[] fileList=file1.listFiles();
				for(File file: fileList){
					if(file.isFile()){
						FileInputStream fis = new FileInputStream(file);
						FileOutputStream fos= new FileOutputStream(file2+"\\"+file.getName());
						byte[] bytes=new byte[1024];
						int i=fis.read(bytes);
						while(i!=-1){
							fos.write(bytes,0,i);
							i=fis.read(bytes);
						}
						fis.close();
						fos.close();
					}else{
						File fb = new File(file2 + "\\" + file.getName());
						fb.mkdir();
						if(file.listFiles() != null){
							copyFile(file,fb);
						}
					}
				}
			}
			else{
				FileInputStream fis=new FileInputStream(file1);
				FileOutputStream fos = new FileOutputStream(file2+"\\"+file1.getName());
				byte[] bytes= new byte[1024];
				int i=fis.read(bytes);
				while(i!=-1){
					fos.write(bytes, 0, i);
					i=fis.read(bytes);
				}
				fis.close();
				fos.close();
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

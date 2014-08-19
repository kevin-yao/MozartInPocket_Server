package mozartinpocket.servlet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mozartinpocket.entities.Post;
import mozartinpocket.database.DbManager;

/**
 * Servlet implementation class ResponsePostServlet
 */

public class ResponsePostServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		ObjectOutputStream oos = null;
		ArrayList<Post> postList=new ArrayList<Post>();

		String currentPostId = request.getParameter("currentPostId");
		int curPostId = Integer.parseInt(currentPostId) ;
//		System.out.println(currentPostId);
		try{
			postList = DbManager.getPostList(curPostId);	
			oos=new ObjectOutputStream (response.getOutputStream());
			oos.writeObject(postList);
			oos.flush();
			oos.close();
			oos = null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

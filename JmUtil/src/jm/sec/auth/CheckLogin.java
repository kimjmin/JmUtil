package jm.sec.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jm.net.Dao;

public class CheckLogin{

	public boolean service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		Dao dao = Dao.getInstance();
		
		HttpSession session = req.getSession();
		JmUser user = (JmUser) session.getAttribute("JmUser");
		
		if (user == null || user.getId() == null || "".equals(user.getId()) ) {
			out.println("<script>");
			out.println("	alert(\"로그인 후 이용 해 주시기 바랍니다.\");");
//			out.println("	location.href = \""+loginUrl+"\";");
			out.println("</script>");
			
			return false;
		} else {
			return true;
		}
	}
	
	
	
}

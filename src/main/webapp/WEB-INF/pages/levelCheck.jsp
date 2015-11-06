<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
${userName}


<%
 

String aa = request.getParameter("level");

if (aa.equals("County")) {
	response.sendRedirect("County.do");
} else if (aa.equals("District")) {
	response.sendRedirect("District.do");
} 
else if (aa.equals("PO")) {
	response.sendRedirect("PO.do");
} 
else if (aa.equals("DOE")) {
	response.sendRedirect("DOE.do");
} 

%>





</body>
</html>
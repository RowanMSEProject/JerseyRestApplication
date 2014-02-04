<%--
    Document   : index
    Created on : Jan 22, 2014, 7:03:18 AM
    Author     : geebzter
--%>

<%@page import="javax.naming.Context"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page C</title>
    </head>
<body>
<h1>Data in my Connection Pooled Database</h1>
<%
InitialContext initialContext = new InitialContext();
Context context = (Context) initialContext.lookup("java:comp/env");
//The JDBC Data source that we just created
DataSource ds = (DataSource) context.lookup("SMLS");
Connection connection = ds.getConnection();

if (connection == null)
{
throw new SQLException("Error establishing connection!");
}
String query = "SELECT DESCRIPTION FROM SKILLS";

PreparedStatement statement = connection.prepareStatement(query);
ResultSet rs = statement.executeQuery();

while (rs.next())
{
out.print(rs.getString("DESCRIPTION") + "<br>");
}
%>
</body>
</html>

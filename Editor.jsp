<%-- 
    This is the poster of user's messages.
    Contact: Sergey Pitirimov, sergey.pitirimov@innopolis.ru
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Poster.Storage"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Your posts are here</title>
  </head>
  <body>
    <%
      if (request.getParameter("username") != null)
      {
    %>
        <h1><%=request.getParameter("username")%>, you may create or edit your posts here!</h1>

        <form method="post" action="Login.htm">
          New post: <input type="text" name = "newpost" value="" size="80" autofocus>
          <input type="submit" value="Publish">
          <input type="hidden" name = "username" value="<%=request.getParameter("username")%>">
          <%
            Storage editStorage = new Storage();
      
            /* Print all posts of user, who has logged in */
            for(String post: editStorage.getPosts(editStorage.getUserId(request.getParameter("username"))))
            {
          %>
              <br>Edit post: <input type="text" name="<%=editStorage.getPostId(post)%>" value="<%=post%>" size="80">
            <%
            }
            %>
        </form>
    <%
      }
      else
      {
        response.sendRedirect("Login.htm");
      }
    %>
  </body>
</html>
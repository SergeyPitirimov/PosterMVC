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
    <title>Poster - you may say whatever you want!</title>
  </head>
  <body>
    <div>
      <h1>You may say whatever you want!</h1>
        <form method="post" action="Editor.htm">
          Name <input type="text" name = "username" value="" autofocus>
          Password <input type="password" name = "userpassword" value="">
          <input type="submit" value="Enter">
      </form>
      <%
        /* Create the storage for posts */
        Storage loginStorage = new Storage();
        /* Print all posts for all users */
        for(String element: loginStorage.getPosts(-1))
        {
      %>
          <p><%=element%></p>
      <%
        }
      %>
    </div>
  </body>
</html>
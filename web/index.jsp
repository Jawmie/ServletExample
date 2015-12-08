<%--
  Created by IntelliJ IDEA.
  User: Jawmie
  Date: 07/12/2015
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>

  <form id = "myForm" action = "/myServlet" method = "post">
    <p><label>
      Example form for input data
      <br/>
      First name:
      <input type="text" name="firstname" size="40"/>
      <br/>
      <br/>
      Last name:
      <input type="text" name="lastname" size="40"/>

      <input type = "submit" value = "Submit" />

      <input type="button" onClick="myReset()" value="Reset">

    </label></p>
  </form>

  <script>
    function myReset(){
      document.getElementById("myForm").reset();
    }
  </script>

  </body>
</html>

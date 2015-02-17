<%-- 
    Document   : admin
    Created on : Feb 11, 2015, 4:54:36 PM
    Author     : kelli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Home</title>
        <link type="text/css" href="styles/admin_style.css" rel="stylesheet"/>
    </head>
    <body>
        <%@include file="../common/header.html" %>
        <div class="login_panel">
            <form action="login_control" method="POST">
                <table>
                    <tr><td style="background-color: gainsboro; margin-bottom: 0px;
                            margin-top: 0px; padding-top: 5px; padding-bottom: 5px;">
                            <strong>Admin Login</strong>
                        </td></tr>
                    <tr><td><input type="text" id="tx_admin_user" placeholder="user_name"/></td></tr>
                    <tr><td><input type="password" id="tx_admin_password" placeholder="password"/></td></tr>
                    <tr ><td ><input  type="submit" value="Login" style="position: relative; left: 150px;"/></td></tr>
                </table>
            </form>
        </div>
        <%@include file="../common/footer.html" %>
    </body>
</html>

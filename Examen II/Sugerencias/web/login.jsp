<%-- 
    Document   : login
    Created on : Jun 17, 2017, 5:25:15 PM
    Author     : michaelcw02
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOGIN</title>
        <link href="css/stylesheet.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <%@include file="header.jspf" %>
            <div class="secondary-header">
                <div class="div-2">
                    <h2>Enter your data to log in</h2>
                </div>
            </div>
        </div>
        <br><br>
        <div class="container">
            <div class="div-off-1 div-1">
                <div class="container login">
                    <form action="javascript:doLogin()" id="loginForm">
                        <div class="row"><h3>LOGIN</h3></div>
                        <div class="row">
                            <div class="div-1">Username: </div>
                            <div class="div-3"><input type="text" name="username" id="username" placeholder="username"></div>
                        </div>
                        <div class="row">
                            <div class="div-1">Password: </div>
                            <div class="div-3"><input type="password" name="password" id="password"></div>
                        </div>
                        <div class="row">
                            <div class="div-off-1 div-1"><button type="submit">Login</button></div>
                            <div class="div-1"><button type="button" id="cancel">Cancel</button></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="js/Proxy.js" type="text/javascript"></script>
        <script src="js/loginController.js" type="text/javascript"></script>
        <script src="js/loginView.js" type="text/javascript"></script>
    </body>
</html>

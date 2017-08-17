<%-- 
    Document   : questions
    Created on : Jun 18, 2017, 9:00:46 AM
    Author     : michaelcw02
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sugerencias</title>
        <link href="css/stylesheet.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <%@include file="header.jspf" %>
            <div class="secondary-header">
                <div class="row">
                    <div class="div-2"><h2>TEXTO: <input type="text" placeholder="Parte del texto de su sugerencia" id="texto"> <button id="searchText">SEARCH</button></h2></div>
                </div>
            </div>
        </div>
        <br><br>
        <div class="container">
            <div class="div-2">
                <div class="container">
                    <table class="grid">
                        <thead>
                            <tr>
                                <th>Sugerencia</th>
                                <th>Puntaje</th>
                            </tr>
                        </thead>
                        <tbody id="sugerencia-list">
                            <tr>
                                <td colspan="2" class="text-center">No data to show</td>
                            </tr>
                        </tbody>
                        <tfoot>
                            <td><input type="text" id="sugerenciaNueva" placeholder="TEXTO"></td>
                            <td class="text-center"><button id="agregar">Agregar</button></td>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
        <script src="js/Proxy.js" type="text/javascript"></script>
        <script src="js/loginController.js" type="text/javascript"></script>
        <script src="js/sugerenciasController.js" type="text/javascript"></script>
        <script src="js/sugerenciasView.js" type="text/javascript"></script>
    </body>
</html>
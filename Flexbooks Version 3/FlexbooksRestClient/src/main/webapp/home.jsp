<%-- 
    Document   : homejsp
    Created on : May 27, 2020, 8:52:22 PM
    Author     : ukwa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Welcome to Off-Da-Hook Flexbooks</title>
        <link href="root.css" rel="stylesheet" type="text/css">
	<link href="home_aboutUs.css" rel="stylesheet" type="text/css">
	<script src="file.js"></script>
    </head>
    <body class="background_image">
        <div align=center><img style="width:80%; border:none;" src="./images/topBanner.png"></div>
        <div class="top_menu">
            <a class="current" href="home.jsp">Home</a>
            <a href="aboutUs.html">About Us</a>
        </div>
        <div class="container1" style="display:flex; margin-top:10px;">
            <div class="grid">
                <jsp:include page="/getRecentlyViewed" />
            </div>
        </div>
        <div class="container2" style="display:flex; margin-top:10px;">
            <div class="grid">
                <h1 align="left">Our Collection</h1>
                <jsp:include page="/products"/>
            </div>
        </div>
        <div class="container3" style="display:flex; margin-top:10px;">
	<div class="grid">
		<p><b>Created By:</b> Ukwa Akkum (68576133), Angela Do (86236858), Andres Garcia (19936980), Andy Yang (32972683)</p>
	</div>
</div>
        
    </body>
</html>

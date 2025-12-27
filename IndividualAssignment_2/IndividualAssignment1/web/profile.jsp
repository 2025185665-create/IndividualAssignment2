<%-- 
    Document   : profile.jsp
    Created on : Dec 27, 2025, 1:07:48 PM
    Author     : user
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.myprofile.ProfileBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Saved Profile</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <div class="container result-box">
        <div class="logo-box">
            <img src="images/logo.jpg" alt="Logo">
        </div>
        
        <h2>Profile Saved</h2>
        <%
            ProfileBean profile = (ProfileBean) request.getAttribute("profile");
            if (profile != null) {
        %>
        <p><strong>Name:</strong> <%= profile.getName() %></p>
        <p><strong>Student ID:</strong> <%= profile.getStudentId() %></p>
        <p><strong>Program Code:</strong> <%= profile.getProgram() %></p>
        <p><strong>Email:</strong> <%= profile.getEmail() %></p>
        <p><strong>Hobbies:</strong> <%= profile.getHobbies() %></p>

        <h3>Self-Intro</h3>
        <div class="intro-box"><%= profile.getIntro() %></div>
        <%
            }
        %>

        <br><br>
        <a href="form.html">
            <button type="button">Create New Profile</button>
        </a>

        <a href="index.html">
            <button type="button" class="btn-secondary">Back to Home</button>
        </a>
    </div>

</body>
</html>
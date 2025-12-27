<%-- 
    Document   : viewProfiles.jsp
    Created on : Dec 27, 2025, 1:39:39 PM
    Author     : user
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.myprofile.ProfileBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View All Profiles</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .search-filter-container {
            background: #f4f7ff;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .search-filter-container form {
            display: inline-block;
            margin-right: 15px;
            width: calc(50% - 10px);
            vertical-align: top;
        }

        .search-filter-container input[type="text"],
        .search-filter-container select {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border-radius: 8px;
            border: 1px solid #aac;
            background: white;
        }

        .search-filter-container button {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #003c8f;
            color: white;
            font-weight: bold;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f0f0f0;
        }

        .action-btn {
            padding: 8px 12px;
            margin: 2px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
            font-size: 14px;
        }

        .delete-btn {
            background-color: #f44336;
            color: white;
        }

        .delete-btn:hover {
            background-color: #da190b;
        }

        .edit-btn {
            background-color: #2196F3;
            color: white;
        }

        .edit-btn:hover {
            background-color: #0b7dda;
        }

        .no-results {
            text-align: center;
            color: #666;
            padding: 20px;
            font-size: 16px;
        }

        .result-info {
            background: #e3f2fd;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 15px;
            color: #003c8f;
        }

        .container {
            width: 90%;
            max-width: 1200px;
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="logo-box">
            <img src="images/logo.jpg" alt="Logo">
        </div>

        <h2>All Student Profiles</h2>

        <div class="search-filter-container">
            <!-- Search by Name or Student ID -->
            <form method="POST" action="ProfileServlet">
                <input type="hidden" name="action" value="search">
                <label><strong>Search by Name or Student ID:</strong></label>
                <input type="text" name="searchTerm" placeholder="Enter name or student ID" required>
                <button type="submit">Search</button>
            </form>

            <br>
            <!-- Filter by Program -->
            <form method="GET" action="ProfileServlet">
                <input type="hidden" name="action" value="filter">
                <label><strong><br>Filter by Program:</strong></label>
                <select name="program" required onchange="this.form.submit()">
                    <option value="">-- Select Program --</option>
                    <%
                        Set<String> programs = (Set<String>) request.getAttribute("programs");
                        String selectedProgram = (String) request.getAttribute("selectedProgram");
                        if (programs != null) {
                            for (String program : programs) {
                                if (program != null && !program.isEmpty()) {
                                    String selected = (selectedProgram != null && selectedProgram.equals(program)) ? "selected" : "";
                    %>
                    <option value="<%= program %>" <%= selected %>><%= program %></option>
                    <%
                                }
                            }
                        }
                    %>
                </select>
            </form>
        </div>

        <!-- Result -->
        <%
            Boolean isSearch = (Boolean) request.getAttribute("isSearch");
            Boolean isFilter = (Boolean) request.getAttribute("isFilter");
            String searchTerm = (String) request.getAttribute("searchTerm");
            String selectedProgramDisplay = (String) request.getAttribute("selectedProgram");

            if (isSearch != null && isSearch) {
        %>
        <div class="result-info">
            Search results for: <strong><%= searchTerm %></strong>
        </div>
        <%
            } else if (isFilter != null && isFilter) {
        %>
        <div class="result-info">
            Profiles filtered by program: <strong><%= selectedProgramDisplay %></strong>
        </div>
        <%
            }
        %>

        <%
            List<ProfileBean> profiles = (List<ProfileBean>) request.getAttribute("profiles");
            if (profiles != null && !profiles.isEmpty()) {
        %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Student ID</th>
                    <th>Program</th>
                    <th>Email</th>
                    <th>Hobbies</th>
                    <th>Date Created</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (ProfileBean profile : profiles) {
                %>
                <tr>
                    <td><%= profile.getId() %></td>
                    <td><%= profile.getName() %></td>
                    <td><%= profile.getStudentId() %></td>
                    <td><%= profile.getProgram() %></td>
                    <td><%= profile.getEmail() %></td>
                    <td><%= profile.getHobbies() %></td>
                    <td><%= profile.getDateCreated() %></td>
                    <td>
                        <form method="POST" action="ProfileServlet" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="<%= profile.getId() %>">
                            <button type="submit" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to delete this profile?');">Delete</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <%
            } else {
        %>
        <div class="no-results">
            <p>No profiles found. <a href="form.html">Create a new profile</a></p>
        </div>
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
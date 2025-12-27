package com.myprofile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/ProfileServlet"})
public class ProfileServlet extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("save".equals(action)) {
            saveProfile(request, response);
        } else if ("search".equals(action)) {
            searchProfile(request, response);
        } else if ("delete".equals(action)) {
            deleteProfile(request, response);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("viewAll".equals(action)) {
            viewAllProfiles(request, response);
        } else if ("filter".equals(action)) {
            filterProfilesByProgram(request, response);
        }
    }

    // Save Profile to Database
    private void saveProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String studentId = request.getParameter("studentId");
            String program = request.getParameter("program");
            String email = request.getParameter("email");
            String hobbies = request.getParameter("hobbies");
            String intro = request.getParameter("intro");

            ProfileBean profile = new ProfileBean(name, studentId, program, email, hobbies, intro);

            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO APP.PROFILE (name, studentId, program, email, hobbies, intro) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, profile.getName());
            pstmt.setString(2, profile.getStudentId());
            pstmt.setString(3, profile.getProgram());
            pstmt.setString(4, profile.getEmail());
            pstmt.setString(5, profile.getHobbies());
            pstmt.setString(6, profile.getIntro());

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            request.setAttribute("profile", profile);
            request.getRequestDispatcher("profile.jsp").forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    // View All Profiles
    private void viewAllProfiles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<ProfileBean> profiles = new ArrayList<>();
            Set<String> programs = new HashSet<>();

            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM APP.PROFILE ORDER BY id ASC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ProfileBean profile = new ProfileBean();
                profile.setId(rs.getInt("id"));
                profile.setName(rs.getString("name"));
                profile.setStudentId(rs.getString("studentId"));
                profile.setProgram(rs.getString("program"));
                profile.setEmail(rs.getString("email"));
                profile.setHobbies(rs.getString("hobbies"));
                profile.setIntro(rs.getString("intro"));
                profile.setDateCreated(rs.getString("dateCreated"));
                profiles.add(profile);
                programs.add(rs.getString("program"));
            }

            rs.close();
            pstmt.close();
            conn.close();

            request.setAttribute("profiles", profiles);
            request.setAttribute("programs", programs);
            request.getRequestDispatcher("viewProfiles.jsp").forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    // Search Profile by Name or Student ID
    private void searchProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String searchTerm = request.getParameter("searchTerm");
            List<ProfileBean> profiles = new ArrayList<>();
            Set<String> programs = new HashSet<>();

            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM APP.PROFILE WHERE name LIKE ? OR studentId LIKE ? ORDER BY id ASC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + searchTerm + "%");
            pstmt.setString(2, "%" + searchTerm + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ProfileBean profile = new ProfileBean();
                profile.setId(rs.getInt("id"));
                profile.setName(rs.getString("name"));
                profile.setStudentId(rs.getString("studentId"));
                profile.setProgram(rs.getString("program"));
                profile.setEmail(rs.getString("email"));
                profile.setHobbies(rs.getString("hobbies"));
                profile.setIntro(rs.getString("intro"));
                profile.setDateCreated(rs.getString("dateCreated"));
                profiles.add(profile);
                programs.add(rs.getString("program"));
            }

            rs.close();
            pstmt.close();
            conn.close();

            request.setAttribute("profiles", profiles);
            request.setAttribute("programs", programs);
            request.setAttribute("searchTerm", searchTerm);
            request.setAttribute("isSearch", true);
            request.getRequestDispatcher("viewProfiles.jsp").forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    // Filter Profiles by Program
    private void filterProfilesByProgram(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String selectedProgram = request.getParameter("program");
            List<ProfileBean> profiles = new ArrayList<>();
            Set<String> programs = new HashSet<>();

            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM APP.PROFILE WHERE program = ? ORDER BY id ASC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, selectedProgram);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ProfileBean profile = new ProfileBean();
                profile.setId(rs.getInt("id"));
                profile.setName(rs.getString("name"));
                profile.setStudentId(rs.getString("studentId"));
                profile.setProgram(rs.getString("program"));
                profile.setEmail(rs.getString("email"));
                profile.setHobbies(rs.getString("hobbies"));
                profile.setIntro(rs.getString("intro"));
                profile.setDateCreated(rs.getString("dateCreated"));
                profiles.add(profile);
                programs.add(rs.getString("program"));
            }

            rs.close();
            pstmt.close();

            // Get all programs for the dropdown
            String sqlAllPrograms = "SELECT DISTINCT program FROM APP.PROFILE ORDER BY program";
            PreparedStatement pstmtAll = conn.prepareStatement(sqlAllPrograms);
            ResultSet rsAll = pstmtAll.executeQuery();
            while (rsAll.next()) {
                programs.add(rsAll.getString("program"));
            }
            rsAll.close();
            pstmtAll.close();
            conn.close();

            request.setAttribute("profiles", profiles);
            request.setAttribute("programs", programs);
            request.setAttribute("selectedProgram", selectedProgram);
            request.setAttribute("isFilter", true);
            request.getRequestDispatcher("viewProfiles.jsp").forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    // Delete Profile
    private void deleteProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Connection conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM APP.PROFILE WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            response.sendRedirect("ProfileServlet?action=viewAll");

        } catch (ClassNotFoundException | SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

}
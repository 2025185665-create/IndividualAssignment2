/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myprofile;

/**
 *
 * @author user
 */
public class ProfileBean {
    private int id;
    private String name;
    private String studentId;
    private String program;
    private String email;
    private String hobbies;
    private String intro;
    private String dateCreated;

    public ProfileBean() {}

    public ProfileBean(String name, String studentId, String program,
                       String email, String hobbies, String intro) {
        this.name = name;
        this.studentId = studentId;
        this.program = program;
        this.email = email;
        this.hobbies = hobbies;
        this.intro = intro;
    }

    // Getters & Setters
    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }

    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }

    public String getStudentId() { 
        return studentId; 
    }
    public void setStudentId(String studentId) { 
        this.studentId = studentId; 
    }

    public String getProgram() { 
        return program; 
    }
    public void setProgram(String program) { 
        this.program = program; 
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getHobbies() { 
        return hobbies; 
    }
    public void setHobbies(String hobbies) { 
        this.hobbies = hobbies; 
    }

    public String getIntro() { 
        return intro; 
    }
    public void setIntro(String intro) { 
        this.intro = intro; 
    }

    public String getDateCreated() { 
        return dateCreated; 
    }
    public void setDateCreated(String dateCreated) { 
        this.dateCreated = dateCreated; 
    }
}

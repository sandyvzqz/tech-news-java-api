package com.technews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

//Entity marks this as a persistable object so User class can map to a table
@Entity
//this specifies properties should be ignored when serializing this object to JSON, the two arguments are the properties that should be ignored
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//this specifies the name of the table that this class maps to
//if annotation isn't present, the table name will be the class name by default
@Table(name = "user")

public class User implements Serializable {
    //signals that id will be used as a unique identifier
    @Id
    //signals that it will be an automatically generated value
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;

    //signals that email value must be unique, duplicates won't be allowed
    @Column(unique = true)
    private String email;
    private String password;

    //Transient signals to Spring Data JPA that this data is NOT to be persisted in the database
    // we don't need/want a user's logged-in status to persist in the data
    @Transient
    boolean loggedIn;

    // @OneToMany annotation in Java is the equivalent to one-to-many relationship in SQL (JavaScript)
    // this annotation creates table relationships automatically
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> posts;

    // Need to use FetchType.LAZY to resolve multiple bags exception
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vote> votes;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public User() {}

    public User(Integer id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public boolean isLoggedIn() {

        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {

        this.loggedIn = loggedIn;
    }

    public List<Post> getPosts() {

        return posts;
    }

    public void setPosts(List<Post> posts) {

        this.posts = posts;
    }

    public List<Vote> getVotes() {

        return votes;
    }

    public void setVotes(List<Vote> votes) {

        this.votes = votes;
    }

    public List<Comment> getComments() {

        return comments;
    }

    public void setComments(List<Comment> comments) {

        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isLoggedIn() == user.isLoggedIn() &&
                Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getPosts(), user.getPosts()) &&
                Objects.equals(getVotes(), user.getVotes()) &&
                Objects.equals(getComments(), user.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getEmail(), getPassword(), isLoggedIn(), getPosts(), getVotes(), getComments());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", loggedIn=" + loggedIn +
                ", posts=" + posts +
                ", votes=" + votes +
                ", comments=" + comments +
                '}';
    }
}

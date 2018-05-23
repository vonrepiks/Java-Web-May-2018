package org.softuni.database.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "user_id")
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_friends",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "friend_id") },
            foreignKey = @ForeignKey(name = "fk_users_friends_users"),
            inverseForeignKey = @ForeignKey(name = "fk_user_friends_friends")
    )
    private Set<User> friends;

    public User() {
        this.friends = new HashSet<>();
    }

    public User(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<User> getFriends() {
        return Collections.unmodifiableSet(this.friends);
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }
}

package spring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserFromRegisterDto {
    @JsonProperty
    private String username;

    @JsonProperty
    private String password;


    @JsonProperty
    private int age;


    @JsonProperty
    private String firstName;


    @JsonProperty
    private String lastName;

    public UserFromRegisterDto() {
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

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        StringBuilder user = new StringBuilder();

        user.append(this.username).append(System.lineSeparator())
                .append(this.password).append(System.lineSeparator())
                .append(this.age).append(System.lineSeparator())
                .append(this.firstName).append(System.lineSeparator())
                .append(this.lastName).append(System.lineSeparator());

        return user.toString();
    }
}

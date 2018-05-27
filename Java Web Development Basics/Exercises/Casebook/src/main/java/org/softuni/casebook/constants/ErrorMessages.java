package org.softuni.casebook.constants;

public final class ErrorMessages {

    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Something went wrong!!!";
    public static final String NOT_FOUND_ERROR_MESSAGE = "Error (404): The page or resource you are looking for is invalid!!!";
    public static final String PASSWORDS_MISMATCH_ERROR_MESSAGE = "Passwords mismatch!!!";
    public static final String USER_ALREADY_EXIST_ERROR_MESSAGE = "User with this email already exist!!!";
    public static final String WRONG_PASSWORD = "Password didn't match with given user email!!!";
    public static final String NO_USER_WITH_THIS_EMAIL = "No such user with given email!!!";
    public static final String ALL_FIELDS_REQUIRED = "Please fill all fields!!!";
    public static final String ROUTES_ACCESS_ERROR_MESSAGE_AFTER_LOGIN = "Please first logout before you try again to access this page!!!";

    private ErrorMessages() {}
}

package org.c4marathon.assignment.user.application.port.in;

public interface JoinUserUseCase {

    void joinUser(String name, String email, String password) throws IllegalAccessException;

}

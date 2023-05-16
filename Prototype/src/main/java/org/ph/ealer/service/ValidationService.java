package org.ph.ealer.service;

import org.ph.ealer.status.UserResponseResult;

public interface ValidationService {
    public UserResponseResult isUsernameExist(String username);
    public UserResponseResult isPasswordLegal(String password);
    public UserResponseResult isTelExist(long tel);
    public UserResponseResult isEmailExist(String email);
    public UserResponseResult isCodeTrue(boolean truth);
}

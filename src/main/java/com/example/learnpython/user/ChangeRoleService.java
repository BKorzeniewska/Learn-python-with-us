package com.example.learnpython.user;

import com.example.learnpython.user.model.ChangeRoleRequest;
import com.example.learnpython.user.model.GetUsersRequest;
import com.example.learnpython.user.model.UsersDTO;

public interface ChangeRoleService {
    UsersDTO getUsers(final GetUsersRequest request);
    void changeRole(final ChangeRoleRequest changeRoleRequest, final String token);
}

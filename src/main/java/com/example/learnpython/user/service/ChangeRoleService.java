package com.example.learnpython.user.service;

import com.example.learnpython.user.model.dto.ChangeRoleRequest;
import com.example.learnpython.user.model.dto.GetUsersRequest;
import com.example.learnpython.user.model.dto.UsersDTO;

public interface ChangeRoleService {
    UsersDTO getUsers(final GetUsersRequest request);
    void changeRole(final ChangeRoleRequest changeRoleRequest, final String bearerToken);
}

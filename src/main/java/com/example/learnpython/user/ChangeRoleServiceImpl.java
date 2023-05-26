package com.example.learnpython.user;

import com.example.learnpython.user.exception.UserNotFoundException;
import com.example.learnpython.user.exception.UserRequestException;
import com.example.learnpython.user.model.ChangeRoleRequest;
import com.example.learnpython.user.model.GetUsersRequest;
import com.example.learnpython.user.model.UserResponse;
import com.example.learnpython.user.model.UsersDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChangeRoleServiceImpl implements ChangeRoleService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void changeRole(final ChangeRoleRequest changeRoleRequest, final String token) {

        final User currentUser = userRepository.findByToken(token)
                .orElseThrow(() -> new UserNotFoundException("User with provided token not found", "USER_NOT_FOUND"));

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new UserRequestException("You can't change role", "ROLE_CHANGE_ERROR");
        }

        final User user = userRepository.findById(changeRoleRequest.userId())
                .orElseThrow(() -> new UserNotFoundException("User with provided id not found", "USER_NOT_FOUND"));

        if (user.equals(currentUser)) {
            throw new UserRequestException("You can't change your own role", "ROLE_CHANGE_ERROR");
        }

        if (user.getRole().equals(Role.ADMIN)) {
            throw new UserRequestException("You can't change role of admin", "ROLE_CHANGE_ERROR");
        }

        if (user.getRole().getValue() == changeRoleRequest.role().getValue()) {
            throw new UserRequestException("User role is already the same", "ROLE_CHANGE_ERROR");
        }

        if (currentUser.getRole().getValue() >= changeRoleRequest.role().getValue()) {
            throw new UserRequestException("You can't change role to higher than yours", "ROLE_CHANGE_ERROR");
        }

        user.setRole(changeRoleRequest.role());
        userRepository.updateRoleById(user.getId(), changeRoleRequest.role());

        log.info("User role changed successfully");
    }

    @Override
    public UsersDTO getUsers(final GetUsersRequest request) {
        log.info("getUsers() - Request: {}", request);

        if (request.pageNumber() != null && (request.query() == null || request.query().isBlank())) {
            return getUsersDTO(request.pageNumber());
        }

        return getUserByNickname(request.query(), request.pageNumber());
    }

    private UsersDTO getUsersDTO(final Integer pageNumber) {
        final int page = pageNumber < 1 ? 0 : pageNumber -1;
        final Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");

        log.info("getUsers() - Page: {}", page);

        final Page<UserResponse> results = userRepository
                .findAll(pageable)
                .map(userMapper::toResponse);

        log.info("getUsers() - Results: {}", results);

        return new UsersDTO(results);
    }

    private UsersDTO getUserByNickname(final String query, final Integer pageNumber) {
        final int page = pageNumber < 1 ? 0 : pageNumber -1;
        final Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");

        log.info("getUserByNickname() - Query: {}, Page: {}", query, page);

        final Page<UserResponse> users = userRepository
                .findByNicknameContainingIgnoreCase(query, pageable)
                .map(userMapper::toResponse);

        log.info("getUserByNickname() - Results: {}", users);

        return new UsersDTO(users);
    }
}

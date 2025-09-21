package com.ncu.college.users.irepository;

import java.util.List;
import com.ncu.college.users.models.User;

public interface IUserRepository {
    List<User> getAllUsers();
    User getUserById(int id);
    User createUser(User user);
    User updateUser(int id, User user);
    int deleteUser(int id);
    List<User> searchUsersByName(String name);
    int updatePassword(int id, String newPasswordHash);
}

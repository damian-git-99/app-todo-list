package com.github.damian_git_99.backend.user.role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findRoleByName(String name);

}

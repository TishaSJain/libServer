package com.unilib.libserver.repo;

import com.unilib.libserver.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Roles,Integer> {
}

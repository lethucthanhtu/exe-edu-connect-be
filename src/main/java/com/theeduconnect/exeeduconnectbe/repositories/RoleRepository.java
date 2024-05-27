/* (C) 2024 */
package com.theeduconnect.exeeduconnectbe.repositories;

import com.theeduconnect.exeeduconnectbe.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {}

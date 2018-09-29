package com.example.demo.dao;

import com.example.demo.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Integer>
{
    Role findByName(String name);
}

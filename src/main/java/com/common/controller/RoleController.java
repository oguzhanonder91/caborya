package com.common.controller;

import com.common.entity.Role;
import com.common.exception.BaseNotFoundException;
import com.common.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by oguzhanonder - 7.11.2018
 */

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Role findOne(@PathVariable String id) {
        Optional<Role> role = roleService.findById(id);
        if (!role.isPresent())
            throw new BaseNotFoundException(id + " - Bu id ye ait Rol Bulunamadı-");
        return role.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role create(@RequestBody Role role) {
        return roleService.save(role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        Optional<Role> role = roleService.findById(id);
        if (!role.isPresent())
            throw new BaseNotFoundException(id + " - Bu id ye ait Rol Bulunamadı-");
        roleService.delete(role.get());
    }

    @PutMapping("/{id}")
    public Role updateRole(@RequestBody Role role, @PathVariable String id) {
        Optional<Role> oldRole = roleService.findById(id);
        if (!oldRole.isPresent())
            throw new BaseNotFoundException(id + " - Bu id ye ait Rol Bulunamadı-");
        return roleService.update(role);
    }
}

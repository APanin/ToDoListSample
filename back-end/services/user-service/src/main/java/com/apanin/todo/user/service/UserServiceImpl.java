package com.apanin.todo.user.service;

import com.apanin.todo.config.api.WebConfig;
import com.apanin.todo.entity.PermissionEntity;
import com.apanin.todo.entity.RoleEntity;
import com.apanin.todo.entity.UserEntity;
import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.repository.PermissionsRepository;
import com.apanin.todo.repository.RoleRepository;
import com.apanin.todo.repository.UserRepository;
import com.apanin.todo.sample.rest.model.Permission;
import com.apanin.todo.sample.rest.model.Role;
import com.apanin.todo.sample.rest.model.User;
import com.apanin.todo.user.PermissionToPermissionEntityMapper;
import com.apanin.todo.user.RoleToRoleEntityMapper;
import com.apanin.todo.user.UserService;
import com.apanin.todo.user.UserToUserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionsRepository permissionsRepository;
    private final WebConfig config;

    public UserServiceImpl(@Autowired UserRepository userRepository,
                           RoleRepository roleRepository, PermissionsRepository permissionsRepository,
                           @Autowired WebConfig config) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionsRepository = permissionsRepository;
        this.config = config;
    }

    @Override
    public List<User> listUsers(int page) throws BusinessException, TechnicalException {
        final Pageable pageable = PageRequest.of(page, config.getItemsOnPage());
        final Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        return userEntityPage.stream().map(userEntity -> {
            final User user = UserToUserEntityMapper.INSTANCE.userEntityToUser(userEntity);
            user.setCurrentPage(page);
            user.setTotalPages(userEntityPage.getTotalPages());
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User getUser(Long userId) throws BusinessException, TechnicalException {
        final Optional<UserEntity> entityOpt = userRepository.findById(userId);
        if (!entityOpt.isPresent())
            throw new BusinessException("Entity not found by id " + userId);
        return UserToUserEntityMapper.INSTANCE.userEntityToUser(entityOpt.get());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserDetails getUserDetailsByLogin(String login) throws BusinessException {
        final UserEntity entity = getUserEntityByLogin(login);
        return new org.springframework.security.core.userdetails.User(entity.getLogin(),
                entity.getPassword(), entity.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User getUserByLogin(String login) throws BusinessException {
        final UserEntity userEntity = getUserEntityByLogin(login);
        return UserToUserEntityMapper.INSTANCE.userEntityToUser(userEntity);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserEntity getUserEntityByLogin(String login) throws BusinessException {
        final Optional<UserEntity> entityOpt = userRepository.findUserEntityByLogin(login);
        if (!entityOpt.isPresent())
            throw new BusinessException("Entity not found by login " + login);
        return entityOpt.get();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Long createUser(User user) throws BusinessException, TechnicalException {
        final UserEntity entity = UserToUserEntityMapper.INSTANCE.userToUserEntity(user);
        final UserEntity persistedEntity = userRepository.save(entity);
        return persistedEntity.getId();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateUser(User user) throws BusinessException, TechnicalException {
        final UserEntity entity = UserToUserEntityMapper.INSTANCE.userToUserEntity(user);
        userRepository.save(entity);
    }

    @Override
    public void deleteUser(Long userId) throws BusinessException, TechnicalException {
        final Optional<UserEntity> entityOpt = userRepository.findById(userId);
        if (!entityOpt.isPresent())
            throw new BusinessException("Entity not found by id " + userId);
        userRepository.delete(entityOpt.get());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Role> listRoles(int page) throws BusinessException, TechnicalException {
        final Pageable pageable = PageRequest.of(page, config.getItemsOnPage());
        final Page<RoleEntity> roleEntityPage = roleRepository.findAll(pageable);
        return roleEntityPage.stream().map(roleEntity -> {
            final Role role = RoleToRoleEntityMapper.INSTANCE.roleEntityToRole(roleEntity);
            role.setCurrentPage(page);
            role.setTotalPages(roleEntityPage.getTotalPages());
            return role;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Permission> listPermissions(int page) throws BusinessException, TechnicalException {
        final Pageable pageable = PageRequest.of(page, config.getItemsOnPage());
        final Page<PermissionEntity> permissionEntityPage = permissionsRepository.findAll(pageable);
        return permissionEntityPage.stream().map(permissionEntity -> {
            final Permission permission = PermissionToPermissionEntityMapper.INSTANCE.permissionEntityToPermission(permissionEntity);
            permission.setCurrentPage(page);
            permission.setTotalPages(permissionEntityPage.getTotalPages());
            return permission;
        }).collect(Collectors.toList());
    }
}

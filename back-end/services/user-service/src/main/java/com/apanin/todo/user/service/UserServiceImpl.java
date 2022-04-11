package com.apanin.todo.user.service;

import com.apanin.todo.config.api.WebConfig;
import com.apanin.todo.entity.UserEntity;
import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.repository.UserRepository;
import com.apanin.todo.sample.rest.model.User;
import com.apanin.todo.user.UserService;
import com.apanin.todo.user.UserToUserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WebConfig config;

    public UserServiceImpl(@Autowired UserRepository userRepository, @Autowired WebConfig config) {
        this.userRepository = userRepository;
        this.config = config;
    }

    @Override
    public List<User> listUsers(int page) throws BusinessException, TechnicalException {
        final Pageable pageable = PageRequest.of(page, config.getItemsOnPage());
        final Page<UserEntity> taskEntityPage = userRepository.findAll(pageable);
        return taskEntityPage.stream().map(taskEntity -> {
            final User user = UserToUserEntityMapper.INSTANCE.userEntityToUser(taskEntity);
            user.setCurrentPage(page);
            user.setTotalPages(taskEntityPage.getTotalPages());
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    public User getUser(Long userId) throws BusinessException, TechnicalException {
        final Optional<UserEntity> entityOpt = userRepository.findById(userId);
        if (!entityOpt.isPresent())
            throw new BusinessException("Entity not found by id " + userId);
        return UserToUserEntityMapper.INSTANCE.userEntityToUser(entityOpt.get());
    }

    @Override
    public UserDetails getUserDetailsByLogin(String login) throws BusinessException {
        final Optional<UserEntity> entityOpt = userRepository.findUserEntityByLogin(login);
        if (!entityOpt.isPresent())
            throw new BusinessException("Entity not found by login " + login);
        final UserEntity entity = entityOpt.get();
        return new org.springframework.security.core.userdetails.User(entity.getLogin(),
                entity.getPassword(), entity.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }

    @Override
    public Long createUser(User user) throws BusinessException, TechnicalException {
        final UserEntity entity = UserToUserEntityMapper.INSTANCE.userToUserEntity(user);
        final UserEntity persistedEntity = userRepository.save(entity);
        return persistedEntity.getId();
    }

    @Override
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
}

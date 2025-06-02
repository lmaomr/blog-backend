package cn.lmao.blogbackend.services;

import cn.lmao.blogbackend.exception.CustomException;
import cn.lmao.blogbackend.model.entity.User;
import cn.lmao.blogbackend.model.enums.ExceptionCodeMsg;
import cn.lmao.blogbackend.repository.UserRepository;
import cn.lmao.blogbackend.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Logger log = LogUtils.getLogger();
    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        log.debug("正在查询用户ID: {}", userId);
        User user = userRepository.getUsersById(userId);
        if (user == null) {
            log.warn("未找到ID为[{}]的用户", userId);
        } else {
            log.debug("成功获取ID为[{}]的用户信息", userId);
        }
        return user;
    }

    public User getUserByName(String username) {
        log.debug("正在通过用户名查询: {}", username);
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            log.debug("用户名[{}]不存在", username);
        }
        return user;
    }

    public User getUserByEmail(String email) {
        log.debug("正在通过邮箱查询: {}", email);
        User user = userRepository.getUsersByEmail(email);
        if (user == null) {
            log.debug("邮箱[{}]未注册", email);
        }
        return user;
    }

    public User registerUser(User user) {
        log.info("开始注册新用户 用户名:[{}] 邮箱:[{}]",
                user.getUsername(), user.getEmail());

        // 检查用户名是否已存在
        User existingUserByName = getUserByName(user.getUsername());
        if (existingUserByName != null) {
            log.warn("注册失败 - 用户名[{}]已存在", user.getUsername());
            throw new CustomException(ExceptionCodeMsg.USERNAME_ALREADY_EXISTS);
        }

        // 检查邮箱是否已存在
        User existingUserByEmail = getUserByEmail(user.getEmail());
        if (existingUserByEmail != null) {
            log.warn("注册失败 - 邮箱[{}]已被注册", user.getEmail());
            throw new CustomException(ExceptionCodeMsg.EMAIL_ALREADY_EXISTS);
        }

        User savedUser = userRepository.save(user);
        log.info("用户注册成功 ID:[{}] 用户名:[{}]",
                savedUser.getId(), savedUser.getUsername());

        return savedUser;
    }
}
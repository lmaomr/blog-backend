package cn.lmao.blogbackend.services;

import cn.lmao.blogbackend.exception.CustomException;
import cn.lmao.blogbackend.model.entity.User;
import cn.lmao.blogbackend.model.enums.ExceptionCodeMsg;
import cn.lmao.blogbackend.repository.UserRepository;
import cn.lmao.blogbackend.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务类
 * 处理用户相关的业务逻辑
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final Logger log = LogUtils.getLogger();
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return 用户信息，如果不存在返回null
     */
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

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息，如果不存在返回null
     */
    public User getUserByName(String username) {
        log.debug("正在通过用户名查询: {}", username);
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            log.debug("用户名[{}]不存在", username);
        } else {
            log.debug("成功获取用户名[{}]的用户信息", username);
        }
        return user;
    }

    /**
     * 根据邮箱获取用户信息
     * @param email 邮箱
     * @return 用户信息，如果不存在返回null
     */
    public User getUserByEmail(String email) {
        log.debug("正在通过邮箱查询: {}", email);
        User user = userRepository.getUsersByEmail(email);
        if (user == null) {
            log.debug("邮箱[{}]未注册", email);
        } else {
            log.debug("成功获取邮箱[{}]的用户信息", email);
        }
        return user;
    }

    /**
     * 注册新用户
     * @param user 用户信息
     * @return 注册成功的用户信息
     * @throws CustomException 如果用户名或邮箱已存在则抛出异常
     */
    public User registerUser(User user) {
        log.debug("开始注册新用户: {}", user.getUsername());
        
        // 检查用户名是否已存在
        if (getUserByName(user.getUsername()) != null) {
            log.warn("用户名[{}]已存在，注册失败", user.getUsername());
            throw new CustomException(ExceptionCodeMsg.USERNAME_ALREADY_EXISTS);
        }
        
        // 检查邮箱是否已存在
        if (getUserByEmail(user.getEmail()) != null) {
            log.warn("邮箱[{}]已被注册，注册失败", user.getEmail());
            throw new CustomException(ExceptionCodeMsg.EMAIL_ALREADY_EXISTS);
        }
        
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 保存用户
        User savedUser = userRepository.save(user);
        log.info("用户[{}]注册成功", savedUser.getUsername());
        
        return savedUser;
    }
}
package cn.lmao.blogbackend.controller;

import cn.lmao.blogbackend.exception.CustomException;
import cn.lmao.blogbackend.model.dto.Response;
import cn.lmao.blogbackend.model.entity.Cloud;
import cn.lmao.blogbackend.model.entity.User;
import cn.lmao.blogbackend.model.enums.ExceptionCodeMsg;
import cn.lmao.blogbackend.services.CloudService;
import cn.lmao.blogbackend.services.UserService;
import cn.lmao.blogbackend.util.LogUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

/**
 * 测试控制器
 * 提供一些基础的测试接口
 */
@RestController
@RequestMapping("/apis")
@RequiredArgsConstructor
public class TestController {

    private static final Logger logger = LogUtils.getLogger();

    private final CloudService cloudService;
    private final UserService userService;

    /**
     * 测试接口
     * @param name 用户名
     * @return 响应结果
     */
    @GetMapping("/test")
    public Response<String> Test(String name) {
        logger.debug("收到测试请求，参数name: {}", name);
        
        if (name == null || name.isEmpty()) {
            // logger.warn("测试请求失败：用户名为空");
            throw new CustomException(404, "用户为空");
        }
        
        if (!name.equals("lmao")) {
            logger.warn("测试请求失败：用户名[{}]不存在", name);
            throw new CustomException(ExceptionCodeMsg.USERNAME_EXISTS);
        }
        
        logger.info("测试请求成功：用户[{}]验证通过", name);
        return Response.error(200, "你好" + name);
    }

    /**
     * 获取用户云盘信息
     * @param id 用户ID
     * @return 云盘信息
     */
    @GetMapping("/cloud")
    public Response<Cloud> getCloudByUserId(Long id) {
        if (id == null) {
            logger.warn("获取云盘失败：用户ID为空");
            throw new CustomException(ExceptionCodeMsg.CLOUD_NOT_FOUND);
        }
        
        User user = userService.getUserById(id);
        if (user == null) {
            logger.warn("获取云盘失败：用户不存在");
            throw new CustomException(ExceptionCodeMsg.CLOUD_NOT_FOUND);
        }

        logger.debug("收到获取云盘请求，用户: {}", user.getUsername());
        Cloud cloud = cloudService.getCloudByUser(user);

        if (cloud == null) {
            logger.warn("用户[{}]的云盘不存在", user.getUsername());
            throw new CustomException(ExceptionCodeMsg.CLOUD_NOT_FOUND);
        }
        
        logger.info("成功获取用户[{}]的云盘信息, 云盘信息: {}", user.getUsername(), cloud);
        return Response.success(cloud);
    }

    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    @Transactional
    public Response<User> registerUser(@RequestBody @Valid User user) {
        logger.debug("收到用户注册请求：{}", user);
        
        User newUser = userService.registerUser(user);
        logger.info("用户[{}]注册成功，开始创建云盘", newUser.getUsername());
        
        cloudService.createCloud(newUser);
        logger.info("用户[{}]的云盘创建成功", newUser.getUsername());
        
        return Response.success(newUser);
    }

    //根据用户名获取用户信息
    @GetMapping("/get")
    public Response<User> findUserById(String username) {
        if (username == null) throw new CustomException(ExceptionCodeMsg.USERNAME_EXISTS);
        return Response.success(userService.getUserByName(username));
    }

    @GetMapping("/all")
    public Response<List<User>> getAllUsers() {
        return Response.success(userService.getAllUsers());
    }

    @DeleteMapping("/delete")
    public Response<String> deleteUser(Long id) {
        userService.deleteUser(id);
        return Response.success("注销成功");
    }

    @PutMapping("/update")
    public Response<String> updateUser(@RequestBody @Valid User user) {
        userService.updateUser(user);
        return Response.success("更新成功");
    }

    @GetMapping("/cloud/delete")
    public Response<String> deleteCloud(Long id) {
        cloudService.deleteCloud(id);
        return Response.success("注销成功");
    }

}

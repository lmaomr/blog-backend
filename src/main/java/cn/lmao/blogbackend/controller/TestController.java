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
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/apis")
@RequiredArgsConstructor
public class TestController {

    private static final Logger logger = LogUtils.getLogger();

    private final CloudService cloudService;
    private final UserService userService;

    @GetMapping("/test")
    public Response<String> Test(String name) {
        if (name == null || name.isEmpty()) {
            logger.warn("用户为空！！！");
            throw new CustomException(404, "用户为空");
        }
        if (!name.equals("lmao")) {
            logger.warn("账号或密码错误！");
            throw new CustomException(ExceptionCodeMsg.USER_NOT_EXISTS);
        }
        return Response.error(200, "你好" + name);
    }

    @GetMapping("/cloud")
    public Response<Cloud> getCloudByUserId(Long id) {
        if (cloudService.getCloudByUserId(id) == null) {
            throw new CustomException(ExceptionCodeMsg.CLOUD_NOT_EXISTS);
        }
        return Response.success(cloudService.getCloudByUserId(id));
    }

    @PostMapping("/register")
    @Transactional
    public Response<User> registerUser(@RequestBody @Valid User user) {
        User newUser = userService.registerUser(user);
        cloudService.createCloud(newUser);
        return Response.success(newUser);
    }

    @GetMapping("/get")
    public Response<User> findUserById(String username) {
        if (username == null) throw new CustomException(ExceptionCodeMsg.PARAM_VERIFY_FAIL);
        return Response.success(userService.getUserByName(username));
    }

}

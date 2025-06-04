package cn.lmao.blogbackend.controller;

import cn.lmao.blogbackend.model.dto.Response;
import cn.lmao.blogbackend.model.entity.User;
import cn.lmao.blogbackend.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apis/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "用户认证相关的接口")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Operation(summary = "用户登录", description = "使用用户名和密码进行登录认证")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "登录成功"),
        @ApiResponse(responseCode = "401", description = "认证失败")
    })
    @PostMapping("/login")
    public Response<String> login(
        @Parameter(description = "登录信息", required = true)
        @RequestBody User loginRequest
    ) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(loginRequest.getUsername());
        
        return Response.success("登录成功", jwt);
    }
} 
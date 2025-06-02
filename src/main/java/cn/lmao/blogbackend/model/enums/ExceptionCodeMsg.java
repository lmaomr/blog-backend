package cn.lmao.blogbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCodeMsg {
    //这个枚举类中定义的都是跟业务有关的异常

        INVALID_CODE(10000,"验证码无效"),
        USER_NOT_EXISTS(10001,"用户不存在或密码错误"),
        USERNAME_ALREADY_EXISTS(10002,"用户名已存在"),
        EMAIL_ALREADY_EXISTS(10003,"邮箱已存在"),
        CLOUD_NOT_EXISTS(10004,"云盘不存在"),
        CLOUD_ALREADY_EXISTS(10005, "云盘已存在"),
        CLOUD_SPACE_DEFICIENCY(10006,"云盘可用空间不足"),
        FILE_SIZE_TOO_MAX(10007,"文件过大，请重新选择小于2GB的文件"),
        PARAM_VERIFY_FAIL(10008,"参数为空或参数错误）"),
        ;

        private final int code ;
        private final String msg ;

}

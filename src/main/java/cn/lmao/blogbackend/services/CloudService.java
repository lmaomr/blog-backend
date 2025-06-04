package cn.lmao.blogbackend.services;

import cn.lmao.blogbackend.exception.CustomException;
import cn.lmao.blogbackend.model.entity.Cloud;
import cn.lmao.blogbackend.model.entity.User;
import cn.lmao.blogbackend.model.enums.ExceptionCodeMsg;
import cn.lmao.blogbackend.repository.CloudRepository;
import cn.lmao.blogbackend.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 云盘服务类
 * 处理用户云盘相关的业务逻辑
 */
@RequiredArgsConstructor
@Service
public class CloudService {

    private final CloudRepository cloudRepository;
    private final Logger log = LogUtils.getLogger();

    /**
     * 根据用户ID获取云盘信息
     * @param userId 用户ID
     * @return 云盘信息，如果不存在返回null
     */
    public Cloud getCloudByUserId(Long userId) {
        log.debug("正在查询用户ID[{}]的云盘信息", userId);
        Cloud cloud = cloudRepository.getCloudByUserId(userId);
        if (cloud == null) {
            log.debug("用户ID[{}]的云盘不存在", userId);
        } else {
            log.debug("成功获取用户ID[{}]的云盘信息", userId);
        }
        return cloud;
    }

    /**
     * 为用户创建云盘
     * @param user 用户信息
     * @throws CustomException 如果用户已有云盘则抛出异常
     */
    public void createCloud(User user) {
        log.debug("开始为用户[{}]创建云盘", user.getUsername());
        
        //判断该用户云盘是否存在
        if (getCloudByUserId(user.getId()) != null) {
            log.warn("用户[{}]的云盘已存在，创建失败", user.getUsername());
            throw new CustomException(ExceptionCodeMsg.CLOUD_ALREADY_EXISTS);
        }
        
        Cloud cloud = new Cloud();
        cloud.setId(user.getId());
        cloudRepository.save(cloud);
        
        log.info("成功为用户[{}]创建云盘", user.getUsername());
    }
}

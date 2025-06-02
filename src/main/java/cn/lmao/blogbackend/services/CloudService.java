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

@RequiredArgsConstructor
@Service
public class CloudService {

    private final CloudRepository cloudRepository;
    private final Logger log = LogUtils.getLogger();

    //根据用户id获取cloud
    public Cloud getCloudByUserId(Long userId) {
        log.debug("已通过id: {} 成功获取cloud", userId);
        return cloudRepository.getCloudByUserId(userId);
    }

    //创建云盘
    public void createCloud(User user) {
        //判断该用户云盘是否存在
        if (getCloudByUserId(user.getId()) != null) throw new CustomException(ExceptionCodeMsg.CLOUD_ALREADY_EXISTS);
        Cloud cloud = new Cloud();
        cloud.setId(user.getId());
        log.debug("成功创建云盘：{}", cloud);
    }
}

package cn.lmao.blogbackend.repository;

import cn.lmao.blogbackend.model.entity.Cloud;
import cn.lmao.blogbackend.model.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudRepository extends JpaRepository<Cloud, Long> {

    Cloud getCloudByUser(User user);

}

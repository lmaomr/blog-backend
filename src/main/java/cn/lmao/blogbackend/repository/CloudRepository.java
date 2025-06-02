package cn.lmao.blogbackend.repository;

import cn.lmao.blogbackend.model.entity.Cloud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudRepository extends JpaRepository<Cloud, Long> {

    Cloud getCloudByUserId(Long userId);

}

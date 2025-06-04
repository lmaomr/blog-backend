package cn.lmao.blogbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.lmao.blogbackend.model.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {

}
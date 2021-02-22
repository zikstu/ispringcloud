package com.xuezhang.repository;

import com.xuezhang.entity.Student;

import java.util.Collection;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/20 20:27
 */
public interface StudentRepository {
    Collection<Student> findAll();

    Student findById(long id);

    void saveOrUpdate(Student student);

    void deleteById(long id);
}

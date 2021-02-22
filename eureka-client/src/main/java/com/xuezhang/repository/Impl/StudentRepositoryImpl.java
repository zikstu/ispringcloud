package com.xuezhang.repository.Impl;

import com.xuezhang.entity.Student;
import com.xuezhang.repository.StudentRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/20 20:30
 */
@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private static Map<Long, Student> studentMap;

    static {
        studentMap = new HashMap<>();
        studentMap.put(1L, new Student(1L, "张三", 22));
        studentMap.put(2L, new Student(2L, "李四", 12));
        studentMap.put(3L, new Student(3L, "王五", 29));
    }

    @Override
    public Collection<Student> findAll() {
        return studentMap.values();
    }

    @Override
    public Student findById(long id) {
        return studentMap.get(id);
    }

    @Override
    public void saveOrUpdate(Student student) {
        studentMap.put(student.getId(), student);
    }

    @Override
    public void deleteById(long id) {
        studentMap.remove(id);
    }
}

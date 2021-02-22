package com.xuezhang.controller;

import com.xuezhang.entitiy.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 15:23
 */
@RestController
@RequestMapping("consumer")
public class ConsumerHandler {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("findAll")
    public Collection<Student> findAll(){
        return restTemplate.getForEntity("http://192.168.0.198:8101/student/findAll", Collection.class).getBody();
    }

    @GetMapping("/findAll2")
    public Collection<Student> findAll2(){
        return restTemplate.getForObject("http://192.168.0.198:8101/student/findAll", Collection.class);
    }

    @GetMapping("/findById/{id}")
    public Student findById(@PathVariable("id") long id){
        return restTemplate.getForEntity("http://192.168.0.198:8101/student/findById/{id}", Student.class, id).getBody();
    }

    @GetMapping("/findById2/{id}")
    public Student findById2(@PathVariable("id") long id){
        return restTemplate.getForObject("http://192.168.0.198:8101/student/findById/{id}", Student.class, id);
    }

    @PostMapping("/save")
    public void save(@RequestBody Student student){
        restTemplate.postForEntity("http://192.168.0.198:8101/student/save", student, null).getBody();
    }

    @PostMapping("/save2")
    public void save2(@RequestBody Student student){
        restTemplate.postForObject("http://192.168.0.198:8101/student/save", student, null);
    }

    @PutMapping("/update")
    public void update(@RequestBody Student student){
        restTemplate.put("http://192.168.0.198:8101/student/update", student);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") long id){
        restTemplate.delete("http://192.168.0.198:8101/student/delete/{id}", id);
    }
}

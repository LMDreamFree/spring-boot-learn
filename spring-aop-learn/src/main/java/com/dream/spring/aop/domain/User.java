package com.dream.spring.aop.domain;

import java.io.Serializable;

/**
 * @author lim
 * @date 2021/2/10 23:50
 * @className User
 * @desc user 对象
 */
public class User  implements Serializable {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

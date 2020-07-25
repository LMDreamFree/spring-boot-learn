package com.dream.spring.boot.entity;

import org.springframework.beans.factory.BeanNameAware;

import java.io.Serializable;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 14:37
 * @className User
 */
public class User implements Serializable, BeanNameAware {
    private int id;
    private String name;
    private String source;
    private int version;
    private String beanName;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", version=" + version +
                ", beanName='" + beanName + '\'' +
                '}';
    }
}

package com.dream.spring.boot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 11:41
 * @className User
 */
@Data
public class User implements Serializable {
    private int id;
    private String name;
}

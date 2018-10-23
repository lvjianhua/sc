package org.sc.facade.os.model.table;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 用户表
 */
@Data
@Document(collection="Test")
public class Test implements Serializable {
	
    @Id
    public String id;
	
    /**
     * 用户名
     */
    public String name;

    /**
     * 年龄
     */
    public Integer age;

    /**
     * 性别
     */
    public String sex;

    /**
     * 地址
     */
    public String add;

	
}
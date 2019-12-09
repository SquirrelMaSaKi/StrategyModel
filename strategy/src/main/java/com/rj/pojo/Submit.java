package com.rj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 这个对象作用是传递用户的Id和发送的消息内容，过滤器中传递这个对象进行层层过滤
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submit implements Serializable {
    private Integer clientId;
    private String content;
}

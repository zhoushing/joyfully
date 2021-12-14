package com.joyfully.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 文件
 *
 * @author marx
 * @date 2021/08/03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private String id;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件类型
     */
    private String type;
}

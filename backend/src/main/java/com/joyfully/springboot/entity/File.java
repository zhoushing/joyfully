package com.joyfully.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String name;
    private String type;
}

package com.example.demo.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;

    @Length(min = 1, max = 128, message = "姓名长度范围为1-128 bytes")
    private String name;

    @NotNull(message = "年龄不为空")
    @Min(17)
    private long age;

    @NotBlank(message = "头像链接地址不为空")
    @Length(min = 8, max = 512, message = "链接长度范围为8-512 bytes")
    private String avatar;

    @Length(max = 1024, message = "个人信息长度范围为0-1024 bytes")
    private String description;

}

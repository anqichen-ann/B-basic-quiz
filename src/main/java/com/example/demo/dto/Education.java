package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Education {

    private long userId;

    @NotBlank(message = "年份不为空")
    @Past(message = "输入历年年份")
    private long year;

    @NotBlank(message = "标题不为空")
    @Length(min = 1, max = 256, message = "标题长度范围为1-256 bytes")
    private String title;

    @NotBlank(message = "描述不为空")
    @Length(min = 1, max = 4096, message = "经历描述长度范围为1-4096 bytes")
    private String description;
}

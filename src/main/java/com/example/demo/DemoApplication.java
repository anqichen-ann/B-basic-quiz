package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO GTB-综合: * 整体上的完成度很不错了，但距离“真正的合格”还有明显差距。

//TODO GTB-完成度: * 整体的功能完成度很高。
//TODO GTB-完成度: * 在一些细节尤其是某些边界条件下，考虑不够全面。

//TODO GTB-完成度: - 用户不存在时应该返回 404 而不是 500。
//TODO GTB-完成度: - name 字段不存在时，依然成功创建了 user。
//TODO GTB-完成度: - 查询educations 时，如果 userId 不存在，应该返回 404，而不是空数组。
//TODO GTB-完成度: - 可以向 id 不存在的 user 创建 education。

//TODO GTB-测试: * 没有实现任何测试。

//TODO GTB-知识点: * Spring MVC 相关的几个 annotations 还要再掌握熟练一点。
//TODO GTB-知识点: + 创建 user 成功后有返回 user 信息。

//TODO GTB-工程实践: * clean code 的基础要求还需要再提高一些。
//TODO GTB-工程实践: * 养成小步提交的好习惯。
//TODO GTB-工程实践: * 输入参数或者返回对象的结构如果跟领域对象（User / Education）不一致，可以使用 DTO。

//TODO GTB-工程实践: - 不够遵循小步提交。
//TODO GTB-工程实践: - 提交信息大多不够表意。
//TODO GTB-工程实践: - 没有使用单独的 RequestModel/ResponseModel，而是直接使用了 User / Education 作为输入或输出的 model。

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

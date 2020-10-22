### 完成度：
* 整体的功能完成度很高。
* 在一些细节尤其是某些边界条件下，考虑不够全面。

__Details:__

- \- UserService.java:17 id 的生成逻辑最好不要跟当前 users 数量有这么强的耦合
- \- 用户不存在时应该返回 404 而不是 500。
- \- name 字段不存在时，依然成功创建了 user。
- \- 查询educations 时，如果 userId 不存在，应该返回 404，而不是空数组。
- \- 可以向 id 不存在的 user 创建 education。

### 测试：
* 没有实现任何测试。

__Details:__



### 知识点：
* Spring MVC 相关的几个 annotations 还要再掌握熟练一点。

__Details:__
+ \+ 创建 user 成功后有返回 user 信息。
- \- 这种情况下不需要用 ResponseEntity 再包一层。其它处同理。
- \- 可以使用 @ResponseStatus 来简化。

### 工程实践：
* clean code 的基础要求还需要再提高一些。
* 养成小步提交的好习惯。
* 输入参数或者返回对象的结构如果跟领域对象（User / Education）不一致，可以使用 DTO。

__Details:__

- \- UserRepository.java:11 可以是 final 的
- \- 推荐使用构造函数注入
- \- 可以直接 return，不需再定义一个 educationList
- \- 尽量使用 equals()
- \- 不够遵循小步提交。
- \- 提交信息大多不够表意。
- \- 没有使用单独的 RequestModel/ResponseModel，而是直接使用了 User / Education 作为输入或输出的 model。

### 综合：
* 整体上的完成度很不错了，但距离“真正的合格”还有明显差距。

__Details:__




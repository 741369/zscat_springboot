### springboot springclod mybatis 通用mapper redis ，kafka freemaker ，beetl，spring blog cms shop 权限管理


 **zscat-springboot，JavaEE管理系统基础框架-zscat的SpringBoot版本
当前版本:0.0.1
QQ交流群号：483681368** 


为什么使用SpringBoot
项目结构更加合理，各模块的独立性增强，每个模块都可以做为一个独立项目直接运行
更方便部署，每个项目都内嵌Tomcat容器
更好的管理静态资源，将公共的js，css，图片等等静态资源统一放到common项目中，其他项目不用重复COPY这些资源
### 
运行部署


 **创建数据库zsboot，运行doc下的sql文件
1.修改zscat-admin模块下的application.properties 的数据库链接 ，
运行Application类，访问链接 http://localhost:8080/** 
![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/210400_99055134_134431.png "在这里输入图片标题")

![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/210410_07a107b4_134431.png "在这里输入图片标题")


 **2.修改zscat-blog模块下的application.properties 的数据库链接 ，
运行Application类，访问链接 http://localhost:8082/** 
![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/210419_3da8e86e_134431.png "在这里输入图片标题")

![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/210436_f13576e3_134431.png "在这里输入图片标题")

 **3.修改zscat-cms模块下的application.properties 的数据库链接 ，
运行Application类，访问链接 http://localhost:8081/** 
![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/210457_d7b382f4_134431.png "在这里输入图片标题")

![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/210612_4eb330ea_134431.png "在这里输入图片标题")

 **4.使用beetl模板 ，修改zscat-shop模块下的application.properties 的数据库链接 ，
运行Application类，访问链接 http://localhost:8083/** 
![输入图片说明](http://git.oschina.net/uploads/images/2017/0411/152322_28b20599_134431.png "在这里输入图片标题")
![输入图片说明](http://git.oschina.net/uploads/images/2017/0411/152435_97e601b0_134431.png "在这里输入图片标题")
![输入图片说明](http://git.oschina.net/uploads/images/2017/0411/152338_deb92a3b_134431.png "在这里输入图片标题")
![输入图片说明](http://git.oschina.net/uploads/images/2017/0411/152349_aeae369a_134431.png "在这里输入图片标题")
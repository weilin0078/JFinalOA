qq交流群号：136981051

码云地址：http://git.oschina.net/glorylion/JFinalOA

演示地址：http://218.56.98.206:8092/JFinalOA

如何部署：http://218.56.98.206:8092/JFinalOA/front/help/howToRunJFinalOA

默认密码：admin/admin

**1.简单介绍** 

项目主要提供办公系统的开发人员提供一套带有内容管理，权限管理，用户管理，流程管理的完整开发平台，以及提供部分解决方案。

选用的技术框架基本都是简单易上手的，方便自己用来做办公类项目，以及其他类型小项目都会比较方便。

除弹层以外没有使用任何iframe（使用pJax），不必纠结各种跨iframe传参数问题等其他问题，但是需要注意外层页面对内层子页面样式的影响。

**2.数据库问题** 

·MySql请使用5.6或以上版本。

·导入sql文件如果报视图的错误，请修改sql文件最后几个语句，建立视图的sql调换下先后顺序（由于mysql低版本建立视图不能嵌套，只能拆分成多个视图，自动导出的sql，视图先后顺序是按照字母排序的部分版本会导入失败）。或者删除最后面建立视图的sql，单独运行视图部分的sql。

·如果是act开头的表导入失败，请，删除所有act的表建立。ActivitiPlugin.java类，修改52行和53行代码（有注释）。重新运行项目，会自动建立activiti所需要的表。不过初始化的流程和模型信息将没有，部分业务功能不能使用。

**3.运行问题**

·使用eclipse。配置run configurations-maven-使用jetty:run或者tomcat:run或者tomcat7:run命令。点击运行。
·使用idea。注意配置context上下文。默认项目名为空。


**4.展示**

登录
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/022852_aPue_2412577.png "在这里输入图片标题")
首页
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/022945_Sw9P_2412577.png "在这里输入图片标题")
即时通讯
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/023054_NkcF_2412577.png "在这里输入图片标题")
UI
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/023118_jV9g_2412577.png "在这里输入图片标题")
公文DEMO
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/023152_N71P_2412577.png "在这里输入图片标题")
流转历史
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/023216_BZm4_2412577.png "在这里输入图片标题")
流程跟踪
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/023306_HDBM_2412577.png "在这里输入图片标题")
菜单管理
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/023342_CvFs_2412577.png "在这里输入图片标题")
用户管理
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/023405_XZ8U_2412577.png "在这里输入图片标题")
流程管理
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/023428_fw9m_2412577.png "在这里输入图片标题")
流程编辑
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/023453_F2Ft_2412577.png "在这里输入图片标题")
代码生成
![输入图片说明](https://static.oschina.net/uploads/space/2018/0429/023512_u7sQ_2412577.png "在这里输入图片标题")


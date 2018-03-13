该项目基本是业余时间做的，不少朋友加qq问问题，有时候来不急回复。建了一个qq群，欢迎大家进群讨论

qq群号：136981051

最新发布了公文管理，流程使用的demo，通知公告，代码生成。


关于部署

tomcat 8 -- 主要用了个websocket包，可以换成其他tomcat版本但需要引用引用一下这个包。或者把报错的地方注释掉，首页有即时聊天功能，调试通了，并没有做完。

mysql 5.6 -- 我用的这个版本，低版本可能导出的sql文件会报错，需要改一下里面的sql语句。



JFinalOA码云地址：http://git.oschina.net/glorylion/JFinalOA

JFinalOA-LayUI码云地址：https://git.oschina.net/glorylion/JFinalOA-LayUI

JFinalOA-LayUI版本功能并不完善。仅供参考。



现使用版本为统一JFinalOA版本

项目主要提供办公系统的开发人员提供一套带有内容管理，权限管理，用户管理，流程管理的完整开发平台，以及提供部分解决方案。

选用的技术框架基本都是简单易上手的，方便自己用来做办公类项目，以及其他类型小项目都会比较方便。

除弹层以外没有使用任何iframe（使用pJax），不必纠结各种跨iframe传参数问题等其他问题


即时通讯模块请使用Tomcat8.5运行，需要使用WebSocket
MySql请使用5.6或以上版本
导入sql文件如果报视图的错误，请修改sql文件最后几个语句，调换下先后顺序（由于mysql低版本建立视图不能嵌套，只能拆分成多个视图）
MySql低版本，可以导入除去流程表的sql，然后修改ActivitiPlugin插件，
将此行代码注释
```
conf.setDatabaseSchemaUpdate(ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_TRUE);
```
将此行代码注释放开
```
conf.setDatabaseSchemaUpdate(ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_DROP_CREATE);//重置数据库!!!调试用!!!请勿打开!!!
```
，启动应用，activiti流程表将会自动创建。创建完成之后，记得要把ActivitiPlugin插件的代码还原，否则每次启动应用，流程表将会全部重置！！！

插件位置

```
com.pointlion.sys.plugin.activiti.ActivitiPlugin
```
使用框架介绍
1. JFinal 3.0+3.0模版引擎
2. Shiro      非常简单方便的控制权限
3. Activiti   流程引擎
4. BootStrap
5. MySql

主要模块
1. 菜单管理
2. 用户管理
3. 组织结构
4. 角色管理
5. 正在运行流程
6. 流程模型管理
7. 公文管理（流程demo）
8. 通知公告

### 首页
![首页](https://gitee.com/uploads/images/2018/0306/115437_7f09736d_868436.jpeg "094328_G1Md_2412577.jpg")

### 流程编辑
![流程编辑](https://gitee.com/uploads/images/2018/0306/115507_0df44ade_868436.jpeg "221449_uWt7_2412577.jpg")

### 自定义皮肤
![自定义皮肤](https://gitee.com/uploads/images/2018/0306/115621_03db99e1_868436.jpeg "220838_wECV_2412577.jpg")

### 菜单管理
![输入图片说明](https://gitee.com/uploads/images/2018/0306/115709_9f01f949_868436.jpeg "221216_DLhi_2412577.jpg")

### 其他
![聊天](https://gitee.com/uploads/images/2018/0306/115728_468f035b_868436.jpeg "220551_1Us2_2412577.jpg")
## 运行环境
> 1. JDK 1.8
> 2. Tomcat 8
> 3. Maven 3.3.9

## 相关说明
> 类路径`src/main/java`内包含一个`SQLExecuter<T>`接口用于执行具体的SQL操作 ,`DBConfiguration`抽象类用于配置Mybatis的`SessionFactory`和 定义相关的数据库操作,所有的具体`Data Access Object(DAO)`都应该继承 `DBConfiguration类`，并且CRUD的全部方法都依赖于 `<T> T myBatisExecute(SQLExecuter<T> executeSQL)`执行，此处使用了接口回调，在执行具体CRUD方法时会回调该方法。

> PersonDao是Person类的一些相关CRUD方法，包含批量操作

> 在资源类路径`src/main/resource`下有`ExampleMapper.xml,mybatis-configuration.xml`两个配置文件，`mybatis-configuration.xml`用于配置数据源 和指定相应的别名和一些全局操作还有加入引入映射文件`（*Mapper.xml）`;`ExampleMapper.xml`是具体的映射配置文件，一个映射配置Mapper 可以配置对一个实体类的CRUD操作，通过

> 在`src/test/java`测试包下包含了一个`PersonDaoTest.java测试类`，该类可以测试我们的PersonDao的所有功能， `右键--Run AS --JUnit Test`即可运行。



## 资源文件

> 目前在项目下的`Doc，sql`文件夹下存有`mybatis3`配置和映射的`dtd`，和`mybatis源码`，源码可以添加到项目，这样在开发过程中可以看源码具体实现，`sql文件夹`下有本示例所用数据表的`DDL`以及相应数据，直接在`Navicat`下导入便可，*请修改相应的配置文件，一对应自己的数据库*。
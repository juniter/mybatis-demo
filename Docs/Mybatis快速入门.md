## 准备

>在线入门文档，轻松入门简介请查阅 http://www.mybatis.org/mybatis-3/getting-started.html 从 https://github.com/mybatis/mybatis-3 下载Mybatis3的源码.zip格式，作为源码包引入项目，从而可以在开发过程中查看项目源码。

## 基本使用

> Mybatis作为一个轻量级ORM框架负责与数据库进行交互，Mybatis的优势在于扩展优势和动态SQL特性。
- 配置
> mybatis 的配置文件主要分为两个部分，一个部分是配置数据源`Configuration`，一部分是配置类映射和SQL操作，配置文件配置方式分为两种，一种基于注解和接口方式，一种记录XML的配置方式，我们项目采用的是MXL的方式来配置，下面简单介绍一下这两种配置文件的配置,每个基于 MyBatis的应用都是以一个SqlSessionFactory的实例为中心的。SqlSessionFactory 的实例可以通过SqlSessionFactoryBuilder获得。而SqlSessionFactoryBuilder则可以从 XML 配置文件或一个预先定制的 Configuration 的实例构建出 SqlSessionFactory 的实例。关于每个配置文件所能配置的标签以及其具体含义，请参照我们给出的mybatis demo下的doc下的相关dtd的配置，和最开始我们给出的两个官方文档支持。

- Configuration
``` xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC"/>
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
      </dataSource>
    </environment>
  </environments>
  <mappers>
    <mapper resource="org/mybatis/example/BlogMapper.xml"/>
  </mappers>
</configuration>
```
- 配置好Configuration以后我们便可以通过Java代码从xml中获取sessionFactory实例，从而使用SqlSession对数据库进行操作

``` java
String resource = "org/mybatis/example/mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
```
> 得到sessionFactory后只需要调用openSession（trur)方法，便可得到SqlSession实例，mybatis的所有操作都是基于该SqlSession

- Mapper.xml映射文件，用于配置数据库映射和操作，以及一些其他相关属性。一下为基本用法
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="agm_example_Person">
	<!-- 查询所有条目 -->
	<select id="list_person" resultType="com.agm.model.Person">
 		SELECT * FROM person
	</select>
	
	<!-- 按条件查询相应条目  -->
	<select id="list_person_by_condition" parameterType="com.agm.model.Person" resultType="com.agm.model.Person">
 		SELECT * FROM person WHERE address=#{address} and company=#{company}
	</select>
	
	<insert id="insert_batch" parameterType="com.agm.model.Person" useGeneratedKeys="true" keyProperty="id">
		INSERT INTO person 
			(name,password,address,phone,company)
		VALUES
		
		<foreach item="person" collection="list" separator=",">
			(#{person.name},#{person.password},#{person.address},#{person.phone},#{person.company})
		</foreach>
	</insert>
	
	<update id="update_person" parameterType="com.agm.model.Person">
		UPDATE person SET
			name=#{name},
			password=#{password},
			phone=#{phone}
		WHERE
			id=#{id}
	</update>
	
	<delete id="delete_specified_person" parameterType="com.agm.model.Person">
		DELETE FROM
		    person 
		WHERE
			id in
		<foreach item="person" collection="list" separator="," open="(" close=")">
			 #{person.id}
		</foreach>
	</delete>
</mapper>

```

> 配置好相应的Mapper之后我们便可以通过程序来调用该mapper从而操作数据库：
- 以SqlSession的selectList（namespace.sqlid,parameters）说明
``` java
session.selectList("agm_example_Person.list_person");
```
> `agm_example_Person`为mapper.xml文件的`namespace`,而`list_person`对应我们需要执行操作的id。`list_person`代表一下代码段

``` xml
<!-- 查询所有条目 -->
<select id="list_person" resultType="com.agm.model.Person">
 	SELECT * FROM person
</select>
```


> 具体如何执行以及执行后的效果，请运行我们给出的demo查看。
**具体可配置项，请参照** http://www.mybatis.org/mybatis-3/zh/configuration.html 和 http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html

## 关于动态sql
> 在mybatis中我们使用最多的特性便是其动态sql特性所以我们着重介绍。mybatis动态SQL有以下几个重要标签
- if
- choose (when ,otherwise)
- trim (where set)
- foreach

1. if 标签
> 此标签可以用于做动态筛选，比如我们要实现过滤功能，或者查找功能时，类似于Hibernate的`detachedCriteria.add()`方法如：
``` xml
<select id="ifdemo_" resultType="JavaBean">
  SELECT * FROM table 
  WHERE state = ‘1’ 
  <if test=" name!= null">
    AND name like #{name}
  </if>
</select>
```
> 配置好以后当我我们在调用这个sql时，会自动判断传入的参数中是否包含name属性，如果包含且不为空时便会添加`name like #{name_value}`这段代码到where代码段中实现过滤功能
当然支持一个也就支持多个条件选择，只需要条件相应的代码便可，如：

``` xml
<select id="ifdemo_" resultType="JavaBean">
  SELECT * FROM table 
  WHERE state = ‘1’ 
  <if test=" name!= null">
    AND name like #{name}
  </if>
  <if test=" address!= null">
    AND address like #{address}
  </if>
</select>
```

2. choose标签（when otherwise）

> 此标签相当于Java的Switch

``` xml
<select id="choosetest" resultType="JavaBean">
  SELECT * FROM table WHERE state = ‘1’
  <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
  </choose>
</select>
```

3. trim, where, set标签

- 前面几个例子已经合宜地解决了一个臭名昭著的动态 SQL 问题。现在考虑回到“if”示例，这次我们将“ACTIVE = 1”也设置成动态的条件，看看会发生什么。

``` xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  WHERE 
  <if test="state != null">
    state = #{state}
  </if> 
  <if test="title != null">
    AND title like #{title}
  </if>
  <if test="author != null and author.name != null">
    AND author_name like #{author.name}
  </if>
</select>
```
> 如果这些条件没有一个能匹配上将会怎样？最终这条 SQL 会变成这样

``` xml
SELECT * FROM BLOG
WHERE
```

> 这会导致查询失败。如果仅仅第二个条件匹配又会怎样？这条 SQL 最终会是这样:

``` xml
SELECT * FROM BLOG
WHERE 
AND title like ‘someTitle’
```
> 这个查询也会失败。这个问题不能简单的用条件句式来解决，如果你也曾经被迫这样写过，那么你很可能从此以后都不想再这样去写了。MyBatis 有一个简单的处理，这在90%的情况下都会有用。而在不能使用的地方，你可以自定义处理方式来令其正常工作。一处简单的修改就能得到想要的效果：

``` xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  <where> 
    <if test="state != null">
         state = #{state}
    </if> 
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>
```

> where 元素知道只有在一个以上的if条件有值的情况下才去插入“WHERE”子句。而且，若最后的内容是“AND”或“OR”开头的，where 元素也知道如何将他们去除。
如果 where 元素没有按正常套路出牌，我们还是可以通过自定义 trim 元素来定制我们想要的功能。比如，和 where 元素等价的自定义 trim 元素为：

``` xml
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ... 
</trim>
```

> prefixOverrides 属性会忽略通过管道分隔的文本序列（注意此例中的空格也是必要的）。它带来的结果就是所有在 prefixOverrides 属性中指定的内容将被移除，并且插入 prefix 属性中指定的内容。类似的用于动态更新语句的解决方案叫做 set。set 元素可以被用于动态包含需要更新的列，而舍去其他的。比如：

``` xml
<update id="updateAuthorIfNecessary">
  update Author
    <set>
      <if test="username != null">username=#{username},</if>
      <if test="password != null">password=#{password},</if>
      <if test="email != null">email=#{email},</if>
      <if test="bio != null">bio=#{bio}</if>
    </set>
  where id=#{id}
</update>
```

> 这里，set 元素会动态前置 SET 关键字，同时也会消除无关的逗号，因为用了条件语句之后很可能就会在生成的赋值语句的后面留下这些逗号。若你对等价的自定义trim元素的样子感兴趣，那这就应该是这个样子

``` xml
//注意这里我们忽略的是后缀中的值，而又一次附加了前缀中的值。
<trim prefix="SET" suffixOverrides=",">
  ...
</trim>
```

- foreach 该元素在我们的Demo程序中也有使用，可参照demo
 
- bind
> bind 元素可以从 OGNL 表达式中创建一个变量并将其绑定到上下文。比如：

``` xml
<select id="selectBlogsLike" resultType="Blog">
  <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
  SELECT * FROM BLOG
  WHERE title LIKE #{pattern}
</select>
```


> 该sql会根据传入的参数来进行查询如果title<>null 便会按照title查询，然后会继续往下匹配，如果都不满足最后会添加`AND featured = 1`

## 其他高级特性 
> 除了上面介绍的一些特性外，Mybatis还提供了一些高级功能，如对接口映射的支持，事务管理等特性，这些暂时在项目用不到我们可以自己学习应用。

## 关于SQL注入
> 经过测试，Mybatis可以过滤SQL注入攻击。可以在属性值后添加 `or 1=1`来进行测试。
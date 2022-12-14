# Hotpot

---
人生如逆旅，我亦是行人

## Introduction

---
这是一个从零搭建的SpringBoot与其他各种框架、组件整合的Demo（如：spring web、spring test、lombok、jackson、redis、jedis、mysql、mybatis、jjwt、spring security、springDoc等），可以很方便的用于前后端分离的后端部分，嘎嘎好用，无脑写接口就完事了ψ(｀∇´)ψ。

前前后后花了一周多，哈哈，我是真的又菜又懒。(╯°□°）╯︵ ┻━┻，废话说的有点多。简而言之，希望这能够帮到刚入门的小白朋友们，大家一起快乐撸码。

有用的话，（〃｀ 3′〃）感谢朋友们右上角小手一抖点个Star。

## Getting Started

---
1. 下载并启动redis。[Windows-Redis下载地址](https://github.com/MicrosoftArchive/redis/releases) [其他版本-Redis下载地址](https://redis.io/download/#redis-downloads)
2. 下载并启动Mysql，同时创建名为 `hotpot` 的数据库，根据 `src/main/resources/static/hotpot.sql` 可恢复数据库表及数据。[Mysql下载地址](https://dev.mysql.com/downloads/mysql/)
3. 拉取项目代码 `git clone https://github.com/TangHaoTAT/Hotpot` 。
4. 使用 `IDEA` 导入Hotpot项目，修改 `application-dev.yml` 配置文件中的 `数据源` 和 `Redis配置` ，使其连接本地的数据库和Redis。
5. 启动项目并访问 `http://localhost:10086/swagger-ui.html` ，其中系统初始账号密码为 `{"username": "admin","password": "123456"}` 。
![springdoc-openapi-ui](src/main/resources/static/springdoc-openapi-ui.jpg)
![springdoc-openapi-ui-login](src/main/resources/static/springdoc-openapi-ui-login.jpg)

## application.yml

---
```yaml
spring:
  profiles:
    active: dev
```

## application-dev.yml

---
```yaml
server:
  port: 10086 # 端口号
spring:
  datasource: # 数据源
    driver-class-name: com.mysql.cj.jdbc.Driver # 数据库驱动
    url: jdbc:mysql://localhost:3306/hotpot # 数据库地址
    username: root # 数据库账号
    password: 123456 # 数据库密码
  redis: # redis配置
    database: 0
    host: localhost # redis地址
    port: 6379 # redis端口号,默认6379
    password: # redis密码，默认为空
    timeout: 5000
    connect-timeout: 5000
    jedis:
      pool:
        max-active: 200 # 连接池最大连接数（使用负值表示没有限制）
        max-wait: -1 # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-idle: 10 # 连接池中的最大空闲连接
        min-idle: 0 # 连接池中的最小空闲连接
springdoc: # springdoc配置
  api-docs:
    enabled: true # 是否开启API文档
    groups:
      enabled: true # 是否开启分组
    path: /v3/api-docs #API元数据访问路径,默认为/v3/api-docs
  swagger-ui:
    path: /swagger-ui.html # swagger-ui文档访问路径,默认为/swagger-ui.html
    enabled: true # 是否开启swagger-ui
  group-configs: # 分组配置
    - group: default
      packagesToScan: org.tanghao.hotpot.controller
```

# pom.xml

---
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.2</version>
        <relativePath/>
    </parent>
    <groupId>org.tanghao</groupId>
    <artifactId>Hotpot</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>Hotpot</name>
    <description>Hotpot</description>
    <properties>
        <java.version>17</java.version>
        <lombok.version>1.18.24</lombok.version>
        <jackson.version>2.13.3</jackson.version>
        <jedis.version>3.8.0</jedis.version>
        <mysql.connector.version>8.0.29</mysql.connector.version>
        <mybatis.plus.version>3.5.2</mybatis.plus.version>
        <jjwt.version>0.11.5</jjwt.version>
        <springdoc.version>1.6.11</springdoc.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!--lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
        </dependency>

        <!--jackson-->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>${jackson.version}</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>${jackson.version}</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson.version}</version>
        </dependency>

        <!--redis-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>${jedis.version}</version>
        </dependency>

        <!--mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.connector.version}</version>
        </dependency>

        <!--mybatis plus-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>${mybatis.plus.version}</version>
        </dependency>

        <!--jjwt-->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>${jjwt.version}</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>${jjwt.version}</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>${jjwt.version}</version>
            <scope>runtime</scope>
        </dependency>

        <!--spring security-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!--springdoc-->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>${springdoc.version}</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## Libraries

---
- jdk 17
- spring boot web 2.7.2
- spring boot test 2.7.2
- lombok 1.18.24
- jackson 2.13.3
- spring boot data redis 3.8.0
- jedis 3.8.0
- mysql 8.0.29
- mybatis plus 3.5.2
- jjwt 0.11.5
- spring security 2.7.2
- springdoc 1.6.11
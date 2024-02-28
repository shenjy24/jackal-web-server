## Docker部署
#### 一.环境准备
1. 在玩家目录（例如`/home/jonas`）创建项目目录`web`，该目录称为工作目录。
2. 将`src/main/deploy/Dockerfile`文件和打包得到的`web.jar`上传到工作目录。

#### 二.启动服务
1. 在工作目录执行如下指令，创建镜像。
```
docker build -t web .
```
- 通过`spring.config.location`配置使得可以在外部修改配置文件，但必须重启才能生效。

2. 执行如下命令启动服务。
```
# 创建用户定义网络
docker network create web-network

# 启动你的应用程序容器并将其连接到网络
docker run -d -p 8088:8088 -e TZ=Asia/Shanghai --name web --network web-network --restart always -v /home/jonas/web/logs:/logs -v /home/jonas/web/conf/application.yml:/conf/application.yml web
```
(1) 挂载日志

通过挂载日志目录，使得可以在工作目录下的`logs`目录查看到服务日志。 

同时记得修改 logback-spring.xml 中的日志路径配置，服务器上此处需要配置为 `/logs`。

(2) 访问 MySQL 容器

访问 MySQL 容器时可使用 `jdbc:mysql://mysql:3306/web?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Hongkong&allowPublicKeyRetrieval=true`




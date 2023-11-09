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
docker run -d -p 18088:8088 -e TZ=Asia/Shanghai --name web --restart always -v /home/jonas/web/logs:/logs -v /home/jonas/web/conf/application.yml:/conf/application.yml --link mysql web
```
- 通过挂载日志目录，使得可以在工作目录下的`logs`目录查看到服务日志。
- 链接 mysql 容器，使用数据库连接时可使用 `jdbc:mysql://mysql:3306/web?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Hongkong&allowPublicKeyRetrieval=true`




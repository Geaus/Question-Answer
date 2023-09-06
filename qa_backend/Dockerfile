# 使用基础的Java镜像
FROM openjdk:8-jdk-alpine

# 将本地的项目文件复制到容器中
COPY ./qa_backend/target/qa_backend-0.0.1-SNAPSHOT.jar /app/

# 设置工作目录为/app
WORKDIR /app
# 设置为阿里云源
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
ENV LANG zh_CN.UTF-8
RUN apk add --update  ttf-dejavu fontconfig
COPY ./qa_backend//src/main/resources/STSONG-light.ttf /usr/share/fonts/ttf-dejavu/msyh.ttf
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

# 暴露应用程序的端口
EXPOSE 8080

# 启动应用程序
CMD ["java", "-jar", "qa_backend-0.0.1-SNAPSHOT.jar"]

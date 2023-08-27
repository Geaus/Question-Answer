# 使用基础的Java镜像
FROM openjdk:8-jdk-alpine

# 将本地的项目文件复制到容器中
COPY ./target/qa_backend-0.0.1-SNAPSHOT.jar /app/

# 设置工作目录为/app
WORKDIR /app


# 暴露应用程序的端口
EXPOSE 8080

# 启动应用程序
CMD ["java", "-jar", "qa_backend-0.0.1-SNAPSHOT.jar"]

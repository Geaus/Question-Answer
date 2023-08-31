FROM node:18-alpine

# 在容器中创建目录

# 指定工作空间，后面的指令都会在当前目录下执行
WORKDIR  /app

# 拷贝 package.json
COPY qa_frontend/package.json ./

# 安装依赖
RUN npm i --production --registry=https://registry.npm.taobao.org

# 拷贝其他所有文件到容器（除了 .dockerignore 中的目录和文件）
COPY qa_frontend/ ./

# build
RUN npm run build

# 暴露端口 9000
EXPOSE 3000

FROM nginx:alpine
COPY --from=builder /app/build /usr/share/nginx/html
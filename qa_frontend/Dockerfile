FROM node:18-alpine as builder

WORKDIR /app

COPY qa_frontend/package.json ./
RUN npm install

COPY qa_frontend/ ./
RUN npm run build


FROM nginx:alpine
COPY --from=builder /app/build /usr/share/nginx/html


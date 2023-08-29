FROM node:18-alpine as builder

WORKDIR /app

COPY qa_frontend/build ./


FROM nginx:alpine
COPY --from=builder /app/build /usr/share/nginx/html


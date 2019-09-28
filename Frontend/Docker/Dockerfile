#Node/angular build
FROM node:latest as builder
COPY Angular .
WORKDIR bidding-system
RUN npm install -g @angular/cli
RUN ng build

#nginx build
FROM nginx:latest
COPY Nginx/nginx.conf /etc/nginx/nginx.conf
RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /bidding-system/dist/bidding-system /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

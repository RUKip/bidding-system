#Node/angular build
FROM node:latest as builder
COPY Angular .
WORKDIR bidding-frontend
RUN npm install -g @angular/cli
RUN ng build

#nginx build
FROM nginx:latest
COPY Nginx/nginx.conf /etc/nginx/nginx.conf
RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /bidding-frontend/dist/bidding-frontend /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

#Node/angular build
FROM node:latest
COPY Angular .
WORKDIR bidding-frontend
RUN npm install -g @angular/cli
RUN npm run build

#nginx build
FROM nginx:latest
COPY Nginx/nginx.conf /etc/nginx/nginx.conf
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
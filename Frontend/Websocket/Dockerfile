#Node
FROM node:latest
COPY app.js .
RUN mkdir socket-server
RUN cd socket-server
RUN mkdir src
RUN npm init -y
RUN npm i express socket.io @types/socket.io --save
EXPOSE 4444
CMD ["node", "app.js"]

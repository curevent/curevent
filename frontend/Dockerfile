FROM node:latest

WORKDIR /tmp
ADD package.json /tmp/package.json
RUN npm install
RUN mkdir -p /usr/src/app
RUN cp -a /tmp/node_modules /usr/src/app

WORKDIR /usr/src/app

ADD . /usr/src/app

EXPOSE 8080

CMD [ "npm", "start"]

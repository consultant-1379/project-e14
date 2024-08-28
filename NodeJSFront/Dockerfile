FROM docker.io/node:slim
# COPY ./.* /
# ENTRYPOINT ["node",".\server.js"]

RUN mkdir /server
ADD ./* /server
WORKDIR /server
RUN apt-get update && apt-get install -y git procps 

# && \ npm install --no-cache-dir -r /server/requirements.txt
RUN npm install express
RUN npm install multer

EXPOSE 8080

ENTRYPOINT ["node","./server.js"]
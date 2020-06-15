# Build stage
FROM node:14 as build-stage
COPY ./ /usr/src/
WORKDIR /usr/src
RUN npm install
RUN npm run ng build --prod

# Compiled app based on nginx
FROM nginx:1.19
COPY --from=build-stage /usr/src/dist/angular-reactive/ /usr/share/nginx/html
COPY /nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 1827

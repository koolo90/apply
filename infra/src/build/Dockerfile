# Build stage
FROM node:alpine AS build

WORKDIR /usr/src/app
COPY ./apply-web /usr/src/app
RUN ls -la /usr/src/app

RUN npm install -g @angular/cli
RUN npm install
RUN npm run build

# Production stage
FROM nginx:alpine

# Copy the build output to replace the default nginx contents
COPY --from=build /usr/src/app/dist/apply-web /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]

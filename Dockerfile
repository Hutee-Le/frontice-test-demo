# Sử dụng một image cơ bản có Java đã cài đặt (ở đây là OpenJDK 17)
FROM openjdk:17-jdk-alpine

# Đặt thư mục làm việc
WORKDIR /app

# Copy file JAR của ứng dụng vào thư mục container
COPY target/*.jar app.jar

# Expose cổng mà Spring Boot sử dụng (mặc định là 8080)
EXPOSE 8080

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

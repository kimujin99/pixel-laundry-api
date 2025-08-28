# 1. JDK 기반 이미지
FROM eclipse-temurin:21-jdk-alpine

# 2. 작업 디렉토리 생성
WORKDIR /app

# 3. 빌드된 jar 복사
COPY build/libs/pixel-laundry-api-0.0.1-SNAPSHOT.jar app.jar

# 4. 포트 설정 (Spring Boot 기본 포트)
EXPOSE 8080

# 5. 앱 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
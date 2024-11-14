# OpenJDK 17 기반 이미지 사용
FROM openjdk:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/resumeSystem-0.0.1-SNAPSHOT.jar app.jar

# 애플리케이션 포트 노출
EXPOSE 8080

# 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]

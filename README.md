# 퀵 스타트
```application.yml
...

jwt:
  access-token-expiration: 1800000 #30분
  secret: {시크릿 키}
```

`openssl rand -hex 64` 명령어를 사용해서 시크릿 키를 추가 해야합니다.

```
./gradlew build

cd build/libs

nohup java -jar querypie-0.0.1-SNAPSHOT.jar &
```

# API 문서
### local
http://localhost:8080/swagger-ui/index.html
### ec2
http://52.79.240.37:8080/swagger-ui/index.html

# 요구사항 (구현 완료)

1. 도서 관리
    - [X] 도서 등록
    - [X] 도서 조회
    - [X] 도서 수정
    - [X] 도서 삭제
2. 사용자 관리
   - [X] 사용자 등록
   - [X] 사용자 조회
3. 대출 및 반납 관리
   - [X] 대출 등록
   - [X] 대출 상태 확인 (도서 조회시 포함)
   - [X] 도서 반납
4. 추가 요구사항
   - [X] 데이터 검증 및 에러 처리
   - [X] 도서 제목 또는 저자 이름으로 도서를 검색하는 기능 구현
   - [X] 페이징 및 제목 또는 출판일을 기준으로 정렬 기능 구현
5. 기술 요구사항
   - [X] Java + Spring Boot
   - [X] Swagger API 문서 추가
   - [X] JPA(ORM) 사용
   - [X] H2 Database 사용 (과제 제출을 MySQL 기반으로 진행하기 어려움이 있어, H2 Database를 사용하였습니다.)
   - [X] JWT 기반 인증/인가 (최소 기능으로 대출 및 반납시 JWT 기반 유저 인증 기능을 추가했습니다.)

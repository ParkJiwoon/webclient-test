# webclient-test

`server-webmvc` 는 외부 API 서버로 사용

실제 call 은 `server-webflux` 에서 진행

- 테스트 환경
  - Kotlin / WebFlux 기반의 Spring Boot
  - 쓰레드는 단 하나만 사용
- 테스트 내용
  - WebClient 로 응답이 오래 걸리는 외부 API 요청 시 쓰레드가 블락되는가?
  - 쓰레드가 처리해야 하는 무거운 연산 요청이 동시에 들어오면 비동기/논블로킹으로 처리 가능한가?
- 주의사항
  - API 테스트를 할 때 동일 브라우저에서 여러 탭을 열어서 같은 요청을 보내면 순서대로 처리하기 때문에 시크릿 브라우저를 열어서 테스트 필요

<br>

# 정리글

- [Spring WebFlux Thread Test 해보기](https://bcp0109.tistory.com/361)
- [if(kakao) 2021 - Webflux 로 막힘없는 프로젝트 만들기](https://github.com/ParkJiwoon/webclient-test/tree/main/server-webflux/src/main/kotlin/com/example/serverwebflux/if_kakao_demo)

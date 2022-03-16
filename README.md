# webclient-test

- 테스트 환경
  - Kotlin / WebFlux 기반의 Spring Boot
  - 쓰레드는 단 하나만 사용
- 테스트 내용
  - WebClient 로 응답이 오래 걸리는 외부 API 요청 시 쓰레드가 블락되는가?
  - 쓰레드가 처리해야 하는 무거운 연산 요청이 동시에 들어오면 비동기/논블로킹으로 처리 가능한가?

`server-webmvc` 는 외부 API 서버로 사용

실제 call 은 `server-webflux` 에서 진행

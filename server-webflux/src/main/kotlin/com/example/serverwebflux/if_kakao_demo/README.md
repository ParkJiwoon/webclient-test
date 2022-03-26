# Webflux Thread Thest Demo

[if(kakao) 2021 - Webflux 로 막힘없는 프로젝트 만들기](https://if.kakao.com/session/107) 를 보고 따라해본 프로젝트입니다.

처음에는 제가 진행했던 [Spring WebFlux Thread Test 해보기](https://github.com/ParkJiwoon/PrivateStudy/blob/master/spring/webflux-thread-test.md) 와 결과가 달라서 의아하게 생각했습니다.

하지만 제가 한 테스트는 **외부의 Blocking Call 을 호출할 때 현재 쓰레드가 블락되는가?** 에 대한 테스트였고 여기서 한 테스트는 **WebFlux 의 쓰레드가 블락되면 어떻게 되는가?** 이기 때문에 목적이 살짝 다릅니다.

발표 영상에서 본 것처럼 테스트 코드 실행 시 여러 쓰레드에서 처리되지 않고 한 쓰레드에서만 처리돼서 제대로된 결과물을 못봤지만, 테스트 목적은 달성했습니다.

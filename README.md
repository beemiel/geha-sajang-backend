# 게하사장

<p align="center"><a href="http://13.124.108.177"><img src="https://user-images.githubusercontent.com/58355499/98968042-88f06780-2550-11eb-9d3e-b488f196ac91.png"/></a></p>
<p align="center"> 위 이미지를 클릭하면 페이지로 이동합니다.</p>
<p align="center"><strong>- 게스트하우스를 운영하는 호스트가 이용하는 게스트하우스 맞춤형 예약관리 서비스 '게하사장'입니다. -</strong></p>
<br>
<p align="center"><img src="https://user-images.githubusercontent.com/58355499/98966896-2e0a4080-254f-11eb-91a6-bd4b8704bf13.gif"/></p>
<br>

## 배경
  여러 숙박시설을 대상으로 하는 서비스와 달리 게스트 하우스에 특화된 서비스를 제공하고자 기획

## 프로젝트 진행
  - 개발 기간 : 2020.08.03 ~ 진행중  <br>
  - 개발 인원 : FE(1명) BE(2명) iOS(1명) <br>

## 구현된 기능
 - 회원가입
 - 인증 코드 메일 전송/재전송
 - 로그인/로그아웃
 - 게스트하우스 기본 정보 등록/조회
 - 게스트하우스 방정보 등록/조회
 - 예약 등록/조회
 - 기존 게스트 조회

## 기술 스택
- java 8 
- spring boot 2.3.1
- spring data jpa
- spring security
- spring rest docs
- junit5
- mysql
- aws ec2, rds, s3
- nginx

## 설계
- DB 설계 <br>
  기획 단계에서 협의된 내용을 기반으로 DB 설계 <br>
   <img src="https://user-images.githubusercontent.com/58145890/99044252-d2819680-25d2-11eb-85be-38f77c9ed413.png" /> <br>
   - *호스트와 하우스는 N:M* 관계를 맺음으로 다중 하우스와 다중 호스트 관계를 만듦. **dtype 컬럼을 이용하여 호스트와 스태프를 구분**할 수 있도록 설계
   - 가장 중심이 되는 House 도메인은 Room, Booking, Checklist 등 다수의 도메인과의 관계를 맺음
   - 하우스마다 특색있는 서비스를 제공하므로 하우스에서 제공하는 서비스를 다른 도메인으로 분리. 추후에 템플릿처럼 사용
   - 게스트 하우스 특성상 도미토리라는 개념때문에 일반 숙박 시설과 다르게 Bed 도메인을 따로 둠으로서 **특정 예약과 특정 Bed를 연결할 수 있도록 설계** (Booking과 Bed N:M)
   - 추후 서비스 확장성을 고려하여 Booking 과 Guest 분리
   - Room과 Bed는 템플릿처럼 사용
   - **재고 관리는 UnbookedRoom과 BookedRoom에서 관리**하도록 설계. 매일 매일 예약이 되어야 하는 숙박시설이므로 등록된 Room과 Bed를 기준으로 재고를 생성하여 사용할 예정 *(쇼핑몰의 재고 시스템에서 따옴)*

## 고민한 점
 ### 1. 객체 지향과 구조 <br>
   객체 지향 언어를 사용하면서 객체 지향은 숱하게 들어왔지만 그 단어를 어렴풋이 이해하는 정도에 지나지 않았다. 그러나 게하사장 프로젝트를 진행하면서 기획이 점점 늘어나고 팀원과 협업해야 할 일이 늘어나면서 더 깊이있는 객체 지향의 이해와 적용이 필요하다고 느꼈다. 때문에 *잘못된 개념과 부족한 개념을 제대로 정립하고자 공부하고 있다.* 그리고 객체 지향 책과 서로 다른 관점을 가진 **팀원과의 토론**을 통해 개선하는 중이다.
    
 ### 2. Spring Security와 JWT <br>
   암호화와 추후 추가될 Oauth를 위해 Security를 처음으로 적용해보았다. 첫시도이므로 완벽함보다는 적용에 의의를 두면서 사용에 익숙해지려고 한다. 구조 및 실행에 관해서는 공부해야 할 부분이 많이 남아있다. 또한 토큰 인증을 통해 *DB I/O*를 줄이고자 했다.
 
 ### 3. Spring data JPA<br>
   이전까지는 사용하는 것 자체에 집중을 했던 반면 이번 프로젝트를 하면서 영속성 컨텍스트, 프록시, 지연 로딩/즉시 로딩 등과 같은 중요한 *개념들을 공부*하고 이해하고자 한다. 또한 *N+1* 문제를 개선하여 DB I/O를 줄이도록 했다.
 
 ### 4. 테스트 <br>
   기존에는 주로 포스트맨과 같은 툴을 이용해서 테스트를 했지만 이번에는 테스트 코드를 적극적으로 작성하려고 노력하고 있다. 하지만 테스트를 위한 테스트가 되는 경향이 있어서 이 부분을 의식하고*'서비스 레이어는 무엇을 어떻게 테스트 해야 좋을까?'처럼 특정 상황 혹은 레이어에서 **어떤 테스트를 어떻게 해야 할 지**에 대해서도 생각하려고 한다.
     
 ### 5. 문서화 및 커뮤니케이션 <br>
   서비스 기획부터 개발까지 직접 참여해보니 커뮤니케이션과 문서화는 떼려야 뗄 수 없이 유기적으로 연결된 것이라고 느꼈다. 그래서 Api 문서를 위한 툴 선택부터 문서의 가독성까지 신경쓰고자 노력했다. 특히 기획단계에서 협의한 내용들이 사소한 것까지도 모두 **체계적으로 문서화가 되어 있어야 각자의 방향성이 어긋나는 일이 적고 더 효율적인 회의가 가능하다는 것을 몸소 느꼈다.** 때문에 문서화와 커뮤니케이션을 위한 툴을 여러가지로 사용해보고 시행착오도 겪으면서 개발만큼 많은 시간을 할애하였다. 또한 팀 작업인만큼 *팀 전체적인 분위기와 팀원의 컨디션 등을 고려하여 서로 격려*하며 지내고자 했다. 
 
 ### 6. 코드 리뷰 <br>
   **서로 다른 관점을 공유하고 서로 배워가기 위한 목적**으로 코드 리뷰를 진행하였다. 

## 협업 방식
### 팀
- Slack
  - 가장 기본적인 소통을 위해 사용

- Notion
  - 전체적인 서비스 기획 및 내용 상세 정리
  - 서비스 기능, 회의와 같이 팀이 함께 협의한 내용 작성 및 정리 <br>
  <img src="https://user-images.githubusercontent.com/58145890/99038524-f9d36600-25c8-11eb-9ccc-ca58e257d65e.png" width="230px"/>
  
- 스프레드 시트
  - [백로그](https://docs.google.com/spreadsheets/d/1e4tcSdlB5U3dLUNJOyHfZPz_Nn3OvCKaT97NlnMxzH0/edit?pli=1#gid=2058238240) <br>
      전체적인 팀 스케줄 관리를 위한 백로그
  - [회고](https://docs.google.com/spreadsheets/d/1B945f5OpvAnVp_VMJJGiSC4eJasXAm7N3PMtQKXVjYs/edit#gid=1362844403) <br>
      데일리 회고

- [Api 문서](http://13.124.108.177:8080/docs/geha-api-docs.html) <br>
  - 예시 <br>
    <img src="https://user-images.githubusercontent.com/58145890/99040250-250b8480-25cc-11eb-9b64-e8c07ad26a1c.png" width="80%"/>

- 오프라인/온라인 회의
   - 기획이나 전체적인 진행 사항등에 대한 정기적 회의
   - 필요한 경우엔 비정기적으로 추가 회의 진행
   - 상황에 맞게 온/오프라인을 병행

### 백엔드
- bitbucket 
  <br>
  github 대신 사용
- Jira
  <br>
  백엔드 관련 이슈들을 Jira로 관리 <br>
  <img src="https://user-images.githubusercontent.com/58145890/99039814-5e8fc000-25cb-11eb-866a-18b2d87d2fad.png" width="230px"/>
- 코드 리뷰
  <br>
  더 나은 구조와 로직을 위한 코드 리뷰 <br>
  <img src="https://user-images.githubusercontent.com/58145890/99040074-d2ca6380-25cb-11eb-9a21-b26dd544f1fc.png" width="400px"/>
  <img src="https://user-images.githubusercontent.com/58145890/99040078-d4942700-25cb-11eb-9dad-6d12be090a8c.png" width="400px"/>


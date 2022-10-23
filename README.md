
# fitMe


## 프로젝트 소개
여러가지 카테고리의 옷 상품을 구매할 수 있는 쇼핑몰 프로젝트이다. 
<br>

## ERD

https://www.erdcloud.com/d/y3sFavJkybtpSCNdi
<br>

  

## 기술스택

### 목표

1. Jwt 토큰을 이용한 로그인 처리
2. Redis를 이용한 중복 거래 방지 처리
3. 많은 트래픽을 대비할 캐시 사용

### 스택
- SpringBoot
- Gradle
- Validation
- Java Mail Sender
- MySQL
- Redis
- Lombok
- JPA
- H2
- Cache

<br>

# 프로젝트 기능

##  고객


> 회원 가입과 로그인 []

- 고객은 이메일 인증을 통하여 회원 가입할 수 있다.
  - 회원가입한 후 , UUID로 된 이메일 key 정보가 메일로 전송되고 링크를 클릭하면 이메일 인증이 완료된다. 이메일 키 유효기간은 30분으로 정한다.
  - 회원 상태는 'S' -> 이메일 인증이 완료된 상태/ 'I' -> 이메일 인증이 아직 완료되지 않은 상태/ 'F' -> 회원 탈퇴 상태/  'B' -> 계정 정지 상태로 정한다.
- 고객은 회원 가입한 이메일, 패스워드로 로그인할 수 있다.

  


> 회원 정보 수정 []

- 고객은 이메일 외의 정보들은 모두 수정이 가능하다.
  - 패스워드 변경은 패스워드 key를 담은 메일이 전송되며, 링크를 클릭하면 인증이 완료되고, 새로운 비밀번호를 입력하면 비밀번호 초기화가 된다.


> 회원 탈퇴 []
- 고객은 회원 탈퇴를 할 수 있다.
  - 회원 탈퇴를 하게 되면 DB에 회원 정보를 삭제하는 것이 아니라 회원 상태를 'F'로 변경시킨다. 하지만 보안 이슈가 있을 수 있으므로, 이메일, 회원 상태 외의 정보들은 초기화 된다.
- 고객은 회원 탈퇴를 하고 같은 이메일로 회원가입할 수 없다.

> 사용자 등급 기능 []
- 고객은 등급이 나누어진다. 
  - 차례로 'welcome', 'vip', 'vvip'가 있다.
  - 등급은 간단하게 총 결제금액에 따라 결정 된다. 0, 500,000, 5,000,000
  
> 장바구니 기능 및 상품 구매 기능 []
- 고객은 장바구니에 상품을 담을 수 있다.
  - 최대 10개의 상품을 담을 수 있다.
- 고객은 한개의 상품을 구매하거나, 장바구니에 있는 여러 개의 상품을 한번에 구매할 수 있다.

> 상품 추천 기능 []
- 고객이 등록한 정보로 상품을 추천받을 수 있다. 
  - 고객은 나이, 몸무게, 스타일을 등록할 수 있다.
  - 회원이 정보를 등록하지 않았다면 추천이 안된다.

> 서랍 기능 및 찜 기능 []
- 고객은 상품을 찜할 수 있다. 
- 찜 상품이 많으면 정리가 되지 않으므로, 고객이 스스로 서랍이라는 카테고리를 만들어 그 안에 찜 상품을 넣을 수 있다.
  - 최대 5개의 서랍을 가질 수 있다.

> 품절 상품 입고 시 알람 기능 []
- 고객은 품절된 상품이 입고되면 알람을 받을 수 있다.


##  매니저 
> 상품 CRUD []  
> 배송 관리 및 구매 처리 기능 []

##  관리자
> 회원 관리 기능 []   
> 회원 정보 변경 []   
> 카테고리 관리 기능 []   
> 배너 관리 기능 []  
> 품절 상품 관리 기능 []
<br>
# 키오스크
## 1. 도메인
- admin, coupon, member, menu, order, receipt로 구성
## 2. 데이터 테이블
- Menu, Member, Order, OrderItem, Receipt 엔티티를 정의하고 SpringData JPA를 사용하여 DB 테이블에 매칭
- 각각의 엔티티는 Primary Key로 Id값을 필드로 가짐
- 상품 정보는 Menu 테이블에서 다루고 주문 관련 정보는 Order와 OrderItem(주문 시 어떤 상품을 선택했는지) 테이블에서 처리
- Receipt는 총비용과 대기번호, 쿠폰 사용 여부의 정보를 가짐
- 스탬프 적립을 위한 회원 정보는 Member에서 관리
## 3. 연관관계
- Order 엔티티는 Receipt와 1:1 매핑
- Order와 OrderItem은 1:N 연관관계(주문서에 주문한 상품 리스트 정보가 저장되기 때문)
## 4. UI
- Thymeleaf, HTML, CSS를 사용하여 페이지 구성
![Image](https://github.com/user-attachments/assets/d5a52590-62de-4032-87f1-f4de9349da44)
## 5. 기능
### 1) 고객
- 상품 옵션, 수량을 선택하여 카트에 저장
- 스탬프 적립, 쿠폰 사용 여부 선택
- 스탬프는 상품 개수만큼 적립(적립 시 등록된 회원 전화번호 필요)
- 영수증 출력 선택(선택하지 않을 경우 대기번호만 출력)
- 전화번호로 회원 등록
### 2) 관리자
- 저장되어 있는 상품 리스트를 조회하여 수정 또는 삭제
- 상품 추가(상품명, 가격, 카테고리 지정)
- 추가된 상품은 상품 카테고리에 반영되어 격자칸 증가(삭제 시에는 상품이 있던 칸 사라짐)
- 당일 주문서 내역 조회
- 특정 날짜, 월, 연도의 매출액 확인 가능(월 매출은 해당 월의 모든 날짜, 연도는 해당 연도의 모든 월 매출액 출력)
- 인터셉터에 관리자 기능들을 경로로 지정하여 로그인 없이 URL로 접근 시 로그인 화면으로 redirect
- 세션 만료 시간 30분 초과 시 로그아웃

## 6. 예외처리
- 고객이 상품 선택 후 결제하는 사이에 관리자가 상품을 삭제했을 경우 MemuNotFoundException 발생
- 주문 생성 전, 영수증 출력 전 DB에 있는 Order와 Receipt 테이블의 해당 데이터 삭제 시 OrderNotFoundException, ReceiptNotFoundException 발생
- 위 3가지 예외들은 RuntimeException을 상속받으며 GlobalExceptionHandler 클래스의 ExceptionHandler에서 일괄적으로 예외 처리
- 예외 발생 시 예외 메시지를 Model에 담아 특정 뷰에 렌더링하는 방식으로 처리
- 오류 페이지 화면(아래)
![Image](https://github.com/user-attachments/assets/c21368e0-ddbf-4a40-9179-5d54e9f49a6a)

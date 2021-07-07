# 1. Mayday

### 성신여자대학교 스마트모바일프로그래밍 수업에서 만든 팀 프로젝트입니다.   
✔ **Google Play Store** : https://play.google.com/store/apps/details?id=com.mayday.Mayday  

## 1.1 기획 의도
#### “비행기·선박의 무선전화에 의한 조난 구조 신호, 메이데이 (cf. S.O.S)”
우울감이나 무력감, 번아웃 증후군에 시달리는 현대인들을 위해 간단하지만 보람을 느낄 수 있는 일일 퀘스트를 무작위로 제공합니다. 제공한 퀘스트 달성 시 격려 문구 출력과, 모을 수 있는 리워드를 제공해 수집욕구를 자극합니다.   
또한 힐링을 통해 앱을 지속할 수 있는 원동력을 부여합니다.   

## 1.2 상세 컨텐츠 및 요구사항
- 퀘스트 개수는 유저가 지정 가능 (최소 1개, 최대 5개)
- 퀘스트 출력 후 일정 시간 지난 후 (예-6시간) 미달성 상태일 시 푸쉬 알람으로 달성 독려
- 게임성을 부가해 앱 사용 지속력 부여. (예- 달성한 퀘스트 누적 개수 n개당 유저 레벨 업/리워드 종류는 뱃지, 칭찬스티커, 과일, 보석, 꽃 등등 다양한 리워드로 수집욕구 자극.)
- 유저가 직접 ‘나만의 퀘스트’를 작성할 수도 있음. 
- 친구와 퀘스트 내용, 달성한 퀘스트, 레벨업 결과를 sns로 공유 가능
- 퀘스트는 랜덤 출력이 기본이나 카테고리를 지정하여 출력하는 것도 가능   
	→ 출력 퀘스트 예시   
	<아웃도어 액티비티> 카테고리의 경우 : “오늘은 날씨가 참 좋네요. 햇빛을 받으며 20분 이상 걸어볼까요?”   
	<인도어 액티비티> 카테고리의 경우 : “읽고 싶은, 혹은 읽고 있는 책이 있나요? 읽는 걸 미루고 있었다면 세 장 이상 읽어봐요. 무슨 생각이 들었는지 공유해요.”   
- 퀘스트 달성 인증 방식 - 사진 또는 글 업로드

## 1.3 시연 이미지

|이미지|설명|
|---|---|
|![그림01](https://user-images.githubusercontent.com/67622600/124698130-1c810900-df23-11eb-8ba1-34546d3653a8.jpg)|✔ Thread와 Handler를 이용한 App Intro 화면|
|![그림02](https://user-images.githubusercontent.com/67622600/124698665-44bd3780-df24-11eb-83b1-a42ec12032b6.jpg)|✔ Tab Host와 Fab을 이용한 Main Activity <br /> ✔ Fab 터치시 Select Quest Activity로 전환|
|![그림03](https://user-images.githubusercontent.com/67622600/124698762-72a27c00-df24-11eb-9eec-d761a2c0c1cb.jpg)|✔ 최대 5개의 퀘스트를 선택 할 수 있는 퀘스트 카테고리 <br /> ✔ 퀘스트의 내용을 4개의 카테고리로 세분화 했으며 랜덤 카테고리를 통해 다양하게 출력하는 것도 가능|
|![그림04](https://user-images.githubusercontent.com/67622600/124699052-0b38fc00-df25-11eb-919f-89518fcaa454.jpg)|✔ 사용자가 직접 원하는 퀘스트를 입력 할 수 있는 Write Quest Activity|
|![그림05](https://user-images.githubusercontent.com/67622600/124699132-29066100-df25-11eb-80b5-83e2c6444a81.jpg)|✔ Main Activity에서 출력된 퀘스트 버튼 터치시 전환되는 Finish Activity <br /> ✔ 사진 첨부 기능과 글 작성 기능을 통해 퀘스트 수행을 완료했다는 인증을 진행|
|![그림06](https://user-images.githubusercontent.com/67622600/124699234-594dff80-df25-11eb-97dd-1caa9b66b1cc.jpg)|✔ 날짜에 따라 완료한 퀘스트를 확인 할 수 있는 Completed Quest Tab <br /> ✔ 캘린더의 날짜를 터치하면 그 날 인증했던 글과 사진을 볼 수 있음 <br /> ✔ 일일 최대 저장 상한은 10개|
|![그림07](https://user-images.githubusercontent.com/67622600/124699347-9b774100-df25-11eb-846f-9a01db631abc.jpg)|✔ 퀘스트를 수행하여 유저의 레벨이 오를 때마다 다양한 리워드 이미지 개방|
|![그림08](https://user-images.githubusercontent.com/67622600/124699414-b3e75b80-df25-11eb-83b8-e9b53a96835c.jpg)|✔ Main Activity에서 원하는 퀘스트 버튼을 길게 눌러 카카오톡으로 공유 가능|








# 2. 기술 스택
- Android studio
- JAVA
  + SQLite
  + Intent 사용
  + 스레드
  + 중복 layout
  + 대화상자
  + 메뉴이용
  + 카카오API

# 3. Contact
   
Contact Email : team8mayday8@gmail.com

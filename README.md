# <img src="https://avatars.githubusercontent.com/u/104211703?s=200&v=4" align=left width=50 >오싹 소개

![logo](https://velog.velcdn.com/images/ryurim0109/post/617aad8d-f4d4-45a9-9efc-eb23f73b55de/image.jpg)
<br>
👻 **[오싹 서비스 바로가기](https://ossack.shop/)**

## 💻 프로젝트 소개
> 오싹은 지도로 오피스 매물과 공유 오피스 매물을 찾아볼 수 있는 모바일 웹입니다.

## 😇‍ 백엔드 팀원

<table>
  <tr>
    <td align="center"><a href="https://github.com/chIorophyII"><img src="https://avatars.githubusercontent.com/u/73023890?v=4" width="100px" /></a></td>
    <td align="center"><a href="https://github.com/Littlecold4"><img src="https://avatars.githubusercontent.com/u/72268423?v=4" width="100px" /></a></td>
    <td align="center"><a href="https://github.com/sumye"><img src="https://avatars.githubusercontent.com/u/101075913?v=4" width="100px" /></a></td>
   
  </tr>
  <tr>
    <td align="center"><b>황다빈</b></td>
    <td align="center"><b>장민우</b></td>
    <td align="center"><b>지수민</b></td>
    
  </tr>
  <tr>
    <td align="center"><b>🤩 Backend 🤩 </b></td>
    <td align="center"><b>🤩 Backend 🤩 </b></td>
    <td align="center"><b>🤩 Backend 🤩 </b></td>
   
  </tr>
</table>

## ⚙️ 아키텍쳐

![아키텍처](https://velog.velcdn.com/images/ryurim0109/post/94c8fd2f-7ea2-44da-b425-fac874853c5c/image.jpg)

## ⚛️ 개인 역할

<code>장민우</code> DB설계, 매물 관련 API 설계, 매물 크롤링, 페이징처리

<code>황다빈</code> 프로필 수정, 로그인, 소셜로그인, https적용, CI/CD

<code>지수민</code> Swagger적용, 좋아요 기능, 페이징처리

## 🔨 Trouble Shooting

<details markdown="1">
<summary>크롤링 시간 지연 및 API 요청 지연</summary>
  
### ✅ 문제상황

> 초기목표는 크롤링을 통해 매일 매물리스트를 갱신하는 것  
> 크롤링 소요시간이 길고, 크롤링 중 API 요청이 지연됨  
> > 82개의 매물을 가져오는 데 약 5시 30분의 시간이 걸릴 것으로 예상

### ✅ 해결방안

> 1. Multi Thread를 적용  
> 2. 서버 스펙 상향  
> → 코어 2개와 4GB RAM의 서버로 업그레이드 해 4개의 Multi Thread를 사용

### ✅ 결과

> 85개의 매물을 크롤링 하는 데 약 2분 20초 소요  
> 크롤링 중 API 응답 속도 28% 감소

### ✅ 향후 추가 개선 목표

> 1. RAM의 부하로 크롤링 지속시간이 길어지면 소요시간이 급격히 늘어나는 문제 발생  
> 2. 크롤링이 진행되지 않는 상황일 때 고사양의 서버를 유지하는 것은 비효율적이라 판단  
> → AWS autoscaling 적용 예정



</details>

### 👀 사용 Stack 👀  
<div>
<img src="https://img.shields.io/badge/JAVA-007396?style=flat&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=Spring&logoColor=white"> 
<img src="https://img.shields.io/badge/Springboot-6DB33F?style=flat&logo=Springboot&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=flat&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1??style=flat&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/AWS-%23FF9900.svg?style=flat&logo=AmazonAWS&logoColor=white"> 
<img src="https://img.shields.io/badge/Amazon S3-569A31?style=flat&logo=Amazon S3&logoColor=white">
<img src="https://img.shields.io/badge/GitHub Actions-2088FF??style=flat&logo=GitHub Actions&logoColor=white"> 
<img src="https://img.shields.io/badge/AWS CodeDeploy-6DB33F??style=flat&logo=AWS Codedeploy&logoColor=white">
<img src="https://img.shields.io/badge/JUnit5-25A162?style=flat&logo=JUnit5&logoColor=white">
<img src="https://img.shields.io/badge/Apache JMeter-D22128?style=flat&logo=Apache JMeter&logoColor=white">
<img src="https://img.shields.io/badge/NGINX-009639?style=flat&logo=NGINX&logoColor=white">
<img src="https://img.shields.io/badge/spring security-6DB33F?style=flat&logo=spring security&logoColor=white">
<img src="https://img.shields.io/badge/Linux-FCC624?style=flat&logo=linux&logoColor=black">
</div>

### ✅ 커밋 종류

> 수정한 종류에 따라 커밋 메시지를 선택  

|메시지명|설명|
|---|---|
|feat|새로운 기능 추가 관련|
|fix|버그 수정|
|test|테스트 코드, 리팩토링 테스트 코드 추가|
|refactor|코드 리팩토링(기능향상)|
|chore|빌드 업무 수정, 패키지 매니저 수정|
|docs|문서 수정(md, git관련 파일, 이미지파일 수정)|
|style|코드 formatting, 세미콜론(;) 누락, 코드 변경이 없는 경우|

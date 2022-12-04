# 날씨 정보

## 요구사항
- **Seoul, London, Chicago** 세 도시의 날씨를 표시
- 위 도시 **순서대로 오늘을 포함한 6일 간의 날씨**를 표시
- 총 3개 도시의 각 6일간의 날씨를 **스크롤이 가능한 리스트를 이용해 한 화면에 표시**

## 구현 계획
1. android clean architecture에 따라 모듈 분리 (완료)
2. 각 모듈 gradle 세팅 (완료)
3. remote layer test code 작성, remote layer 구현 (완료)
4. cache layer test code 작성, cache layer 구현 (완료)
5. data layer test code 작성, data layer 구현 (완료)
6. domain layer test code 작성, domain layer 구현 (완료)
7. presentation layer viewmodel test code 작성, viewmodel 구현 (완료)
8. ui 구현 (완료)

## 참고 링크
- [날씨 API](https://openweathermap.org/current)
- [날씨 이미지](https://erikflowers.github.io/weather-icons/)

## 🏗 Project Structure
![Architecture_In_Search_Movie](https://user-images.githubusercontent.com/48168117/193544728-cf576bb6-417e-4651-b329-1f3daaa3d31c.png)

## 📖 Tech Skill
#### Architecture
- MVVM Pattern
- Clean Architecture in Android (presentation, domain, data)
#### UI
- AppCompact
- RecyclerView
#### Network
- Retrofit
- Okhttp
#### DI
- Hilt
#### ETC
- Coroutine
- Gson
- Room

## 결과

   Network connection O    |    Network connection X    |
:-------------------------:|:--------------------------:|
![connect_network](https://user-images.githubusercontent.com/48168117/200204677-d381a0c3-100e-4271-9ff1-7253c43aa5e9.gif)  |  ![disconnect_network](https://user-images.githubusercontent.com/48168117/200204669-8022b04a-ed8c-4ec3-b566-f9e50fb0bb68.gif)


## Developer
[@Sangmeebee](https://github.com/Sangmeebee)  
> [11월 2일] 개발 시작  
> [11월 7일] 개발 종료

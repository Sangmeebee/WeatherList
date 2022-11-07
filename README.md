# 핏펫 과제
## 확인해주세요! 
```local.properties``` 파일에 ```open_weather_app_id="b0d3d40b069fcd8f9b62eaab3d76d8cc"``` 를 추가해야 정상적으로 빌드됩니다. 

앱키를 숨겨놓기 위해 git repository에 올라가지 않는 local.properties 파일에 키를 넣어놨습니다. 빌드시 꼭! 추가 부탁드립니다.  

한가지 더 말씀드리고 싶은건 과제 요구사항이 daily 날씨정보를 화면에 로드하는 것입니다. (daily의 최저, 최고기온 / daily의 날씨)  
하지만 현재 openWeatherAPI는 시간 간격과 3시간 간격에 대한 날씨 정보를 제공하는 API는 무료이지만 daily 날씨 API는 하루에 1000회 이상 조회하면 비용이 지불됩니다. OpenWeather 사이트에서 1000회만 호출할 수 있게 막아놨습니다.(혹시 잘되다가 오류가 난다면 이러한 이유일 것입니다. 물론, 그러한 상황은 토스트로도 확인 하실 수 있습니다.)  

위와 같은 이유로 local.properties에 저의 app id를 추가하거나, One Call API 3.0을 구독하고 있는 키를 추가해야합니다.

마지막으로 날씨정보 api 요청시 쿼리로 도시이름을 보내주면 해당도시의 날씨를 응답받지만, 현재 open weather api에서 더이상 버그에 대한 지원을 하지 않아 권고하지 않는다고 합니다.  
그래서 geocoder api를 통해 해당 도시의 우편번호를 통해 경도/위도를 응답받고, 응답받은 경도/위도를 통해 날씨 정보를 호출했습니다.  
 
열심히 수행한 과제인 만큼 꼼꼼히 봐주시면 감사하겠습니다:)  


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

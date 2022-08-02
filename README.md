# 가계부 관리 앱

## 걸린 시간
7월 25일 ~ (진행 중)

## 위키
* [구현 내용 정리](https://github.com/woowa-techcamp-2022/android-accountbook-20/wiki/구현할-내용-정리)
* [깃헙 컨벤션](https://github.com/woowa-techcamp-2022/android-accountbook-20/wiki/깃헙-컨벤션)

## 피쳐
- [X] 프로젝트 추가 및 초기 설정
- [X] HistoryScreen 개발
- [X] HistoryCreateScreen 개발
- [X] CalendarScreen 개발
- [X] StatisticsScreen 개발
- [X] SettingScreen 개발
- [X] CategoryCreateScreen 개발
- [X] PaymentMethodCreateScreen 개발
- [ ] 프로젝트 파일 구조 정리하기

## Database 구조
![제목 없는 다이어그램 drawio](https://user-images.githubusercontent.com/18213322/182394719-e646c8c1-2de3-4b39-a0f2-91cf97908a6f.png)

## 기술 스택
|Type|Name|
| :--: | :-----------------------: |
|Dependancy Injection|[Hilt](https://dagger.dev/hilt/)|
|UI|[Compose](https://developer.android.com/jetpack/compose)|
|Async Processing|Coroutine/Flow|
|Local Database|[SQLiteOpenHelper](https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper)|
|Third Party Library|[google/accompanist](https://github.com/google/accompanist)|
|Architecture|Clean-Architecture, AAC ViewModel|
|Design Pattern|DAO Pattern, Repository Pattern, UseCase Pattern|

## Github Branch 구조
|title|description|
|:---------:|:---------:|
|main|현재 릴리즈 된 코드의 브랜치|
|release|릴리즈를 준비하는 브랜치|
|dev|개발용으로 사용하는 브랜치|
|hotfix|릴리즈 완료 이후 버그 발생 시 사용하는 브랜치|
|feature|기능별 개발 시 사용하는 브랜치|
|fix|버그 수정 시 사용하는 브랜치|
|add|기능 추가 시 사용하는 브랜치|

## 개발자
|<img src="https://github.com/2004yyj.png" width="200"/>|
|:--:|
|[양윤재](https://github.com/2004yyj)|

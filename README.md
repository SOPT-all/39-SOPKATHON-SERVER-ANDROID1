# 39-SOPKATHON-SERVER-ANDROID1

> 🍋 PLAY SOPT 39기 솝커톤 안드로이드 1조 서버 아이셔버 🍋

## 🖥️ Server Developer

| **[박다윤](https://github.com/DAYOUN1205)** | **[장현준](https://github.com/guswnsj0112)** | **[조서연](https://github.com/csyjulia0478)** |
|:-------------------------------------------:|:--------------------------------------------:|:---------------------------------------------:|
| <img src="https://i.pinimg.com/originals/df/0c/4f/df0c4fb7805fd6c03138e3c38d552133.gif" width="170" alt="박다윤"> | <img src="https://media1.tenor.com/m/-kZbu04j6HIAAAAC/obanai-iguro.gif" width="170" alt="장현준"> | <img src="https://i.pinimg.com/originals/10/5c/31/105c311b3064c379113654bd338c6a7e.gif" width="170" alt="조서연"> |
|             `Server Developer`              |                `Server Lead`                 |              `Server Developer`               |

## 🛠️ 서버 기술 스택 및 선정 이유

### 1. Language & Framework

- **Java 21 (LTS)**
    - 장기 지원 버전으로 안정적인 개발 환경과 유지보수성을 확보하기 위해 선택했습니다.
- **Spring Boot 4.1.2**
    - 현재 지원되는 정식 안정 버전으로, 자동 설정 기능을 활용해 초기 환경 구성의 복잡도를 줄이고 비즈니스 로직 구현에 집중하기 위해 선택했습니다.

### 2. Database & ORM

- **MySQL (Amazon RDS)**
    - 세미나에서 학습한 MySQL을 실제 프로젝트에 적용하고, Amazon RDS를 통해 별도의 데이터베이스 서버 운영 부담을 줄이면서 팀원이 동일한 원격 데이터베이스 환경을 사용하기 위해 선택했습니다.
- **Spring Data JPA**
    - 객체와 관계형 데이터베이스 간 매핑을 지원하고, Repository를 통해 반복적인 데이터 접근 코드를 줄이기 위해 선택했습니다.

### 3. Web & API

- **Spring Web**
    - REST API를 구현하고 클라이언트와 HTTP 기반으로 통신하기 위해 사용합니다.
- **Spring Validation**
    - 클라이언트 요청 데이터의 유효성을 검증하여 잘못된 데이터의 유입을 방지하기 위해 사용합니다.
- **springdoc-openapi (Swagger UI)**
    - API 명세를 문서화하고, 클라이언트 개발자와 API 요청·응답 구조를 공유하기 위해 사용합니다.

### 4. Development Tools

- **Gradle**
    - 프로젝트 빌드 및 의존성을 효율적으로 관리하기 위해 사용합니다.
- **Lombok**
    - Getter, 생성자 등 반복적인 코드를 줄여 핵심 비즈니스 로직의 가독성을 높이기 위해 사용합니다.
- **Spring Boot DevTools**
    - 개발 중 변경 사항을 빠르게 반영하고 개발 편의성을 높이기 위해 사용합니다.

## 🌱 Git 컨벤션

> 해커톤인 만큼 최대한 간결하고 쉽게 작성합니다.

### Issue

- 각자 파트에 맞는 이슈 템플릿을 사용합니다.
- 이슈 이름에는 `feat:` 등의 태그를 붙이지 않고 기능만 작성합니다.
    - Good: `테스트 기능 구현`
    - Bad: ~~feat: 테스트 기능 구현~~

### Pull Request

- PR 이름에는 기능만 작성합니다.
    - Good: `테스트 기능 구현`, `테스트 리팩터링`
    - Bad: ~~Refactor: 테스트~~

### Branch

- `{label}/#{이슈번호}` 형식으로 작성합니다.
    - Good: `feat/#4`
    - Bad: ~~frontend/#4/feat/ui-setting~~

### Commit

- `{이슈번호} {label}: {기능명}` 형식으로 작성합니다.
    - Good: `1 chore: PR 템플릿에 close 추가`
    - Bad: ~~테스트 구현~~

### Label

| 태그                | 설명                                                                       |
|:--------------------|:---------------------------------------------------------------------------|
| `feat:`             | 새로운 기능을 추가할 경우                                                  |
| `fix:`              | 버그를 고친 경우                                                           |
| `design:`           | 사용자 UI 디자인을 변경한 경우                                             |
| `!BREAKING CHANGE:` | 커다란 API 변경이 있는 경우                                                |
| `!HOTFIX:`          | 치명적인 버그를 급하게 고쳐야 하는 경우                                    |
| `style:`            | 코드 포맷 변경, 세미콜론 누락 등 코드 수정이 없는 경우                     |
| `refactor:`         | 프로덕션 코드를 리팩토링한 경우                                            |
| `comment:`          | 필요한 주석을 추가하거나 변경한 경우                                       |
| `docs:`             | 문서를 수정한 경우                                                         |
| `test:`             | 테스트 추가 또는 테스트 리팩토링을 한 경우(프로덕션 코드 변경 없음)        |
| `chore:`            | 빌드 태스크 업데이트, 패키지 매니저 설정 등 프로덕션 코드 변경이 없는 경우 |
| `rename:`           | 파일 또는 폴더명을 수정하거나 옮기는 작업만 수행한 경우                    |
| `remove:`           | 파일을 삭제하는 작업만 수행한 경우                                         |

## 🤖 AI 허용 범위

> AI 사용은 최대한 지양하며, 검증이 필요한 경우에만 사용합니다.

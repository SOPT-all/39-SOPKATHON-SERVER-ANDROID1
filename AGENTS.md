# AGENTS.md

이 문서는 저장소 전체에 적용됩니다.

## 명령어

```bash
./gradlew bootRun              # 로컬 실행
./gradlew test                 # 테스트
./gradlew clean test           # 클린 빌드 및 테스트
```

Windows PowerShell에서는 `./gradlew` 대신 `.\gradlew.bat`을 사용합니다.

애플리케이션 실행 후 Swagger UI는 `http://localhost:8080/swagger-ui/index.html`에서 확인합니다.

## 아키텍처

모놀리식 단일 애플리케이션과 도메인형 패키지 구조(Controller-Service-Repository)를 유지합니다. 계층형, 클린, 헥사고날 아키텍처를 새로 도입하지 않습니다.

기본 패키지는 `sopt._39sopkathonserverandroid1`입니다.

```text
sopt._39sopkathonserverandroid1
├─ domain/
│  └─ <도메인명>/
│     ├─ controller/              # REST 엔드포인트
│     │  ├─ XxxApi.java           # Swagger 어노테이션 전용 인터페이스
│     │  └─ XxxController.java    # 인터페이스 구현체
│     ├─ dto/                     # 계층 간 요청·응답 DTO
│     │  ├─ request/
│     │  └─ response/
│     ├─ service/
│     │  └─ XxxService.java
│     ├─ repository/              # JpaRepository 인터페이스
│     ├─ entity/                  # JPA 엔티티 + enum
│     ├─ code/                    # XxxErrorCode, XxxSuccessCode
│     └─ exception/               # BusinessException 하위
└─ global/
   ├─ code/        # ErrorCode, SuccessCode, GlobalErrorCode
   ├─ config/      # SwaggerConfig
   ├─ dto/         # CommonResponse
   ├─ entity/      # BaseTimeEntity
   ├─ exception/   # BusinessException, GlobalExceptionHandler
   └─ swagger/     # ApiExceptions, ApiExceptionsOperationCustomizer
```

- 새 도메인은 위 구조를 그대로 따릅니다.
- DTO는 도메인 바로 아래의 독립된 `dto/{request,response}` 패키지에 둡니다.
- 도메인 간 의존은 단방향으로 유지해 순환 의존을 만들지 않습니다.

## 코드 컨벤션

### 엔티티

- `@Entity`, `@Getter`, `@Table(name = "...")`, `@NoArgsConstructor(access = AccessLevel.PROTECTED)`로 시작합니다.
- `@Setter`는 사용하지 않습니다. 상태 변경은 의미 있는 도메인 메서드로 노출하고 필드는 모두 `private`으로 선언합니다.
- `created_at`, `updated_at`이 있는 테이블은 `BaseTimeEntity`를 상속합니다. 타임스탬프가 없는 정션·디테일 테이블은 상속하지 않습니다.
- PK는 기본적으로 `@GeneratedValue(strategy = GenerationType.IDENTITY)`를 사용합니다. PK 자체가 FK이면 `@GeneratedValue`를 붙이지 않습니다.
- FK는 `Long` ID 컬럼과 관계 객체를 함께 두는 이중 매핑으로 작성합니다. 관계 객체는 항상 `LAZY`, `insertable = false, updatable = false`로 설정하고 쓰기는 ID 컬럼으로만 처리합니다.

```java
@Column(name = "parent_id", nullable = false)
private Long parentId;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "parent_id", insertable = false, updatable = false)
private Parent parent;
```

- NOT NULL은 `@Column(nullable = false)`, TEXT는 `columnDefinition = "TEXT"`, Enum은 `@Enumerated(EnumType.STRING)`으로 명시합니다.

### DTO (`domain/<도메인명>/dto/{request,response}`)

- DTO를 `controller`나 `service` 하위에 두지 않습니다.
- DTO는 모두 `record`로 작성합니다.
- 변환 책임은 DTO의 정적 팩토리 메서드 `of(...)`에 둡니다.
- 응답 안에서만 쓰는 하위 구조는 중첩 `record`로 둡니다.
- DTO 필드에는 `@Schema(description = "...", example = "...")`를 붙입니다.

### Repository

- `JpaRepository<Entity, Long>`을 확장합니다.
- 엔티티가 여러 개면 엔티티별로 Repository를 분리합니다.
- 메서드명은 Spring Data 파생 규칙을 우선합니다.
- N+1을 피해야 하는 조회만 `@Query`의 `join fetch`로 명시합니다.

### Service

- 클래스에 `@Service`, `@RequiredArgsConstructor`, `@Transactional(readOnly = true)`를 두고 쓰기 메서드만 `@Transactional`로 덮어씁니다.
- 의존성은 `private final` 생성자 주입만 사용합니다. `@Autowired` 필드 주입은 금지합니다.
- 책임이 다르면 서비스를 분리하고, 검증만 담당하는 책임은 `XxxValidator`로 분리합니다.
- ID 단순 조회는 `read...`, 그 외 조회는 `find...`로 이름을 시작합니다. 조회 실패 시 예외를 던지면 `findByIdOrThrow`처럼 이름에 드러냅니다.
- 서비스 전용 VO는 만들지 않습니다. 계층 간 데이터 전달은 도메인의 독립된 `dto` 패키지를 사용합니다.
- 응답 변환은 DTO의 정적 팩토리 메서드 `of(...)`로 처리합니다.

### Controller

- URL은 버전 prefix가 붙은 복수형 자원으로 작성합니다. 예: `/v1/resources`.
- Swagger 어노테이션은 `XxxApi` 인터페이스에 두고 `XxxController`가 구현합니다.
- 모든 응답은 `CommonResponse<T>`로 감쌉니다.
- 데이터가 있으면 `CommonResponse.success(SuccessCode, data)`, 없으면 `CommonResponse.success(SuccessCode)`를 사용합니다.
- 요청 검증은 Spring Validation 어노테이션으로 처리합니다.
- 페이지 번호는 1부터 시작합니다.

### 에러·성공 코드와 예외

- 도메인별로 `XxxErrorCode implements ErrorCode`, `XxxSuccessCode implements SuccessCode` enum을 만듭니다.
- 상수명은 `<도메인>_<상태>` 형태의 대문자 SNAKE_CASE로 작성합니다. 예: `RESOURCE_NOT_FOUND`.
- 메시지는 사용자에게 노출되는 한국어 문장으로 작성합니다.

```java
@Getter
@RequiredArgsConstructor
public enum ResourceErrorCode implements ErrorCode {
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
```

- 비즈니스 예외는 `BusinessException`을 상속하고 no-args 생성자를 둡니다. `ApiExceptionsOperationCustomizer`가 리플렉션으로 예외를 생성해 ErrorCode를 추출할 때 필요합니다.
- 실패 응답을 직접 만들지 않습니다. 예외를 던지면 `GlobalExceptionHandler`가 `CommonResponse.error(...)`로 변환합니다.
- 컨트롤러나 서비스에서 `try-catch`로 응답을 만들지 않습니다.
- 바인딩·검증 실패는 글로벌 핸들러가 `CommonResponse.validationError(...)`로 변환합니다.

## 새 API 추가 순서

1. `entity/` — 필요한 엔티티와 연관관계를 점검합니다.
2. `repository/` — 필요한 조회 메서드를 추가합니다.
3. `code/` — `XxxErrorCode`, `XxxSuccessCode` 상수를 추가합니다.
4. `exception/` — `BusinessException` 하위 예외를 추가합니다.
5. `dto/{request,response}/` — `record` DTO와 `@Schema`를 추가합니다.
6. `service/` — 트랜잭션, 검증, 조회 로직을 구현합니다.
7. `controller/XxxApi` — `@Tag`, `@Operation`, `@ApiExceptions`를 작성합니다.
8. `controller/XxxController` — `@RestController` 구현체를 작성합니다.
9. Swagger UI에서 요청, 응답, 에러 예시를 확인합니다.

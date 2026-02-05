-- 1. 기존 테이블 및 타입 삭제 (의존성 역순)
DROP TABLE IF EXISTS "weeklyreports" CASCADE;
DROP TABLE IF EXISTS "receipts" CASCADE;
DROP TABLE IF EXISTS "expenses" CASCADE;
DROP TABLE IF EXISTS "users" CASCADE;

DROP TYPE IF EXISTS category_type;
DROP TYPE IF EXISTS source_provider;

-- 2. ENUM 타입 생성
CREATE TYPE category_type AS ENUM ('FOOD', 'TRANSPORT', 'LIVING', 'HEALTH', 'CULTURE', 'SAVING', 'ETC');
CREATE TYPE source_provider AS ENUM ('MANUAL', 'RECEIPT_OCR', 'CARD_OCR');

-- 3. users 테이블 생성 (유저 정보)
CREATE TABLE "users" (
                         "id"            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         "email"         VARCHAR(100) NOT NULL UNIQUE,
                         "password"      VARCHAR(255) NOT NULL,
                         "nickname"      VARCHAR(50)  NOT NULL,
                         "target_budget" INTEGER      NULL,
                         "created_at"    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         "updated_at"    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- 4. expenses 테이블 생성 (지출 내역)
CREATE TABLE "expenses" (
                            "id"            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            "user_id"       BIGINT       NOT NULL,
                            "amount"        NUMERIC(19, 2) NOT NULL,
                            "merchant_name" VARCHAR(100) NULL,
                            "content"       VARCHAR(255) NULL,
                            "expense_date"  TIMESTAMP    NOT NULL,
                            "category"      category_type NOT NULL,
                            "source_type"   source_provider NOT NULL,
                            "ai_analysis"   JSONB        NULL,
                            "deleted_at"    TIMESTAMP    NULL,
                            "created_at"    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
                            "updated_at"    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 수정 시간 추가
                            CONSTRAINT "fk_users_to_expenses" FOREIGN KEY ("user_id") REFERENCES "users" ("id")
);

-- 5. receipts 테이블 생성 (증빙 파일 - 불변 데이터)
CREATE TABLE "receipts" (
                            "id"                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            "expense_id"        BIGINT       NOT NULL,
                            "s3_key"            VARCHAR(255) NOT NULL,
                            "original_filename" VARCHAR(255) NOT NULL,
                            "file_size"         BIGINT       NOT NULL,
                            "created_at"        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 생성 시간만 추가 (수정 불가 논리)
                            CONSTRAINT "fk_expenses_to_receipts" FOREIGN KEY ("expense_id") REFERENCES "expenses" ("id")
);

-- 6. weeklyreports 테이블 생성 (AI 리포트)
CREATE TABLE "weeklyreports" (
                                 "id"             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 "user_id"        BIGINT    NOT NULL,
                                 "report_content" TEXT      NULL,
                                 "start_date"     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
                                 "end_date"       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
                                 "created_at"     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 생성 시간 추가
                                 "updated_at"     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 수정 시간 추가
                                 CONSTRAINT "fk_users_to_weeklyreports" FOREIGN KEY ("user_id") REFERENCES "users" ("id")
);

-- 7. 성능 최적화를 위한 인덱스
CREATE INDEX "idx_expenses_user_date" ON "expenses" ("user_id", "expense_date" DESC, "id" DESC);
CREATE INDEX "idx_expenses_deletedat" ON "expenses" ("deleted_at") WHERE "deleted_at" IS NOT NULL;
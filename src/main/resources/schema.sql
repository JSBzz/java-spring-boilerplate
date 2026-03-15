-- 기존 테이블 삭제
DROP TABLE IF EXISTS member CASCADE;

-- 회원(Member) 테이블
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(50) NOT NULL UNIQUE,      -- 로그인 아이디
    password VARCHAR(255) NOT NULL,            -- 암호화된 비밀번호
    name VARCHAR(50) NOT NULL,                 -- 이름
    email VARCHAR(100),                        -- 이메일
    role VARCHAR(20) DEFAULT 'USER',           -- 권한 (USER, ADMIN)
    status VARCHAR(20) DEFAULT 'ACTIVE',       -- 상태 (ACTIVE, LOCKED, WITHDRAWAL)
    last_login_at TIMESTAMP,                   -- 최종 로그인 일시
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
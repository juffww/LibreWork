# LibreWork — Database Schema

Tài liệu thiết kế cơ sở dữ liệu cho nền tảng freelance LibreWork, xây dựng trên PostgreSQL.

---

## Tổng quan kiến trúc

Schema được tổ chức theo **3 domain cốt lõi** trong giai đoạn đầu:

```
Domain 1 — Category & Skill     skill_categories, job_categories, skills
Domain 2 — Identity & Profiles  users, user_profiles, freelancer_profiles,
                                 client_accounts, user_oauth_providers, user_skills
Domain 3 — Job & Proposal       jobs, job_skills, proposals, proposal_milestones
```

### Điểm thiết kế quan trọng

**Multi-tenant Client** — Một người dùng có thể tạo nhiều `client_accounts` độc lập (khác tên công ty, logo, lịch sử chi tiêu). Đây là điểm khác biệt so với mô hình 1 user = 1 client thông thường.

```
users (1) ──► user_profiles      (freelancer info)
         └──► client_accounts[]  (N client identities)
```

**Tách freelancer và client** — Thay vì dùng `role ENUM` gắn vào `users`, hệ thống dùng hai bảng profile riêng biệt. Một user có thể vừa có `user_profiles` (freelancer) vừa sở hữu nhiều `client_accounts`.

---

## Domain 1 — Category & Skill

| Bảng | Mô tả |
|---|---|
| `skill_categories` | Nhóm kỹ năng (Web Dev, Design, Writing...) |
| `job_categories` | Danh mục công việc, hỗ trợ nested qua `parent_id` |
| `skills` | Kỹ năng cụ thể, thuộc về một `skill_category` |

**Lưu ý:** `job_categories` hỗ trợ self-join — cho phép tạo danh mục con không giới hạn độ sâu (ví dụ: *Development → Frontend → React*).

---

## Domain 2 — Identity & Profiles

| Bảng | Mô tả |
|---|---|
| `users` | Bảng trung tâm, lưu thông tin xác thực |
| `user_profiles` | Profile chung + thông tin freelancer (hourly rate, availability...) |
| `client_accounts` | Tài khoản client — 1 user có thể có nhiều account |
| `user_oauth_providers` | Đăng nhập qua Google, GitHub... |
| `user_skills` | Kỹ năng của freelancer kèm proficiency level |

### Quan hệ chính

```
users
 ├── user_profiles       (1-1, freelancer info)
 ├── client_accounts[]   (1-N, multi-tenant client)
 ├── user_oauth_providers[]
 └── user_skills[]
```

### Trạng thái tài khoản (`users.status`)

| Giá trị | Ý nghĩa |
|---|---|
| `ACTIVE` | Hoạt động bình thường |
| `SUSPENDED` | Bị khóa bởi admin |
| `PENDING_VERIFY` | Chờ xác thực email |

---

## Domain 3 — Job & Proposal

| Bảng | Mô tả |
|---|---|
| `jobs` | Tin tuyển dụng do client đăng |
| `job_skills` | Kỹ năng yêu cầu cho job |
| `proposals` | Freelancer ứng tuyển và báo giá |
| `proposal_milestones` | Lịch thanh toán theo milestone đề xuất trong proposal |

### Flow cơ bản

```
client_accounts  ──đăng──►  jobs
                              │
users (freelancer) ──ứng──►  proposals
                              │
                         proposal_milestones
                         (đề xuất lịch thanh toán)
```

### Trạng thái Job (`jobs.status`)

```
OPEN → IN_PROGRESS → COMPLETED
     ↘ CANCELLED
     ↘ PAUSED
```

### Trạng thái Proposal (`proposals.status`)

```
PENDING → SHORTLISTED → ACCEPTED
        ↘ REJECTED
        ↘ WITHDRAWN
```

---

## Cài đặt

### Yêu cầu

- PostgreSQL 15+
- Extension `pgcrypto` (cho `gen_random_uuid()`)

### Khởi tạo database

```bash
# Tạo database
createdb librework

# Chạy schema
psql -d librework -f schema/v1_core.sql

```

---

## Cấu trúc thư mục

```
database/
├── schema/
│   ├── v1_core.sql          # Domain 1-3 (file hiện tại)
│   └── migrations/          # Các migration sau này
└── README.md
```

---

## Quyết định thiết kế

**Tại sao không dùng `role ENUM` trên `users`?**
Gắn role vào user làm cứng cấu trúc — không thể có 1 user vừa freelance vừa sở hữu nhiều công ty client. Tách thành `user_profiles` và `client_accounts` linh hoạt hơn và phản ánh đúng nghiệp vụ thực tế.

**Tại sao `client_accounts` không có `UNIQUE(owner_id)`?**
Đây là điểm cốt lõi của multi-tenant. Bỏ UNIQUE constraint cho phép 1 user tạo nhiều account client độc lập, mỗi account có lịch sử chi tiêu và thông tin công ty riêng.

**Tại sao gộp `freelancer_profiles` vào `user_profiles`?**
Hai bảng đều có quan hệ 1-1 với `users`. Tách ra chỉ tạo thêm JOIN không cần thiết mà không mang lại lợi ích gì ở giai đoạn này.

---

## Roadmap schema

- [ ] **Domain 4** — Contract & Milestone
- [ ] **Domain 5** — Payment & Escrow
- [ ] **Domain 6** — Messaging
- [ ] **Domain 7** — Review & Rating
- [ ] **Domain 8** — Dispute
- [ ] **Domain 9** — Notification
- [ ] **Domain 10** — Admin & Platform

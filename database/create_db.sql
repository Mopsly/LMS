CREATE TABLE "user"(
    "id" BIGINT NOT NULL,
    "nickname" CHAR(255) NOT NULL,
    "fullname" CHAR(255) NOT NULL,
    "email" CHAR(255) NOT NULL,
    "pic" bytea NOT NULL,
    "register_date" DATE NOT NULL,
    "edit_date" DATE NULL,
    "edit_author_id" BIGINT NULL,
    "delete_date" DATE NULL,
    "delete_author_id" BIGINT NULL,
    "admin" BOOLEAN NOT NULL
);
ALTER TABLE
    "user" ADD PRIMARY KEY("id");
CREATE TABLE "course"(
    "id" BIGINT NOT NULL,
    "author_id" BIGINT NOT NULL,
    "title" CHAR(255) NOT NULL,
    "description" CHAR(255) NOT NULL,
    "create_date" DATE NOT NULL,
    "complete_time" TIME(0) WITHOUT TIME ZONE NOT NULL,
    "edit_author_id" INTEGER NULL,
    "edit_date" DATE NULL,
    "delete_author_id" BIGINT NULL,
    "delete_date" DATE NULL,
    "rating" INTEGER NOT NULL
);
ALTER TABLE
    "course" ADD PRIMARY KEY("id");
CREATE TABLE "Module"(
    "id" INTEGER NOT NULL,
    "title" CHAR(255) NOT NULL,
    "course_id" BIGINT NOT NULL,
    "description" TEXT NOT NULL,
    "author_id" BIGINT NOT NULL,
    "create_date" DATE NOT NULL,
    "edit_date" DATE NULL,
    "edit_author_id" BIGINT NULL,
    "delete_date" DATE NULL,
    "delete_author_id" BIGINT NULL
);
ALTER TABLE
    "Module" ADD PRIMARY KEY("id");
CREATE TABLE "lesson"(
    "id" INTEGER NOT NULL,
    "module_id" INTEGER NOT NULL,
    "title" CHAR(255) NOT NULL,
    "description" CHAR(255) NOT NULL,
    "content_id" BIGINT NOT NULL,
    "create_date" DATE NOT NULL,
    "author_id" BIGINT NULL,
    "edit_date" DATE NULL,
    "edit_author_id" BIGINT NULL,
    "delete_date" DATE NULL,
    "delete_author_id" INTEGER NULL
);
ALTER TABLE
    "lesson" ADD PRIMARY KEY("id");
CREATE TABLE "courses_users"(
    "courses_id" BIGINT NOT NULL,
    "users_id" BIGINT NOT NULL
);
ALTER TABLE
    "courses_users" ADD PRIMARY KEY("courses_id");
CREATE TABLE "rating"(
    "id" BIGINT NOT NULL,
    "mark" INTEGER NOT NULL,
    "course_id" INTEGER NOT NULL
);
ALTER TABLE
    "rating" ADD PRIMARY KEY("id");
CREATE INDEX "rating_mark_index" ON
    "rating"("mark");
CREATE TABLE "content"(
    "id" INTEGER NOT NULL,
    "content" bytea NULL
);
ALTER TABLE
    "content" ADD PRIMARY KEY("id");
CREATE TABLE "rating_users"(
    "user_id" INTEGER NOT NULL,
    "course_id" INTEGER NOT NULL,
    "user_mark" INTEGER NOT NULL
);
ALTER TABLE
    "rating_users" ADD PRIMARY KEY("user_id");
ALTER TABLE
    "courses_users" ADD CONSTRAINT "courses_users_users_id_foreign" FOREIGN KEY("users_id") REFERENCES "user"("id");
ALTER TABLE
    "user" ADD CONSTRAINT "user_edit_author_id_foreign" FOREIGN KEY("edit_author_id") REFERENCES "user"("id");
ALTER TABLE
    "user" ADD CONSTRAINT "user_delete_author_id_foreign" FOREIGN KEY("delete_author_id") REFERENCES "user"("id");
ALTER TABLE
    "course" ADD CONSTRAINT "course_author_id_foreign" FOREIGN KEY("author_id") REFERENCES "user"("id");
ALTER TABLE
    "Module" ADD CONSTRAINT "module_course_id_foreign" FOREIGN KEY("course_id") REFERENCES "course"("id");
ALTER TABLE
    "Module" ADD CONSTRAINT "module_author_id_foreign" FOREIGN KEY("author_id") REFERENCES "user"("id");
ALTER TABLE
    "Module" ADD CONSTRAINT "module_edit_author_id_foreign" FOREIGN KEY("edit_author_id") REFERENCES "user"("id");
ALTER TABLE
    "Module" ADD CONSTRAINT "module_delete_author_id_foreign" FOREIGN KEY("delete_author_id") REFERENCES "user"("id");
ALTER TABLE
    "lesson" ADD CONSTRAINT "lesson_module_id_foreign" FOREIGN KEY("module_id") REFERENCES "Module"("id");
ALTER TABLE
    "lesson" ADD CONSTRAINT "lesson_author_id_foreign" FOREIGN KEY("author_id") REFERENCES "user"("id");
ALTER TABLE
    "lesson" ADD CONSTRAINT "lesson_edit_author_id_foreign" FOREIGN KEY("edit_author_id") REFERENCES "user"("id");
ALTER TABLE
    "lesson" ADD CONSTRAINT "lesson_delete_author_id_foreign" FOREIGN KEY("delete_author_id") REFERENCES "user"("id");
ALTER TABLE
    "rating" ADD CONSTRAINT "rating_course_id_foreign" FOREIGN KEY("course_id") REFERENCES "course"("id");
ALTER TABLE
    "lesson" ADD CONSTRAINT "lesson_content_id_foreign" FOREIGN KEY("content_id") REFERENCES "content"("id");
ALTER TABLE
    "rating_users" ADD CONSTRAINT "rating_users_course_id_foreign" FOREIGN KEY("course_id") REFERENCES "rating"("id");
ALTER TABLE
    "rating_users" ADD CONSTRAINT "rating_users_course_id_foreign" FOREIGN KEY("course_id") REFERENCES "course"("id");
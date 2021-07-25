//Получение всех пользователей записанных на курс х
SELECT * FROM user
WHERE course_id = "x"

//Получение всех кусров с названием 
SELECT * FROM course
WHERE title like "x"

//Получение всех курсов с рейтингом больше заданного
SELECT * FROM course
WHERE rating > 4


//Получение всех курсов с рейтингом больше заданного
//SELECT * FROM course
WHERE author_id = '1'
ORDER BY rating DESC

//Получение всего содержимого урока 
SELECT * FROM content 
WHERE lesson_id = 'x'

//Добавление нового пользователя
INSERT INTO users (id, nickname,fullname, email, admin)
VALUES ('1', 'Alex', 'Alecandr Ivanov','alex@mail.ru', 'false')

//Добавление нового урока
INSERT INTO lesson (id, module_id,title, description,author_id)
VALUES ('1', 'Lesson', '2','...', '4')

//Получение списка всех администраторов 
SELECT * FROM user
WHERE admin = 'true' 


//Получение количества пользователей на курсе
SELECT courses.id ,COUNT(user.username) FROM course
INNER JOIN courses_users ON courses_users.course_id=course.id
GROUP BY groups.id




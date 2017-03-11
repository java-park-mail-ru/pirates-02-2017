# Регистрация
curl -i -H "Content-Type: application/json" -X POST -d '{"login":"Sergey", "email":"email@mail.ru", "password":"password"}'  https://motion-project.herokuapp.com/user/
# Логин
curl -i -H "Content-Type: application/json" -X POST -d '{"login":"Sergey", "password":"password"}'  https://motion-project.herokuapp.com/sessions/login
# Пример проверки залогиненного пользователя
curl -i -X GET  -v --cookie "remember_token=bu3vgljhd8jevn1mmfbv7u14ku" https://motion-project.herokuapp.com/sessions/current

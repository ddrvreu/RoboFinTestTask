#### Customer REST service реализует следующие функции: ####
1. Создание клиента
2. Изменение фактического адреса клиента
3. Поиск клиента по имени и фамилии

Клиентов с одинаковыми полями (firstName,
lastName, middleName, customerSex) может быть несколько даже зарегистрированных под одним адрессом  
Адресс уникален по полям относящимся к конкретной локации (Country, region, city, etc)  

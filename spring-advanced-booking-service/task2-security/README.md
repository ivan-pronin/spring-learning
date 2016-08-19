[10:19:43 AM] Igor Seliverstov: В Spring MVC есть спец классы: WebInitializer и его производные для создания красивой точки входа (никаких DispatcherServlet и web.xml)
[10:20:01 AM] Igor Seliverstov: Деплоим все на Tomcat 8
FreeMarkerViewResolver  - очень рекомендую использовать Thymeleaf вместо него
Thymeleaf обладает большими возможностями и у него (что будет видно когда вы с секьюрностью будете работать) лучшая интеграция со спрингом - многое в нем работает прямо из коробки и не требует доп усилий

[10:30:16 AM] Igor Seliverstov: Ну и еще один момент, кто из вас слышал про Spring boot?))
[10:30:45 AM] Igor Seliverstov: так вот, хоть это и современно, его мы не используем
[10:30:53 AM] Igor Seliverstov: Пока))

 that will return result as PDF document - Советую познакомиться с JasperReports
 Для генерации PDF - быстро, удобно)) Лучше не использовать вью классы (тут многие допускают жуткие ошибки) а просто возвращать стрим со сгенерированым PDF из метода контроллера
 Доп задание - заставить браузер именно загрузить PDF по доп кнопочке не отображая его содержимое во вкладочке (при наличии плагина браузера для просмотра PDF)
 [10:38:02 AM] Igor Seliverstov: ну и не забываем про всякие accept хидеры и мапинги)) Это важно!
[10:38:06 AM] Igor Seliverstov: Вроде все)))


Task description:

1. Based on the codebase of previous hometasks, create a web application, configure Spring MVC application context and dispatcher servlet.

2. For all BookingFacde operations implement Spring MVC annotation-based controllers.

3. For operations that return one or several entites as a result (e.g. getUserByEmail, getUsersByName, getBookedTickets) implement simple views rendered via Freemarker template engine. Use FreeMarkerViewResolver for view resolving procedure.

4. For operations, that return list of booked tickets (by event, or by user) implement alternative controllers, that will return result as PDF document. Map this controller to a specific value of Accept request header  - Accept=application/pdf

5. Implement batch loading of users and events into system. In order to do this, create controller which accepts multipart file upload, parses it and calls BookingFacade methods to add events and users into the system. The format of the file (JSON, XML, ...) is up to you as long as you can implement the correct parsing procedure.

6. Implement generic exception handler which should redirect all controller exceptions to simple Freemarker view, that just prints exception message.
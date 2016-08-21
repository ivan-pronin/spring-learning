set tomcat_dir="c:\Program Files\apache-tomcat-8.5.4"
set bin_dir=%tomcat_dir%\bin
set app_dir=%tomcat_dir%\webapps\spring-adv-gradle
set war_dir="d:\GitRepos\epam-learning\spring-learning\spring-advanced-booking-service\task1-web-setup\output\libs"

call %bin_dir%\shutdown.bat
timeout /t 1 /nobreak

taskkill /f /im "java.exe"

rd /s /q %app_dir%
del %tomcat_dir%\webapps\spring-adv-gradle.war /S /Q

XCOPY %war_dir%\spring-adv-gradle.war  %tomcat_dir%\webapps\ /s /c
timeout /t 1 /nobreak
call %bin_dir%\startup.bat

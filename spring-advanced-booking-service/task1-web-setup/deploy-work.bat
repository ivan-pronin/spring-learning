set tomcat_dir=c:\Users\Ivan_Pronin\ProgramFiles\apache-tomcat-8.5.4
set bin_dir=%tomcat_dir%\bin
set app_dir=%tomcat_dir%\webapps\spring-adv-gradle
set war_dir="d:\Repos\Learning\spring-advanced-2016\output\libs"

call %bin_dir%\shutdown.bat
timeout /t 1 /nobreak
rd /s /q %app_dir%
del %tomcat_dir%\webapps\spring-adv-gradle.war /S /Q

XCOPY %war_dir%\spring-adv-gradle.war  %tomcat_dir%\webapps\ /s /c
timeout /t 1 /nobreak
call %bin_dir%\startup.bat

pause

set tomcat_dir="c:\Program Files\apache-tomcat-8.5.4"
set bin_dir=%tomcat_dir%\bin
set app_dir=%tomcat_dir%\webapps\spring-adv-gradle
set war_dir="d:\GitRepos\epam-learning\spring-advanced-2016\output\libs"

call %bin_dir%\shutdown.bat
rd /s /q %app_dir%
del %tomcat_dir%\webapps\spring-adv-gradle.war /S /Q

XCOPY %war_dir%\spring-adv-gradle.war  %tomcat_dir%\webapps\ /s /c
call %bin_dir%\startup.bat

pause
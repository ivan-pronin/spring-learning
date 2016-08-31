mkdir build\resources\event
mkdir build\resources\user
schemagen -d "build\resources\event" -cp build\classes\main src\main\java\com\epam\springadvanced\entity\Event.java
schemagen -d "build\resources\user" -cp build\classes\main src\main\java\com\epam\springadvanced\entity\User.java
pause
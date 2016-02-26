This directory contains three runnable jar files: LogFileProcessingSystem_s.jar, LogFileProcessingSystem_m.jar, LogFileProcessingSystem_m2.jar.

LogFileProcessingSystem_s.jar: 

This jar file is the single thread version of my solution, you can use the following command to run this jar file:

java -jar LogFileProcessingSystem_s.jar YourFileFolder

"YourFileFolder" is the your corresponding directory that contains your log files.


LogFileProcessingSystem_m.jar, LogFileProcessingSystem_m2.jar: 

These two jar files are the multithreading versions of my solution, you can use the following command to run this jar file:

java -jar LogFileProcessingSystem_m.jar YourFileFolder 8
java -jar LogFileProcessingSystem_m2.jar YourFileFolder 8

"YourFileFolder" is the your corresponding directory that contains your log files.
8 is the number of thread you want to use, you can set it to any number larger than 0.


ATTENTION:

1. The computer you use to run the file should installed Java Runtime Environment.
2. The name of your log files should follows the format "logtest.2014-07-11.log".
# Food-app

Przed wykonywaniem należy stworzyć użytkownika 'test' z hasłem 'testpass' w postgresql, czyli:
$sudo apt-get install postgresql
$sudo service postgresql start
$sudo su postgres
$psql
=#CREATE USER test PASSWORD 'testpass'
=#CREATE DATABASE test OWNER test;
=#alter user test superuser;

Po wykonywaniu tego, można jak zwykłe uruchomić aplikację. Korzystamy z library, którzy są skopiowane do folderu "libraries"

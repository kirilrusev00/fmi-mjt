# Мрежово програмиране

## Chat Server :speech_balloon:

Ще разработим чат клиент-сървър, със следната функционалност:

### Chat Client

- Всеки потребител има уникално име (*nick*), с което се индентифицира пред сървъра
- Потребителите могат да изпращат лични съобщение до друг активен (т.е. в момента свързан към сървъра) потребител, както и съобщения до всички активни потребители
- Потребителите могат да получат списък от имената на всички активни потребители
- Потребителите могат да напуснат чата по всяко време

### Chat Server

- Сървърът трябва да може да обслужва множество клиенти едновременно
- Сървърът получава команди от клиентите и връща подходящ резултат

### Описание на клиентските команди

```bash
- nick <username> - задава дадено потребителско име на текущия клиент
- send <username> <message> - изпраща лично съобщение до даден активен потребител
- send-all <message> - изпраща съобщение до всички активни потребители
- list-users – извежда списък с всички активни в момента потребители
- disconnect – потребителят напуска чата
```

**Подсказка:** Припомнете си различните имплементации на [Echo Server](https://github.com/fmi/java-course/tree/master/11-network/snippets), които разгледахме на лекцията. Можем ли да ги превърнем в Chat Server?

**Подсказка:** Решението на тази задача ще ви улесни изключително много при разработката на курсовите ви проекти, защото всички те представляват приложения тип клиент-сървър, като сървърът обслужва много потребители едновременно.

### Пример

```bash
# start a client from the command line
$ java bg.sofia.uni.fmi.mjt.chat.ChatClient
=> nick java-duke

=> send java-duke hi there
[04.01.2020 11:05] java-duke: hi there
[04.01.2020 11:06] adam: hallo # sent to java-duke from adam
# or
User [java-duke] seems to be offline

=>list-users
java-duke, adam

=>disconnect
Disconnected from server
```

Имате свобода да използвате съобщения и формати по ваш избор.

### Тестване

Голяма част от имплементацията може да се тества със стандартни JUnit средства, но всяка програма тип клиент-сървър ни навежда на мисълта да ползваме mocking, за да тестваме изолирано логиката на клиента и логиката на сървъра.

### Примерна структура на проекта

Добра практика при създаването на приложения тип клиент-сървър е да отделяте клиента и сървъра в отделни проекти. Това предотвратява грешки от типа, класове/интерфейси от клиента да се ползват от сървъра, или обратно. Също така, в реална ситуация, бихме искали да пакетираме и разпространяваме поотделно клиентската и сървърната част на нашето приложение.

В [sapera.org](http://grader.sapera.org/) качете *oбщ* `zip` архив на `src` и `test` директориите от *двата* проекта.

```bash
src
╷
├─ bg/sofia/uni/fmi/mjt/chat/
|  ├─ ChatClient.java
|  └─ ChatServer.java
test
├─ bg/sofia/uni/fmi/mjt/chat/
|  └─ ChatClientTest.java
|  └─ ChatServerTest.java
lib
├─ mockito-core-x.xx.x.jar
└─ ...
```

### Dependencies

```bash
# for testing
wget https://repo1.maven.org/maven2/org/mockito/mockito-core/3.1.0/mockito-core-3.1.0.jar
wget https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.9.10/byte-buddy-agent-1.9.10.jar
wget https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.9.10/byte-buddy-1.9.10.jar
wget https://repo1.maven.org/maven2/org/objenesis/objenesis/2.6/objenesis-2.6.jar
```

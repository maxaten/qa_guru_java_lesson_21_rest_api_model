# Демо проект по автоматизации тестирования сайта API на сайте [**reqres.in**](https://reqres.in/)

<p align="center">
<img title="reqres.in" src="images/logo/reqres.png">
</p>



---

## **Содержание**:

---

* <a href="#tools">Технологии и инструменты</a>

* <a href="#cases">Примеры автоматизированных тест-кейсов</a>

* <a href="#jenkins">Сборка в Jenkins</a>

* <a href="#console">Запуск из терминала</a>

* <a href="#allure">Allure отчет</a>

* <a href="#allure-testops">Интеграция с Allure TestOps</a>

* <a href="#jira">Интеграция с Jira</a>

* <a href="#telegram">Уведомление в Telegram при помощи бота</a>

---

<a id="tools"></a>

## <a name="Технологии и инструменты">**Технологии и инструменты:**</a>

<p align="center">  
<a href="https://www.jetbrains.com/idea/"><img src="images/logo/Intelij_IDEA.svg" title="Intellij_IDEA" width="50" height="50"  alt="IDEA"/></a>  
<a href="https://www.java.com/"><img src="images/logo/Java.svg"title="Java" width="50" height="50"  alt="Java"/></a>  
<a href="https://github.com/"><img src="images/logo/github-original.svg" title="Github" width="50" height="50"  alt="Github"/></a>  
<a href="https://junit.org/junit5/"><img src="images/logo/JUnit5.svg" title="JUnit5" width="50" height="50"  alt="JUnit 5"/></a>  
<a href="https://gradle.org/"><img src="images/logo/gradle-plain.svg" title="Gradle" width="50" height="50"  alt="Gradle"/></a>  
<a href="https://selenide.org/"><img src="images/logo/Selenide.svg" title="Selenide" width="50" height="50"  alt="Selenide"/></a>  
<a href="https://rest-assured.io/"><img src="images/logo/RestAssured.png" title="RestAssured" width="50" height="50"  alt="Selenide"/></a>  
<a href="https://allurereport.org/"><img src="images/logo/Allure.svg" title="Allure" width="50" height="50"  alt="Allure report"/></a> 
<a href="https://qameta.io/"><img src="images/logo/Allure2.svg" title="Allure TestOps" width="50" height="50" alt="Allure TestOps"/></a>   
<a href="https://www.jenkins.io/"><img src="images/logo/Jenkins.svg" title="Jenkins" width="50" height="50"  alt="Jenkins"/></a>  
<a href="https://www.atlassian.com/ru/software/jira/"><img src="images/logo/Jira.svg" title="Jira" width="50" height="50"  alt="Jira"/></a>
<a href="https://core.telegram.org/bots/api/"><img src="images/logo/telegram_logo.svg" title="Telegram" width="50" height="50"  alt="Jira"/></a> 
</p>

* Проект написан в среде разработки программного обеспечения <code>Intelij_IDEA</code>.
* В проекте автотесты написаны на языке <code>Java</code> с использованием фреймворка для тестирования <code>
  Selenide</code>.
* Использованы фреймворк <code>JUnit 5</code>.
* В качестве сборщика был использован - <code>Gradle</code>.
* Для тестирования Api была использована библиотека <code>RestAssured</code>.
* Для удаленного запуска реализована джоба в <code>Jenkins</code> с формированием отчета в <code>Allure</code> и
  отправкой результатов в <code>Telegram</code> c помощью бота.
* Осуществлена интеграция с <code>Allure TestOps</code> с <code>Jenkins</code>, при которой возможен запуск тестов
  из <code>Allure TestOps</code>.
* Осуществлена интеграция <code>Jira</code> с <code>Allure TestOps</code>, где рерультат прогона авто-тестов
  подтягивается в задачу в <code>Jira</code>

---
<a id="cases"></a>

## <a name="Примеры автоматизированных тест-кейсов">**Примеры автоматизированных тест-кейсов:**</a>

- *Получение информации по одному пользователю*
- *Успешная регистрация пользователя*
- *Неуспешная регистрация пользователя*
- *Успешная авторизация пользователя*
- *Неуспешная авторизация пользователя*
- *Успешное создание пользователя*
- *Получения списка пользователей*

---

<a id="console"></a>

## Команды для запуска из терминала

***Локальный запуск:***

```bash  
gradle clean test allureServe
```

---
<a id="jenkins"></a>

## <img alt="Jenkins" height="25" src="images/logo/Jenkins.svg" width="25"/></a><a name="Сборка"></a>Сборка в [Jenkins](https://jenkins.autotests.cloud/job/qa_guru_java_api_21_maxaten/)</a>

<p align="center">  
<a href="https://jenkins.autotests.cloud/job/Project%20qa_guru_java_lesson_17_mag_maxaten/"><img src="images/screen/jenkins_build.png" alt="Jenkins" width="1920"/></a>  
</p>


---
<a id="allure"></a>

## <img alt="Allure" height="25" src="images/logo/Allure.svg" width="25"/></a> <a name="Allure"></a>Отчет [Allure](https://jenkins.autotests.cloud/job/qa_guru_java_api_21_maxaten/allure/)</a>

### *Основная страница отчёта*

<p align="center">  
<img title="Allure Overview Dashboard" src="images/screen/allure_report.png" width="1920">  
</p>

### *Тест-кейсы*

<p align="center">  
<img title="Allure Tests" src="images/screen/testcase_in_allure.png" width="1920">  
</p>

### *Графики*

  <p align="center">  
<img title="Allure Graphics" src="images/screen/allure_graphs_1.png" width="1920">

<img title="Allure Graphics" src="images/screen/allure_graphs_2.png" width="1920">  
</p>

___
<a id="allure-testops"></a>

## <img alt="Allure" height="25" src="images/logo/Allure2.svg" width="25"/></a>Интеграция с [Allure TestOps](https://allure.autotests.cloud/project/3948/dashboards)</a>

### *Allure TestOps Dashboard*

<p align="center">  
<img title="Allure TestOps Dashboard" src="images/screen/testops_dashboard.png" width="1920">  
</p>  

### *Авто тест-кейсы*

<p align="center">  
<img title="Allure TestOps Tests" src="images/screen/testops_testcases.png" width="1919">  
</p>

___
<a id="jira"></a>

## <img alt="Allure" height="25" src="images/logo/Jira.svg" width="25"/></a> Интеграция с [Jira](https://jira.autotests.cloud/browse/HOMEWORK-1039)</a>

<p align="center">  
<img title="Jira" src="images/screen/jira.png" width="1920">  
</p>

___
<a id="telegram"></a>

## <img alt="Allure" height="25" src="images/logo/telegram_logo.svg" width="25"/></a> Уведомление в Telegram при помощи бота

<p align="center">  
<img title="Telegram Overview Dashboard" src="images/screen/telegram.png" width="452">  
</p>

# CBRTests
ИСПОЛЬЗОВАНЫ:
Java + Apache Maven + Selenium + Junit4 + Yandex Allure

Browser Google Chrome Version 77.0.3865.90 (Official Build) (64-bit)
WebDriver: chromedriver (файлы для ОС Windows/Linux находятся в папке проекта)

ИНСТРУКЦИЯ по использованию тестов:
1. Открыть консоль
2. Открыть папку проекта CBRTests
Выполнить следующие команды:

2.1. Прогнать тесты:
mvn clean test

Примечание: Результаты выполнения тестов склаыдваются в папку /CBRTests/target/allure-results

2.2. Получить и просмотреть отчет по тестам:
mvn allure:serve

Примечание: После этого должен сформироваться сам html–отчет, который автоматически откроется в браузере.


КЕЙСЫ:
1) Test01.testFilter
- Открыть сайт ДНС
- С помощью левого меню перейти по пути: Смартфоны - Смартфоны - Смартфоны 2019 года
- С помощью радиобаттона ограничить сумму
- С помощью фильтра установить "Проиводитель" = "Xiaomi"
- Нажать кнопку "Применить"
- С помощью фильтра установить "Объем встроенной памяти" = "64" и "128"
- Нажать кнопку "Применить"
- Удостовериться, что выборка соответствует заданным фильтрам

2) Test02.testPhoneComparison
- Открыть сайт ДНС
- С помощью левого меню перейти по пути: Смартфоны - Смартфоны - Смартфоны 2019 года
- С помощью радиобаттона ограничить сумму
- Добавить два телефона к сравнению
- Перейти в раздел "Сравнение"
- С помощью таба (переключателя) выбрать режим "Только различающиеся параметры"
- Удостовериться, что пересали отображаться одинаковые параметры

3) Test03.testPriceWithGauarantee
- Открыть сайт ДНС
- С помощью левого меню перейти по пути: Смартфоны - Смартфоны - Смартфоны 2019 года
- С помощью радиобаттона ограничить сумму
- С помощью фильтра установить "Проиводитель" = "Apple"
- Нажать кнопку "Показать"
- Перейти к одному из вариантов выборки
- С помощью выпадающего списка "Доп. Гарантия" выбрать дополнительную гарантию на 1 год
- Удостовериться, что цена на смартфон изменилась, вывести в консоль цену гарантии.

4) Test04.testPopupItemCount - Тест упадет, на сайте содержатся ошибки.
- Открыть сайт ДНС
- С помощью левого меню дойти до пункта меню: Смартфоны - Смартфоны - С большим аккумулятором
- Взять из пункта меню количество предполагаемых телефонов
- С помощью левого меню перейти пути Смартфоны - Смартфоны - С большим аккумулятором
- Вычилить количеств телефонов на всех страницах
- Сравнить полученные значения - количество телефонов из пункта меню и количество телефонов на страницах

5) Test05.testLisAndProductPagePrices
- Открыть сайт ДНС
- С помощью левого меню дойти до пункта меню: Смартфоны - Смартфоны - С большим аккумулятором
- С помоцщью фильтра установить "Акция" = "Товары по акции"
- Перейти к одному из вариантов выборки
- Проверить:
-     Цена по акции меньше чем предыдущая стоимость
-     Размер шрифта для старой цены меньше чем размер шрифта цены по акции
-     Толщина шрифта для старой цены меньше чем толщина шрифта цены по акции
- Открыть выбранный товар в новой вкладке браузера
- Переключиться на новую вкладку браузера
- Проверить:
-     Старые цены в списке продуктов и на странице продукта совпадают
      Цены по акции в списке продуктов и на странице продукта совпадают
-     Цена по акции меньше чем предыдущая стоимость
-     Размер шрифта для старой цены меньше чем размер шрифта цены по акции
-     Толщина шрифта для старой цены меньше чем толщина шрифта цены по акции
- Закрыть вкладку браузера
- Вернуться на вкладку со списком товаров

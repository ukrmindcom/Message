# Message Bot v2.0

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-8.13-blue.svg?logo=gradle)](https://gradle.org)

> **"Message"** — це комплексний програмний продукт, що розробляється як центральний інструмент для автоматизації процесів та збагачення користувацького досвіду в офіційній україномовній спільноті гри `Mindustry`.

Цей документ є вичерпним технічним завданням (ТЗ), що визначає повні функціональні та нефункціональні вимоги до розробки Discord-бота "Message".

---

## Ключові особливості

- **Система моніторингу та логування:** Надійний збір та структуроване представлення даних про всі події на сервері.
- **Функціональні утиліти:** Набір унікальних утиліт, тісно інтегрованих з `Mindustry`.
- **Інтелектуальний модератор:** Проактивний ШІ агент (Google Gemini) для автоматичного аналізу контенту, виявлення порушень та вживання відповідних заходів.
- **Україноцентричність:** Уся взаємодія з ботом здійснюється виключно українською мовою.
- **Контекстуальність:** Глибоке розуміння контексту повідомлень, каналів та історії користувачів.

---

## Зміст

<details>
<summary>Натисніть, щоб розгорнути зміст</summary>

- [**1. Загальні положення**](#1-загальні-положення)
  - [1.1. Мета документа](#11-мета-документа)
  - [1.2. Огляд проєкту](#12-огляд-проєкту)
  - [1.3. Цільова аудиторія](#13-цільова-аудиторія)
- [**2. Ключові концепції та архітектура**](#2-ключові-концепції-та-архітектура)
  - [2.1. Філософія бота](#21-філософія-бота)
  - [2.2. Архітектура системи](#22-архітектура-системи)
  - [2.3. Внутрішні модулі та сервіси](#23-внутрішні-модулі-та-сервіси)
- [**3. Функціональні вимоги: Система логування**](#3-функціональні-вимоги-система-логування)
  - [3.1. Технічні логи](#31-технічні-логи)
  - [3.2. Канали для Адміністрації та Модерації](#32-канали-для-адміністрації-та-модерації)
  - [3.3. Тематичні канали логів](#33-тематичні-канали-логів)
- [**4. Функціональні вимоги: Модуль AI-Модератор "Вартовий"**](#4-функціональні-вимоги-модуль-ai-модератор-вартовий)
  - [4.1. Джерела даних для аналізу](#41-джерела-даних-для-аналізу)
  - [4.2. Логіка аналізу та прийняття рішень](#42-логіка-аналізу-та-прийняття-рішень)
  - [4.3. Моделі поведінки та дій](#43-моделі-поведінки-та-дій)
  - [4.4. Механізм зворотного зв'язку](#44-механізм-зворотного-звязку)
- [**5. Функціональні вимоги: Детальний опис команд**](#5-функціональні-вимоги-детальний-опис-команд-та-їх-логіка)
  - [5.1. Система прав доступу](#51-система-прав-доступу-до-команд)
  - [5.2. Команди модерації](#52-команди-модерації)
  - [5.3. Команди для спільноти та утиліти](#53-команди-для-спільноти-та-утиліти)
  - [5.4. Команди, специфічні для `Mindustry`](#54-команди-специфічні-для-mindustry)
  - [5.5. Інструменти для Адміністрації](#55-інші-інструменти-команди-серверу)
- [**6. Візуальний та стилістичний гайд**](#6-візуальний-та-стилістичний-гайд)
- [**7. Нефункціональні вимоги**](#7-нефункціональні-вимоги)
- [**8. План розробки**](#8-план-розробки-версії-20)

</details>

---

## **1. Загальні положення**

### **1.1. Мета документа**
Цей документ є вичерпним технічним завданням (ТЗ), що визначає повні функціональні та нефункціональні вимоги до розробки Discord-бота "Message" (далі — Бот). Він слугує єдиним джерелом правди для розробників, тестувальників та адміністраторів проєкту на всіх етапах життєвого циклу продукту. Метою даного ТЗ є усунення будь-якої двозначності та необхідності звернення до додаткових обговорень, надаючи чіткі та деталізовані інструкції для реалізації.

### **1.2. Огляд проєкту**
"Message" — це комплексний програмний продукт, що розробляється як центральний інструмент для автоматизації процесів та збагачення користувацького досвіду в офіційній україномовній спільноті гри `Mindustry`. Бот є другою ітерацією проєкту і покликаний виконувати три ключові ролі:
1.  **Система моніторингу та логування:** Надійний збір та структуроване представлення даних про всі події на сервері.
2.  **Функціональні утиліти:** Набір унікальних утиліт, тісно інтегрованих з `Mindustry`.
3.  **Інтелектуальний модератор:** Проактивний ШІ агент, що використовує велику мультимодалну модель (Google Gemini) для автоматичного аналізу контенту, виявлення порушень та вживання відповідних заходів.

### **1.3. Цільова аудиторія**
-   **Адміністрація та Модерація:** Ключові користувачі, що отримують інструменти для управління спільнотою.
-   **Учасники спільноти:** Користувачі, що взаємодіють з утилітами бота.

---

## **2. Ключові концепції та архітектура**

### **2.1. Філософія бота**
Функціонування бота базується на чотирьох основних принципах, що визначають його поведінку та взаємодію з користувачами:

-   **Інтегрованість:** Бот не є стороннім інструментом. Його функціонал тісно переплетений з тематикою спільноти `Mindustry`, що реалізується через парсинг специфічних для гри файлів (`.msch`, `.msav`), надання доступу до ресурсів гри та симуляцію ігрових механік.
-   **Україноцентричність:** Уся текстова взаємодія з ботом, включаючи назви команд, описи, повідомлення та відповіді, здійснюється виключно українською мовою, що підкреслює ідентичність та створює комфортне середовище для цільової аудиторії.
-   **Проактивність:** Бот не обмежується пасивною реакцією на команди. Він активно моніторить сервер у фоновому режимі, виявляючи порушення правил без прямого втручання людини та, залежно від серйозності ситуації, самостійно застосовує заходи та/або інформує команду модераторів.
-   **Контекстуальність:** Кожне рішення, особливо прийняте ШІ-модулем, базується на глибокому розумінні контексту. Бот аналізує не лише саме повідомлення, а й попередню розмову, опис та призначення каналу, а також історію взаємодії з конкретним користувачем.

### **2.2. Архітектура системи**
Система бота є модульною, що забезпечує гнучкість розробки та легкість подальшого розширення. Вона складається з чотирьох основних логічних блоків:

-   **Система логування:** Модуль, що відповідає за перехоплення, обробку та збереження інформації про всі події на сервері у визначених форматах та сховищах.
-   **Інтеграції з API:** Набір класів та функцій для взаємодії із зовнішніми сервісами: Discord API (основна взаємодія), Google Gemini API (для ШІ-аналізу) та GitHub API (для специфічних команд `Mindustry`).
-   **Обробник команд:** Модуль, що відповідає за реєстрацію, парсинг та виконання слеш-команд, ініційованих користувачами.
-   **AI-Модератор:** Асинхронний фоновий процес, що отримує дані від системи логування, аналізує їх за допомогою ШІ та ініціює відповідні дії.

### **2.3. Внутрішні модулі та сервіси**
Для реалізації специфічного для `Mindustry` функціоналу бот містить два ключові внутрішні сервіси:

-   **Парсер контенту `Mindustry`:** Спеціалізований внутрішній модуль, відповідальний за роботу з файлами гри. Його функціональні обов'язки включають:
    1.  **Читання:** Парсинг файлів `.msch` (схеми) та `.msav` (мапи) та схем у текстовому форматі, що копіюється з буфера обміну гри.
    2.  **Витягнення метаданих:** Отримання назви, опису та розмірів та кількості контенту з файлу.
    3.  **Рендеринг:** Генерація статичного зображення-прев'ю на основі вмісту схеми або мапи.
    4.  **Запис:** Модифікація метаданих (назви, опису) безпосередньо у файлі та збереження оновленого файлу.

-   **Емулятор `Mindustry` логіки:** Внутрішній модуль, що реалізує спрощену копію вмісту для виконання коду логічних процесорів.
    1.  **Стан:** Емулятор завжди працює в "нульовому стані", тобто він не симулює стан ігрового світу, наявність ресурсів чи юнітів.
    2.  **Виконання:** Емулятор інтерпретує код, перевіряє його синтаксис та виконує базові інструкції (напр., `print`, `draw`).
    3.  **Результат:** Повертає текстовий вивід, згенероване зображення логічного дисплея або звіт про синтаксичні помилки.

---

## **3. Функціональні вимоги: Система логування**

### **3.1. Технічні логи**
-   **Призначення:** Створення вичерпного, надійного та машино читабельного журналу всіх без винятку подій на сервері. Цей журнал є єдиним джерелом правди для отримання інформації, довгострокової пам'яті ШІ та є крайнім інструментом для відладки.
-   **Канал:** `#технічний-лог`. Канал має бути приватним, доступним лише для бота та розробників.
-   **Формат запису:** `Logfmt`. Кожна подія записується як один рядок тексту, що містить пари `ключ=значення`.
    -   **Обов'язкові поля для кожного запису:**
        -   `ts`: Часова мітка у форматі `ISO 8601` (напр., `2025-05-21T18:00:00.123Z`).
        -   `event`: Назва події (напр., `message_created`, `message_deleted`, `user_joined`).
        -   `event_id`: Унікальний ідентифікатор події.
    -   **Обробка значень:** Всі текстові значення, що можуть містити пробіли, лапки або інші спеціальні символи, **обов'язково** беруться в подвійні лапки. Внутрішні подвійні лапки екрануються за допомогою `\` (напр., `content="Він сказав: \"Привіт!\""`).
-   **Події для логування:**
    -   **Стандартні повідомлення:**
        -   `message_created`: Поля: `author_id`, `channel_id`, `message_id`, `content`.
        -   `message_deleted`: Поля: `author_id`, `channel_id`, `message_id`, `cached_content`.
        -   `message_edited`: Поля: `author_id`, `channel_id`, `message_id`, `old_content`, `new_content`.
    -   **Спеціальні типи повідомлень:**
        -   `reply_created`: На додачу до полів `message_created`, містить `replied_to_message_id`.
        -   `thread_created_from_message`: Подія створення гілки з повідомлення. Поля: `author_id`, `channel_id`, `original_message_id`, `thread_id`, `thread_name`.
    -   **Логування пересланих повідомлень (Forwarded Messages):** Оскільки переслане повідомлення є просто Embed'ом, згенерованим клієнтом Discord, бот логує його як звичайне повідомлення, але в лог-записі `message_created` додається поле `is_forward: true` та, якщо можливо, витягується інформація про оригінального автора та сервер.
    -   **Логування голосувань (Polls):** При створенні нового голосування створюється лог `poll_created` з полями `author_id`, `question`, `options`. При завершенні голосування створюється лог `poll_ended` з фінальними результатами по кожній опції.
    -   **Взаємодії та компоненти:**
        -   `command_executed`: Поля: `user_id`, `command_name`, `options`.
        -   `button_clicked`: Поля: `user_id`, `message_id`, `custom_id`.
        -   `dropdown_option_selected`: Поля: `user_id`, `message_id`, `custom_id`, `selected_values`.
    -   **Учасники:** `user_joined`, `user_left`, `user_updated` (зміна нікнейму, ролей).
    -   **Голосові канали:** `voice_joined`, `voice_left`, `voice_state_updated`.
    -   **Внутрішні події:** `bot_started`, `api_error`, `ai_analysis_completed`.

### **3.2. Канали для Адміністрації та Модерації**
-   `#дії-модераторів`: Єдина стрічка всіх дій модерації (попередження, бани, м'юти, зняття покарань), виконаних як модераторами вручну, так і автоматично ШІ-модулем. Повідомлення форматуються у вигляді стандартизованих Embed'ів.
-   `#стан-бота`: Повідомлення про технічний стан бота: успішний старт, перезапуск, помилки в логіці, підключенні до зовнішніх API (Discord, Gemini, GitHub), сповіщення про досягнення лімітів API.
-   `#підозри-ші`: Детальні звіти від ШІ про неоднозначні потенційні порушення. Кожен звіт містить цитату підозрілого контенту, ID користувача, вердикт ШІ (`violated_rules`, `confidence`, `severity`, `reasoning`) та кнопки для швидких дій (`[Видати попередження], [Проігнорувати], [Помилкове спрацювання]`).
    -   **Деталізація логіки кнопки `[Помилкове спрацювання]`:**
        1.  При натисканні на кнопку модератором бот відкриває **модальне вікно (форму)**.
        2.  Форма містить одне велике текстове поле із заголовком "Будь ласка, коротко поясніть, чому рішення ШІ було невірним".
        3.  Після заповнення та надсилання форми, бот зберігає всю інформацію (початковий контент, вердикт ШІ, коментар модератора) у спеціальний лог-файл `feedback_for_finetuning.log`.
        4.  Після успішного збереження бот редагує оригінальне повідомлення зі звітом, видаляючи з нього кнопки і додаючи неактивну кнопку-примітку, напр., "✅ Позначено як помилкове спрацювання".

### **3.3. Тематичні канали логів**
-   `#логи-видалення`: Для кожного видаленого повідомлення створюється Embed, що містить автора, канал та повний текст повідомлення. Якщо були вкладення, бот намагається завантажити їх і прикріпити до логу.
-   `#логи-редакції`: Для кожного відредагованого повідомлення створюється Embed, що містить автора, канал, текст повідомлення "до" та "після", а також пряме посилання на відредаговане повідомлення.
-   `#логи-учасники`: Embed'и про приєднання/вихід учасників, зміну у їхніх профілях або ролях.
-   `#звіти-войс`: Фінальні звіти по завершенню голосових сесій. Звіт містить назву каналу, тривалість сесії, список учасників, короткий самарі розмови від ШІ та, у випадку виявлених порушень, прикріплені короткі аудіофайли-докази.

---

## **4. Функціональні вимоги: Модуль AI-Модератор "Вартовий"**

### **4.1. Джерела даних для аналізу**
-   **Текст:** Повний вміст кожного нового (`message_created`) та відредагованого (`message_edited`) повідомлення.
-   **Мультимедіа:**
    -   **Зображення/GIF/Аудіо/Відео:** Бот завантажує візуальний контент з повідомлень та передає його для аналізу.
    -   **Посилання:** Бот переходить за посиланнями, та якщо може аналізує вміст, інакше превіряє сам URL на наявність підозрілих доменів або ключових слів.
-   **Аудіопотік:**
    -   **Процес:** Бот підключається до голосового каналу при вході першого користувача. Він безперервно записує аудіо в короткий циклічний буфер у пам'яті. Кожні ~15 секунд (обирається відповідний момент, щоб не обрізати слова) цей буфер (або "шматок") відправляється на аналіз в API Gemini. Після отримання відповіді цей фрагмент видаляється з кешу.
    -   **Обробка тиші:** Якщо протягом 15 хвилин у каналі не фіксується аудіоактивність (розмова), бот тимчасово припиняє аналіз, залишаючись у каналі. Аналіз відновлюється з початком нової розмови.
-   **Профілі користувачів:**
    -   **Тригери:** Подія `user_joined` (новий учасник) та `user_updated` (зміна аватара, нікнейму, банера, займенників або опису "Про себе").
    -   **Процес:** Бот збирає всі доступні дані профілю (нікнейм, опис, зображення аватара та банера) і відправляє їх на комплексний аналіз.

### **4.2. Логіка аналізу та прийняття рішень**
1.  **Побудова запиту до Gemini:** Для кожної події бот формує деталізований запит, що містить:
    *   **Системні інструкції:** Системні інструкції для ШІ буде розроблено на етапі реалізації модуля. Вони будуть динамічними та адаптуватимуться до контексту задачі (наприклад, аналіз текстового повідомлення, перевірка профілю користувача, аналіз аудіопотоку). Загальна мета інструкції — надати ШІ чітку роль, "закони" поведінки, перелік правил для перевірки та точний формат відповіді (JSON).
    *   **Контент для аналізу:** Текст, зображення або інший мультимодальний контент.
    *   **Контекст:**
        *   Назва та опис каналу, де відбулася подія.
        *   Ролі користувача, що ініціював подію.
        *   Текст 5 попередніх повідомлень із цього ж каналу (короткострокова пам'ять).
        *   Результати пошуку по довгостроковій пам'яті (RAG).
2.  **Архітектура довгострокової пам'яті (RAG):**
    *   **Призначення:** Надання ШІ доступу до релевантної інформації з усієї історії сервера для глибшого розуміння контексту.
    *   **Архітектурне розділення:** Система складається з двох компонентів: `#технічний-лог` (постійне сховище даних) та окрема **векторна база даних** (швидкий індекс для пошуку). Вектори не зберігаються в технічному лозі.
    *   **Процес:** Перед запитом до Gemini бот шукає у векторній базі даних найбільш релевантні повідомлення з минулого і додає їхній текст до контексту.
    *   **Технології:** При виборі інструментів (векторної БД та моделі для ембедингів) перевага надається безплатний та відкритим (open-source) рішенням. Оновлення індексу відбувається періодично (раз на годину) або при накопиченні певної кількості нових даних (>500).
3.  **Структура відповіді від ШІ:** Бот очікує від Gemini `JSON` об'єкт визначеної структури. Якщо відповідь не відповідає формату, бот робить повторний запит з проханням виправити форматування.
4.  **Ескалація покарань:**
    *   **Принцип:** Система не застосовує автоматичне блокування за накопиченням попереджень, щоб уникнути помилкових банів.
    *   **Логіка:** Коли бот (або модератор) видає користувачеві попередження, система перевіряє загальну кількість активних попереджень. Якщо ця кількість досягає встановленого порогу (напр., 3), бот автоматично створює звіт з високим пріоритетом у каналі `#підозри-ші`, згадуючи роль `@Модератор` та надаючи рекомендацію розглянути більш серйозні заходи.

### **4.3. Моделі поведінки та дій**
-   **Автоматична дія:** Виконується, якщо `confidence_score > 90%` та `severity_score` перевищує поріг для конкретного правила (напр., для `r1` поріг може бути 70, а для `r8` — 85). Бот виконує дію (видалення повідомлення, видача попередження) і створює лог, ідентичний до дії модератора.
-   **Сповіщення:** Виконується, якщо `confidence_score > 70%` - недостатній для автоматичної дії. Бот надсилає детальний звіт у `#підозри-ші`.
-   **"М'яке" втручання:** Виконується, якщо `confidence_score > 90%`, але `severity_score < 30%`.
    *   **Логіка звернення:** Якщо порушення ініційовано 1–4 користувачами, бот звертається до них персонально (`@user1, @user2...`). Якщо порушників більше або їх неможливо точно ідентифікувати, бот використовує безособове звернення.
    *   **Кулдаун та ескалація:** Бот не може надсилати "м'яке" нагадування в одному каналі частіше, ніж раз на 10 хвилин. Якщо користувач, до якого було звернення, протягом 5 хвилин здійснює аналогічне порушення, система виконує відповідні каральні дії, або створює звіт у `#підозри-ші` з позначкою про ігнорування усного попередження.

### **4.4. Механізм зворотного зв'язку**
-   **Призначення:** Збір даних для майбутнього покращення (fine-tuning) ШІ-моделі розробником.
-   **Реалізація:** Повідомлення-звіти в `#підозри-ші` містять кнопку `[Помилкове спрацювання]`. При її натисканні модератор може залишити коментар у модальному вікні. Ці дані (початковий контент, вердикт ШІ, коментар модератора) зберігаються в окремий журнал і не впливають на роботу бота в реальному часі та будуть використані для коригування системних інструкцій в майбутньому.

---

## **5. Функціональні вимоги: Детальний опис команд та їх логіка**

### **5.1. Система прав доступу до команд**
-   **Архітектура:** Використовується гібридний підхід для забезпечення гнучкості та надійності:
    1.  **Права за замовчуванням:** Для кожної команди при її реєстрації в Discord API вказуються мінімально необхідні права Discord (наприклад, `Ban Members` для команди `/блок`). Це слугує як базовий фільтр.
    2.  **Керування на сервері (Якщо досі релевантне):** Адміністратори сервера мають можливість перевизначати ці налаштування за замовчуванням для конкретних ролей або користувачів через стандартний інтерфейс Discord (`Налаштування сервера -> Інтеграції -> Message`). Це є основним способом керування правами.
    3.  **Додаткова перевірка в коді:** Перед виконанням будь-якої критичної дії (наприклад, видача бану) бот додатково перевіряє, чи має користувач, що викликав команду, відповідну роль на сервері (наприклад, "Модератор" або "Адміністратор"). Ця перевірка слугує як додатковий рівень безпеки на випадок некоректної роботи або налаштування прав в UI Discord.
-   **Примітка для розробки:** Фінальна реалізація архітектури прав може бути скоригована на етапі розробки після детального тестування надійності UI Discord, з пріоритетом на максимальну стабільність та безпеку.

### **5.2. Команди модерації**
<details>
<summary><code>/попередити</code></summary>

- **Призначення:** Видача офіційного попередження учаснику (за порушення правил або з інших причин).
- **Аргументи:**
    - `користувач` (обов'язковий): Учасник або його ID.
    - `правила` (обов'язковий): Список порушених правил (напр., "r1, r2").
    - `причина` (необов'язковий): Додатковий текстовий коментар.
    - `посилання` (необов'язковий): Технічний аргумент який автоматично додається до логів як посилання на повідомлення-порушення, якщо попередження викликаається як відповідь на повідомлення.
- **Логіка виконання:**
    1.  Створює запис у базі даних, що містить ID користувача, ID модератора, список правил, причину, посилання та час.
    2.  Надсилає публічне Embed-повідомлення в канал виклику.
    3.  Надсилає детальний лог у канал `#дії-модераторів`.
</details>

<details>
<summary><code>/блок</code></summary>

- **Призначення:** Блокування учасника (тимчасове або перманентне).
- **Аргументи:**
    - `користувач` (обов'язковий): Учасник або його ID.
    - `час` (необов'язковий): Тривалість блокування (напр., `1d`, `6h`).
    - `правила` (обов'язковий): Список порушених правил (напр., "r1, r2").
    - `причина` (необов'язковий): Причина блокування.
- **Логіка виконання:**
    1.  Якщо аргумент `час` вказано, застосовує тимчасове блокування (м'ют) на вказаний термін.
    2.  Якщо `час` не вказано, виконує перманентний бан учасника на сервері.
    3.  У випадку перманентного бану, бот намагається надіслати приватне повідомлення заблокованому з причиною.
</details>

<details>
<summary><code>/зняти-попередження</code></summary>

- **Призначення:** Видалення раніше виданого попередження.
- **Аргументи:** `користувач` (обов'язковий).
- **Логіка виконання:**
    1.  Бот надсилає приватну відповідь з переліком усіх активних попереджень вказаного користувача.
    2.  У цій відповіді міститься випадаючий список (dropdown), що дозволяє модератору вибрати, яке саме попередження зняти.
    3.  Бот надсилає приватну відповідь з проханням підтвердити дію.
    4.  Відповідь містить кнопки `[Так, очистити історію]` та `[Скасувати]`.
    5.  Дія виконується лише після натискання на кнопку підтвердження.
    6.  Після вибору бот видаляє відповідний запис із бази даних (`#технічний-лог`) та логує дію.
</details>

<details>
<summary><code>/очистити-історію</code></summary>

- **Призначення:** Повне видалення всіх записів про попередження для учасника.
- **Аргументи:** `користувач` (обов'язковий).
- **Логіка виконання:**
    1.  Бот надсилає приватну відповідь з проханням підтвердити дію.
    2.  Відповідь містить кнопки `[Так, очистити історію]` та `[Скасувати]`.
    3.  Дія виконується лише після натискання на кнопку підтвердження.
    4. Після вибору бот видаляє відповідний запис із бази даних (`#технічний-лог`) та логує дію.
</details>

<details>
<summary><code>/історія-покарань</code></summary>

- **Призначення:** Перегляд історії покарань учасника.
- **Аргументи:** `користувач` (необов'язковий): Доступний лише модераторам.
- **Логіка виконання:**
    1.  Якщо викликається модератором із зазначенням користувача, показує історію цього користувача.
    2.  Якщо викликається без аргументів, показує користувачеві його власну історію (доступно всім користувачам).
    3.  Якщо кількість записів перевищує 5, відповідь автоматично форматується з пагінацією (кнопками для перегляду сторінок).
</details>

<details>
<summary><code>/очистити-чат</code></summary>

- **Призначення:** Видалення вказаної кількості останніх повідомлень у каналі.
- **Аргументи:** `кількість` (обов'язковий).
- **Логіка виконання:** Вимагає кроку підтвердження через кнопки, аналогічно до `/очистити-історію`.
</details>

### **5.3. Команди для спільноти та утиліти**
<details>
<summary><code>/інфо</code></summary>

- **Призначення:** Надання доступу до бази знань сервера.
- **Аргументи:** `тип` (обов'язковий, вибір: `ЧаПи`/`Правила`), `розділ_чапів` (необов'язковий, доступний при виборі "ЧаПи"), `публічно` (необов'язковий, Так/Ні, за замовчуванням Ні).
- **Логіка виконання:**
    1.  Якщо викликається лише з аргументом `тип`, бот надсилає приватне повідомлення з випадним списком доступних розділів (у випадку ЧаПів).
    2.  З аргументом `розділ_чапів` одразу показує вміст цього розділу.
    3.  Аргумент `публічно` контролює видимість відповіді.
</details>

<details>
<summary><code>/аватар</code></summary>

- **Призначення:** Показати аватар учасника у великому розмірі.
- **Аргументи:** `користувач` (обов'язковий).
- **Логіка виконання:** Повертає Embed-повідомлення, що містить ім'я користувача, його аватар як основне зображення, та пряме посилання для завантаження зображення у повній роздільній здатності.
</details>

<details>
<summary><code>/загуглити</code></summary>

- **Призначення:** Виконати пошук у Google.
- **Аргументи:** `запит` (обов'язковий).
- **Логіка виконання:** Виконує пошук із застосуванням фільтра "Безпечний пошук" (SafeSearch) і повертає один, найрелевантніший результат. Відповідь оформлюється у жартівливому стилі (https://lmgt.org).
</details>

<details>
<summary><code>/пінг</code></summary>

- **Призначення:** Перевірити статус ігрового сервера.
- **Аргументи:** `ip` (обов'язковий).
- **Логіка виконання:** Повертає детальний Embed зі статусом ігрового сервера (статус, пінг, кількість гравців, назва поточної мапи, режим гри) або детальну помилку (невірний формат IP, timeout, помилка протоколу).
</details>

### **5.4. Команди, специфічні для `Mindustry`**
<details>
<summary><code>/опублікувати</code></summary>

- **Призначення:** Єдиний дозволений спосіб публікації схем та мап у відповідних каналах-форумах.
- **Аргументи:** `файл` або `текст` (обов'язковий, один з двох).
- **Логіка:**
    1.  Бот парсить наданий файл/текст за допомогою внутрішнього парсера, визначає тип схема чи мапа і самостійно обирає відповідний форму, витягує назву та опис із наданого файлу/тексту.
    2.  Відкриває модальне вікно, де поля "Назва" та "Опис" вже заповнені. Користувач може їх відредагувати.
    3.  У цьому ж вікні міститься випадаючий список з тегами, доступними для відповідного форуму.
    4.  Якщо користувач змінює назву/опис, бот модифікує вихідний файл.
    5.  Створює новий пост у форумі, використовуючи дані з форми.
</details>

<details>
<summary><code>/верифікація-розробника</code></summary>

- **Призначення:** Автоматична верифікація розробників модів для отримання ролі.
- **Аргументи:** `репозиторій` (необов'язковий): посилання або рядок формату `user/repo`.
- **Логіка виконання:**
    1.  Без аргументу показує вимоги.
    2.  З аргументом виконує запит до API GitHub, перевіряючи репозиторій за критеріями: 10+ зірок, тег `mindustry-mod`, юзернейм Discord в описі.
    3.  У разі успіху видає роль та публічне повідомлення. У разі невдачі — надсилає приватний звіт із поясненням причин.
</details>

<details>
<summary><code>/випадковий</code></summary>

- **Призначення:** Показати випадкову схему або мапу зі спільноти.
- **Аргументи:** `тип` (обов'язковий, вибір: `Схема`/`Мапа`).
- **Логіка виконання:** Знаходить випадковий пост у відповідному форумі, завантажує та ре-парсить його файл, надсилаючи новий стандартний Embed з посиланням на оригінал.
</details>

<details>
<summary><code>/додати-локалізації</code></summary>

- **Призначення:** Допомогти розробникам модів додати файли локалізації.
- **Аргументи:** `файл.zip` (необов'язковий).
- **Логіка виконання:**
    1.  Без аргументу повертає архів з порожніми файлами локалізації у `bundles/`.
    2.  З аргументом — додає файли локалізації в архів користувача (знаходить і поміщає у `bundles/`) і повертає оновлений.
</details>

<details>
<summary><code>/знайти-ресурс</code></summary>

- **Призначення:** Знайти файл ігрового ресурсу.
- **Аргументи:** `запит` (обов'язковий).
- **Логіка виконання:** Виконує пошук по репозиторіях `Mindustry` та `arc` на GitHub, повертаючи до 5 релевантних посилань на файли.
</details>

<details>
<summary><code>/симуляція-коду</code></summary>

- **Призначення:** Виконати симуляцію логічного коду (логічний процесор).
- **Аргументи:** `код` (обов'язковий).
- **Логіка виконання:** Виконує код за допомогою внутрішнього емулятора в "нульовому стані". Повертає текстовий вивід (від інструкції `print`), згенероване зображення дисплея (від інструкцій `draw`) або звіт про синтаксичні помилки.
</details>

### **5.5. Інші інструменти команди серверу**
<details>
<summary><code>/опублікувати-новину</code></summary>

- **Призначення:** Створення кастомізованих повідомлень у каналі новин.
- **Логіка виконання:**
    1.  Бот надсилає приватне повідомлення з унікальним, тимчасовим посиланням на зовнішній вебредактор.
    2.  Адміністратор автентифікується через Discord (OAuth2).
    3.  Створює повідомлення у візуальному редакторі із живим попереднім переглядом.
    4.  Після збереження бот публікує результат у каналі новин.
- **MVP для редактора:** Можливість створити один Embed (заголовок, опис, колір), додати до нього поля (`name`/`value`), зображення, текст поза ембеддом та прикріплені файли.
</details>

<details>
<summary><code>/переіндексувати-пам'ять</code></summary>

- **Призначення:** Обслуговування системи довгострокової пам'яті ШІ.
- **Логіка виконання:** Запускає процес повного очищення векторної бази даних і її перебудови на основі даних з технічного логу. Команда доступна лише власнику бота.
</details>

---

## **6. Візуальний та стилістичний гайд**

-   **Загальний принцип:** Цей розділ слугує для визначення візуальної ідентичності та тону комунікації бота. Дотримання цих настанов є обов'язковим для забезпечення консистентного та професійного користувацького досвіду.
-   **Першоджерело стилю:** Єдиним та вичерпним джерелом правди для візуального стилю є набір концепт-вебхуків, наданих Замовником/Розробником. Розробка повинна суворо відтворювати формат, структуру, іконки, кольори та загальний вигляд Embed-повідомлень, представлених у цих концептах. Будь-які візуальні елементи, не визначені в наданих матеріалах, повинні бути розроблені в аналогічному стилі та додатково узгоджені.
-   **Тон голосу (Tone of Voice):** Бот повинен дотримуватися професійного тону комунікації, із можливими легкими та ледь помітним жартами/підколами.
    -   **Форма звернення:** У публічних повідомленнях та при взаємодії з користувачами бот завжди використовує форму звернення "ви".
    -   **Стиль мови:** Повідомлення повинні бути написані зрозумілою, живою українською мовою, без надмірного використання технічного жаргону.
    -   **Гумор:** Використання гумору обмежується лише тими командами, де це явно передбачено (наприклад, `/загуглити`, `дружні нагадування`). В усіх інших випадках бот зберігає нейтральний та діловий тон.

---

## **7. Нефункціональні вимоги**

-   **Продуктивність:**
    -   **Час відповіді:** Час відповіді на слеш-команди не повинен перевищувати стандартний ліміт Discord у 3 секунди. Для операцій, що вимагають більше часу, обов'язково використовується механізм відкладеної відповіді (`deferReply`).
    -   **Обробка навантаження:** Архітектура бота повинна бути розрахована на обробку пікових навантажень без значного погіршення продуктивності.
    -   **Управління лімітами API:** Бот повинен мати вбудовані механізми для відстеження та управління лімітами запитів до всіх зовнішніх API (Discord, Google Gemini, GitHub). При наближенні до ліміту (напр., 85%) бот повинен надсилати сповіщення у канал `#стан-бота` та, якщо можливо, тимчасово обмежувати функціонал, щоб уникнути блокування/ланцюга помилок.
-   **Надійність:**
    -   **Доступність:** Бот повинен працювати в режимі 24/7.
    -   **Відмовостійкість:** Необхідно налаштувати механізм автоматичного перезапуску процесу бота (напр., через `systemd`, `Docker` або інший процес-менеджер) у разі непередбаченого збою.
    -   **Обробка помилок:** Всі потенційні помилки (напр., недоступність API, невірні дані від користувача) повинні коректно оброблятися, не призводячи до падіння всього додатку. Користувач повинен отримувати зрозуміле повідомлення про помилку, а технічна інформація повинна логуватися у `#технічний-лог`.
-   **Безпека:**
    -   **Зберігання токенів:** Усі токени доступу (Discord, Gemini, GitHub) та інші чутливі дані повинні зберігатися виключно як змінні середовища (`environment variables`) і не повинні бути присутніми у вихідному коді або публічних репозиторіях.
    -   **Валідація вводу:** Усі дані, що надходять від користувача (аргументи команд, дані з форм), повинні проходити сувору валідацію, щоб унеможливити ін'єкції або інші види атак.
-   **Конфігурація:**
    -   **Конфігураційний файл:** Всі параметри, що можуть потребувати зміни без втручання в код, повинні бути винесені в окремий конфігураційний файл (напр., `config.yml` або `.env`).
    -   **Перелік параметрів для конфігурації:**
        -   **Токени:** токени для Discord, Gemini, GitHub.
        -   **ID Каналів:** ID для `#технічний-лог`, `#дії-модераторів`, `#стан-бота`, `#підозри-ші` та всіх інших.
        -   **ID Ролей:** ID основних ролей ("Розробник Модифікацій", "Модератор", "Адміністратор").
        -   **Параметри ШІ:** Поріг упевненості, поріг серйозності, кулдауни для "м'яких" нагадувань.
        -   **Налаштування функцій:** Кількість зірок для верифікації, шляхи до репозиторіїв `Mindustry` та `arc`.
        -   **Список каналів, що ігноруються ШІ.**

---

## **8. План розробки версії 2.0**

Розробка ведеться поетапно, за визначеними віхами (milestones).

<details>
<summary><strong>Віха 1: Основа та інфраструктура</strong></summary>

- **Задачі:**
    - [x] Створення структури проєкту, налаштування середовища розробки.
    - [x] Підключення до Discord API, реалізація базового клієнта, що може реагувати на події.
    - [x] Створення обробника слеш-команд, що дозволяє реєструвати та виконувати прості команди.
    - [ ] Реалізація повної системи логування: створення та запис у `#технічний-лог` у форматі `logfmt`, а також у всі тематичні та адміністративні канали (`#логи-видалення`, `#дії-модераторів` і т.д.).
    - [x] Створення механізму завантаження конфігурації з файлу.
- **Критерії завершення:** Бот успішно запускається, логує усі стандартні Discord події у відповідні канали, відповідає на одну тестову команду.
</details>

<details>
<summary><strong>Віха 2: Модуль модерації</strong></summary>

- **Задачі:**
    - [ ] Реалізація повного функціоналу всіх команд з розділу 5.2.
    - [ ] Налаштування системи видачі ролей та прав доступу до команд.
    - [ ] Налаштування інтерактивних елементів (кнопки підтвердження, випадні списки, пагінація).
- **Критерії завершення:** Усі команди модерації працюють згідно з ТЗ, права доступу коректно обробляються, усі нові події коректно логуються у `#технічний-лог`.
</details>

<details>
<summary><strong>Віха 3: Модулі спільноти та `Mindustry`</strong></summary>

- **Задачі:**
    - [ ] Реалізація всіх команд з розділів 5.3 та 5.4.
    - [ ] Інтеграція з API GitHub для команд `/верифікація-розробника` та `/знайти-ресурс`.
    - [ ] Розробка внутрішніх модулів: Парсер контенту `Mindustry` та Емулятор логіки `Mindustry`.
- **Критерії завершення:** Усі команди працюють згідно з ТЗ, взаємодія з GitHub API стабільна.
- **Статус MVP:** По завершенню цієї віхи бот вважається готовим до запуску (Minimum Viable Product). Наступні віхи є розширенням базового функціоналу.
</details>

<details>
<summary><strong>Віха 4: Інтеграція AI-Модератора</strong></summary>

- **Задачі:**
    - [ ] Підключення до API Google Gemini.
    - [ ] Реалізація фонового аналізу тексту та зображень.
    - [ ] Впровадження логіки прийняття рішень.
- **Критерії завершення:** Бот автоматично реагує на порушення в текстових і голосових каналах та повідомляє про потенційні порушення.
</details>

<details>
<summary><strong>Віха 5: MVP вебредактора новин</strong></summary>

- **Задачі:**
    - [ ] Розгортання базової інфраструктури для вебдодатку (Frontend/Backend).
    - [ ] Реалізація системи автентифікації через Discord OAuth2.
    - [ ] Створення інтерфейсу редактора з функціоналом, визначеним в MVP (створення одного Embed'а, додавання полів, зображення, простого тексту та прикріплених файлів).
    - [ ] Налаштування взаємодії між вебдодатком та ботом для публікації повідомлень.
- **Критерії завершення:** Користувач із відповідними правами може створити та опублікувати новину через вебінтерфейс згідно з функціоналом MVP.
</details>

<details>
<summary><strong>Віха 6: Тестування та впровадження</strong></summary>

- **Задачі:**
    - [] Проведення комплексного тестування всіх модулів та їх взаємодії.
    - [ ] Виправлення виявлених помилок та оптимізація продуктивності.
    - [ ] Підготовка інструкцій для адміністраторів щодо налаштування та використання бота.
    - [ ] Фінальне розгортання бота на робочому сервері.
- **Критерії завершення:** Продукт розгорнуто, стабільно працює та відповідає всім вимогам, зазначеним у цьому ТЗ.
</details>
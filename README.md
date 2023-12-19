<div align="center">
<h1 align="center">
<br>TELEGRAMBOTTREE</h1>
<h3>◦ Developed with the software and tools below.</h3>

<p align="center">
<a href="https://www.java.com"><img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=flat-square&logo=openjdk&logoColor=white" alt="java" /></a>
<a href="https://www.postgresql.org/"><img src="https://img.shields.io/badge/PostgreSQL-%23336791.svg?style=flat-square&logo=postgresql&logoColor=white" alt="postgresql" /></a>
<a href="https://spring.io"><img src="https://img.shields.io/badge/spring-%23ED8B0.svg?style=flat-square&logo=spring&logoColor=white" alt="spring" /></a>
<a href="https://hibernate.org/"><img src="https://img.shields.io/badge/hibernate-%23AF5E64.svg?style=flat-square&logo=hibernate&logoColor=white" alt="hibernate" /></a>
<a href="https://spring.io/projects/spring-data-jpa"><img src="https://img.shields.io/badge/spring-data_jpa-%23ED8B0.svg?style=flat-square&logo=spring&logoColor=white" alt="spring data jpa" /></a>
<a href="https://mapstruct.org/"><img src="https://img.shields.io/badge/MapStruct-%23F3508B.svg?style=flat-square&logo=mapstruct&logoColor=white" alt="mapstruct" /></a>
<a href="https://projectlombok.org/"><img src="https://img.shields.io/badge/Lombok-%230A0A0A.svg?style=flat-square&logo=lombok&logoColor=white" alt="lombok" /></a>
</p>

<img src="https://img.shields.io/github/license/NTurbo1/telegramBotTree?style=flat-square&color=5D6D7E" alt="GitHub license" />
<img src="https://img.shields.io/github/last-commit/NTurbo1/telegramBotTree?style=flat-square&color=5D6D7E" alt="git-last-commit" />
<img src="https://img.shields.io/github/commit-activity/m/NTurbo1/telegramBotTree?style=flat-square&color=5D6D7E" alt="GitHub commit activity" />
<img src="https://img.shields.io/github/languages/top/NTurbo1/telegramBotTree?style=flat-square&color=5D6D7E" alt="GitHub top language" />
</div>

---

## 📖 Table of Contents
- [📖 Table of Contents](#-table-of-contents)
- [📍 Overview](#-overview)
- [📦 Features](#-features)
- [⚙️ Modules](#modules)
- [🚀 Getting Started](#-getting-started)
    - [🔧 Installation](#-installation)
    - [🤖 Running telegramBotTree](#-running-telegramBotTree)
    - [🧪 Tests](#-tests)
---


## 📍 Overview

A simple telegram bot that maintains a tree structure of categories. A user can add elements, remove them, view the tree structure download an excel file with the tree structure and upload their own excel files with elements' structure data so that they can be saved.

---

## 📦 Features

- **MVC Design Pattern**
- **Command Design Pattern**
- **Spring Boot Testing**
- **SOLID**
- **Code documentation**
- **Exception handling**
---


## ⚙️ Modules

<details closed><summary>Root</summary>

| File                                                                      | Summary       |
| ---                                                                       | ---           |
| [mvnw.cmd](https://github.com/NTurbo1/telegramBotTree/blob/main/mvnw.cmd) | ► INSERT-TEXT |
| [mvnw](https://github.com/NTurbo1/telegramBotTree/blob/main/mvnw)         | ► INSERT-TEXT |

</details>

<details closed><summary>Telegrambot</summary>

| File                                                                                                                                                            | Summary       |
| ---                                                                                                                                                             | ---           |
| [TelegramBotApplicationTests.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/test/java/com/nturbo1/telegramBot/TelegramBotApplicationTests.java) | ► INSERT-TEXT |
| [TelegramBotApplication.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/TelegramBotApplication.java)           | ► INSERT-TEXT |

</details>

<details closed><summary>Service</summary>

| File                                                                                                                                                    | Summary       |
| ---                                                                                                                                                     | ---           |
| [CategoryServiceTest.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/test/java/com/nturbo1/telegramBot/service/CategoryServiceTest.java) | ► INSERT-TEXT |
| [CategoryService.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/service/CategoryService.java)         | ► INSERT-TEXT |

</details>

<details closed><summary>Utils</summary>

| File                                                                                                                                                  | Summary       |
| ---                                                                                                                                                   | ---           |
| [ExcelComparator.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/test/java/com/nturbo1/telegramBot/service/utils/ExcelComparator.java) | ► INSERT-TEXT |

</details>

<details closed><summary>Telebot</summary>

| File                                                                                                                                    | Summary       |
| ---                                                                                                                                     | ---           |
| [TelebotTest.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/test/java/com/nturbo1/telegramBot/telebot/TelebotTest.java) | ► INSERT-TEXT |
| [Telebot.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/telebot/Telebot.java)         | ► INSERT-TEXT |

</details>

<details closed><summary>Repository</summary>

| File                                                                                                                                                             | Summary       |
| ---                                                                                                                                                              | ---           |
| [CategoryRepositoryTest.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/test/java/com/nturbo1/telegramBot/repository/CategoryRepositoryTest.java) | ► INSERT-TEXT |
| [CategoryRepository.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/repository/CategoryRepository.java)         | ► INSERT-TEXT |

</details>

<details closed><summary>Validation</summary>

| File                                                                                                                                                                      | Summary       |
| ---                                                                                                                                                                       | ---           |
| [CategoryValidator.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/service/validation/CategoryValidator.java)            | ► INSERT-TEXT |
| [RootCategoryValidator.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/repository/validation/RootCategoryValidator.java) | ► INSERT-TEXT |

</details>

<details closed><summary>Exceptions</summary>

| File                                                                                                                                                                                             | Summary       |
| ---                                                                                                                                                                                              | ---           |
| [NonRootElementHasNoParentException.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/service/exceptions/NonRootElementHasNoParentException.java) | ► INSERT-TEXT |
| [ElementAlreadyExistsException.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/service/exceptions/ElementAlreadyExistsException.java)           | ► INSERT-TEXT |
| [RootHasParentException.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/service/exceptions/RootHasParentException.java)                         | ► INSERT-TEXT |
| [InvalidDataType.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/service/exceptions/InvalidDataType.java)                                       | ► INSERT-TEXT |
| [ParentNotFoundException.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/service/exceptions/ParentNotFoundException.java)                       | ► INSERT-TEXT |
| [ElementNotFoundException.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/service/exceptions/ElementNotFoundException.java)                     | ► INSERT-TEXT |

</details>

<details closed><summary>Dto</summary>

| File                                                                                                                                        | Summary       |
| ---                                                                                                                                         | ---           |
| [CategoryDto.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/service/dto/CategoryDto.java) | ► INSERT-TEXT |

</details>

<details closed><summary>Mapper</summary>

| File                                                                                                                                                 | Summary       |
| ---                                                                                                                                                  | ---           |
| [CategoryMapper.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/service/mapper/CategoryMapper.java) | ► INSERT-TEXT |

</details>

<details closed><summary>Util</summary>

| File                                                                                                                                           | Summary       |
| ---                                                                                                                                            | ---           |
| [CommandNames.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/telebot/util/CommandNames.java) | ► INSERT-TEXT |

</details>

<details closed><summary>Commands</summary>

| File                                                                                                                                                               | Summary       |
| ---                                                                                                                                                                | ---           |
| [HelpCommand.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/telebot/commands/HelpCommand.java)                   | ► INSERT-TEXT |
| [RemoveElementCommand.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/telebot/commands/RemoveElementCommand.java) | ► INSERT-TEXT |
| [AddElementCommand.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/telebot/commands/AddElementCommand.java)       | ► INSERT-TEXT |
| [UploadCommand.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/telebot/commands/UploadCommand.java)               | ► INSERT-TEXT |
| [DownloadCommand.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/telebot/commands/DownloadCommand.java)           | ► INSERT-TEXT |
| [ViewTreeCommand.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/telebot/commands/ViewTreeCommand.java)           | ► INSERT-TEXT |

</details>

<details closed><summary>Interfaces</summary>

| File                                                                                                                                                | Summary       |
| ---                                                                                                                                                 | ---           |
| [Command.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/telebot/commands/interfaces/Command.java) | ► INSERT-TEXT |

</details>

<details closed><summary>Annotations</summary>

| File                                                                                                                                                               | Summary       |
| ---                                                                                                                                                                | ---           |
| [ValidRootCategory.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/repository/annotations/ValidRootCategory.java) | ► INSERT-TEXT |

</details>

<details closed><summary>Entity</summary>

| File                                                                                                                                        | Summary       |
| ---                                                                                                                                         | ---           |
| [Category.java](https://github.com/NTurbo1/telegramBotTree/blob/main/src/main/java/com/nturbo1/telegramBot/repository/Entity/Category.java) | ► INSERT-TEXT |

</details>

---

## 🚀 Getting Started

### 🔧 Installation

1. Clone the telegramBotTree repository:
```sh
git clone https://github.com/NTurbo1/telegramBotTree
```

2. Change to the project directory:
```sh
cd telegramBotTree
```

3. Install the dependencies:
```sh
mvn clean install
```

### 🤖 Running telegramBotTree

```sh
java -jar target/myapp.jar
```

### 🧪 Tests
```sh
mvn test
```

---
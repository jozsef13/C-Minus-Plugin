# C-Minus Language Support Plugin
An IntelliJ IDEA plugin that acts like an editor support for a custom language named C-Minus. The language first appeared in the book "Compiler Construction Principles and Practice" written by Kenneth C. Louden. The plugin is written in Java, and I used IntelliJ Platform's Language API, as well as GrammarKit plugin, Gradle and JFlex. The grammar is a BNF context-free grammar.

## C-Minus Language
The language that I chose to build the editor plugin for is called C-, also known as C-Minus, which is a language introduced in the book “Complier Construction Principles and Practice” written by Kenneth C. Louden, where is presented as “A more extensive language than TINY, suitable for a compiler project.”. It is a restricted subset of C, which contains integers, integers array and functions (also void functions), local and global declarations and recursive functions, if and while-statements, but for which I added: string variables and values, cin and cout for reading and writing, constants and Boolean variables and values. I decided to add the new features because I wanted it to be more complex and also to be a subset of C++, not only C.

## Plugin Features
* Registered File Type
* Lexer
* Parser and PSI
* Syntax and Error Highlighting
* References and Resolve
* Code Completion
* Find Usages
* Rename Refactoring
* Code Formatter
* Structure View
* Go To Symbol
* Additional minor features: Brace Matching, Code Folding, To Do View, Quote Handler

## Project Structure
The project follows the standard Gradle directory layout strcture:

```
C-Minus-Plugin
|- build.gradle
|- gradle
|	|- wrapper
|		|- gradle-wrapper.jar
|		|- gradle-wrapper.properties
|- src
|	|- main
|	|	|- java
|	|	|- resources
|	|		|- META-INF
|	|			|- plugin.xml
|	|- test
|		|- java
|		|- resources
|- gradlew
|- gradlew.bat
|- settings.gradle
```

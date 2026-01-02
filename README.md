# CENG 211 - Programming Fundamentals

This repository contains homework assignments for the **CENG 211** course.  

---

## 📂 Structure
<pre>
Ceng_211_Homeworks/
├── HW01/
│   ├── Files/
│   │   ├── games.csv
│   │   ├── gamers.csv
│   │   └── CENG211_Fall2025_HW1.pdf
│   ├── EsportManagementApp.java 
│   ├── FileIO.java
│   ├── Game.java
│   ├── Gamer.java
│   ├── Match.java
│   ├── MatchManagement.java
│   ├── PointsBoard.java
│   └── Query.java
│
├── HW02/
│   ├── Files/
│   │   ├── ScholarshipApplications.csv
│   │   └── CENG211_Fall2025_HW2.pdf
│   ├── App.java
│   ├── Application.java
│   ├── FileIO.java
│   ├── MeritApplication.java
│   ├── NeedApplication.java
│   ├── Publication.java
│   └── ResearchApplication.java
│
├── HW03/
│   ├── Files/
│   │   └── CENG211_Fall2025_HW3.pdf
│   └── slidingpuzzle/
│       ├── Direction.java
│       ├── EmperorPenguin.java
│       ├── Food.java
│       ├── GameObject.java
│       ├── Hazard.java
│       ├── HeavyIceBlock.java
│       ├── HoleInIce.java
│       ├── IcyTerrain.java
│       ├── ITerrainObject.java
│       ├── KingPenguin.java
│       ├── LightIceBlock.java
│       ├── Penguin.java
│       ├── RockhopperPenguin.java
│       ├── RoyalPenguin.java
│       ├── SeaLion.java
│       └── SlidingPuzzleApp.java
├── HW04/
│   ├── Files/
│   │   └── CENG211_Fall2025_HW4.pdf
│   └── src/
│       └── main/
│           ├── boxes/
│           │   ├── Box.java
│           │   ├── FixedBox.java
│           │   ├── RegularBox.java
│           │   └── UnchangingBox.java
│           ├── core/
│           │   ├── BoxGrid.java
│           │   └── BoxPuzzle.java
│           ├── exceptions/
│           │   ├── BoxAlreadyFixedException.java
│           │   ├── EmptyBoxException.java
│           │   └── UnmovableFixedBoxException.java
│           ├── tools/
│           │   ├── BoxFixer.java
│           │   ├── BoxFlipper.java
│           │   ├── MassColumnStamp.java
│           │   ├── MassRowStamp.java
│           │   ├── PlusShapeStamp.java
│           │   └── SpecialTool.java
│           ├── BoxPuzzleApp.java
│           └── Direction.java
</pre>

---

## HW01 – The E-Sports Tournament Challenge
In this homework, a simple **tournament management system** is implemented in Java.  
The program reads data from CSV files (`games.csv` and `gamers.csv`) and simulates matches for multiple players.  
It calculates points, assigns medals, and prints several query results such as the highest-scoring match or medal distribution.

Topics covered:
- Classes, constructors, getters, setters  
- CSV file I/O  
- 1-D and 2-D arrays  
- Basic object-oriented programming principles  

---

## HW02 – Scholarship Evaluation System
In this homework, a **Scholarship Evaluation System** is implemented in Java.  
The system automatically evaluates multiple scholarship applications based on academic performance, financial need, or research output.  
All applications are read from a single CSV file (`ScholarshipApplications.csv`), which contains mixed records from different scholarship types.

This assignment focuses on **inheritance**, **polymorphism**, and **dynamic data structures** such as ArrayLists.  
It emphasizes building an extendable architecture capable of handling new application types with minimal code changes.

Topics Covered:
- Inheritance and polymorphism  
- CSV file I/O using standard Java I/O libraries  
- ArrayLists for flexible data storage  
- Class hierarchies and abstract classes  
- Data encapsulation and code reusability

---

## HW03 – Sliding Penguins Puzzle Game App (BROKEN)
In this homework, a **Sliding Penguins Puzzle Game** is implemented in Java using strict Object-Oriented principles.  
The application simulates a 10x10 icy grid where penguins slide to collect food while avoiding hazards like ice blocks, holes, and sea lions.  
Each penguin type (King, Emperor, Royal, Rockhopper) has unique movement abilities and competes to gather the highest weight of food within 4 turns.

This assignment focuses heavily on **advanced OOP concepts** such as **Interfaces**, **Abstract Classes**, and **Enumerations** to manage the interaction between different terrain objects (Hazards, Food, Penguins) .

Topics Covered:
- **Interfaces** (`ITerrainObject`) for unifying grid entities
- **Abstract Classes** and **Inheritance** for code reuse across different Penguins and Hazards
- **Polymorphism** for handling unique movement behaviors
- **Enumerations** for types and directions
- **Lists and ArrayLists** for managing dynamic game objects

---

## HW04 – Box Top Side Matching Puzzle App
In this homework, a **Box Top Side Matching Puzzle** game is implemented in Java.  
The application simulates an 8x8 grid containing different types of boxes (Regular, Fixed, Unchanging). The player's goal is to maximize the occurrences of a specific "target letter" on the top sides of the boxes within 5 turns.  
The game involves complex mechanics such as rolling boxes with a **domino effect**, opening boxes to find special tools, and using these tools to manipulate the grid.

This assignment focuses on advanced Java features, particularly **Generics**, **Exception Handling**, and **Inner Classes**, while reinforcing polymorphism through a tool-usage system.

Topics Covered:
- **Generics** and **Generic Methods** for type-safe tool handling  
- **Custom Exception Handling** (`UnmovableFixedBoxException`, etc.) to manage game rules  
- **Abstract Classes** and **Polymorphism** (Box and SpecialTool hierarchies)  
- **Inner Classes** for encapsulating menu and UI logic  
- **Complex Game Logic** (Recursive-like propagation for box rolling)
---
More assignments will be added as the semester continues.

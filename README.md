🏫 School Management System
A sleek, desktop-based Student Information System built with Java Swing and AWT. This project demonstrates modern GUI design principles within a classic Java environment, featuring custom UI components, smooth animations, and an intuitive user experience.

JavaSwingIDE

📋 Description
The School Management System is a lightweight application designed to manage student records efficiently. Unlike standard Swing applications, this project focuses on UI/UX enhancement by implementing custom-painted components, such as gradient buttons with hover effects and draggable interactive icons. It serves as an excellent foundation for understanding event handling, custom graphics, and layout management in Java.

✨ Features
🎨 Modern UI Design:
Gradient Animated Buttons: Custom-painted buttons that transition colors smoothly on hover.
Scalable Background: Intelligent background image handling that adapts to window resizing.
Visual Depth: Draggable icons featuring dynamic shadow effects for a tactile feel.
👨‍🎓 Student Management:
Add Student: A clean popup modal to input student details (Name, ID, Grade, etc.).
View Students: A dedicated dialog displaying a scrollable list of all enrolled students.
💾 Data Handling:
Uses ArrayList for temporary, in-memory storage (fast and efficient for prototyping).
🛠️ Technologies Used
Technology	Purpose
Java	Core programming language
Java Swing	Main GUI framework (Windows, Frames, Dialogs)
Java AWT	Event handling and graphics primitives
Graphics2D	Custom rendering for gradients and shadows
ArrayList	Data structure for object storage
📂 Project Structure
School-Management-System/│├── src/│   └── com/│       └── sms/│           ├── Main.java              # Entry point│           ├── ui/│           │   ├── MainFrame.java     # Primary application window│           │   ├── CustomButton.java  # Gradient button logic│           │   └── DraggableIcon.java # Interactive icon component│           ├── dialogs/│           │   ├── AddStudentDialog.java│           │   └── ViewStudentsDialog.java│           └── model/│               └── Student.java       # Data model│├── images/│   └── background.jpg                 # Background assets│└── README.md
🚀 How to Run
Prerequisites
JDK 8 or higher installed on your machine.
An IDE (IntelliJ IDEA, Eclipse, or NetBeans) or a text editor.
Steps
Clone the repository:
bash

git clone https://github.com/your-username/School-Management-System.git
Open the project:
Open your IDE and import the project as a Maven/Gradle project (if applicable) or a standard Java project.
Locate the Entry Point:
Navigate to src/com/sms/Main.java.
Run:
Right-click Main.java and select Run.
Alternatively, compile and run via terminal:
bash

javac src/com/sms/*.java
java -cp src com.sms.Main
📸 Screenshots
Note: Screenshots demonstrating the gradient buttons, draggable icon, and student dialogs will be added here.

Main Dashboard
Add Student Dialog
Main Screen	Add Dialog

🧠 Concepts Covered
This project applies several core Computer Science and Software Engineering concepts:

Object-Oriented Programming (OOP): Encapsulation, Inheritance (extending Swing components), and Polymorphism.
Event-Driven Programming: Handling ActionListener, MouseListener, and MouseMotionListener for interactivity.
Custom Graphics: Overriding paintComponent(Graphics g) to create non-standard UI elements (gradients, rounded corners).
Layout Management: Using BorderLayout, FlowLayout, and GridBagLayout for responsive design.
Collections Framework: Utilizing ArrayList for dynamic data storage and retrieval.

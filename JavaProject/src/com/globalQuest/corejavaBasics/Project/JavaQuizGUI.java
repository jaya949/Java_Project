package com.globalQuest.corejavaBasics.Project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JavaQuizGUI extends JFrame {

    public JavaQuizGUI() {

        setTitle("Java Quiz Application");
        setSize(500, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel(
                "JAVA QUIZ APPLICATION",
                JLabel.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));

        JButton startQuiz = new JButton("Start Quiz");
        JButton addQuestion = new JButton("1. Add New Question");
        JButton viewAll = new JButton("2. View All Questions");
        JButton viewById = new JButton("3. View Question By ID");
        JButton updateQuestion = new JButton("4. Update Question");
        JButton deleteQuestion = new JButton("5. Delete Question");
        
        JButton exit = new JButton("6. Exit");

        panel.add(startQuiz);
        panel.add(addQuestion);
        panel.add(viewAll);
        panel.add(viewById);
        panel.add(updateQuestion);
        panel.add(deleteQuestion);
        panel.add(exit);

        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        );

        add(panel, BorderLayout.CENTER);

        startQuiz.addActionListener(e -> JavaQuiz.startQuiz());
        
        addQuestion.addActionListener(e -> {

            JTextField questionField = new JTextField();
            JTextField option1Field = new JTextField();
            JTextField option2Field = new JTextField();
            JTextField option3Field = new JTextField();
            JTextField option4Field = new JTextField();

            String[] answers = {"1", "2", "3", "4"};

            JComboBox<String> correctAnswerBox =
                    new JComboBox<String>(answers);

            Object[] fields = {
                    "Enter Question:", questionField,
                    "Option 1:", option1Field,
                    "Option 2:", option2Field,
                    "Option 3:", option3Field,
                    "Option 4:", option4Field,
                    "Correct Answer:", correctAnswerBox
            };

            int result = JOptionPane.showConfirmDialog(
                    this, fields, "Add New Question",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {

                String question = questionField.getText();
                String op1 = option1Field.getText();
                String op2 = option2Field.getText();
                String op3 = option3Field.getText();
                String op4 = option4Field.getText();

                if (question.isEmpty() || op1.isEmpty() ||
                        op2.isEmpty() || op3.isEmpty() || op4.isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Please fill all fields!");

                } else {

                    int correctAnswer = Integer.parseInt(
                            correctAnswerBox.getSelectedItem().toString()
                    );

                    JavaQuestion.addQuestion(
                            question, op1, op2, op3, op4, correctAnswer
                    );

                    JOptionPane.showMessageDialog(this, "Question added successfully!");
                }
            }
        });

        viewAll.addActionListener(e -> {
        	
        	
        	    ArrayList<String[]> questions = JavaQuestion.viewAllQuestions();

        	    if (questions.isEmpty()) {

        	        JOptionPane.showMessageDialog(
        	                this, "No questions found!"
        	        );

        	        return;
        	    }

        	    JTextArea area = new JTextArea(20, 40);

        	    area.setEditable(false);

        	    for (String[] q : questions) {

        	        area.append("Question ID: " + q[0] + "\n");
        	        area.append("Question: " + q[1] + "\n");
        	        area.append("1. " + q[2] + "\n");
        	        area.append("2. " + q[3] + "\n");
        	        area.append("3. " + q[4] + "\n");
        	        area.append("4. " + q[5] + "\n");
        	        area.append("Correct Answer: " + q[6] + "\n");
        	        area.append("--------------------------------\n");
        	    }

        	    JScrollPane scrollPane = new JScrollPane(area);

        	    JOptionPane.showMessageDialog(
        	            this,
        	            scrollPane,
        	            "All Questions",
        	            JOptionPane.INFORMATION_MESSAGE
        	    );
        	});
        viewById.addActionListener(e -> {

            String input = JOptionPane.showInputDialog(
                    this, "Enter Question ID:"
            );

            if (input == null || input.trim().isEmpty()) {
                return;
            }

            try {

                int id = Integer.parseInt(input);

                String[] q = JavaQuestion.viewQuestionByID(id);

                if (q == null) {

                    JOptionPane.showMessageDialog(this, "Question not found!");

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Question ID: " + q[0] +
                            "\nQuestion: " + q[1] +
                            "\n1. " + q[2] +
                            "\n2. " + q[3] +
                            "\n3. " + q[4] +
                            "\n4. " + q[5] +
                            "\nCorrect Answer: " + q[6]
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this, "Please enter a valid ID!");
            }
        });
        
        updateQuestion.addActionListener(e -> {

            String input = JOptionPane.showInputDialog(
                    this, "Enter Question ID to Update:"
            );

            if (input == null || input.trim().isEmpty()) {
                return;
            }

            try {

                int id = Integer.parseInt(input);

                String[] q = JavaQuestion.viewQuestionByID(id);

                if (q == null) {

                    JOptionPane.showMessageDialog(this, "Question not found!");

                } else {

                    JTextField questionField = new JTextField(q[1]);
                    JTextField option1Field = new JTextField(q[2]);
                    JTextField option2Field = new JTextField(q[3]);
                    JTextField option3Field = new JTextField(q[4]);
                    JTextField option4Field = new JTextField(q[5]);

                    String[] answers = {"1", "2", "3", "4"};

                    JComboBox<String> correctAnswerBox =
                            new JComboBox<String>(answers);

                    correctAnswerBox.setSelectedItem(q[6]);

                    Object[] fields = {
                            "Question:", questionField,
                            "Option 1:", option1Field,
                            "Option 2:", option2Field,
                            "Option 3:", option3Field,
                            "Option 4:", option4Field,
                            "Correct Answer:", correctAnswerBox
                    };

                    int result = JOptionPane.showConfirmDialog(
                            this, fields, "Update Question",
                            JOptionPane.OK_CANCEL_OPTION
                    );

                    if (result == JOptionPane.OK_OPTION) {

                        JavaQuestion.updateQuestion(
                                id,
                                questionField.getText(),
                                option1Field.getText(),
                                option2Field.getText(),
                                option3Field.getText(),
                                option4Field.getText(),
                                Integer.parseInt(
                                        correctAnswerBox.getSelectedItem().toString()
                                )
                        );

                        JOptionPane.showMessageDialog(
                                this, "Question updated successfully!"
                        );
                    }
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this, "Please enter a valid ID!");
            }
        });
        
        deleteQuestion.addActionListener(e -> {

            String input = JOptionPane.showInputDialog(
                    this, "Enter Question ID to Delete:"
            );

            if (input == null || input.trim().isEmpty()) {
                return;
            }

            try {

                int id = Integer.parseInt(input);

                String[] q = JavaQuestion.viewQuestionByID(id);

                if (q == null) {

                    JOptionPane.showMessageDialog(this, "Question not found!");

                } else {

                    int result = JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete this question?\n\n"
                            + q[1],
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (result == JOptionPane.YES_OPTION) {

                        JavaQuestion.deleteQuestion(id);

                        JOptionPane.showMessageDialog(
                                this, "Question deleted successfully!"
                        );
                    }
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this, "Please enter a valid ID!");
            }
        });
        
        exit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
    

    public static void main(String[] args) {
        new JavaQuizGUI();
        
    }
}
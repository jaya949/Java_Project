package com.globalQuest.corejavaBasics.Project;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class JavaQuiz extends JFrame {

    ArrayList<String[]> questions;

    int questionNumber = 0;
    int reward = 0;
    boolean lifelineUsed = false;

    int[] rewards = {
        1000, 2000, 3000, 4000, 5000,
        6000, 7000, 8000, 9000, 10000
    };

    JLabel questionLabel;
    JLabel rewardLabel;

    JRadioButton option1;
    JRadioButton option2;
    JRadioButton option3;
    JRadioButton option4;

    ButtonGroup group;

    JButton submitButton;
    JButton lifelineButton;

    public JavaQuiz() {

        setTitle("Java Quiz Application");
        setSize(650, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        questionLabel = new JLabel();
        questionLabel.setBounds(30, 30, 580, 60);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel);

        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();

        option1.setBounds(50, 120, 550, 30);
        option2.setBounds(50, 165, 550, 30);
        option3.setBounds(50, 210, 550, 30);
        option4.setBounds(50, 255, 550, 30);

        group = new ButtonGroup();

        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);

        add(option1);
        add(option2);
        add(option3);
        add(option4);

        rewardLabel = new JLabel("Reward: Rs.0");
        rewardLabel.setBounds(30, 310, 250, 30);
        rewardLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(rewardLabel);

        lifelineButton = new JButton("Use Lifeline");
        lifelineButton.setBounds(50, 370, 150, 35);
        add(lifelineButton);

        submitButton = new JButton("Submit Answer");
        submitButton.setBounds(250, 370, 160, 35);
        add(submitButton);

        submitButton.addActionListener(e -> checkAnswer());

        lifelineButton.addActionListener(e -> useLifeline());

        questions = JavaQuestion.viewAllQuestions();

        if (questions.size() == 0) {
            JOptionPane.showMessageDialog(this, "No questions found!");
            dispose();
        } else {
            displayQuestion();
            setVisible(true);
        }
    }

    public void displayQuestion() {

        if (questionNumber >= questions.size()) {
            finishQuiz();
            return;
        }

        String[] q = questions.get(questionNumber);

        questionLabel.setText(
            "Question " + (questionNumber + 1) + ": " + q[1]
        );

        option1.setText("1. " + q[2]);
        option2.setText("2. " + q[3]);
        option3.setText("3. " + q[4]);
        option4.setText("4. " + q[5]);

        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);
        option4.setVisible(true);

        group.clearSelection();

        rewardLabel.setText("Reward: Rs." + reward);

        lifelineButton.setEnabled(
            !lifelineUsed && questionNumber < 7
        );
    }

    public void checkAnswer() {

        int answer = 0;

        if (option1.isSelected()) answer = 1;
        else if (option2.isSelected()) answer = 2;
        else if (option3.isSelected()) answer = 3;
        else if (option4.isSelected()) answer = 4;

        if (answer == 0) {
            JOptionPane.showMessageDialog(this, "Please select an answer!");
            return;
        }

        String[] q = questions.get(questionNumber);

        int correctAnswer = Integer.parseInt(q[6]);

        if (answer == correctAnswer) {

            reward = rewards[questionNumber];

            JOptionPane.showMessageDialog(
                this, "Correct Answer!\nYou won Rs." + reward
            );

            questionNumber++;
            displayQuestion();

        } else {

            if (questionNumber < 4) {
                reward = 0;
            }

            JOptionPane.showMessageDialog(
                this,
                "Wrong Answer!\nCorrect Answer: " +
                q[correctAnswer + 1] +
                "\nYour Reward: Rs." + reward
            );

            finishQuiz();
        }
    }

    public void useLifeline() {

        String[] choices = {
            "Audience Poll",
            "50:50"
        };

        int choice = JOptionPane.showOptionDialog(
            this,
            "Choose your Lifeline",
            "Lifeline",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            choices,
            choices[0]
        );

        if (choice == 0) {

            String[] q = questions.get(questionNumber);

            int correct = Integer.parseInt(q[6]);

            int[] poll = new int[4];

            int correctPercentage = 40 + (int)(Math.random() * 31);
            int remaining = 100 - correctPercentage;

            int first = (int)(Math.random() * (remaining + 1));
            int second = (int)(Math.random() * (remaining - first + 1));
            int third = remaining - first - second;

            int count = 0;

            for (int i = 0; i < 4; i++) {

                if (i == correct - 1) {
                    poll[i] = correctPercentage;
                } else {
                    if (count == 0) poll[i] = first;
                    else if (count == 1) poll[i] = second;
                    else poll[i] = third;

                    count++;
                }
            }

            JOptionPane.showMessageDialog(
                this,
                "Audience Poll:\n" +
                "Option 1: " + poll[0] + "%\n" +
                "Option 2: " + poll[1] + "%\n" +
                "Option 3: " + poll[2] + "%\n" +
                "Option 4: " + poll[3] + "%"
            );

            lifelineUsed = true;

        } else if (choice == 1) {

            String[] q = questions.get(questionNumber);

            int correct = Integer.parseInt(q[6]);

            int removed1 = 0;
            int removed2 = 0;

            while (removed1 == 0 || removed1 == correct) {
                removed1 = 1 + (int)(Math.random() * 4);
            }

            while (removed2 == 0 || removed2 == correct
                    || removed2 == removed1) {
                removed2 = 1 + (int)(Math.random() * 4);
            }

            if (removed1 == 1 || removed2 == 1) option1.setVisible(false);
            if (removed1 == 2 || removed2 == 2) option2.setVisible(false);
            if (removed1 == 3 || removed2 == 3) option3.setVisible(false);
            if (removed1 == 4 || removed2 == 4) option4.setVisible(false);

            lifelineUsed = true;
        }

        lifelineButton.setEnabled(false);
    }

    public void finishQuiz() {

        JOptionPane.showMessageDialog(
            this,
            "QUIZ COMPLETED!\nYour Total Reward: Rs." + reward
        );

        dispose();
    }

    public static void startQuiz() {
        new JavaQuiz();
    }
}
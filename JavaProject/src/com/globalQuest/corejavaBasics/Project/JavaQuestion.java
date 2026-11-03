package com.globalQuest.corejavaBasics.Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JavaQuestion {

    public static ArrayList<String[]> viewAllQuestions() {

        ArrayList<String[]> questionList = new ArrayList<String[]>();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    JavaQuizCredentials.url,
                    JavaQuizCredentials.user,
                    JavaQuizCredentials.pwd
            );

            String query = "SELECT * FROM questions ORDER BY question_id";

            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                String[] question = new String[7];

                question[0] = rs.getString("question_id");
                question[1] = rs.getString("question_text");
                question[2] = rs.getString("option1");
                question[3] = rs.getString("option2");
                question[4] = rs.getString("option3");
                question[5] = rs.getString("option4");
                question[6] = rs.getString("correct_answer");

                questionList.add(question);
            }

            rs.close();
            pstmt.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return questionList;
    }
    public static void addQuestion(String question, String option1,
            String option2, String option3, String option4, int correctAnswer) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    JavaQuizCredentials.url,
                    JavaQuizCredentials.user,
                    JavaQuizCredentials.pwd
            );

            String query = "INSERT INTO questions "
                    + "(question_text, option1, option2, option3, option4, correct_answer) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, question);
            pstmt.setString(2, option1);
            pstmt.setString(3, option2);
            pstmt.setString(4, option3);
            pstmt.setString(5, option4);
            pstmt.setInt(6, correctAnswer);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                System.out.println("Question added successfully");
            }

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String[] viewQuestionByID(int id) {

        String[] question = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    JavaQuizCredentials.url,
                    JavaQuizCredentials.user,
                    JavaQuizCredentials.pwd
            );

            String query = "SELECT * FROM questions WHERE question_id = ?";

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                question = new String[7];

                question[0] = rs.getString("question_id");
                question[1] = rs.getString("question_text");
                question[2] = rs.getString("option1");
                question[3] = rs.getString("option2");
                question[4] = rs.getString("option3");
                question[5] = rs.getString("option4");
                question[6] = rs.getString("correct_answer");
            }

            rs.close();
            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return question;
    }
    public static void updateQuestion(int id, String question,
            String option1, String option2, String option3,
            String option4, int correctAnswer) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    JavaQuizCredentials.url,
                    JavaQuizCredentials.user,
                    JavaQuizCredentials.pwd
            );

            String query = "UPDATE questions SET question_text = ?, "
                    + "option1 = ?, option2 = ?, option3 = ?, "
                    + "option4 = ?, correct_answer = ? "
                    + "WHERE question_id = ?";

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, question);
            pstmt.setString(2, option1);
            pstmt.setString(3, option2);
            pstmt.setString(4, option3);
            pstmt.setString(5, option4);
            pstmt.setInt(6, correctAnswer);
            pstmt.setInt(7, id);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                System.out.println("Question updated successfully");
            } else {
                System.out.println("Question ID not found");
            }

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteQuestion(int id) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    JavaQuizCredentials.url,
                    JavaQuizCredentials.user,
                    JavaQuizCredentials.pwd
            );

            String query = "DELETE FROM questions WHERE question_id = ?";

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Question deleted successfully!");
            } else {
                System.out.println("Question ID not found!");
            }

            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
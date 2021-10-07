package com.codebind;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main_menu {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton AddProdButton;
    private JButton searchProductButton;
    private JButton DeleteProductButton;
    private JTextField textFieldAdd;
    private JTextField textFieldDelete;
    private JTextField textFieldSearch;
    private JButton LinkCreateButton;
    private JButton LinkDeleteButton;
    private JTextField OriginCreate;
    private JTextField OriginExplore;
    private JTextField DestinationExplore;
    private JTextField RecommendationSearch;
    private JButton RecommendationButton;
    private JTextArea ResponsePathExplore;
    private JButton searchPathButtonExplore;
    private JTextField DestinationCreate;
    private JTextField DestinationDelete;
    private JLabel Productresponse;
    private JTextField OriginDelete;
    private JPanel LinkResponse;
    private JLabel ResponseLink;
    private JLabel ResponsePath;
    private JLabel ResponseRecommendation;

    public main_menu() {

        AddProdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productNum = textFieldAdd.getText();
                if(!productNum.isEmpty()){
                    String response = CommandDB.CreateProduct(productNum);
                    if(response=="Creation failed"){
                        Productresponse.setText("Creation failed");
                    }
                    else{
                        Productresponse.setText("Creation of product : "+ productNum + " with id : " + response);
                    }
                }
                textFieldAdd.setText("");
            }
        });
        DeleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productNum = textFieldDelete.getText();
                if(!productNum.isEmpty()){
                    String response = CommandDB.DeleteProduct(productNum);
                    if(response=="Delete failed"){
                        Productresponse.setText("Deletion failed");
                    }
                    else{
                        Productresponse.setText(response);
                    }
                }
                textFieldDelete.setText("");
            }
        });
        searchProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productNum = textFieldSearch.getText();
                if(!productNum.isEmpty()){
                    String response = CommandDB.NumberToRID(productNum);
                    if(response=="Object not found"){
                        Productresponse.setText("Product :"+productNum+" not found");
                    }
                    else{
                        Productresponse.setText("Product with number :" + productNum +" has id :"+response);
                    }
                }
                textFieldSearch.setText("");
            }
        });
        LinkCreateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origin = OriginCreate.getText();
                String destination =DestinationCreate.getText();
                if(!origin.isEmpty() && !destination.isEmpty()){
                    String response = CommandDB.CreateLink(origin,destination);
                    ResponseLink.setText(response+" between" + origin +" and "+ destination);
                    if(response!="Link creation failed"){
                        ResponseLink.setText(response);
                    }
                }
                OriginCreate.setText("");
                DestinationCreate.setText("");
            }
        });
        LinkDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origin = OriginDelete.getText();
                String destination = DestinationDelete.getText();
                if(!origin.isEmpty() && !destination.isEmpty()){
                    String response = CommandDB.DeleteLink(origin,destination);
                    ResponseLink.setText(response+" between" + origin +" and "+ destination);
                    if(response!="Link deletion failed"){
                        ResponseLink.setText(response);
                    }
                }
                OriginDelete.setText("");
                DestinationDelete.setText("");
            }
        });
        searchPathButtonExplore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origin = OriginExplore.getText();
                String destination = DestinationExplore.getText();
                if(!origin.isEmpty() && !destination.isEmpty()){
                    String[] response = CommandDB.ShortestPath(origin,destination);
                    String result = "";
                    if(response.length==1){
                        ResponsePath.setText("No edge between :" + origin + " and " + destination);
                    }
                    else{
                        for(int i=0; i< response.length;i++){
                            result += response[i] + " ";
                        }
                        ResponsePath.setText(result);
                    }
                }
            }
        });
        RecommendationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = RecommendationSearch.getText();
                if(!number.isEmpty()){
                    String[] response = CommandDB.OutRecommendation(number);
                    String result = "";
                    if(response[0].isEmpty()){
                        ResponseRecommendation.setText("No recommendation for :" + number);
                    }
                    else{
                        for(int i=0; i< response.length;i++){
                            result += response[i] + " ";
                        }
                        ResponseRecommendation.setText(result);
                    }
                }
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("main_menu");
        frame.setContentPane(new main_menu().tabbedPane1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

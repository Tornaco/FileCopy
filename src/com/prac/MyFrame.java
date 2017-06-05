package com.prac;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created @2017/6/3 17:51
 */
public class MyFrame {

    String path, dest;

    public void show() {
        JFrame frame = new JFrame("File copy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(true);

        JTextArea textArea = new JTextArea();

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBorderPainted(true);
        progressBar.setMaximum(100);
        progressBar.setMinimum(0);
        progressBar.setValue(0);

        JButton chooseBtn = new JButton("CHOOSE FILE");
        chooseBtn.setSize(100, 30);
        chooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyLogger.log("chooseBtn clicked.");

                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.showDialog(new JLabel(), "选择");
                File file = jfc.getSelectedFile();
                MyLogger.log(file);
                textArea.append("CHOOSE FILE: " + file.getPath() + "\n");
                path = file.getPath();
            }
        });


        JButton chooseDest = new JButton("CHOOSE DEST");
        chooseDest.setSize(100, 30);
        chooseDest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyLogger.log("chooseDest clicked.");

                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.showDialog(new JLabel(), "选择");
                File file = jfc.getSelectedFile();
                MyLogger.log(file);
                textArea.append("CHOOSE DEST: " + file.getPath() + "\n");
                dest = file.getPath();
            }
        });

        JButton okBtn = new JButton("OK");
        okBtn.setSize(100, 30);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = new File(path).getName();
                String newDest = dest + File.separator + name;
                FileUtil.copyAsync(path, newDest, new ProgressAdapter() {
                    @Override
                    public void onProgressUpdate(int progress) {
                        super.onProgressUpdate(progress);
                        MyLogger.log("onProgressUpdate %d", progress);
                        progressBar.setValue(progress);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        textArea.append("Done\n");
                    }
                });
            }
        });


        frame.add(chooseBtn, BorderLayout.WEST);
        frame.add(chooseDest, BorderLayout.EAST);
        frame.add(okBtn, BorderLayout.SOUTH);
        frame.add(textArea, BorderLayout.CENTER);


        frame.add(progressBar, BorderLayout.NORTH);


        frame.setVisible(true);
    }
}

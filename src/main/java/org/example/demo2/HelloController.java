package org.example.demo2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static org.example.demo2.VulnerabilityValidator.sendMaliciousRequestFuzz;
import static org.example.demo2.VulnerabilityValidator.sendMaliciousRequestWindows;

public class HelloController {


    public Text VulnPath1;
    public TextArea outputTextArea;

    @FXML
    private TextField Simpleurl; // 第一个 TextField

    @FXML
    private ChoiceBox<String> choiceBox;

   // 用来显示"批量目标"或其他信息

    @FXML
    private void handleFileChooser() {
        // 创建文件选择器
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("文本文件", "*.txt"));  // 设置过滤器仅显示 txt 文件

        // 获取 Stage 对象
        Stage stage = (Stage) VulnPath1.getScene().getWindow();

        // 显示文件选择对话框
        File selectedFile = fileChooser.showOpenDialog(stage);

        // 如果选中了文件，更新 Text 控件中的文本
        if (selectedFile != null) {
            VulnPath1.setText("\n"+"\n"+selectedFile.getAbsolutePath());  // 显示选中文件的路径
            // 你可以在此处进行后续操作，如验证文件内容等
        } else {
            VulnPath1.setText("未选择文件");  // 如果未选择文件，更新提示文本
        }

    }


    @FXML
    private void handleButtonClick() {
        // 获取用户输入的URL
        String url = Simpleurl.getText();
        // 获取选择的文件路径
        String filePath = VulnPath1.getText();

        // 如果输入框为空或选择了文件
        if (url.isEmpty() && (filePath.isEmpty() || filePath.equals("未选择文件"))) {
            // 输入框为空且未选择文件，执行默认操作
            outputTextArea.appendText("请输入URL或选择文件进行检测。\n");
            executeDefaultRequest();  // 执行默认的请求方法
        } else if (!url.isEmpty()) {
            // 如果输入框有URL，根据操作系统选择执行相应的漏洞检测
            outputTextArea.appendText("开始检测URL: " + url + "\n");

            // 获取 ChoiceBox 当前的选择值
            String selectedOS = choiceBox.getValue();

            if ("Windows系统".equals(selectedOS)) {
                // 执行 Windows 系统的漏洞检测
                sendRequestForWindows(url);
            } else if ("Linux系统".equals(selectedOS)) {
                // 执行 Linux 系统的漏洞检测
                sendRequestForLinux(url);
            } else {
                // 如果没有选择操作系统，输出提示信息
                outputTextArea.appendText("未选择操作系统，无法进行检测。\n");
            }
        } else if (!filePath.isEmpty() && !filePath.equals("未选择文件")) {
            // 如果选择了文件，根据文件内容进行操作
            outputTextArea.appendText("开始检测文件: " + filePath.trim().replaceAll("[\\n\\r]", "") + "\n");
            processFile(filePath);  // 处理文件的检测
        }
    }

    // 执行默认操作的示例方法
    private void executeDefaultRequest() {
        // 默认的请求或操作
        outputTextArea.appendText("请输入文件或者url进行检测\n");
        // 你可以在这里实现默认的请求逻辑
    }

    private void processFile(String filePath) {
        String trimmedFilePath = filePath.trim().replaceAll("[\\n\\r]", "");

        // 确保路径有效
        File file = new File(trimmedFilePath);
        if (!file.exists() || !file.isFile()) {
            outputTextArea.appendText("文件不存在或路径错误:" + trimmedFilePath + "\n");
            return;
        }

        // 输出文件路径
        outputTextArea.appendText("开始读取文件: " + trimmedFilePath + "\n");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // 输出每一行内容
                String selectedOS = choiceBox.getValue();
                // 在这里调用 sendMaliciousRequestWindows 函数，传入 URL 和 TextArea 控件
                if ("Windows系统".equals(selectedOS)) {
                    // 执行 Windows 系统的漏洞检测
                    sendRequestForWindows(line);
                } else if ("Linux系统".equals(selectedOS)) {
                    // 执行 Linux 系统的漏洞检测
                    sendRequestForLinux(line);
                } else {
                    // 如果没有选择操作系统，输出提示信息
                    outputTextArea.appendText("未选择操作系统，无法进行检测。\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // 输出异常信息
            outputTextArea.appendText("读取文件时发生错误: " + e.getMessage() + "\n");
        }
    }

    // 执行 Windows 系统的漏洞检测方法
    private void sendRequestForWindows(String url) {
        // 你可以在这里加入 Windows 漏洞检测的具体实现
        outputTextArea.appendText("开始进行 Windows 系统漏洞检测...\n");
        sendMaliciousRequestWindows(url, outputTextArea);
    }

    // 执行 Linux 系统的漏洞检测方法
    private void sendRequestForLinux(String url) {
        // 你可以在这里加入 Linux 漏洞检测的具体实现
        outputTextArea.appendText("开始进行 Linux 系统漏洞检测...\n");
        VulnerabilityValidator.sendMaliciousRequestLinux(url, outputTextArea);
    }

    public void initialize() {
        // 监听 ChoiceBox 的选项变化（这里可以继续使用）
        choiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // 选择发生变化时，输出当前选择的操作系统
                outputTextArea.appendText("选择的操作系统是: " + newValue + "\n");
            }
        });
    }

    public void handlfuzzButtonClick(ActionEvent actionEvent) {
        // 获取用户输入的URL
        String url = Simpleurl.getText();
        // 获取选择的文件路径
        String filePath = VulnPath1.getText();

        // 如果输入框为空且未选择文件
        if (url.isEmpty() && (filePath.isEmpty() || filePath.equals("未选择文件"))) {
            // 输入框为空且未选择文件，执行默认操作
            outputTextArea.appendText("请输入URL或选择文件进行检测。\n");
            executeDefaultRequest();  // 执行默认的请求方法
        } else if (url.isEmpty() && (filePath.isEmpty() || filePath.equals("未选择文件"))) {
            // 如果未输入URL也未选择文件，提示用户输入目标
            outputTextArea.appendText("请输入目标URL或选择文件进行检测。\n");
        } else if (!url.isEmpty()) {
            // 如果输入框有URL，根据操作系统选择执行相应的漏洞检测
            outputTextArea.appendText("开始进行fuzz检测...\n");

            // 创建一个线程池，最大线程数为 10
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            executorService.submit(() -> sendMaliciousRequestFuzz(url, outputTextArea));

            executorService.shutdown();
        } else {
            outputTextArea.appendText("请输入检测的目标...\n");
        }
    }
}

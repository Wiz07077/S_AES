package S_AES;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static S_AES.SAES_Algorithm.*;

//主页面
public class SAES extends JFrame {
    private JButton encryptButton;
    private JButton multiplyButton;
    private JButton decodeButton;
    private JButton ASCIIButton;

    public SAES() {
        // 设置窗口标题
        setTitle("S-AES 加解密工具");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建自定义的背景面板
        JPanel backgroundPanel = new JPanel() {
            ImageIcon backgroundImage = new ImageIcon("bg.jpg"); // 替换为你的图片路径
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制背景图片
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // 将背景面板添加到窗口
        setContentPane(backgroundPanel);
        // 设置布局
        // 设置布局为 GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 创建按钮
        encryptButton = new JButton("S-AES加密解密");
        encryptButton.setPreferredSize(new Dimension(150, 30));
        ASCIIButton = new JButton("ASCII加密解密");
        ASCIIButton.setPreferredSize(new Dimension(150, 30));
        multiplyButton = new JButton("多重加解密");
        multiplyButton.setPreferredSize(new Dimension(150, 30));
        decodeButton = new JButton("工作模式");
        decodeButton.setPreferredSize(new Dimension(150, 30));

        // 设置按钮位置
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // 设置按钮之间的间距
        gbc.anchor = GridBagConstraints.CENTER; // 使按钮居中
        add(encryptButton, gbc);

        gbc.gridy = 1;
        add(ASCIIButton, gbc);

        gbc.gridy = 2;
        add(multiplyButton, gbc);

        gbc.gridy = 3;
        add(decodeButton, gbc);



        // 加密按钮事件监听
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES_en().setVisible(true);
                dispose();
            }
        });

        //解密
        ASCIIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES_en_ASCII().setVisible(true);
                dispose();
            }
        });

        // 破解按钮的事件监听
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CBCModePage().setVisible(true);
                dispose();

            }
        });

        // 多重加解密按钮的事件监听
        multiplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES_multiple().setVisible(true);
                dispose();

            }
        });

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new S_AES.SAES().setVisible(true);
            }
        });
    }
}

class SAES_en extends JFrame {

    // GUI Components
    private JTextField keyField;
    private JTextField inputField;
    private JTextField outputField;
    private JButton actionButton;
    private JButton generateKeyButton;
    private JButton backButton;
    private JRadioButton encryptRadio;
    private JRadioButton decryptRadio;

    public SAES_en() {
        // 设置窗口标题
        setTitle("S-AES加密解密");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // 创建自定义的背景面板
        JPanel backgroundPanel = new JPanel() {
            ImageIcon backgroundImage = new ImageIcon("bg.jpg"); // 替换为你的图片路径
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制背景图片
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // 将背景面板添加到窗口
        setContentPane(backgroundPanel);

        // 设置布局为 SpringLayout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        // 创建并添加组件
        JLabel keyLabel = new JLabel("     密钥 (16位二进制):");
        keyField = new JTextField(20);
        JLabel inputLabel = new JLabel("     输入 (16位二进制):");
        inputField = new JTextField(20);
        JLabel outputLabel = new JLabel("                     输出结果: ");
        outputField = new JTextField(20);
        outputField.setEditable(false);

        encryptRadio = new JRadioButton("加密", true);
        decryptRadio = new JRadioButton("解密");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(encryptRadio);
        modeGroup.add(decryptRadio);

        actionButton = new JButton("执行");
        generateKeyButton = new JButton("随机生成密钥");
        backButton = new JButton("返回");

        // 添加组件到窗口
        add(keyLabel);
        add(keyField);
        add(inputLabel);
        add(inputField);
        add(outputLabel);
        add(outputField);
        add(encryptRadio);
        add(decryptRadio);
        add(actionButton);
        add(generateKeyButton);
        add(backButton);

        // 使用 SpringLayout 进行布局
        layout.putConstraint(SpringLayout.WEST, keyLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, keyLabel, 10, SpringLayout.NORTH, this.getContentPane());
        layout.putConstraint(SpringLayout.WEST, keyField, 10, SpringLayout.EAST, keyLabel);
        layout.putConstraint(SpringLayout.NORTH, keyField, 0, SpringLayout.NORTH, keyLabel);

        layout.putConstraint(SpringLayout.WEST, inputLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, inputLabel, 20, SpringLayout.SOUTH, keyLabel);
        layout.putConstraint(SpringLayout.WEST, inputField, 10, SpringLayout.EAST, inputLabel);
        layout.putConstraint(SpringLayout.NORTH, inputField, 0, SpringLayout.NORTH, inputLabel);

        layout.putConstraint(SpringLayout.WEST, outputLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, outputLabel, 20, SpringLayout.SOUTH, inputLabel);
        layout.putConstraint(SpringLayout.WEST, outputField, 10, SpringLayout.EAST, outputLabel);
        layout.putConstraint(SpringLayout.NORTH, outputField, 0, SpringLayout.NORTH, outputLabel);

        layout.putConstraint(SpringLayout.WEST, encryptRadio, 120, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, encryptRadio, 20, SpringLayout.SOUTH, outputLabel);
        layout.putConstraint(SpringLayout.WEST, decryptRadio, 40, SpringLayout.EAST, encryptRadio);
        layout.putConstraint(SpringLayout.NORTH, decryptRadio, 20, SpringLayout.SOUTH, outputLabel);

        layout.putConstraint(SpringLayout.WEST, actionButton, 50, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, actionButton, 20, SpringLayout.SOUTH, encryptRadio);

        layout.putConstraint(SpringLayout.WEST, generateKeyButton, 20, SpringLayout.EAST, actionButton);
        layout.putConstraint(SpringLayout.NORTH, generateKeyButton, 20, SpringLayout.SOUTH, encryptRadio);

        layout.putConstraint(SpringLayout.WEST, backButton, 20, SpringLayout.EAST, generateKeyButton);
        layout.putConstraint(SpringLayout.NORTH, backButton, 20, SpringLayout.SOUTH, encryptRadio);

        // 执行按钮事件监听
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = keyField.getText();
                String inputText = inputField.getText();
                if (validateInput(key, inputText, 16, 16)) {
                    int[][] state = convertToStateMatrix(inputText);
                    int[][] keyMatrix = convertToStateMatrix(key);
                    String result;
                    if (encryptRadio.isSelected()) {
                        int[][] encrypted = encrypt(state, keyMatrix);
                        result = convertStateMatrixToString(encrypted);
                    } else {
                        int[][] decrypted = decrypt(state, keyMatrix);
                        result = convertStateMatrixToString(decrypted);
                    }
                    outputField.setText(result);
                } else {
                    JOptionPane.showMessageDialog(SAES_en.this, "请输入正确的16位密钥和16位二进制输入。", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 随机生成密钥按钮的事件监听
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomKey = generateRandomKey(16);
                keyField.setText(randomKey);
                JOptionPane.showMessageDialog(SAES_en.this, "生成的随机密钥: " + randomKey, "随机密钥生成", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 返回按钮的事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES().setVisible(true);
                dispose();
            }
        });
    }


    // 验证输入是否是二进制且长度正确
    private boolean validateInput(String key, String text, int keyLength, int textLength) {
        return key.matches("[01]{" + keyLength + "}") && text.matches("[01]{" + textLength + "}");
    }

    // 生成随机的二进制密钥
    private String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(random.nextInt(2)); // 生成0或1
        }
        return key.toString();
    }


}



class SAES_multiple extends JFrame {

    private JButton twoWayEncryptionButton;   // 二重加解密按钮
    private JButton meetInTheMiddleAttackButton; // 中间相遇攻击按钮
    private JButton threeWayEncryptionButton; // 三重加解密按钮
    private JButton backButton;

    public SAES_multiple() {
        setTitle("S-AES扩展加密解密");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建自定义的背景面板
        JPanel backgroundPanel = new JPanel() {
            ImageIcon backgroundImage = new ImageIcon("bg.jpg"); // 替换为你的图片路径
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制背景图片
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // 将背景面板添加到窗口
        setContentPane(backgroundPanel);

        // 设置布局
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        // 创建并添加按钮
        twoWayEncryptionButton = new JButton("二重加解密");
        twoWayEncryptionButton.setPreferredSize(new Dimension(150, 30));
        meetInTheMiddleAttackButton = new JButton("中间相遇攻击");
        meetInTheMiddleAttackButton.setPreferredSize(new Dimension(150, 30));
        threeWayEncryptionButton = new JButton("三重加解密");
        threeWayEncryptionButton.setPreferredSize(new Dimension(150, 30));
        backButton = new JButton("返回");
        backButton.setPreferredSize(new Dimension(150, 30));

        // 设置按钮位置
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // 设置按钮之间的间距
        gbc.anchor = GridBagConstraints.CENTER; // 使按钮居中
        add(twoWayEncryptionButton, gbc);

        gbc.gridy = 1;
        add(threeWayEncryptionButton, gbc);

        gbc.gridy = 2;
        add(meetInTheMiddleAttackButton, gbc);

        gbc.gridy = 3;
        add(backButton, gbc);




        // 二重加解密按钮事件监听器
        twoWayEncryptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES_en2().setVisible(true);  // 打开二重加解密页面
                dispose();  // 关闭当前页面
            }
        });

        // 中间相遇攻击按钮事件监听器
        meetInTheMiddleAttackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MeetInTheMiddleAttackPage().setVisible(true);  // 打开中间相遇攻击页面
                dispose();  // 关闭当前页面
            }
        });

        // 三重加解密按钮事件监听器
        threeWayEncryptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES_en3().setVisible(true);  // 打开三重加解密页面
                dispose();  // 关闭当前页面
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES().setVisible(true);  // 打开三重加解密页面
                dispose();  // 关闭当前页面
            }
        });

    }
}

class SAES_en2 extends JFrame {

    // GUI Components
    private JTextField inputField;
    private JTextField outputField;
    private JTextField keyField;
    private JRadioButton encryptRadio;
    private JRadioButton decryptRadio;
    private JButton actionButton;
    private JButton generateKeyButton;
    private JButton backButton;

    public SAES_en2() {
        // 设置窗口标题
        setTitle("S-AES双重加密");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建自定义的背景面板
        JPanel backgroundPanel = new JPanel() {
            ImageIcon backgroundImage = new ImageIcon("bg.jpg"); // 替换为你的图片路径
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制背景图片
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // 将背景面板添加到窗口
        setContentPane(backgroundPanel);

        // 设置布局为 SpringLayout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // 创建并添加组件
        JLabel keyLabel = new JLabel("     密钥 (32bits):");
        keyField = new JTextField(30);
        JLabel inputLabel = new JLabel("     明文 (16bits):");
        inputField = new JTextField(30);
        JLabel outputLabel = new JLabel("            输出结果:");
        outputField = new JTextField(30);
        outputField.setEditable(false);

        // 创建单选按钮并添加到按钮组
        encryptRadio = new JRadioButton("加密", true);
        decryptRadio = new JRadioButton("解密");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(encryptRadio);
        modeGroup.add(decryptRadio);

        actionButton = new JButton("执行");
        generateKeyButton = new JButton("随机生成密钥");
        backButton = new JButton("返回");

        // 添加组件到窗口
        add(keyLabel);
        add(keyField);
        add(inputLabel);
        add(inputField);
        add(outputLabel);
        add(outputField);
        add(encryptRadio);
        add(decryptRadio);
        add(actionButton);
        add(generateKeyButton);
        add(backButton);

        // 使用 SpringLayout 进行布局
        layout.putConstraint(SpringLayout.WEST, keyLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, keyLabel, 10, SpringLayout.NORTH, this.getContentPane());
        layout.putConstraint(SpringLayout.WEST, keyField, 10, SpringLayout.EAST, keyLabel);
        layout.putConstraint(SpringLayout.NORTH, keyField, 0, SpringLayout.NORTH, keyLabel);

        layout.putConstraint(SpringLayout.WEST, inputLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, inputLabel, 20, SpringLayout.SOUTH, keyLabel);
        layout.putConstraint(SpringLayout.WEST, inputField, 10, SpringLayout.EAST, inputLabel);
        layout.putConstraint(SpringLayout.NORTH, inputField, 0, SpringLayout.NORTH, inputLabel);

        layout.putConstraint(SpringLayout.WEST, outputLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, outputLabel, 20, SpringLayout.SOUTH, inputLabel);
        layout.putConstraint(SpringLayout.WEST, outputField, 10, SpringLayout.EAST, outputLabel);
        layout.putConstraint(SpringLayout.NORTH, outputField, 0, SpringLayout.NORTH, outputLabel);

        layout.putConstraint(SpringLayout.WEST, encryptRadio, 150, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, encryptRadio, 20, SpringLayout.SOUTH, outputLabel);
        layout.putConstraint(SpringLayout.WEST, decryptRadio, 60, SpringLayout.EAST, encryptRadio);
        layout.putConstraint(SpringLayout.NORTH, decryptRadio, 20, SpringLayout.SOUTH, outputLabel);

        layout.putConstraint(SpringLayout.WEST, actionButton, 100, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, actionButton, 20, SpringLayout.SOUTH, encryptRadio);

        layout.putConstraint(SpringLayout.WEST, generateKeyButton, 20, SpringLayout.EAST, actionButton);
        layout.putConstraint(SpringLayout.NORTH, generateKeyButton, 20, SpringLayout.SOUTH, encryptRadio);

        layout.putConstraint(SpringLayout.WEST, backButton, 20, SpringLayout.EAST, generateKeyButton);
        layout.putConstraint(SpringLayout.NORTH, backButton, 20, SpringLayout.SOUTH, encryptRadio);

        // 执行按钮事件监听
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().trim();
                String keyInput = keyField.getText().trim();
                if (validateInput(keyInput, input, 32, 16)) {
                    // 获取32位密钥并拆分为两个16位子密钥
                    String key1 = keyInput.substring(0, 16);
                    String key2 = keyInput.substring(16);
                    int[][] state = convertToStateMatrix(input);
                    String result;

                    if (encryptRadio.isSelected()) {
                        // 第一次加密
                        int[][] keyMatrix1 = convertToStateMatrix(key1);
                        int[][] encrypted1 = encrypt(state, keyMatrix1);

                        // 第二次加密
                        int[][] keyMatrix2 = convertToStateMatrix(key2);
                        int[][] encrypted2 = encrypt(encrypted1, keyMatrix2);

                        result = convertStateMatrixToString(encrypted2);
                    } else {
                        // 第一次解密
                        int[][] keyMatrix2 = convertToStateMatrix(key2);
                        int[][] decrypted1 = decrypt(state, keyMatrix2);

                        // 第二次解密
                        int[][] keyMatrix1 = convertToStateMatrix(key1);
                        int[][] decrypted2 = decrypt(decrypted1, keyMatrix1);

                        result = convertStateMatrixToString(decrypted2);
                    }
                    outputField.setText(result);
                } else {
                    JOptionPane.showMessageDialog(SAES_en2.this, "请输入正确的32位密钥和16位明文或密文。", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 随机生成密钥按钮的事件监听
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomKey = generateRandomKey(32);
                keyField.setText(randomKey);
                JOptionPane.showMessageDialog(SAES_en2.this, "生成的随机密钥: " + randomKey, "随机密钥生成", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 返回按钮的事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES_multiple().setVisible(true); // 假设有主页面
                dispose(); // 关闭当前窗口
            }
        });
    }


    // 验证输入是否是二进制且长度正确
    private boolean validateInput(String key, String text, int keyLength, int textLength) {
        return key.matches("[01]{" + keyLength + "}") && text.matches("[01]{" + textLength + "}");
    }

    // 生成随机的二进制密钥
    private String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(random.nextInt(2)); // 生成0或1
        }
        return key.toString();
    }
}


class SAES_en3 extends JFrame {

    // GUI Components
    private JTextField inputField;
    private JTextField outputField;
    private JTextField keyField;
    private JRadioButton encryptRadio;
    private JRadioButton decryptRadio;
    private JButton actionButton;
    private JButton generateKeyButton;
    private JButton backButton;

    public SAES_en3() {
        // 设置窗口标题
        setTitle("S-AES三重加密");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建自定义的背景面板
        JPanel backgroundPanel = new JPanel() {
            ImageIcon backgroundImage = new ImageIcon("bg.jpg"); // 替换为你的图片路径
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制背景图片
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // 将背景面板添加到窗口
        setContentPane(backgroundPanel);

        // 设置布局为 SpringLayout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // 创建并添加组件
        JLabel keyLabel = new JLabel("     密钥 (48bits):");
        keyField = new JTextField(30);
        JLabel inputLabel = new JLabel("     明文 (16bits):");
        inputField = new JTextField(30);
        JLabel outputLabel = new JLabel("            输出结果:");
        outputField = new JTextField(30);
        outputField.setEditable(false);

        // 创建单选按钮并添加到按钮组
        encryptRadio = new JRadioButton("加密", true);
        decryptRadio = new JRadioButton("解密");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(encryptRadio);
        modeGroup.add(decryptRadio);

        actionButton = new JButton("执行");
        generateKeyButton = new JButton("随机生成密钥");
        backButton = new JButton("返回");

        // 添加组件到窗口
        add(keyLabel);
        add(keyField);
        add(inputLabel);
        add(inputField);
        add(outputLabel);
        add(outputField);
        add(encryptRadio);
        add(decryptRadio);
        add(actionButton);
        add(generateKeyButton);
        add(backButton);

        // 使用 SpringLayout 进行布局
        layout.putConstraint(SpringLayout.WEST, keyLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, keyLabel, 10, SpringLayout.NORTH, this.getContentPane());
        layout.putConstraint(SpringLayout.WEST, keyField, 10, SpringLayout.EAST, keyLabel);
        layout.putConstraint(SpringLayout.NORTH, keyField, 0, SpringLayout.NORTH, keyLabel);

        layout.putConstraint(SpringLayout.WEST, inputLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, inputLabel, 20, SpringLayout.SOUTH, keyLabel);
        layout.putConstraint(SpringLayout.WEST, inputField, 10, SpringLayout.EAST, inputLabel);
        layout.putConstraint(SpringLayout.NORTH, inputField, 0, SpringLayout.NORTH, inputLabel);

        layout.putConstraint(SpringLayout.WEST, outputLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, outputLabel, 20, SpringLayout.SOUTH, inputLabel);
        layout.putConstraint(SpringLayout.WEST, outputField, 10, SpringLayout.EAST, outputLabel);
        layout.putConstraint(SpringLayout.NORTH, outputField, 0, SpringLayout.NORTH, outputLabel);

        layout.putConstraint(SpringLayout.WEST, encryptRadio, 150, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, encryptRadio, 20, SpringLayout.SOUTH, outputLabel);
        layout.putConstraint(SpringLayout.WEST, decryptRadio, 60, SpringLayout.EAST, encryptRadio);
        layout.putConstraint(SpringLayout.NORTH, decryptRadio, 20, SpringLayout.SOUTH, outputLabel);

        layout.putConstraint(SpringLayout.WEST, actionButton, 100, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, actionButton, 20, SpringLayout.SOUTH, encryptRadio);

        layout.putConstraint(SpringLayout.WEST, generateKeyButton, 20, SpringLayout.EAST, actionButton);
        layout.putConstraint(SpringLayout.NORTH, generateKeyButton, 20, SpringLayout.SOUTH, encryptRadio);

        layout.putConstraint(SpringLayout.WEST, backButton, 20, SpringLayout.EAST, generateKeyButton);
        layout.putConstraint(SpringLayout.NORTH, backButton, 20, SpringLayout.SOUTH, encryptRadio);

        // 执行按钮事件监听
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText().trim();
                String keyInput = keyField.getText().trim();
                if (validateInput(keyInput, input, 48, 16)) {
                    // 获取48位密钥并拆分为三个16位子密钥
                    String key1 = keyInput.substring(0, 16);
                    String key2 = keyInput.substring(16, 32);
                    String key3 = keyInput.substring(32);
                    int[][] state = convertToStateMatrix(input);
                    String result;

                    if (encryptRadio.isSelected()) {
                        // 第一次加密
                        int[][] keyMatrix1 = convertToStateMatrix(key1);
                        int[][] encrypted1 = encrypt(state, keyMatrix1);

                        // 第二次加密
                        int[][] keyMatrix2 = convertToStateMatrix(key2);
                        int[][] encrypted2 = encrypt(encrypted1, keyMatrix2);

                        // 第三次加密
                        int[][] keyMatrix3 = convertToStateMatrix(key3);
                        int[][] encrypted3 = encrypt(encrypted2, keyMatrix3);

                        result = convertStateMatrixToString(encrypted3);
                    } else {
                        // 第一次解密（使用Key3）
                        int[][] keyMatrix3 = convertToStateMatrix(key3);
                        int[][] decrypted1 = decrypt(state, keyMatrix3);

                        // 第二次解密（使用Key2）
                        int[][] keyMatrix2 = convertToStateMatrix(key2);
                        int[][] decrypted2 = decrypt(decrypted1, keyMatrix2);

                        // 第三次解密（使用Key1）
                        int[][] keyMatrix1 = convertToStateMatrix(key1);
                        int[][] decrypted3 = decrypt(decrypted2, keyMatrix1);

                        result = convertStateMatrixToString(decrypted3);
                    }
                    outputField.setText(result);
                } else {
                    JOptionPane.showMessageDialog(SAES_en3.this, "请输入正确的48位密钥和16位明文或密文。", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 随机生成密钥按钮的事件监听
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomKey = generateRandomKey(48);
                keyField.setText(randomKey);
                JOptionPane.showMessageDialog(SAES_en3.this, "生成的随机密钥: " + randomKey, "随机密钥生成", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 返回按钮的事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES_multiple().setVisible(true); // 假设有主页面
                dispose(); // 关闭当前窗口
            }
        });
    }

    // 验证输入是否是二进制且长度正确
    private boolean validateInput(String key, String text, int keyLength, int textLength) {
        return key.matches("[01]{" + keyLength + "}") && text.matches("[01]{" + textLength + "}");
    }

    // 生成随机的二进制密钥
    private String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(random.nextInt(2)); // 生成0或1
        }
        return key.toString();
    }
}


class MeetInTheMiddleAttackPage extends JFrame {

    private JTextField plaintextField;
    private JTextField ciphertextField;
    private JTextArea resultArea;
    private JButton attackButton;
    private JButton backButton;

    public MeetInTheMiddleAttackPage() {
        setTitle("中间相遇攻击");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // 创建自定义的背景面板
        JPanel backgroundPanel = new JPanel() {
            ImageIcon backgroundImage = new ImageIcon("bg.jpg"); // 替换为你的图片路径
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制背景图片
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // 将背景面板添加到窗口
        setContentPane(backgroundPanel);

        // 设置布局为 SpringLayout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // 创建并添加组件
        JLabel plaintextLabel = new JLabel("       明文 (16bits):");
        plaintextField = new JTextField(30);

        JLabel ciphertextLabel = new JLabel("       密文 (16bits):");
        ciphertextField = new JTextField(30);

        resultArea = new JTextArea(7, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        attackButton = new JButton("执行中间相遇攻击");
        backButton = new JButton("返回");

        // 添加组件到窗口
        add(plaintextLabel);
        add(plaintextField);
        add(ciphertextLabel);
        add(ciphertextField);
        add(resultScrollPane);
        add(attackButton);
        add(backButton);

        // 使用 SpringLayout 进行布局
        layout.putConstraint(SpringLayout.WEST, plaintextLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, plaintextLabel, 10, SpringLayout.NORTH, this.getContentPane());
        layout.putConstraint(SpringLayout.WEST, plaintextField, 10, SpringLayout.EAST, plaintextLabel);
        layout.putConstraint(SpringLayout.NORTH, plaintextField, 0, SpringLayout.NORTH, plaintextLabel);

        layout.putConstraint(SpringLayout.WEST, ciphertextLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, ciphertextLabel, 20, SpringLayout.SOUTH, plaintextField);
        layout.putConstraint(SpringLayout.WEST, ciphertextField, 10, SpringLayout.EAST, ciphertextLabel);
        layout.putConstraint(SpringLayout.NORTH, ciphertextField, 0, SpringLayout.NORTH, ciphertextLabel);

        layout.putConstraint(SpringLayout.WEST, resultScrollPane, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, resultScrollPane, 20, SpringLayout.SOUTH, ciphertextField);
        layout.putConstraint(SpringLayout.EAST, resultScrollPane, -10, SpringLayout.EAST, this.getContentPane());

        layout.putConstraint(SpringLayout.WEST, attackButton, 110, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, attackButton, 20, SpringLayout.SOUTH, resultScrollPane);

        layout.putConstraint(SpringLayout.WEST, backButton, 50, SpringLayout.EAST, attackButton);
        layout.putConstraint(SpringLayout.NORTH, backButton, 20, SpringLayout.SOUTH, resultScrollPane);

        // 按钮事件监听
        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plaintext = plaintextField.getText().trim();
                String ciphertext = ciphertextField.getText().trim();

                // 调用中间相遇攻击方法
                String[] keys = meetInTheMiddle(plaintext, ciphertext);

                // 显示结果
                if (keys != null) {
                    resultArea.setText("找到的密钥对:\n");
                    for (String key : keys) {
                        resultArea.append(key + "\n");
                    }
                } else {
                    resultArea.setText("未找到匹配的密钥对");
                }
            }
        });

        // 返回按钮的事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES_multiple().setVisible(true); // 假设有主页面
                dispose(); // 关闭当前窗口
            }
        });
    }

    // 需要实现的方法: meetInTheMiddle
}

class SAES_en_ASCII extends JFrame {

    // GUI Components
    private JTextField inputField;
    private JTextField outputField;
    private JTextField keyField;
    private JRadioButton encryptRadio;
    private JRadioButton decryptRadio;
    private JButton actionButton;
    private JButton generateKeyButton;
    private JButton backButton;
    public SAES_en_ASCII() {
        // 设置窗口标题
        setTitle("S-AES ASCII加密解密");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建自定义的背景面板
        JPanel backgroundPanel = new JPanel() {
            ImageIcon backgroundImage = new ImageIcon("bg.jpg"); // 替换为你的图片路径
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制背景图片
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // 将背景面板添加到窗口
        setContentPane(backgroundPanel);

        // 设置布局为 SpringLayout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // 创建并添加组件
        JLabel keyLabel = new JLabel("       密钥 (16bits):");
        keyField = new JTextField(20);
        JLabel inputLabel = new JLabel("       输入 (2bytes):");
        inputField = new JTextField(20);
        JLabel outputLabel = new JLabel("              输出结果:");
        outputField = new JTextField(20);
        outputField.setEditable(false);

        // 创建单选按钮并添加到按钮组
        encryptRadio = new JRadioButton("加密", true);
        decryptRadio = new JRadioButton("解密");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(encryptRadio);
        modeGroup.add(decryptRadio);

        actionButton = new JButton("执行");
        generateKeyButton = new JButton("随机生成密钥");
        backButton = new JButton("返回");

        // 添加组件到窗口
        add(keyLabel);
        add(keyField);
        add(inputLabel);
        add(inputField);
        add(outputLabel);
        add(outputField);
        add(encryptRadio);
        add(decryptRadio);
        add(actionButton);
        add(generateKeyButton);
        add(backButton);

        // 使用 SpringLayout 进行布局
        layout.putConstraint(SpringLayout.WEST, keyLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, keyLabel, 10, SpringLayout.NORTH, this.getContentPane());
        layout.putConstraint(SpringLayout.WEST, keyField, 10, SpringLayout.EAST, keyLabel);
        layout.putConstraint(SpringLayout.NORTH, keyField, 0, SpringLayout.NORTH, keyLabel);

        layout.putConstraint(SpringLayout.WEST, inputLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, inputLabel, 20, SpringLayout.SOUTH, keyLabel);
        layout.putConstraint(SpringLayout.WEST, inputField, 10, SpringLayout.EAST, inputLabel);
        layout.putConstraint(SpringLayout.NORTH, inputField, 0, SpringLayout.NORTH, inputLabel);

        layout.putConstraint(SpringLayout.WEST, outputLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, outputLabel, 20, SpringLayout.SOUTH, inputLabel);
        layout.putConstraint(SpringLayout.WEST, outputField, 10, SpringLayout.EAST, outputLabel);
        layout.putConstraint(SpringLayout.NORTH, outputField, 0, SpringLayout.NORTH, outputLabel);

        layout.putConstraint(SpringLayout.WEST, encryptRadio, 120, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, encryptRadio, 20, SpringLayout.SOUTH, outputLabel);
        layout.putConstraint(SpringLayout.WEST, decryptRadio, 40, SpringLayout.EAST, encryptRadio);
        layout.putConstraint(SpringLayout.NORTH, decryptRadio, 20, SpringLayout.SOUTH, outputLabel);

        layout.putConstraint(SpringLayout.WEST, actionButton, 50, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, actionButton, 20, SpringLayout.SOUTH, encryptRadio);

        layout.putConstraint(SpringLayout.WEST, generateKeyButton, 20, SpringLayout.EAST, actionButton);
        layout.putConstraint(SpringLayout.NORTH, generateKeyButton, 20, SpringLayout.SOUTH, encryptRadio);

        layout.putConstraint(SpringLayout.WEST, backButton, 20, SpringLayout.EAST, generateKeyButton);
        layout.putConstraint(SpringLayout.NORTH, backButton, 20, SpringLayout.SOUTH, encryptRadio);

        // 执行按钮事件监听
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = asciiToBinary(inputField.getText().trim()); // 转换为二进制
                String keyInput = asciiToBinary(keyField.getText().trim()); // 转换为二进制
                String result;

                int[][] state = convertToStateMatrix(input); // 二进制 转 矩阵
                int[][] key = convertToStateMatrix(keyInput); // 将密钥转换为矩阵
                if (encryptRadio.isSelected()) {
                    int[][] encrypted = encrypt(state, key);
                    result = binaryToAscii(convertStateMatrixToString(encrypted, true)); // 输出为ASCII
                } else {
                    int[][] decrypted = decrypt(state, key);
                    result = binaryToAscii(convertStateMatrixToString(decrypted, true)); // 输出为ASCII
                }
                outputField.setText(result);
            }
        });

        // 随机生成密钥按钮的事件监听
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomKey = generateRandomKey(16);
                keyField.setText(randomKey);
                JOptionPane.showMessageDialog(SAES_en_ASCII.this, "生成的随机密钥: " + randomKey, "随机密钥生成", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 返回按钮的事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES().setVisible(true); // 回到主页面
                dispose(); // 关闭当前窗口
            }
        });
    }

    // 将 ASCII 字符串转换为二进制字符串
    private String asciiToBinary(String text) {
        StringBuilder binary = new StringBuilder();
        for (char ch : text.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(ch)).replace(' ', '0'));
        }
        return binary.toString();
    }

    // 将二进制字符串转换回 ASCII 字符串
    private String binaryToAscii(String binary) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            String byteStr = binary.substring(i, i + 8);
            int charCode = Integer.parseInt(byteStr, 2);
            text.append((char) charCode);
        }
        return text.toString();
    }

    // 生成随机的二进制密钥
    private String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(random.nextInt(2)); // 生成 0 或 1
        }
        return key.toString();
    }

    // 将二进制字符串转换为状态矩阵
    private int[][] convertToStateMatrix(String binaryInput) {
        int[][] state = new int[2][2];
        for (int i = 0; i < 4; i++) {
            String nibble = binaryInput.substring(i * 4, (i * 4) + 4);
            state[i / 2][i % 2] = Integer.parseInt(nibble, 2);
        }
        return state;
    }

    // 将状态矩阵转换为二进制字符串
    private String convertStateMatrixToString(int[][] state, boolean isAscii) {
        StringBuilder binaryString = new StringBuilder();
        for (int[] row : state) {
            for (int value : row) {
                String binary = String.format("%4s", Integer.toBinaryString(value)).replace(' ', '0');
                binaryString.append(binary);
            }
        }
        return binaryString.toString();
    }

}
class CBCModePage extends JFrame {

    private JTextArea inputArea;    // 输入区域（明文和密文）
    private JTextArea keyArea;       // 密钥输入区域
    private JTextArea ivArea;        // 初始向量输入区域
    private JTextArea resultArea;    // 输出区域
    private JRadioButton encryptRadio;    // 加密选择按钮
    private JRadioButton decryptRadio;    // 解密选择按钮
    private JButton actionButton;    // 执行按钮
    private JButton randomKeyButton;  // 生成随机密钥按钮
    private JButton randomIVButton;   // 生成随机初始向量按钮
    private JButton backButton;       // 返回按钮

    public CBCModePage() {
        setTitle("S-AES CBC模式加解密");
        setSize(700, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建自定义的背景面板
        JPanel backgroundPanel = new JPanel() {
            ImageIcon backgroundImage = new ImageIcon("bg.jpg"); // 替换为你的图片路径
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制背景图片
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // 将背景面板添加到窗口
        setContentPane(backgroundPanel);

        // 设置布局为 SpringLayout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // 创建并添加组件
        JLabel inputLabel = new JLabel(" 明文/密文(16bits倍数):");
        inputArea = new JTextArea(1, 45);
        JScrollPane inputScrollPane = new JScrollPane(inputArea);

        JLabel keyLabel = new JLabel("                                   密钥:");
        keyArea = new JTextArea(1, 45);
        JScrollPane keyScrollPane = new JScrollPane(keyArea);

        JLabel ivLabel = new JLabel("                          初始向量:");
        ivArea = new JTextArea(1, 45);
        JScrollPane ivScrollPane = new JScrollPane(ivArea);

        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        // 创建加密和解密选择按钮
        encryptRadio = new JRadioButton("加密", true);
        decryptRadio = new JRadioButton("解密");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(encryptRadio);
        modeGroup.add(decryptRadio);

        actionButton = new JButton("执行");
        randomKeyButton = new JButton("生成随机密钥");
        randomIVButton = new JButton("生成随机初始向量");
        backButton = new JButton("返回");

        // 添加组件到窗口
        add(inputLabel);
        add(inputScrollPane);
        add(keyLabel);
        add(keyScrollPane);
        add(ivLabel);
        add(ivScrollPane);
        add(resultScrollPane);
        add(encryptRadio);
        add(decryptRadio);
        add(actionButton);
        add(randomKeyButton);
        add(randomIVButton);
        add(backButton);

        // 使用 SpringLayout 进行布局
        layout.putConstraint(SpringLayout.WEST, inputLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, inputLabel, 10, SpringLayout.NORTH, this.getContentPane());
        layout.putConstraint(SpringLayout.WEST, inputScrollPane, 10, SpringLayout.EAST, inputLabel);
        layout.putConstraint(SpringLayout.NORTH, inputScrollPane, 0, SpringLayout.NORTH, inputLabel);

        layout.putConstraint(SpringLayout.WEST, keyLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, keyLabel, 20, SpringLayout.SOUTH, inputScrollPane);
        layout.putConstraint(SpringLayout.WEST, keyScrollPane, 10, SpringLayout.EAST, keyLabel);
        layout.putConstraint(SpringLayout.NORTH, keyScrollPane, 0, SpringLayout.NORTH, keyLabel);

        layout.putConstraint(SpringLayout.WEST, ivLabel, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, ivLabel, 20, SpringLayout.SOUTH, keyScrollPane);
        layout.putConstraint(SpringLayout.WEST, ivScrollPane, 10, SpringLayout.EAST, ivLabel);
        layout.putConstraint(SpringLayout.NORTH, ivScrollPane, 0, SpringLayout.NORTH, ivLabel);

        layout.putConstraint(SpringLayout.WEST, resultScrollPane, 10, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, resultScrollPane, 20, SpringLayout.SOUTH, ivScrollPane);
        layout.putConstraint(SpringLayout.EAST, resultScrollPane, -10, SpringLayout.EAST, this.getContentPane());

        layout.putConstraint(SpringLayout.WEST, encryptRadio, 240, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, encryptRadio, 20, SpringLayout.SOUTH, resultScrollPane);

        layout.putConstraint(SpringLayout.WEST, decryptRadio, 100, SpringLayout.EAST, encryptRadio);
        layout.putConstraint(SpringLayout.NORTH, decryptRadio, 20, SpringLayout.SOUTH, resultScrollPane);

        layout.putConstraint(SpringLayout.WEST, actionButton, 120, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, actionButton, 60, SpringLayout.SOUTH, resultScrollPane);

        layout.putConstraint(SpringLayout.WEST, randomKeyButton, 20, SpringLayout.EAST, actionButton);
        layout.putConstraint(SpringLayout.NORTH, randomKeyButton, 60, SpringLayout.SOUTH, resultScrollPane);

        layout.putConstraint(SpringLayout.WEST, randomIVButton, 20, SpringLayout.EAST, randomKeyButton);
        layout.putConstraint(SpringLayout.NORTH, randomIVButton, 60, SpringLayout.SOUTH, resultScrollPane);

        layout.putConstraint(SpringLayout.WEST, backButton, 20, SpringLayout.EAST, randomIVButton);
        layout.putConstraint(SpringLayout.NORTH, backButton, 60, SpringLayout.SOUTH, resultScrollPane);

        // 执行按钮事件监听
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputArea.getText().trim();
                String key = keyArea.getText().trim();
                String iv = ivArea.getText().trim();
                String result;

                if (encryptRadio.isSelected()) {
                    // 调用 CBC 加密方法
                    result = cbcEncrypt(input, convertToStateMatrix(key), iv);
                    resultArea.setText("加密结果:\n" + result);
                } else {
                    // 调用 CBC 解密方法
                    result = cbcDecrypt(input, convertToStateMatrix(key), iv);
                    resultArea.setText("解密结果:\n" + result);
                }
            }
        });

        // 随机密钥按钮的事件监听
        randomKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomKey = generateRandomKey(16); // 生成16位二进制密钥
                keyArea.setText(randomKey);
            }
        });

        // 随机初始向量按钮的事件监听
        randomIVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomIV = generateIV(); // 生成随机IV
                ivArea.setText(randomIV);
            }
        });

        // 返回按钮的事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SAES_multiple().setVisible(true); // 假设有主页面
                dispose(); // 关闭当前窗口
            }
        });
    }

    // 生成随机的二进制密钥
    private String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(random.nextInt(2)); // 生成0或1
        }
        return key.toString();
    }}
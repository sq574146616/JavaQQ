package com.zust.client.view;

import com.zust.client.UDP.ClientUDP;
import com.zust.client.manager.ManagerPanel;
import com.zust.common.bean.ChatBean;
import com.zust.common.bean.DataFormat;
import com.zust.common.tool.DesUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
//右边聊天面板：
class ChatPanel extends JPanel {
    String userName;
    String toUserName;
    Integer fromId;
    Integer toId;
    JTabbedPane tabbedPane;
    JTextArea showPanel;
    JTextArea editTextArea;
    //创建一个JPanel，并为构造函数初始false，表示不适用双缓冲
    public ChatPanel(String userName,String toUserName,JTabbedPane tabbedPane,Integer fromId,Integer toId){
        this.userName=userName;
        this.toUserName=toUserName;
        this.tabbedPane=tabbedPane;
        this.fromId=fromId;
        this.toId=toId;
        createChatPanel();


    }

    public void createChatPanel() {
        setLayout(new GridLayout(2, 1));
        showPanel = new JTextArea();
		JScrollPane scroll = new JScrollPane(showPanel);
        showPanel.setEditable(false);
        showPanel.setForeground(Color.BLUE);
//        showPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        showPanel.setLineWrap(true);        //激活自动换行功能
        showPanel.setWrapStyleWord(true);            // 激活断行不断字功能
        add(scroll);//把label加入了panel面板

        JPanel jp1 = new JPanel();
        editTextArea = new JTextArea(3, 6);//new一个多行输入框，指定 行数和列数分别为3,6
        editTextArea.setPreferredSize(new Dimension(420, 150));
        editTextArea.registerKeyboardAction(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				sendMessage(fromId, toId);//发送数据
				editTextArea.setText("");
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
        JPanel jp2 = new JPanel(new FlowLayout());
        jp2.setPreferredSize(new Dimension(250, 50));
        JButton sendBtn = new JButton("发送");
        sendBtn.setBackground(new Color(193, 199, 215));
        sendBtn.setForeground(Color.white);
        sendBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sendMessage(fromId, toId);//发送数据
            }
        });
        JButton closeBtn = new JButton("关闭");
        closeBtn.setBackground(new Color(193, 199, 215));
        closeBtn.setForeground(Color.white);
        Font font = new Font("微软雅黑",Font.PLAIN,12);
        sendBtn.setFont(font);
        closeBtn.setFont(font);
        showPanel.setFont(font);
        closeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tabbedPane.remove(tabbedPane.getSelectedIndex());
                if(tabbedPane.getTabCount()==0){
                    System.out.println("there is no friend on the chatPane!");
                    ManagerPanel.delete("chatPanel");
                    ChatPane.frame.dispose();
                }
            }
        });
        jp2.add(sendBtn);
        jp2.add(closeBtn);
        jp1.add(editTextArea, BorderLayout.CENTER);
        jp1.add(jp2, BorderLayout.SOUTH);
        add(jp1);
    }

    public void sendMessage(Integer fromId,Integer toId)
    {
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date());
            showPanel.append("\n"+userName+"  "+date+":\r\n");
            showPanel.append("          "+editTextArea.getText()+"\r\n");
			showPanel.setCaretPosition(showPanel.getText().length());
            String message=editTextArea.getText();
            message = DesUtil.encrypt(message);
            ChatBean chatBean=new ChatBean();
            chatBean.setMessage(message);
            DataFormat dataFormat = new DataFormat(fromId, toId, DataFormat.MESSAGE, chatBean, System.currentTimeMillis());
            ClientUDP.sendUdpMsg(dataFormat);
            editTextArea.setText("");
        }
        catch(IOException err)
        {
            showPanel.setText("");
            showPanel.append(err+"\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void showMsg(String msg){
        //        判断是否有消息：
        if(msg!=null){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date());
            showPanel.append("\n"+toUserName+" "+date+"：\n");
            showPanel.append("         "+msg + "\n");
			showPanel.setCaretPosition(showPanel.getText().length());
        }

    }
}

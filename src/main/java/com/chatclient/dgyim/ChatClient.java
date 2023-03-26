package com.chatclient.dgyim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;

public class ChatClient {

    public static final int PORT = 7777;
    public static final String HOST = "192.168.0.56";

    public void run() {
        try {
            Socket socket = new Socket(HOST, PORT);

            // 서버에 로그인 시도
            BufferedReader clientReader = IoUtils.toBufferedReader(System.in);
            BufferedReader serverReader = IoUtils.toBufferedReader(socket.getInputStream());
            Writer serverWriter = IoUtils.toWriter(socket.getOutputStream());
            boolean isLogin = false;
            while (true) {
                try {
                    String loginLine = clientReader.readLine();
                    serverWriter.write(loginLine);
                    serverWriter.write('\n');

                    String response = serverReader.readLine();
                    if ("SUCCESS".equals(response)) {
                        isLogin = true;
                        break;
                    }

                    if ("FAIL".equals(response)) {
                        // 실패 시에는 다시 로그인을 시도한다.
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }

            if (!isLogin) {
                clientReader.close();
                socket.close();
                return;
            }

            //서버 입장멘트
            Writer clientWriter = IoUtils.toWriter(System.out);
            String enterMessage = serverReader.readLine();
            clientWriter.write(enterMessage);
            clientWriter.write('\n');


            //chat을 연다.
            Thread receiveChatThread = new Thread(()->{
                //서버에서 받는 챗
                try {
                    while (true) {
                        String message = serverReader.readLine();
                        if (message == null) {
                            break;
                        }
                        clientWriter.write(message);
                        clientWriter.write('\n');
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    // 여기에 socket close하면 빨간 줄 뜸... why??
                }
            });
            receiveChatThread.start();

            try {
                while (true) {
                    String message = clientReader.readLine();
                    if (message == null) {
                        break;
                    }
                    serverWriter.write(message);
                    serverWriter.write('\n');
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
                socket.close();
                clientWriter.close();
                clientReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

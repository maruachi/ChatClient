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
                return;
            }

            Writer clientWriter = IoUtils.toWriter(System.out);
            String message = serverReader.readLine();
            clientWriter.write(message);
            clientWriter.write('\n');

            //서버 입장멘트
            //chat을 연다.

            //서버로 보느는 챗

            //서버에서 받는 챗


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

package com.chatclient.dgyim;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class IoUtils {
    public static BufferedReader toBufferedReader(InputStream in) {
        InputStream bis = new BufferedInputStream(in, 8192);
        InputStreamReader reader = new InputStreamReader(bis, StandardCharsets.UTF_8);
        return new BufferedReader(reader);
    }

    public static Writer toWriter(OutputStream out) {
        OutputStream bos = new BufferedOutputStream(out, 8192);
        return new OutputStreamWriter(bos, StandardCharsets.UTF_8);
    }
}

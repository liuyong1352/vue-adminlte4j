package com.vue.adminlte4j.web;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Created by bjliuyong on 2018/3/29.
 */
public class ResponseFilterWrapper extends HttpServletResponseWrapper{

    private CharArrayWriter charArrayWriter = new CharArrayWriter();

    public ResponseFilterWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(charArrayWriter);

    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return super.getOutputStream();
    }




    public CharArrayWriter getCharWriter(){
        return charArrayWriter;
    }


}

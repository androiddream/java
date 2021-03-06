package com.jsoniter;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TestString extends TestCase {

    public void test_string() throws IOException {
        Jsoniter iter = Jsoniter.parse("'hello''world'".replace('\'', '"'));
        assertEquals("hello", iter.readString());
        assertEquals("world", iter.readString());
        iter = Jsoniter.parse("'hello''world'".replace('\'', '"'));
        assertEquals("hello", iter.readString());
        assertEquals("world", iter.readString());
    }

    public void test_base64() throws IOException {
        Jsoniter iter = Jsoniter.parse("'YWJj'".replace('\'', '"'));
        assertEquals("abc", new String(iter.readBase64()));
    }

    public void test_string_across_buffer() throws IOException {
        Jsoniter iter = Jsoniter.parse(new ByteArrayInputStream("'hello''world'".replace('\'', '"').getBytes()), 2);
        assertEquals("hello", iter.readString());
        assertEquals("world", iter.readString());
    }

    public void test_utf8() throws IOException {
        byte[] bytes = {'"', (byte) 0xe4, (byte) 0xb8, (byte) 0xad, (byte) 0xe6, (byte) 0x96, (byte) 0x87, '"'};
        Jsoniter iter = Jsoniter.parse(new ByteArrayInputStream(bytes), 2);
        assertEquals("中文", iter.readString());
    }

    public void test_normal_escape() throws IOException {
        byte[] bytes = {'"', (byte) '\\', (byte) 't', '"'};
        Jsoniter iter = Jsoniter.parse(new ByteArrayInputStream(bytes), 2);
        assertEquals("\t", iter.readString());
    }

    public void test_unicode_escape() throws IOException {
        byte[] bytes = {'"', (byte) '\\', (byte) 'u', (byte) '4', (byte) 'e', (byte) '2', (byte) 'd', '"'};
        Jsoniter iter = Jsoniter.parse(new ByteArrayInputStream(bytes), 2);
        assertEquals("中", iter.readString());
    }

    public void test_null_string() throws IOException {
        Jsoniter iter = Jsoniter.parse("null".replace('\'', '"'));
        assertEquals(null, iter.readString());
    }
}

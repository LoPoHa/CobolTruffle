package com.github.lopoha.coboltruffle.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

// todo: make class abstract and have multiple implementations: Number, String, ...
public class HeapPointer {
    final int position;
    final int length;
    final String defaultValue;
    private final List<Character> heap;

    public HeapPointer(int position, int length, List<Character> heap, String defaultValue) {
        this.position = position;
        this.length = length;
        this.heap = heap;
        this.defaultValue = defaultValue;
    }

    public void initialize() {
        if (defaultValue != null) {
            this.setValue(this.defaultValue);
        }
    }

    public String getValue() {
        StringBuilder value = new StringBuilder();
        for (int i = position; i < position + length; i++) {
            value.append(heap.get(i));
        }
        return value.toString();
    }

    public void setValue(String value) {
        // todo: check if the alignment etc. is correct
        // todo: is the default for number here space or zero?
        if (value.length() < this.length) {
            value = new String(new char[this.length - value.length()]).replace('\0', ' ') + value;
            for (int i = 0; i < value.length(); i++) {
                heap.set(position + i, value.charAt(i));
            }
        } else if (value.length() == this.length) {
            for (int i = 0; i < value.length(); i++) {
                heap.set(position + i, value.charAt(i));
            }
        } else {
            for (int i = 0; i < this.length; i++) {
                heap.set(position + i, value.charAt(i));
            }
        }
    }
}
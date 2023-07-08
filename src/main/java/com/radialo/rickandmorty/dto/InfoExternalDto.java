package com.radialo.rickandmorty.dto;

import lombok.Data;

@Data
public class InfoExternalDto {
    private int count;
    private int pages;
    private String next;
    private String prev;
}

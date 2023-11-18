package ru.skypro.homework.dto;

import lombok.Data;

import java.util.ArrayList;

/**
 * DTO предоставления всех объявлений
 */
@Data
public class AdsAll {
    private int count;
    private ArrayList<AdDto> result;
}

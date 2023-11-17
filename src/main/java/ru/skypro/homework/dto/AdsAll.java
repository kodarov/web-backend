package ru.skypro.homework.dto;

import lombok.Data;

import java.util.ArrayList;

/**
 * DTO предоставления всех объявлений
 */
@Data
public class AdsAll {
    int count;
    ArrayList<AdDto> result;
}
package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO предоставления всех объявлений
 */
@Data
public class AdsAll {
    private int count;
    private List<AdDto> results;
}

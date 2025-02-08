package uz.pdp.ecommer.dto;

import lombok.Value;

import java.util.Map;

@Value
public class OrderDTO {
    Map<Integer, Integer> products;
}

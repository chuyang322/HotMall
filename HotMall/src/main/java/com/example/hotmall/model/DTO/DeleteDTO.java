package com.example.hotmall.model.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeleteDTO {
    private List<Serializable> ids;
}

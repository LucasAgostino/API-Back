package com.api.api.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoDto {
    private Long idCarrito;
    private List<CarritoProductoDto> productos;
}

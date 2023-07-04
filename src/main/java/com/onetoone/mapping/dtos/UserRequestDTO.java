package com.onetoone.mapping.dtos;

import com.onetoone.mapping.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String name;
    private Address address;
}

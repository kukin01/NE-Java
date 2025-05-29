package com.nicoleneza.template.v1.dtos.request.user;

import com.nicoleneza.template.v1.dtos.request.auth.RegisterUserDTO;

public class CreateManagerDTO extends CreateAdminDTO {
    private String managerCreateCode;
    public CreateManagerDTO(RegisterUserDTO registerUserDTO , String adminCreateCode){
        this.setFirstName(registerUserDTO.getFirstName());
        this.setLastName(registerUserDTO.getLastName());
        this.setEmail(registerUserDTO.getEmail());
        this.setPhoneNumber(registerUserDTO.getPhoneNumber());
        this.setPassword(registerUserDTO.getPassword());
        this.managerCreateCode = managerCreateCode;
    }

    public CreateManagerDTO(RegisterUserDTO registerUserDTO ){
        this.setFirstName(registerUserDTO.getFirstName());
        this.setLastName(registerUserDTO.getLastName());
        this.setEmail(registerUserDTO.getEmail());
        this.setPhoneNumber(registerUserDTO.getPhoneNumber());
        this.setPassword(registerUserDTO.getPassword());
    }
}

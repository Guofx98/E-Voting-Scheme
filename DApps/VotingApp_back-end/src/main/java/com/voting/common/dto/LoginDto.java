package com.voting.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "ID不能为空")
    private String Id;
    @NotBlank(message = "密码不能为空")
    private String password;
}

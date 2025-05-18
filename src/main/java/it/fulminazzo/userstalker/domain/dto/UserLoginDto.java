package it.fulminazzo.userstalker.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDto {

    @Size(min = 3, max = 32, message = "User login username must be between 3 and 32 characters long")
    @NotBlank(message = "User login username must not be null nor empty")
    private String username;

    @NotBlank(message = "User login ip must not be null nor empty")
    private String ip;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @PastOrPresent(message = "User login date must be past or present")
    @NotNull(message = "User login date must not be null")
    private LocalDateTime loginDate;

}

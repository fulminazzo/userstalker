package it.fulminazzo.userstalker.mapper;

import it.fulminazzo.userstalker.domain.dto.UserLoginDto;
import it.fulminazzo.userstalker.domain.entity.UserLogin;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserLoginMapper {

    UserLogin entityToDto(UserLogin userLogin);

    UserLoginDto dtoToEntity(UserLogin userLogin);

}

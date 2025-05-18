package it.fulminazzo.userstalker.mapper;

import it.fulminazzo.userstalker.domain.dto.UserLoginDto;
import it.fulminazzo.userstalker.domain.entity.UserLogin;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * The mapper for {@link UserLogin}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserLoginMapper {

    /**
     * Maps {@link UserLogin} to {@link UserLoginDto}.
     *
     * @param entity the user login
     * @return the user login dto
     */
    UserLoginDto entityToDto(UserLogin entity);

    /**
     * Maps {@link UserLoginDto} to {@link UserLogin}.
     *
     * @param dto the user login dto
     * @return the user login
     */
    UserLogin dtoToEntity(UserLoginDto dto);

}

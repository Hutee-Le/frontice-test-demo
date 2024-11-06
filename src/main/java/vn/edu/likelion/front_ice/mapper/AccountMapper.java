package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import vn.edu.likelion.front_ice.dto.request.RegisterRequest;
import vn.edu.likelion.front_ice.dto.response.RegisterResponse;
import vn.edu.likelion.front_ice.entity.AccountEntity;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountEntity toAccount(RegisterRequest registerRequest);

    RegisterResponse toRegisterResponse(AccountEntity accountEntity);

}

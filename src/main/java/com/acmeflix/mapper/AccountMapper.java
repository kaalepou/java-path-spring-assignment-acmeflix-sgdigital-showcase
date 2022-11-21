package com.acmeflix.mapper;

import com.acmeflix.base.BaseMapper;
import com.acmeflix.domain.Account;
import com.acmeflix.transfer.resource.AccountResource;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CreditCardMapper.class, ProfileMapper.class})
public interface AccountMapper extends BaseMapper<Account, AccountResource> {

  AccountResource toResource(Account account);

  @InheritInverseConfiguration
  Account toDomain(AccountResource accountResource);
}

package com.service.accountservice;

import com.model.Account;
import com.model.Status;
import com.service.IGeneralService;

public interface IAccountService extends IGeneralService<Account> {
    Account findByUserName(String username);
}

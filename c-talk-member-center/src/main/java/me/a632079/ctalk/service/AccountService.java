package me.a632079.ctalk.service;

import me.a632079.ctalk.po.Account;


public interface AccountService {
    Account getAccount(Long accountId);
    Account getAccount(String username, String password);
    boolean addAccount(Account account);
    boolean updateAccount(Account account);
}

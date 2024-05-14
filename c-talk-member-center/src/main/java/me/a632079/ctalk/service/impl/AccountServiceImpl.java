package me.a632079.ctalk.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.po.Account;
import me.a632079.ctalk.repository.AccountRepository;
import me.a632079.ctalk.service.AccountService;
import me.a632079.ctalk.util.Argon2Util;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService
{
    private AccountRepository accountRepository;

    private final String COLLECTION_NAME = "accounts";

    private final Argon2Util argon2Util;

    @Override
    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public Account getAccount(String username, String password) {
        Account account = Account.builder().username(username).build();
        Account accountInDb = accountRepository.findOne(Example.of(account)).orElse(null);
        if (accountInDb == null || !argon2Util.verify(accountInDb.getPassword(), password)) {
            return null;
        }
        return accountInDb;
    }

    @Override
    public boolean addAccount(Account account) {
        try {
            accountRepository.insert(account);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    // TODO: update account
    public boolean updateAccount(Account account) {
        return true;
    }
}

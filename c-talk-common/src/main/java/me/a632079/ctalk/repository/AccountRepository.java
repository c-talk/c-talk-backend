package me.a632079.ctalk.repository;

import me.a632079.ctalk.po.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends MongoRepository<Account, Long> {
}

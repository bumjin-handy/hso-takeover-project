package com.hs.takeover.repository;

import com.hs.takeover.domain.TakeOver;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TakeOverRepository extends JpaRepository<TakeOver, String> {

}

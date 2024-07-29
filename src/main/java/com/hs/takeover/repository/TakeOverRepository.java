package com.hs.takeover.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hs.takeover.domain.TakeOver;


public interface TakeOverRepository extends JpaRepository<TakeOver, String> {

}

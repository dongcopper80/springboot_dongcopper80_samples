/*
 * The MIT License
 *
 * Copyright 2022 Nguyễn Thúc Đồng (dongcopper80).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.dongcopper80.mybatis.repository;

import com.dongcopper80.mybatis.model.Customer;
import com.dongcopper80.mybatis.repository.primary.CustomerPrimaryRepository;
import com.dongcopper80.mybatis.repository.second.CustomerSecondRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nguyễn Thúc Đồng (dongcopper80)
 */
@Repository
public class CustomerRepository implements CustomerCombineRepository {
    
    private final CustomerPrimaryRepository customerPrimaryRepository;
    private final CustomerSecondRepository customerSecondRepository;

    public CustomerRepository(
            CustomerPrimaryRepository customerPrimaryRepository, 
            CustomerSecondRepository customerSecondRepository) {
        
        this.customerPrimaryRepository = customerPrimaryRepository;
        this.customerSecondRepository = customerSecondRepository;
    }

    @Override
    public <S extends Customer> S save(S s) {
        return customerPrimaryRepository.save(s);
    }

    @Override
    public <S extends Customer> Iterable<S> saveAll(Iterable<S> iterable) {
        return customerPrimaryRepository.saveAll(iterable);
    }

    @Override
    public Optional<Customer> findById(UUID aLong) {
        return customerSecondRepository.findById(aLong);
    }

    @Override
    public boolean existsById(UUID aLong) {
        return customerSecondRepository.existsById(aLong);
    }

    @Override
    public Iterable<Customer> findAll() {
        return customerSecondRepository.findAll();
    }

    @Override
    public Iterable<Customer> findAllById(Iterable<UUID> iterable) {
        return customerSecondRepository.findAllById(iterable);
    }

    @Override
    public long count() {
        return customerSecondRepository.count();
    }

    @Override
    public void deleteById(UUID aLong) {
        customerPrimaryRepository.deleteById(aLong);
    }

    @Override
    public void delete(Customer customer) {
        customerPrimaryRepository.delete(customer);
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> iterable) {
        customerPrimaryRepository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        customerPrimaryRepository.deleteAll();
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> ids) {
        customerSecondRepository.deleteAllById(ids);
    }
}

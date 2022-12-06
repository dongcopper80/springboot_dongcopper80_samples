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
package com.dongcopper80.mysql.hibernate.services.impl;

import com.dongcopper80.mysql.hibernate.entities.Customer;
import com.dongcopper80.mysql.hibernate.repositories.CustomerRepository;
import com.dongcopper80.mysql.hibernate.services.CustomerService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nguyễn Thúc Đồng (dongcopper80)
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }
    
    @Override
    @Transactional
    public void init(){
        customerRepository.save(new Customer("dongcopper80", 
                "nguyenthucdong@gmail.com", "123456", "Nguyễn",
        "Thúc Đồng", "Tầng 1, tòa nhà Ladeco 266 Đội Cấn, phường Cống Vị, Ba Đình, Hà Nội",
        new Date(), new Date()));
    }
}

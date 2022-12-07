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
package com.dongcopper80.mysql.hibernate.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
 * to
 * ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COLLATE=utf8mb4_unicode_ci;
 * @author Nguyễn Thúc Đồng (dongcopper80)
 */
@Data
@Table(name = "sys_user", indexes = {
    @Index(name = "username_idx", columnList = "username", unique = true),
    @Index(name = "email_idx", columnList = "email", unique = true),
})
@EntityListeners(AuditingEntityListener.class)
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SysUser implements Serializable {

    @Id
    private Long id;
    
    @NotBlank
    @Size(max = 20)
    private String username;
    
    @NotBlank
    @Size(max = 50)
    private String password;
    
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    @Column(columnDefinition = "integer default 0")
    private Integer status;//0-active, 1: delete, 2: deactive

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",
            joinColumns = {
                @JoinColumn(name = "sys_user", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "sys_role", referencedColumnName = "id")}
    )
    private List<SysRole> sysRoles;
}

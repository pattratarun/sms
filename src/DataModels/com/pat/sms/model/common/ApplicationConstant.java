package com.pat.sms.model.common;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Tarun Pattra.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(
        name = "application_constant",
                indexes = @Index(name = "idx_constantKey", columnList = "constant_key", unique = true)
)
public class ApplicationConstant {
	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uid;
	
    @Column(name="constant_key", length = 200)
    private String constantKey;
    
    @Column(length = 1500)
    private String constantValue;

    @Column(length = 100)
    private String env;
    
}

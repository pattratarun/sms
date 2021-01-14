package com.pat.sms.model.user;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicUpdate
@Table(name = "user_login_log")
public class UserLoginLogs {

	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uid;
	
	@Column(length = 50)
	private String ipAddress;
	
	@Column(length = 50)
	private String maccAddress;
	
	@CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date loginTime;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date logoutTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User user;

}

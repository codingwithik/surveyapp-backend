package io.grupa.surveyapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length=32, nullable = false)
	private String name;
	private String uuid;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String role;
	@Column(length=32, nullable = false, unique = true)
	private String phoneNumber;

	@Column(nullable = false, columnDefinition="TEXT")
	private String address;
	private Boolean enable = true;
	
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	public User(User user) {
		this.id = user.id;
		this.name = user.name;
		this.email = user.email;
		this.password = user.password;
		this.phoneNumber = user.phoneNumber;
		this.role = user.role;
		this.address = user.address;
		this.uuid = user.uuid;
		this.enable = user.enable;
		
	}

	public User() {
	}

	@PrePersist
	private void setCreatedAt() {
		createdAt = new Date();
	}

	@PreUpdate
	private void setUpdatedAt() {
		updatedAt = new Date();
	}
	
	@Override
	public boolean equals(Object user) {
		return this.id.equals(((User)user).getId());
		
	}
	

}

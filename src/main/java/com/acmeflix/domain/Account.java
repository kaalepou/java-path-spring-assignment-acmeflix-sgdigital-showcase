package com.acmeflix.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNTS", indexes = {@Index(columnList = "email", unique = true)})
@SequenceGenerator(name = "idGenerator", sequenceName = "ACCOUNTS_SEQ", initialValue = 1, allocationSize = 1)
public class Account extends BaseModel {
	@Column(nullable = false, unique = true, length = 30)
	private String email = "xxx2@gmail.com";

	@Column(nullable = false, length = 30)
	private String password;

	@Column(name = "phone_number", length = 20)
	private String phoneNumber;

	@ManyToOne
	@JoinColumn(name = "subscription_plan_id", nullable = false)
	private SubscriptionPlan subscriptionPlan;

	@ToString.Exclude
	@Builder.Default
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<CreditCard> creditCards = new HashSet<>();

	@ToString.Exclude
	@Builder.Default
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Profile> profiles = new HashSet<>();

	public Profile getProfile(String name) {
		if (profiles.size() > 0) {
			return profiles.stream().filter(profile -> name.equalsIgnoreCase(profile.getName())).findFirst()
					.orElseThrow(() -> new IllegalArgumentException("No profiles were found with profile name: "+ name));
		}
		throw new NoSuchElementException("There are no profiles available!");
	}
}

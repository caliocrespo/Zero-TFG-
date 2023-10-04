package com.zero.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)

public class Developer extends DomainEntity{
	private String name;
	private String description;
	private Integer founded_year;
	
	@NotBlank
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Integer getFounded_year() {
		return founded_year;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setFounded_year(Integer founded_year) {
		this.founded_year = founded_year;
	}
}
package com.zero.domain;

import java.util.Collection;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)

public class Genre extends DomainEntity{
	private String name;
	private String slug;
	private String description;
	private Collection<Game> games;
	
	@NotBlank
	public String getName() {
		return name;
	}
	
	public String getSlug() {
		return slug;
	}
	@Column(length=65555)
	public String getDescription() {
		return description;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	//---------Relationships-------------------

	@ManyToMany
	public Collection<Game> getGames() {
		return games;
	}
	public void setGames(Collection<Game> games) {
		this.games = games;
	}
	
	
}

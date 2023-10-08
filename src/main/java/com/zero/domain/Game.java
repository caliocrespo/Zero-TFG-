package com.zero.domain;

import java.util.Collection;
import java.util.Date;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)

public class Game extends DomainEntity{
	private String name;
	private String description;
	private String image;
	//private Integer rate;
	private Date release_date;
	private Collection<Progress> progress;
	private Developer developer;
	private Collection<Genre> genres;
	private Collection<Platform> platforms;
	
	@NotBlank
	public String getTitle() {
		return name;
	}
	@NotBlank
	public String getDescription() {
		return description;
	}
	@URL
	public String getImage() {
		return image;
	}
	
	
	public Date getRelease_date() {
		return release_date;
	}
	public void setTitle(String title) {
		this.name = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}
	
	//---------Relationships-------------------
	
	@OneToMany(mappedBy="game")
	public Collection<Progress> getProgress() {
		return progress;
	}
	public void setProgress(Collection<Progress> progress) {
		this.progress = progress;
	}
	
	
	@ManyToOne(optional=false)
	public Developer getDeveloper() {
		return developer;
	}
	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}
	
	
	@ManyToMany(mappedBy="games")
	public Collection<Genre> getGenres() {
		return genres;
	}	
	public void setGenres(Collection<Genre> genres) {
		this.genres = genres;
	}
	
	
	@ManyToMany(mappedBy="games")
	public Collection<Platform> getPlatforms() {
		return platforms;
	}
	public void setPlatforms(Collection<Platform> platforms) {
		this.platforms = platforms;
	}
	
	
}

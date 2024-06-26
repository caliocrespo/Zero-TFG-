package com.zero.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zero.auxiliar.GenreAPI;
import com.zero.auxiliar.GenreList;
import com.zero.auxiliar.PlatformAPI;
import com.zero.auxiliar.PlatformList;
import com.zero.domain.Genre;
import com.zero.domain.Platform;
import com.zero.repository.PlatformRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PlatformService {
	
	private final String RAWG_URL= "https://api.rawg.io/api/platforms?key=e63df09f5ae744498fb5a5ee6d3ca236";
	
	@Autowired
	private final RestTemplate restTemplate;
	//Own Repository
	
	@Autowired
	private PlatformRepository platformRepository;
	
	//Others repositories/services
	
	//Constructor
	
	public PlatformService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	//Create method
	
	//Finds method
	
	public Platform findById(int id) {
		Platform result;
		
		result= this.platformRepository.findById(id);
		
		return result;
	}
	
	public Page<Platform> findAllPagining(Pageable paging) {
		Page<Platform> result;
		
		result = platformRepository.findAll(paging);
		
		return result;
	}
	
	//Others method
	
	public void getAPIPlatforms() {
		
		ResponseEntity<PlatformList> responseEntity = restTemplate.exchange(RAWG_URL,HttpMethod.GET,null,PlatformList.class);
		
		if(responseEntity.getStatusCode().is2xxSuccessful()) {
			PlatformList plataformList=responseEntity.getBody();
			
			if(plataformList != null && plataformList.getResults() != null) {
				List<PlatformAPI> platforms= plataformList.getResults();
				
				
				for(PlatformAPI pAPI : platforms) {
					
					if(platformRepository.findByName(pAPI.getName())==null) {
						
						String RAWG_URL2= "https://api.rawg.io/api/platforms/"+ pAPI.getId() +"?key=e63df09f5ae744498fb5a5ee6d3ca236";
						
						ResponseEntity<PlatformAPI> responseEntity2 = restTemplate.exchange(RAWG_URL2,HttpMethod.GET,null,PlatformAPI.class);
						
						PlatformAPI aux= responseEntity2.getBody();
						
						Platform addPlataform= new Platform();
						
						addPlataform.setIdRAWG(aux.getId());
						addPlataform.setName(aux.getName());
						addPlataform.setYear_start(aux.getYearStart());
						addPlataform.setDescription(aux.getDescription());
						addPlataform.setSlug(aux.getSlug());
						
						platformRepository.save(addPlataform);
					}
				}
			}
		}
	}

	public List<PlatformAPI> platformsApi() {
		List<PlatformAPI> platformsAPI = new ArrayList<>();
		
		Collection<Platform> platforms = this.platformRepository.findAll();
		
		for(Platform platform : platforms) {
			PlatformAPI p = new PlatformAPI();
			
			p.setId(platform.getId());
			p.setName(platform.getName());
			p.setDescription(platform.getDescription());
			p.setGamesCount(platform.getGames().size());
			
			platformsAPI.add(p);
		}
		
		return platformsAPI;
	}

	

}

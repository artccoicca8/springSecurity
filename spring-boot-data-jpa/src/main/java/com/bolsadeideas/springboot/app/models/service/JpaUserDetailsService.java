package com.bolsadeideas.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IUsuarioDao;
import com.bolsadeideas.springboot.app.models.entity.Role;
import com.bolsadeideas.springboot.app.models.entity.Usuario;


 /***se impleneta esta clase para la gestion del login*/
@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{

	
	
	@Autowired
	private IUsuarioDao usuarioDao ; 
	
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		/**  se busca el usuario en bd **/
		Usuario usuario= usuarioDao.findByUsername(username); 
		
		if (usuario==null) {
			logger.info("error no existe usuarios ");
			throw new UsernameNotFoundException("Username " +username+ " no existe en bd "); 
			
		}
		
		/*** creas una lista de los roles a los cuales se va apoder autorizar el acceoso  */
		  List<GrantedAuthority> authorities = new ArrayList <GrantedAuthority>();
		
		  /** se obtiene los rolses del ususaior
		   * y se va llenado la lista de authorities , para obtenre los roles de bd y llevarlos al contexto del spring  */
		  for(Role role: usuario.getRoles()) {
//	        	logger.info("Role: ".concat(role.getAuthority()));
	        	authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
	        }
		  
		  if (authorities.isEmpty()) {
				logger.info("error no existe roles asigando al ususario  ");
				throw new UsernameNotFoundException("Username " +username+ " no tiene roles  en bd "); 
		}
		  
		  
	        
		  /***se devuelve una instacia del ususario propio de spring , en la cual se adiciona los datos obtenidos de la tabla usuario , asi como 
		   * los roles propios*/
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

}

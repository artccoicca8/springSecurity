package com.bolsadeideas.springboot.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;


/**
 * Clase creada para enviar mensajes ade login , se registra como un bean 
 * 
 * **/
@Component
public class LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler  {

	/**
	 * Metodo sobreescrito este envia los mensajes , aquie se pueden mapear el todas alas acciones del login , si 
	 * son exitosos 
	 * 
	 * */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		 
		
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager(); 
		FlashMap flashMap = new FlashMap(); 
		flashMap.put("success", "holi "+authentication.getName()+" inicio  de session exitosa prro ");
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		if (authentication!=null) {
			logger.info("El ususrio "+authentication.getName() + "inicio session ");
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
	
}

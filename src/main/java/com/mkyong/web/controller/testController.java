package com.mkyong.web.controller;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mkyong.users.dao.UserDao;
import com.mkyong.users.dao.UserDaoImpl;
import com.mkyong.users.model.User;
import com.mkyong.users.model.UserRole;

@Controller
public class testController {
	
	@RequestMapping(value = { "/test" }, method = RequestMethod.GET)
	public ModelAndView testPage(Principal principal) {
		ModelAndView model = new ModelAndView();
		//model.setViewName("test");
		return model;
	}
	
	
	@RequestMapping(value = { "/test2" }, method = RequestMethod.GET)
	public ModelAndView test2Page(Principal principal) {
		ModelAndView model = new ModelAndView();
		//model.setViewName("test");
		return model;
	}
}

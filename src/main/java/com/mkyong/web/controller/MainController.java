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
public class MainController {

	@Autowired
	private UserDao userDao;

	/*
	 * @RequestMapping(value = { "/", "/welcome**" }, method =
	 * RequestMethod.GET) public ModelAndView defaultPage() {
	 * 
	 * ModelAndView model = new ModelAndView(); model.addObject("title",
	 * "NJDOE EdCode System"); model.addObject("message",
	 * "This is the Welcome Page"); model.setViewName("hello"); return model; }
	 */

	/*
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "NJDOE EdCode System");
		model.addObject("message", "This is the Welcome Page");
		model.setViewName("hello");
		return model;
	}

	*/

	/*
	 * @RequestMapping(value = { "/success" }, method = RequestMethod.GET)
	 * public ModelAndView successPage() {
	 * 
	 * ModelAndView model = new ModelAndView(); return model; }
	 * 
	 */

	@RequestMapping(value = { "/County" }, method = RequestMethod.GET)
	public ModelAndView countyPage(Principal principal) {

		ModelAndView model = new ModelAndView();

		final String loggedInUserName = principal.getName();

		model.addObject("userName", loggedInUserName);

		User user = userDao.findByUserName(loggedInUserName);
		model.addObject("thisuser", user.getLevel());

		/*
		 * UserDaoImpl dao = new UserDaoImpl(); User thisUser =
		 * dao.findByUserName(loggedInUserName);
		 * 
		 * if (thisUser.getLevel().equals("County")) { model.addObject("flag",
		 * "true"); } else { model.addObject("flag", "false"); }
		 */
		return model;
	}

	@RequestMapping(value = { "/District" }, method = RequestMethod.GET)
	public ModelAndView DistrictPage(Principal principal) {
		ModelAndView model = new ModelAndView();
		final String loggedInUserName = principal.getName();
		model.addObject("userName", loggedInUserName);
		User user = userDao.findByUserName(loggedInUserName);
		model.addObject("thisuser", user.getLevel());
		return model;
	}

	

	@RequestMapping(value = { "/DOE" }, method = RequestMethod.GET)
	public ModelAndView DOEPage(Principal principal) {
		ModelAndView model = new ModelAndView();
		
		final String loggedInUserName = principal.getName();
		model.addObject("userName", loggedInUserName);
		User user = userDao.findByUserName(loggedInUserName);
		model.addObject("thisuser", user.getLevel());
		
		return model;
	}

	@RequestMapping(value = { "/fail" }, method = RequestMethod.GET)
	public ModelAndView failPage() {

		ModelAndView model = new ModelAndView();
		return model;
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "NJDOE EdCode System");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");

		return model;

	}

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();

		List<String> UserRoleList = new ArrayList<String>();
		UserRoleList.add("County");
		UserRoleList.add("District");
		UserRoleList.add("DOE");
		UserRoleList.add("PO");

		model.addObject("UserRoleList", UserRoleList);
		model.addObject("UserRole", new UserRole());

		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;

	}

}
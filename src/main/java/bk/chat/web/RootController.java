package bk.chat.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class RootController {
	@RequestMapping("/")
	public String onRootAccess(Model model, Authentication authentication) {
		if (authentication!=null) {
			model.addAttribute("user", authentication.getName());
		}
		return "chatpage";
	}
}

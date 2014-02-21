package org.sicdlab.microlecture.common.Test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SensitiveWordsTest {
	@RequestMapping("sensitiveWordTest.htm")
	public ModelAndView sensitiveWord(HttpServletRequest request) throws Exception{
		String originContent = "他是一个中国共产党人，参加了十八大";
		return new ModelAndView("/admin/test","originContent",originContent);
	}
}

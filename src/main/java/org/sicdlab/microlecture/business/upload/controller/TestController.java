package org.sicdlab.microlecture.business.upload.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.sicdlab.microlecture.util.FileUploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.marswork.core.minitools.object.BasicUtils;
import com.marswork.websupport.common.jsonsupport.JSONOperator;
import com.marswork.websupport.servletsupport.common.RequestParam;

@Controller
public class TestController {
	
	
	@RequestMapping("test.htm")
	public ModelAndView test(){
		return new ModelAndView("/test/testxheditors");
	}
	
	@RequestMapping("delFile.htm")
	public void delFile(HttpServletRequest request, OutputStream out)
			throws Exception {
		FileUploadUtils.DelFile(request, request.getParameter("url"));
	}
	
	@RequestMapping("uploadFile.htm")
	public void uploadfile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		HashMap params = FileUploadUtils.UploadFile(request, response, "files");
		String responseInfo = JSONOperator.toJSON("{'err':'','msg':'"
				+ params.get("filedata") + "'}");
		response.getWriter().write(responseInfo);
	}

	@RequestMapping("uploadPic.htm")
	public void uploadpic(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		HashMap params = FileUploadUtils.UploadFile(request, response, "folder");
		JSONObject res=JSONObject.fromObject("{'err':'','msg':'"
				+ params.get("filedata") + "'}");
//		String responseInfo = JSONOperator.toJSON("{'err':'','msg':'"
//				+ params.get("filedata") + "'}");
		response.getWriter().write(res.toString());
	}
	
//	@RequestMapping("downloadFile.htm")
//	public void downloadFile(HttpServletRequest request,HttpServletResponse response){
//		String filePath=ServletRequestUtils.getStringParameter(request, "filePath", "");
//		try {
//			FileUploadUtils.downFile(request, response, filePath, null,true);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}

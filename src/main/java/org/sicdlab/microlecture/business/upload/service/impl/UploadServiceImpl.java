package org.sicdlab.microlecture.business.upload.service.impl;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.upload.service.UploadService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.HeadImage;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UploadServiceImpl extends BaseServiceImpl implements UploadService {

	@Override
	public void insertDB(String filename, String filetype, String fileUrl,
			String objectId) {
		// TODO Auto-generated method stub
		System.out.println("########@@@@@@@@@@@@@@@@@@@%%%%%%%%%");
		HeadImage head = new HeadImage();
        head.setImageId(UUIDGenerator.randomUUID());
		head.setImageLage(fileUrl);
		save(head);
		User user=(User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", objectId)).uniqueResult();
		user.setHeadImage(head);
		getCurrentSession().update(user);
	}

	@Override
	public String upLoad(HttpServletRequest request,HttpServletResponse response) {
		/*HttpServletRequest request = null;*/
		// 取出来的路径很长
		
		System.out.println("从这里开始");
		String uploadPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "upload";
		System.out.println(uploadPath);
		File folder = new File(uploadPath);

		String message = null;
		String uploadstr = null;   
		String str = "";
		String str1 = "";
		if (!folder.exists())
			folder.mkdirs();

		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			System.out.println(ServletFileUpload.isMultipartContent(request));
			if (true) {

				DiskFileItemFactory disk = new DiskFileItemFactory();
				disk.setSizeThreshold(20 * 1024);
				disk.setRepository(disk.getRepository());

				ServletFileUpload up = new ServletFileUpload(disk);
				int maxsize = 10 * 1024 * 1024;

				up.setFileSizeMax(5000000L);
				up.setSizeMax(10000000L);
				up.setHeaderEncoding("UTF-8");
				
				List list = up.parseRequest(request);
				Iterator i = list.iterator();
				
				while (i.hasNext()) {
					FileItem fm = (FileItem) i.next();
					
					if (!fm.isFormField()) {
						String filepath = fm.getName();
						System.out.println(filepath);
						String fileName = "";
						int startIndex = filepath.lastIndexOf("\\");
						if (startIndex != -1) {

							fileName = filepath.substring(startIndex + 1);
							
						} else {

							fileName = filepath;
							
						}
						
						if (fm.getSize() > maxsize) {

							message = "文件太大，不要超过2M";
							break;
						}
						if ((fileName == null || fileName.equals(""))
								&& (fm.getSize() == 0)) {

							message = "文件不能为空，大小不能为零";
							break;

						}
					

						// String name=fileName+new Date().getTime();
						File savefile = new File(uploadPath, fileName);

						fm.write(savefile);
						message = " 上传成功";

						String filename2 = savefile.getName();

						String fileURL = uploadPath + "\\" + fileName;
						
						String filename3 = filename2.substring(0,
								fileName.lastIndexOf("."));

						uploadstr = filename3 + ";" + fileURL;
						
						
					}else{
						
						String bb=fm.getString();
						String name = new String(bb.getBytes("ISO8859-1"),"UTF-8"); 
						str=str+";"+name;
						
					}
					
					

				}
				 str1=str;
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uploadstr+str;
	}

}
